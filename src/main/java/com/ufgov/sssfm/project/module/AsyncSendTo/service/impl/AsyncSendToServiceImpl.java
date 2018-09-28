package com.ufgov.sssfm.project.module.AsyncSendTo.service.impl;

import com.ufgov.sssfm.common.utils.nx.*;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgBig;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgSmall;
import com.ufgov.sssfm.common.utils.nx.bean.MsgHeaderParamBean;
import com.ufgov.sssfm.project.module.AsyncSendTo.service.AsyncSendToService;
import com.ufgov.sssfm.project.module.queryutils.bean.FmInterfaceUtils;
import com.ufgov.sssfm.project.module.queryutils.service.FmBankXmlLogService;
import com.ufgov.sssfm.project.module.queryutils.service.FmInterfaceUtilsService;
import com.ylzinfo.analysis.service.EsbQueryFactory;
import com.ylzinfo.analysis.service.QueryListService;
import com.ylzinfo.esb.bas.EsbException;
import com.ylzinfo.esb.bas.EsbResponse;
import com.ylzinfo.esb.client.XMLRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 异步发送数据去财政，业务，银行等的业务处理层
 * @Author xucy
 * @Date 2018/8/5
 **/
@Service("asyncSendToService")
public class AsyncSendToServiceImpl implements AsyncSendToService {

    @Autowired
    private FmInterfaceUtilsService fmInterfaceUtilsService;

    @Autowired
    private FmBankXmlLogService fmBankXmlLogService;

    public void init(){
        fmInterfaceUtilsService=(FmInterfaceUtilsService)SpringUtil.getBean("fmInterfaceUtilsService");
        fmBankXmlLogService=(FmBankXmlLogService)SpringUtil.getBean("fmBankXmlLogService");
    }
    @Async
    public String[] send_outcome_intcome_to_czsb(String msgContext,List<String> ossstrList, List<String> filePathList,String bse173){
        init();

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
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_intcome_to_czsb",bse173,""
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
        if(bse173.equals(NxConstants.JF07_WEB_SERVICE)){
            msgBig.setBse173(NxConstants.JF07_WEB_SERVICE);
            msgBig.setBse174(NxConstants.JF07_WEB_SERVICE);
        }else if(bse173.equals(NxConstants.AD68_WEB_SERVICE)){
            msgBig.setBse173(NxConstants.AD68_WEB_SERVICE);
            msgBig.setBse174(NxConstants.AD68_WEB_SERVICE);
        }
        msgBig.setAae036(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        msgBig.setAbe100("");
        msgBig.setMsgtype("");
        msgBig.setMsgcontent(msgContext);
        msgBig.setMd5msgcode(Md5Util.md5Password(msgContext));
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
            fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_intcome_to_czsb",bse173,""
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
        fmBankXmlLogService.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("send_outcome_intcome_to_czsb",bse173,""
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
