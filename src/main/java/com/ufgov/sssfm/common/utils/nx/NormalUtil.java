package com.ufgov.sssfm.common.utils.nx;

import com.ufgov.sssfm.project.module.queryutils.bean.FmBankXmlLog;
import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * 常见的一些工具类
 * 
 * @author Administrator
 *
 */
public class NormalUtil {
	/*
     * 获取项目的根目录
     * 因为tomcat和weblogic获取的根目录不一致，所以需要此方法
     */
    public static String getWebRootUrl(HttpServletRequest request){
        String fileDirPath = request.getSession().getServletContext().getRealPath("/");
        if(fileDirPath == null){
            //如果返回为空，则表示服务器为weblogic，则需要使用另外的方法
            try{
                return request.getSession().getServletContext().getResource("/").getFile();
            }catch(MalformedURLException e){
                System.out.println("获取项目的根目录出错！");
                throw new RuntimeException("获取项目的根目录出错！");
            }
        }else{
            return fileDirPath;
        }
    }

    public static String getWebRootUrlSpringboot(){
        File path=null;
        try{
            //获取跟目录
            path= new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");
            System.out.println("path:"+path.getAbsolutePath());
        }catch (FileNotFoundException fe){
            System.out.println("查找服务根路径失败");
            return "获取根路径失败";
        }
        return path.getAbsolutePath();
    }

    //得到日志对象工具类
    public  static FmBankXmlLog getFmBankXmlLog(String dataType, String senderId, String dataId,
                                                String errorMessage, String requestXml, String resultXml  ) {
        FmBankXmlLog log=new FmBankXmlLog();
        log.setDataId(dataId);
        log.setDataType(dataType);
        log.setSenderId(senderId);
        log.setErrorMessage(errorMessage);
        log.setRequestXml(requestXml);
        log.setResultXml(resultXml);
        log.setSendDate(new Date());
        log.setRecNo(new BigDecimal(1));
        log.setSerialNum(new BigDecimal(1));
        return log;
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应 的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}
