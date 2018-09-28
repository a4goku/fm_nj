package com.ufgov.sssfm.project.module.outcomeinfo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ufgov.sssfm.common.utils.nx.AnalysisMsgUtil;
import com.ufgov.sssfm.common.utils.nx.NormalUtil;
import com.ufgov.sssfm.common.utils.nx.NxConstants;
import com.ufgov.sssfm.common.utils.nx.OSSFileUtil;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgBig;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgSmall;
import com.ufgov.sssfm.common.utils.nx.bean.MsgHeaderParamBean;
import com.ufgov.sssfm.project.module.outcomeinfo.service.OutcomeService;
import com.ufgov.sssfm.project.module.queryutils.bean.FmInterfaceUtils;
import com.ufgov.sssfm.project.module.queryutils.service.FmBankXmlLogService;
import com.ufgov.sssfm.project.module.queryutils.service.FmInterfaceUtilsService;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 支出模块控制层
 * @Author xucy
 * @Date 2018/7/30 14:39
 **/
@RestController
@Api(value = "支出模块测试",tags = "支出模块测试")
@RequestMapping("outcomeinfo/OutcomeController")
public class OutcomeController {

    @Autowired
    private OutcomeService outcomeService;

    @Autowired
    private FmInterfaceUtilsService fmInterfaceUtilsService;

    @Autowired
    private FmBankXmlLogService fmBankXmlLogService;


    /** @Author xucy
     * @Description 征缴页面查询表格数据
     * @Date 11:03 2018/7/31
     * @Param  pageNumber 当前页, String pageSize页面大小,String AAE140险种
     *             ,String AAE035 拨付时间,String AAA079 拨付方式，AAE008 银行编码
     * @return
     **/
    @PostMapping("/query_jf07_pagedata")
    @ApiOperation(value="征缴页面查询表格数据", notes="征缴页面查询表格数据")
    private String  query_jf07_pagedata( String pageNumber, String pageSize,String AAE035,String AAE140,String AAA079,String AAE008,String PAYMENT_STATUS){

        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        int page=Integer.parseInt(pageNumber);
        int rows=Integer.parseInt(pageSize);
        int pageStart=(rows*(page-1));

        map.put("PAGESTART",pageStart );
        map.put("PAGESIZE", rows);
        map.put("AAE035",AAE035);
        map.put("AAE140",AAE140);
        map.put("AAE008",AAE008);
        map.put("AAA079",AAA079);
        map.put("PAYMENT_STATUS",PAYMENT_STATUS);
        List query_list= outcomeService.query_jf07_pagedata(map);
        int total=outcomeService.query_jf07_pagedata_total(map);
        jsonObject.put("total",total);
        jsonObject.put("result",query_list);
        return jsonObject.toString();
    }

    @PostMapping("/query_jf07_pagedata_item")
    @ApiOperation(value="征缴页面明细子表查询表格数据", notes="征缴页面明细子表查询表格数据")
    private String  query_jf07_pagedata_item( String pageNumber, String pageSize,String AAZ031){

        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        int page=Integer.parseInt(pageNumber);
        int rows=Integer.parseInt(pageSize);
        int pageStart=(rows*(page-1));

        map.put("PAGESTART",pageStart );
        map.put("PAGESIZE", rows);
        map.put("AAZ031", AAZ031);


        List query_list= outcomeService.query_jf07_pagedata_item(map);
        int total=outcomeService.query_jf07_pagedata_item_total(map);
        jsonObject.put("total",total);
        jsonObject.put("result",query_list);
        return jsonObject.toString();
    }

    @PostMapping("/query_jf07_pagedata_child")
    @ApiOperation(value="征缴页面明细孙子表查询表格数据", notes="征缴页面明细孙子表查询表格数据")
    private String  query_jf07_pagedata_child( String pageNumber, String pageSize,String AAZ220){

        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        int page=Integer.parseInt(pageNumber);
        int rows=Integer.parseInt(pageSize);
        int pageStart=(rows*(page-1));

        map.put("PAGESTART",pageStart );
        map.put("PAGESIZE", rows);
        map.put("AAZ220", AAZ220);


        List query_list= outcomeService.query_jf07_pagedata_child(map);
        int total=outcomeService.query_jf07_pagedata_child_total(map);
        jsonObject.put("total",total);
        jsonObject.put("result",query_list);
        return jsonObject.toString();
    }


    /* /** @Author xucy
     * @Description 发送财政：1.组织数据到本地路径。2.上传文件至OSS服务器。3.发送至财政社保端
     * @Date 17:57 2018/7/30
     * @Param 
     * @return 
     **/
    @PostMapping("/send_outcome_to_czsb")
    @ApiOperation(value="发送财政", notes="发送财政")
    private String send_outcome_to_czsb(String AAZ031Json){

        JSONObject jsonObject = new JSONObject();
        List listAAZ031 = JSONArray.parseArray(AAZ031Json);
        // 1.组织数据到本地路径存储
        List<String> filePathList= collageDataToLocal(listAAZ031);
        if(filePathList.size()==0 ||filePathList==null){
            jsonObject.put("result","组织数据到本地路径存储失败");
            return jsonObject.toString();
        }

        //2.将文件上传到OSS服务器上,返回一个ossstr 用于通知财政或其他系统  上OSS上拿文件
        List<String> ossstrList=uoloadFileToOSS(filePathList);
        if(ossstrList==null || ossstrList.size()==0 ){
            jsonObject.put("result","将文件上传到OSS服务器失败");
            return jsonObject.toString();
        }
        //3.带着ossstr通知财政
        String[] result=send_outcome_to_czsb(ossstrList,filePathList);

        //4.根据第三步返回的result[0]判断是否发送成功，发送成功，修改对应数据库中的发送状态
        try{
            if(result[0].equals("00")){
                //修改状态
                for(int i=0;i<listAAZ031.size();i++){
                    Map mapUpdate=new HashMap();
                    mapUpdate.put("AAZ031",listAAZ031.get(i));
                    outcomeService.updateGraSendStatusByPk(mapUpdate);
                }
            }
        }catch(RuntimeException ex){
            System.out.println("更新主表中发送状态报错");
            result[1]="更新主表中发送状态报错";
        }
        jsonObject.put("result",result[1]);
        return jsonObject.toString();
    }

    //1.组织数据到本地路径
    public List collageDataToLocal(List listAAZ031){

        //判断前台所选择的数据是否为空，为空直接返回Null
        if(listAAZ031==null || listAAZ031.size()<=0){
            //存日志记录
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","collageDataToLocal",""
                    ,"前台所选择的组织文件数据为空","",""));
            return null;
        }
        List dataList=null;

        List<String> filePathList=new ArrayList<String>();

        /**
         * size代表的是一个文件里面最多放多少条主表数据，就需要换文件。
         * start是从第几条数据开始，递增。
         * times 看一共循环 几次 生成多少个文件。
         */
        SimpleDateFormat sb=new SimpleDateFormat("yyMMdd");
        int start=0;
        int size=3;
        int times=0;
        if(listAAZ031.size()%size==0){
            times=	listAAZ031.size()/size;
        }else{
            times=	listAAZ031.size()/size+1;
        }
        for(int i=0;i<times;i++){
            dataList=new ArrayList();
            for(int j=start;j<((i+1)*size)&&j<listAAZ031.size();j++){
                dataList.add(listAAZ031.get(j));
            }

            Map map=new HashMap();
            map.put("AAZ031LIST", dataList);

            //选择本地路径存储生成文件（注：此路径必须OSS服务器能访问到）
            String fileName="RS"+sb.format(new Date())+"00"+i+".xml";
            map.put("path", NormalUtil.getWebRootUrlSpringboot()+"/static/upload/"+fileName);

            //组装业务文件
            String[] result=outcomeService.collageDataToLocal(map);
            if(result[0].equals("00")){
                filePathList.add(fileName);
            }else if(result[0].equals("01")){
                //插入日志表记录
                fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","collageDataToLocal",""
                        ,result[1],"",""));
                return null;
            }
            start+=size;
        }
        //插入日志表记录
        fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","collageDataToLocal",""
                ,"拼装文件成功","",""));
        return filePathList ;
    }

    //2.将文件上传到OSS服务器上,返回一个ossstr 用于通知财政或其他系统  上OSS上拿文件
    public List<String> uoloadFileToOSS(List<String> filePathList){

        //拼装上传文件报文头，并上传文件
        FmInterfaceUtils fmInterfaceUtils=getRequestParam("rs_cz","upload");

        List<String> ossstrList=new ArrayList<String>();

        for(int i=0;i<filePathList.size();i++){

            Map resultMap=OSSFileUtil.upload(fmInterfaceUtils,filePathList.get(i));

            if(resultMap.get("errorMsg")!=null){
                //插入日志表记录
                fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","uoloadFileToOSS",""
                        ,resultMap.get("errorMsg")+"",resultMap.get("requestXML")+"",resultMap.get("responseXML")+""));
                return null;
            }else{
                ossstrList.add(resultMap.get("ossstr")+"");
                //插入日志表记录
                fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","uoloadFileToOSS",""
                        ,resultMap.get("ossstr")+"",resultMap.get("requestXML")+"",resultMap.get("responseXML")+""));
            }
            //当上传成功之后 删除本地的文件
            try{
                    boolean isOk=NormalUtil.deleteFile(NormalUtil.getWebRootUrlSpringboot()+"/static/upload/"+filePathList.get(i));
            }catch (Exception e){
                e.printStackTrace();
                //插入日志表记录
                fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","uoloadFileToOSS",""
                        ,"上传成功后删除本地文件报错"+e.toString(),resultMap.get("requestXML")+"",resultMap.get("responseXML")+""));
                return null;
            }
        }

        return ossstrList;
    }

    //3.发送至财政社保端,带着ossstr通知财政
    public String[] send_outcome_to_czsb( List<String> ossstrList, List<String> filePathList ){
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
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","send_outcome_to_czsb",""
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
        msgBig.setBse173(NxConstants.JF07_WEB_SERVICE);
        msgBig.setBse174(NxConstants.JF07_WEB_SERVICE);
        msgBig.setAae036(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        msgBig.setAbe100("");
        msgBig.setMsgtype("2");
        msgBig.setMsgcontent("");
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
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","send_outcome_to_czsb",""
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
        fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_to_czsb","send_outcome_to_czsb",""
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

    //得到请求报文中的header信息，不对外暴露
    private FmInterfaceUtils getRequestParam(String transtowhere, String  usertowhere ){

        Map map=new HashMap();
        map.put("TRANSTOWHERE",transtowhere);
        map.put("USERTOWHERE",usertowhere);
        FmInterfaceUtils fmInterfaceUtils=fmInterfaceUtilsService.selectByPrimaryKey(map);

        return fmInterfaceUtils;
    }
}
