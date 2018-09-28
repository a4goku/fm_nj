package com.ufgov.sssfm.demoaxis2.web.impl;

import com.ufgov.sssfm.common.utils.nx.*;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgBig;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgSmall;
import com.ufgov.sssfm.demoaxis2.web.SiReceiveService;
import com.ufgov.sssfm.project.module.AsyncSendTo.service.AsyncSendToService;
import com.ufgov.sssfm.project.module.incomeinfo.entity.Ad68Fa;
import com.ufgov.sssfm.project.module.incomeinfo.entity.Ad68Son;
import com.ufgov.sssfm.project.module.incomeinfo.mapper.IncomeMapper;
import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07Fa;
import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07GraFa;
import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07Son;
import com.ufgov.sssfm.project.module.outcomeinfo.mapper.OutcomeMapper;
import com.ufgov.sssfm.project.module.queryutils.bean.FmInterfaceUtils;
import com.ufgov.sssfm.project.module.queryutils.mapper.FmBankXmlLogMapper;
import com.ufgov.sssfm.project.module.queryutils.mapper.FmInterfaceUtilsMapper;
import com.ylzinfo.esb.server.ReaderSoapXmlOut4NX;
import org.apache.axiom.om.OMElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 人社接收业务数据webservice
 * @Author xucy
 * @Date 2018/8/3
 **/
@Service("siReceiveService")
public class SiReceiveServiceImpl implements SiReceiveService {

    @Autowired
    private FmInterfaceUtilsMapper fmInterfaceUtilsMapper;

    @Autowired
    private FmBankXmlLogMapper fmBankXmlLogMapper;

    @Autowired
    private IncomeMapper incomeMapper;

    @Autowired
    private OutcomeMapper outcomeMapper;

    @Autowired
    private AsyncSendToService asyncSendToService;

    //业务平台通过webservice发送业务数据
    public String getYWDataRecieveMessage(OMElement element) {
        /**
         * 1、解析报文，读取数据,2、数据处理，业务流程
         *
         */
        AnalysisReceiveMsgBig analysisReceiveMsgBig=null;


        try{
            //得到请求报文中的参数
            analysisReceiveMsgBig=AnalysisMsgUtil.getRecieveMessage(element);
        }catch (Exception e){
            //插入日志表记录
            fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","getYWDataRecieveMessage",""
                    ,"解析业务方发送报文失败!"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

            return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！");
        }

        //得到业务代码bse173，根据业务代码判断是哪一种业务场景，分不同业务场景进行不同操作
        String bse173 = analysisReceiveMsgBig.getBse173();
        if(bse173.equals(NxConstants.AD68_WEB_SERVICE) || bse173.equals(NxConstants.JF07_WEB_SERVICE)){
            return ad68OrJf07Request(element,analysisReceiveMsgBig);
        }else{
            //插入日志表记录
            fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","getYWDataRecieveMessage",""
                    ,"没有对应匹配的业务代码!",element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "没有对应匹配的业务代码！")));
            return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "没有对应匹配的业务代码！");
        }

    }

    //收入要素支出要素接收
    public String ad68OrJf07Request(OMElement element,AnalysisReceiveMsgBig analysisReceiveMsgBig){

        //将业务传过来报文中的oss以及文件名存起来 用于自动转发财政时拼接报文
        List<String> ossstrList=new ArrayList();
        List<String> filePathList=new ArrayList();

        //得到业务代码bse173，根据业务代码判断是哪一种业务场景，分不同业务场景进行不同操作
        String bse173 = analysisReceiveMsgBig.getBse173();

        //得到报文业务要素内容，如果不为空，则解析内容入库
        String msgContext = analysisReceiveMsgBig.getMsgcontent();
        if(msgContext!=null && msgContext.length()>0){
            boolean bool=true;
            try {
                bool = Md5Util.md5Password(msgContext).equals(analysisReceiveMsgBig.getMd5msgcode());
            } catch (Exception e) {
                //插入日志表记录
                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","md5校验",""
                        ,"md5校验完整性的时候报错",element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "md5校验完整性的时候报错！")));

                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "md5校验完整性的时候报错");
            }

            if(bool==false){
                //插入日志表记录
                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","md5校验",""
                        ,"md5校验完整性失败",element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "md5校验完整性失败！")));

                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "md5校验完整性失败");
            }

            //解析入库
            if(NxConstants.AD68_WEB_SERVICE.equals(bse173)){
                long start = System.currentTimeMillis();
                Map mapResult = NxXmlUtil.xmlToBeanList(msgContext, Ad68Fa.class, NxConstants.AD68_SAX_HANDLER,true);
                if(mapResult.get("errorMsg")!=null){
                    //插入日志表记录
                    fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                            ,"此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+mapResult.get("errorMsg"),element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+mapResult.get("errorMsg"))));

                    return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+mapResult.get("errorMsg"));
                }else{
                    List<Ad68Fa> ad68FaList=(List<Ad68Fa>)mapResult.get("returnList");
                    for(int i=0;i<ad68FaList.size();i++){
                        Ad68Fa ad68Fa=ad68FaList.get(i);
                        try{
                            incomeMapper.insert_Fa(ad68Fa);
                        }catch (Exception e){
                            //插入日志表记录
                            fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                    ,"此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e)));

                            return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e);
                        }

                        for(int j=0;j<ad68Fa.getAd68SonList().size();j++){
                            Ad68Son ad68Son= ad68Fa.getAd68SonList().get(j);
                            try{
                                incomeMapper.insert_Son(ad68Son);
                            }catch (Exception e){
                                //插入日志表记录
                                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                        ,"此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e)));

                                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e);
                            }
                        }
                    }
                }
                long end = System.currentTimeMillis();
                System.out.println("共耗时："+(end-start)+"毫秒");

            }else if(NxConstants.JF07_WEB_SERVICE.equals(bse173)){
                long start = System.currentTimeMillis();
                Map mapResult = NxXmlUtil.xmlToBeanList(msgContext, Jf07GraFa.class, NxConstants.JF07_SAX_HANDLER,false);
                if(mapResult.get("errorMsg")!=null){
                    //插入日志表记录
                    fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                            ,"此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+mapResult.get("errorMsg"),element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                    return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+mapResult.get("errorMsg"));
                }else{
                    List<Jf07GraFa> jf07GraList=(List<Jf07GraFa>)mapResult.get("returnList");
                    for(int i=0;i<jf07GraList.size();i++){
                        Jf07GraFa jf07GraFa=jf07GraList.get(i);
                        try{
                            outcomeMapper.insert_Gra(jf07GraFa);
                        }catch (Exception e){
                            //插入日志表记录
                            fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                    ,"此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e)));

                            return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e);
                        }

                        List<Jf07Fa> jf07FaList =jf07GraFa.getJf07FaList();
                        for(int j=0;j<jf07FaList.size();j++){
                            Jf07Fa jf07Fa=jf07FaList.get(j);
                            try{
                                outcomeMapper.insert_Fa(jf07Fa);
                            }catch (Exception e){
                                //插入日志表记录
                                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                        ,"此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e)));

                                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e);
                            }

                            List<Jf07Son> Jf07SonList=jf07Fa.getJf07SonList();
                            for(int h=0;h<Jf07SonList.size();h++){
                                Jf07Son jf07Son=Jf07SonList.get(h);
                                try{
                                    outcomeMapper.insert_Son(jf07Son);
                                }catch (Exception e){
                                    //插入日志表记录
                                    fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                            ,"此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e)));

                                    return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此msgid："+analysisReceiveMsgBig.getMsgid()+"下载解析入库时报错:::"+e);
                                }
                            }
                            System.out.println(Jf07SonList);
                        }
                    }
                }
                long end = System.currentTimeMillis();
                System.out.println("共耗时："+(end-start)+"毫秒");
            }
        }


        //得到请求报文中的各个上传文件的信息
        List<AnalysisReceiveMsgSmall> listSmall=analysisReceiveMsgBig.getAnalysisReceiveMsgSmallList();

        //得到请求下载文件的报文头
        FmInterfaceUtils fmInterfaceUtils=getRequestParam("rs_cz", "download");

        for(int z=0;z<listSmall.size();z++){
            AnalysisReceiveMsgSmall analysisReceiveMsgSmall=new AnalysisReceiveMsgSmall();
            analysisReceiveMsgSmall=listSmall.get(z);

            //将报文中的关于文件信息存起来
            String ossstr=analysisReceiveMsgSmall.getOssstr();
            String filePath=analysisReceiveMsgSmall.getOssstr();

            ossstrList.add(ossstr);
            filePathList.add(filePath);

            //根据ossstr字符串去下载文件到本地
            Map mapFilePath= OSSFileUtil.download(ossstr,fmInterfaceUtils);
            String dowloadPath=mapFilePath.get("path")+"/"+analysisReceiveMsgSmall.getFjid();
            if(mapFilePath.get("errorMsg")!=null){
                //插入日志表记录
                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","download",""
                        ,"此ossstr："+ossstr+"下载时报错:::"+mapFilePath.get("errorMsg"),element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"下载时报错:::"+mapFilePath.get("errorMsg"));
            }
            boolean bool=true;
            try {
                bool = Md5Util.fileMD5(dowloadPath).equals(analysisReceiveMsgSmall.getMd5fjcode());
		    } catch (Exception e) {
                //插入日志表记录
                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","md5校验",""
                        ,"此ossstr："+ossstr+"md5校验完整性的时候报错",element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "md5校验完整性的时候报错！")));

                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"md5校验完整性的时候报错");
		    }

		    if(bool==false){
                //插入日志表记录
                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","md5校验",""
                        ,"此ossstr："+ossstr+"md5校验完整性失败",element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "md5校验完整性失败！")));

                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"md5校验完整性失败");
            }

            if(NxConstants.AD68_WEB_SERVICE.equals(bse173)){
                long start = System.currentTimeMillis();
                Map mapResult = NxXmlUtil.xmlToBeanList(dowloadPath, Ad68Fa.class, NxConstants.AD68_SAX_HANDLER,false);
                if(mapResult.get("errorMsg")!=null){
                    //插入日志表记录
                    fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                            ,"此ossstr："+ossstr+"下载解析入库时报错:::"+mapResult.get("errorMsg"),element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                    return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"下载解析入库时报错:::"+mapResult.get("errorMsg"));
                }else{
                    List<Ad68Fa> ad68FaList=(List<Ad68Fa>)mapResult.get("returnList");
                    for(int i=0;i<ad68FaList.size();i++){
                        Ad68Fa ad68Fa=ad68FaList.get(i);
                        try{
                            incomeMapper.insert_Fa(ad68Fa);
                        }catch (Exception e){
                            //插入日志表记录
                            fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                    ,"此ossstr："+ossstr+"入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                            return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"入库时报错:::"+e);
                        }

                        for(int j=0;j<ad68Fa.getAd68SonList().size();j++){
                            Ad68Son ad68Son= ad68Fa.getAd68SonList().get(j);
                            try{
                                incomeMapper.insert_Son(ad68Son);
                            }catch (Exception e){
                                //插入日志表记录
                                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                        ,"此ossstr："+ossstr+"入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"入库时报错:::"+e);
                            }
                        }
                    }
                }
                long end = System.currentTimeMillis();
                System.out.println("共耗时："+(end-start)+"毫秒");

            }else if(NxConstants.JF07_WEB_SERVICE.equals(bse173)){
                long start = System.currentTimeMillis();
                Map mapResult = NxXmlUtil.xmlToBeanList(dowloadPath, Jf07GraFa.class, NxConstants.JF07_SAX_HANDLER,false);
                if(mapResult.get("errorMsg")!=null){
                    //插入日志表记录
                    fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                            ,"此ossstr："+ossstr+"下载解析入库时报错:::"+mapResult.get("errorMsg"),element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                    return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"下载解析入库时报错:::"+mapResult.get("errorMsg"));
                }else{
                    List<Jf07GraFa> jf07GraList=(List<Jf07GraFa>)mapResult.get("returnList");
                    for(int i=0;i<jf07GraList.size();i++){
                        Jf07GraFa jf07GraFa=jf07GraList.get(i);
                        try{
                            outcomeMapper.insert_Gra(jf07GraFa);
                        }catch (Exception e){
                            //插入日志表记录
                            fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                    ,"此ossstr："+ossstr+"入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                            return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"入库时报错:::"+e);
                        }

                        List<Jf07Fa> jf07FaList =jf07GraFa.getJf07FaList();
                        for(int j=0;j<jf07FaList.size();j++){
                            Jf07Fa jf07Fa=jf07FaList.get(j);
                            try{
                                outcomeMapper.insert_Fa(jf07Fa);
                            }catch (Exception e){
                                //插入日志表记录
                                fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                        ,"此ossstr："+ossstr+"入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                                return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"入库时报错:::"+e);
                            }

                            List<Jf07Son> Jf07SonList=jf07Fa.getJf07SonList();
                            for(int h=0;h<Jf07SonList.size();h++){
                                Jf07Son jf07Son=Jf07SonList.get(h);
                                try{
                                    outcomeMapper.insert_Son(jf07Son);
                                }catch (Exception e){
                                    //插入日志表记录
                                    fmBankXmlLogMapper.insertFmBankXmlLog( NormalUtil.getFmBankXmlLog("getYWDataRecieveMessage","xmlToBeanList",""
                                            ,"此ossstr："+ossstr+"入库时报错:::"+e,element.toString(),ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "解析业务方发送报文失败！")));

                                    return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "此ossstr："+ossstr+"入库时报错:::"+e);
                                }
                            }
                            System.out.println(Jf07SonList);
                        }
                    }
                }
                long end = System.currentTimeMillis();
                System.out.println("共耗时："+(end-start)+"毫秒");
            }
        }
        /**
         * 3、启动线程，将业务要素自动转发财政
         *    通过对业务类别的判断，去拼装支出或者收入报文
         */

        asyncSendToService.send_outcome_intcome_to_czsb(msgContext,ossstrList,filePathList,bse173);


        /**
         * 4、返回处理结果信息
         */
        //TODO
        try {
            Map<String,String> paraMap=new HashMap<String, String>();
            String returnstr = ReaderSoapXmlOut4NX.buildSoapXMl4Single(paraMap);
            return returnstr;
        } catch (Exception e) {
            e.printStackTrace();
            return ReaderSoapXmlOut4NX.buildSoapXMl4Error(900, "构造返回报文出错！");
        }

    }

    //得到请求报文中的header信息，不对外暴露
    private FmInterfaceUtils getRequestParam(String transtowhere,String  usertowhere ){

        Map map=new HashMap();
        map.put("TRANSTOWHERE",transtowhere);
        map.put("USERTOWHERE",usertowhere);
        FmInterfaceUtils fmInterfaceUtils=fmInterfaceUtilsMapper.selectByPrimaryKey(map);

        return fmInterfaceUtils;
    }
}
