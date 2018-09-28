package com.ufgov.sssfm.project.module.fundApply.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ufgov.sssfm.common.utils.nx.AnalysisMsgUtil;
import com.ufgov.sssfm.common.utils.nx.NormalUtil;
import com.ufgov.sssfm.common.utils.nx.NxConstants;
import com.ufgov.sssfm.common.utils.nx.OSSFileUtil;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgBig;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgSmall;
import com.ufgov.sssfm.common.utils.nx.bean.MsgHeaderParamBean;
import com.ufgov.sssfm.project.module.files.domain.Files;
import com.ufgov.sssfm.project.module.files.service.IFilesService;
import com.ufgov.sssfm.project.module.fundApply.entity.FmBkApply;
import com.ufgov.sssfm.project.module.fundApply.service.FundApplyService;
import com.ufgov.sssfm.project.module.queryutils.bean.FmInterfaceUtils;
import com.ufgov.sssfm.project.module.queryutils.bean.FmSpDaily;
import com.ufgov.sssfm.project.module.queryutils.service.FmBankXmlLogService;
import com.ufgov.sssfm.project.module.queryutils.service.FmInterfaceUtilsService;
import com.ufgov.sssfm.project.module.queryutils.service.FmSpDailyService;
import com.ylzinfo.analysis.service.EsbQueryFactory;
import com.ylzinfo.analysis.service.QueryListService;
import com.ylzinfo.esb.bas.EsbException;
import com.ylzinfo.esb.bas.EsbResponse;
import com.ylzinfo.esb.client.XMLRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 生成用款单控制层
 * @Author xucy
 * @Date 2018/8/5
 **/
@RestController
@RequestMapping("fundApply/FundApplyController")
@Api(value = "生成用款单",tags = "生成用款单")
public class FundApplyController {

    @Autowired
    private FundApplyService fundApplyService;

    @Autowired
    private FmInterfaceUtilsService fmInterfaceUtilsService;

    @Autowired
    private FmBankXmlLogService fmBankXmlLogService;
    @Autowired
    private IFilesService filesService;

    @Autowired
    private FmSpDailyService fmSpDailyService;

    @PostMapping("/send_bkd_to_czsb")
    @ApiOperation(value = "发送拨款单去财政", notes="发送拨款单去财政")
    public String send_bkd_to_czsb(String bkdJson){
        JSONObject jsonObject=new JSONObject();
        //得到前台传过来的bkd主键id的集合
        if(bkdJson==null || (bkdJson.length()<=0)){
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_bkd_to_czsb","send_bkd_to_czsb",""
                    ,"前台传过来的数据为空","",""));
            jsonObject.put("result","前台传过来的数据为空");
            return jsonObject.toString();
        }
        List bkdList=JSONArray.parseArray(bkdJson);

        //1.根据拨款单Id去库里将拨款单的信息查询出来。
        for(int i=0;i<bkdList.size();i++){

            List<String> filePathList=new ArrayList();
            List<FmBkApply> fmBkApplyList=new ArrayList<>();
            //得到所有的附件以及生成的盖章Pdf
            List<Files> list = filesService.selectFilesListByBillId(bkdList.get(i)+"");
            FmBkApply fmBkApply=fundApplyService.selectBKApplyByPK(bkdList.get(i)+"");
            for(Files file:list){
                filePathList.add(file.getFileName());
            }
            fmBkApplyList.add(fmBkApply);


            //2.将文件上传到OSS服务器上,返回一个ossstr 用于通知财政或其他系统  上OSS上拿文件
            List<String> ossstrList=uoloadFileToOSS(filePathList);
            if(ossstrList==null || ossstrList.size()==0 ){
                jsonObject.put("result","将文件上传到OSS服务器失败");
                return jsonObject.toString();
            }

            //3.带着ossstr通知财政
            String[] result=send_bkd_to_czsb_1(ossstrList,filePathList,fmBkApplyList);

            //4.根据第三步返回的result[0]判断是否发送成功，发送成功，修改对应数据库中的发送状态
            try{
                if(result[0].equals("00")){
                    //修改状态
                    fundApplyService.updateBkdSendStatus(bkdList.get(i)+"");
                }
            }catch(RuntimeException ex){
                System.out.println("更新主表中发送状态报错");
                result[1]="更新主表中发送状态报错";
                fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_bkd_to_czsb","send_bkd_to_czsb",""
                        ,fmBkApplyList.get(0).getBkdId()+"更新主表中发送状态报错","",""));
                jsonObject.put("result",result[1]);
                return jsonObject.toString();
            }
        }

        jsonObject.put("result","发送成功");
        return jsonObject.toString();
    }

    //2.将文件上传到OSS服务器上,返回一个ossstr 用于通知财政或其他系统  上OSS上拿文件
    public List<String> uoloadFileToOSS(List<String> filePathList){

        //拼装上传文件报文头，并上传文件
        FmInterfaceUtils fmInterfaceUtils=getRequestParam("rs_cz","uploadpdf");

        List<String> ossstrList=new ArrayList<String>();

        for(int i=0;i<filePathList.size();i++){

            Map resultMap=OSSFileUtil.upload(fmInterfaceUtils,filePathList.get(i));

            if(resultMap.get("errorMsg")!=null){
                //插入日志表记录
                fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_bkd_to_czsb","uoloadFileToOSS",""
                        ,resultMap.get("errorMsg")+"",resultMap.get("requestXML")+"",resultMap.get("responseXML")+""));
                return null;
            }else{
                ossstrList.add(resultMap.get("ossstr")+"");
                //插入日志表记录
                fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_bkd_to_czsb","uoloadFileToOSS",""
                        ,resultMap.get("ossstr")+"",resultMap.get("requestXML")+"",resultMap.get("responseXML")+""));
            }
        }

        return ossstrList;
    }

    //3.发送至财政社保端,带着ossstr通知财政
    public String[] send_bkd_to_czsb_1( List<String> ossstrList,List<String> filePathList,List<FmBkApply> fmBkApplyList ){
        //返回数组结果,00代表发送成功，01代表发送失败
        String[] result=new String[2];

        FmInterfaceUtils fmInterfaceUtils=getRequestParam("rs_cz","sendrequest");

        //拼装报文header部分
        MsgHeaderParamBean headerParamBean=new MsgHeaderParamBean();
        headerParamBean.setEsbUrl(fmInterfaceUtils.getEsburl());
        headerParamBean.setEsbUserPwd(new String[]{fmInterfaceUtils.getEsbuserpwdUser(),fmInterfaceUtils.getEsbuserpwdPwd()});
        headerParamBean.setOrg(fmInterfaceUtils.getOrg());
        headerParamBean.setSys(fmInterfaceUtils.getSys());								//设置请求服务的机构编号
        headerParamBean.setVer(fmInterfaceUtils.getVer());							//设置请求服务的版本号D
        headerParamBean.setSvid(fmInterfaceUtils.getSvid());//测试消息联通性服务编号（用于测试联通性，测试用）

        if(ossstrList.size()!=filePathList.size()){
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_bkd_to_czsb","send_bkd_to_czsb",""
                    ,"文件数量和返回的ossstr加密串数量不相等","",""));
            result[0]="01";
            result[1]="文件数量和返回的ossstr加密串数量不相等";
            return result;
        }
        List<AnalysisReceiveMsgSmall> listSmall=new ArrayList<AnalysisReceiveMsgSmall>();
        for(int i=0;i<ossstrList.size();i++){
            AnalysisReceiveMsgSmall msgSmall=new AnalysisReceiveMsgSmall();
            msgSmall.setFjid(filePathList.get(i));
            msgSmall.setMd5fjcode("");
            msgSmall.setOssstr(ossstrList.get(i));
            msgSmall.setFjname(filePathList.get(i));
            msgSmall.setFjtype("1");
            listSmall.add(msgSmall);
        }
        //拼装报文消息部分
        AnalysisReceiveMsgBig msgBig =new AnalysisReceiveMsgBig();
        msgBig.setMsgid((UUID.randomUUID()+"").replaceAll("-",""));
        msgBig.setOldmsgid("");
        msgBig.setSenderno(NxConstants.RS_SEND_BH);
        msgBig.setRecieverno(NxConstants.CZ_RECIEVE_BH);
        msgBig.setBse173(NxConstants.BKD_WEB_SERVICE);
        msgBig.setBse174(NxConstants.BKD_WEB_SERVICE);
        msgBig.setAae036(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        msgBig.setAbe100("");
        msgBig.setMsgtype("1");
        //拼装拨款单发送报文
        String[] resultBkd=AnalysisMsgUtil.CompleteBkdMsg(fmBkApplyList,FmBkApply.class);
        //判断是否为
        if(!resultBkd[0].equals("00")){
            //插入日志表记录
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_bkd_to_czsb","send_bkd_to_czsb",""
                    ,"send_bkd_to_czsb拼装发送报文的时候报错","",""));

            result[0]="01";
            result[1]="send_bkd_to_czsb拼装发送报文的时候报错";
            return result;
        }
        msgBig.setMsgcontent(resultBkd[1]);
        msgBig.setMd5msgcode("");
        msgBig.setFjnum(ossstrList.size()+"");
        msgBig.setAnalysisReceiveMsgSmallList(listSmall);

        XMLRequest xmlRequest=AnalysisMsgUtil.getSendXMLRequest(headerParamBean, msgBig);

        String requestXML="";
        try{
            requestXML=xmlRequest.genRequestMessage(fmInterfaceUtils.getEsbuserpwdUser(),fmInterfaceUtils.getEsbuserpwdPwd())+"";
        }catch (EsbException e){
            requestXML="获取请求报文失败";
        }
        EsbResponse esbRsp = xmlRequest.postXmlRequest();		//发送SOAP数据，并获取响应
        String ls_return= esbRsp.getResponseData(); 			//读取响应报文内容




        EsbQueryFactory esbServiceFactory = new EsbQueryFactory();	    //new 一个xml报文结果解释对象
        QueryListService service = null;
        try {
            service = esbServiceFactory.getServiceResult(ls_return, QueryListService.class);
        } catch (EsbException e) {
            // TODO 报文解释异常
            e.printStackTrace();
            //插入日志表记录
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_bkd_to_czsb","send_bkd_to_czsb",""
                    ,"解析发送财政报文中的service失败",requestXML,ls_return));

            result[0]="01";
            result[1]="解析发送财政报文中的service失败";
            return result;
        }

        System.out.println("响应报文:\n"+ls_return);

        String msg ="";
        boolean isok=false;
        //读取响应结果
        if(null != service){
            /**
             * 1、读取报文通用响应状态信息
             */
            //读取报文响应状态 true 成功，FALSE失败
            isok= service.isOK();
            System.out.println("响应结果："+isok);

            //读取响应编码 000.000.000表示成功，其它表示失败
            String code = service.getFaultCode();
            System.out.println("响应编码："+code);

            //读取响应说明，读取由服务提供方反馈的响应结果描述
            msg = service.getFaultString();
            System.out.println("响应描述："+msg);

        }

        //插入日志表记录
        fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_bkd_to_czsb","send_bkd_to_czsb",""
                ,msg,requestXML,ls_return));
        if(isok){
            result[0]="00";
            result[1]=msg;
        }else{
            result[0]="01";
            result[1]=msg;
        }

        return result;

    }

    @PostMapping("/insert_FmBkApply")
    public String insert_FmBkApply(String bkdJson){
        JSONObject jsonObject=new JSONObject();
        try{
            FmBkApply fmBkApply = JSON.parseObject(bkdJson,FmBkApply.class);
            //判断是否存在拨款单Id,如果存在，则先删除，后插入。不存在，直接插入
            if((fmBkApply.getBkdId().length()<=0)){
                fmBkApply.setBkdId((UUID.randomUUID()+"").replaceAll("-",""));
                fundApplyService.insert_FmBkApply(fmBkApply);
            }else{
                //删除
                fundApplyService.deleteBKApplyByPK(fmBkApply.getBkdId());
                //插入
                fundApplyService.insert_FmBkApply(fmBkApply);
            }

        }catch (Exception e){
            jsonObject.put("result","插入数据库失败");
            return jsonObject.toString();
        }

        jsonObject.put("result","生成拨款单成功");
        return jsonObject.toString();

    }

    //编辑查出单条拨款申请单
    @PostMapping("/selectBKApplyByPK")
    public String selectBKApplyByPK(String bkdId){

        FmBkApply fmBkApply=fundApplyService.selectBKApplyByPK(bkdId);
        JSONObject jsonObject=(JSONObject)JSON.toJSON(fmBkApply);

        return jsonObject.toString();
    }

    //根据时间查出所有的拨款申请单
    @PostMapping("/selectAllBkApplyTime")
    public String selectAllBkApplyTime(String timeStart,String timeEnd,String send_status,String sp_status){
        JSONObject jsonObject=new JSONObject();

        Map map=new HashMap();
        map.put("send_status",send_status);
        map.put("sp_status",sp_status);

        List resultList=fundApplyService.selectAllBkApplyTime(map);

        jsonObject.put("result",resultList);
        return jsonObject.toString();
    }

    //根据bkd_id删除拨款申请单
    @PostMapping("/deleteBKApplyByPK")
    public String deleteBKApplyByPK(String bkdId){
        JSONObject jsonObject=new JSONObject();

        try {
            fundApplyService.deleteBKApplyByPK(bkdId);
        }catch (Exception e){
            jsonObject.put("result","删除失败");
            return jsonObject.toString();
        }
        jsonObject.put("result","删除成功");
        return jsonObject.toString();
    }


    //修改拨款单的审批状态
    @PostMapping("/updateBkdSpStatus")
    public String updateBkdSpStatus(String bkdId,String sp_status,String sp_name,String sp_status_name){
        JSONObject jsonObject=new JSONObject();
        Map queryMap=new HashMap();
        queryMap.put("bkdId",bkdId);
        queryMap.put("sp_status",sp_status);
        queryMap.put("sp_name",sp_name);
        FmSpDaily fmSpDaily=new FmSpDaily();
        fmSpDaily.setId(bkdId);
        fmSpDaily.setCzFs(sp_status_name);
        fmSpDaily.setCzPeople(sp_name);
        fmSpDaily.setCzTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        fmSpDaily.setSpBusiness("fundApply");
        try{
            fundApplyService.updateBkdSpStatus(queryMap);
            fmSpDailyService.insert(fmSpDaily);
        }catch (Exception e){
            jsonObject.put("result",sp_status_name+"失败");
            return jsonObject.toString();
        }
        jsonObject.put("result",sp_status_name+"成功");
        return jsonObject.toString();
    }

    //查看审批日志
    @PostMapping("/query_sp_daily")
    public String query_sp_daily(String bkdId){
        JSONObject jsonObject=new JSONObject();
        Map selectMap=new HashMap();
        selectMap.put("id",bkdId);
        selectMap.put("spBusiness","fundApply");
        List<FmSpDaily> list=null;
        try{
            list=fmSpDailyService.select(selectMap);
        }catch(Exception e){
            jsonObject.put("result","查询失败");
            return jsonObject.toString();
        }
        jsonObject.put("result",list);
        return jsonObject.toString();
    }

    //生成拨款单pdf,保存到本地
    @PostMapping("/createBKpdfToLocal")
    public String createBKpdfToLocal(String base64Pdf,String bkdId){
        JSONObject jsonObject=new JSONObject();
        if (base64Pdf == null) {
            jsonObject.put("result","生成PDF失败");
            return jsonObject.toString();
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(base64Pdf);
            for (int i = 0; i < b.length; ++i) {
                // 调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String imgFilePath = NormalUtil.getWebRootUrlSpringboot()+"/static/upload/bkdpdf/"+bkdId+".pdf";

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("生成PDF失败");
            jsonObject.put("result","生成PDF失败");
            return jsonObject.toString();
        }
        //生成pdf之后，修改fm_bk_apply表中的pdf_address的地址 ，将生成Pdf的地址存起来。
        try{
            Map updateMap=new HashMap();
            updateMap.put("bkdId",bkdId);
            updateMap.put("fileName",bkdId+".pdf");
            fundApplyService.updateBkdPdfAddress(updateMap);

            //存到附件表中
            Files files = new Files();
            String fileName = bkdId+".pdf";
            //获取文件类型
            String suffix = "pdf";
            files.setFileId(UUID.randomUUID().toString().trim().replace("-", ""));
            files.setFilePath(bkdId+".pdf");
            files.setFileName(fileName);
            files.setFileType(suffix);
            files.setCreatTime(new Date());
            files.setBillId(bkdId);
            filesService.insertFiles(files);
        }catch (Exception e){
            System.out.println("修改库中PDF地址失败");
            jsonObject.put("result","修改库中PDF地址失败");
            return jsonObject.toString();
        }

        jsonObject.put("result","生成PDF成功");
        return jsonObject.toString();

    }

    //得到请求报文中的header信息，不对外暴露
    private FmInterfaceUtils getRequestParam(String transtowhere, String  usertowhere ){

        Map map=new HashMap();
        map.put("TRANSTOWHERE",transtowhere);
        map.put("USERTOWHERE",usertowhere);
        FmInterfaceUtils fmInterfaceUtils=fmInterfaceUtilsService.selectByPrimaryKey(map);

        return fmInterfaceUtils;
    }
}
