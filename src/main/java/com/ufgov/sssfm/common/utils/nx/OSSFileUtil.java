package com.ufgov.sssfm.common.utils.nx;

import com.ufgov.sssfm.project.module.queryutils.bean.FmInterfaceUtils;
import com.ylzinfo.analysis.service.EsbQueryFactory;
import com.ylzinfo.analysis.service.QueryListService;
import com.ylzinfo.esb.bas.EsbException;
import com.ylzinfo.esb.bas.EsbResponse;
import com.ylzinfo.esb.client.XMLRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author firmboy
 * @create 2018-06-26 下午11:24
 **/
public class OSSFileUtil {



    static {
        //通过SpringUtil获取dao类，进而获取部分配置数据
    }

    /**
     *
     * @param file  要上传文件
     * @param fileName 上传文件名
     * @return 一个已经上传到平台oss的地址，需要填写到业务报文中
     */
    public static Map<String,String> upload(FmInterfaceUtils fmInterfaceUtils,String fileName){
        Map resultMap=new HashMap();

        //获取文件服务器的地址
    	String path = fmInterfaceUtils.getPath()+"/"+fileName;
       
        XMLRequest xmlRequest = new XMLRequest();									 //new一个客户端请求对象
	    xmlRequest.setEsbUrl(fmInterfaceUtils.getEsburl());		//人社政务网服务地址（调用方固定传入）
	    xmlRequest.setEsbUserPwd(new String[]{fmInterfaceUtils.getEsbuserpwdUser(),fmInterfaceUtils.getEsbuserpwdPwd()}); 		//设置请求服务的用户id（身份证号码）,密码串
	    xmlRequest.setSys(fmInterfaceUtils.getSys()); 								//设置请求服务的机构编号CZSYSTEM  RSSYSTEM
	    xmlRequest.setVer(fmInterfaceUtils.getVer());							//设置请求服务的版本号D
	     
		xmlRequest.setSvid(fmInterfaceUtils.getSvid());//cn.nxhrss.z.sbcx.ybycxdyffcx//cn.nxhrss.a.login
		xmlRequest.setParam(new String[] {"bucketName","urlstr","key"});
		xmlRequest.setParamValue(new String[] {fmInterfaceUtils.getBacketname(),path,fileName});

		//将发送报文放到结果集合中
        String requestXML="";
        try{
            requestXML=xmlRequest.genRequestMessage(fmInterfaceUtils.getEsbuserpwdUser(),fmInterfaceUtils.getEsbuserpwdPwd())+"";
        }catch (EsbException e){
            requestXML="获取请求报文失败";
        }
		resultMap.put("requestXML",requestXML);
        EsbResponse esbRsp = xmlRequest.postXmlRequest();		//发送SOAP数据，并获取响应
        String ls_return= esbRsp.getResponseData(); 			//读取响应报文内容
        //将接收报文放到结果集合中
        resultMap.put("responseXML",ls_return);

        EsbQueryFactory esbServiceFactory = new EsbQueryFactory();	    //new 一个xml报文结果解释对象
        QueryListService service = null;

        try {
            service = esbServiceFactory.getServiceResult(ls_return, QueryListService.class);
        } catch (EsbException e) {
            // TODO 报文解释异常
            e.printStackTrace();
            resultMap.put("errorMsg","获取返回报文中的service失败");
            return resultMap;
        }


        System.out.println("响应报文:\n"+ls_return);

        String msg="";
        String ossstr="";
        //读取响应结果
  		if(null != service){
  			/**
  			 * 1、读取报文通用响应状态信息
  			 */			
  			//读取报文响应状态 true 成功，FALSE失败
  			boolean isok = service.isOK();
  			System.out.println("响应结果："+isok);
            //读取响应编码 000.000.000表示成功，其它表示失败
            String code = service.getFaultCode();
            System.out.println("响应编码："+code);

            //读取响应说明，读取由服务提供方反馈的响应结果描述
            msg = service.getFaultString();
            System.out.println("响应描述："+msg);
  			if(isok){
                Map map=service.getResultMap();
                ossstr=map.get("information")+"";
                System.out.println("map："+map.get("information"));
            }else{
                resultMap.put("errorMsg",code+":::"+msg);
  			    return resultMap;
            }
  		}
        //如果通讯成功，并上传成功才会生成ossstr
  		resultMap.put("ossstr",ossstr);
  		return resultMap;
    }

    /**
     *
     * @param ossstr 报文中获取的文件秘钥
     * @param path 要下载到文件夹路径
     * @return 下载下来的文件的绝对路径
     */
    public static Map download(String ossstr,FmInterfaceUtils fmInterfaceUtils){
        //返回结果
        Map resultMap=new HashMap();

        XMLRequest xmlRequest = new XMLRequest();
        xmlRequest.setEsbUrl(fmInterfaceUtils.getEsburl());		//人社政务网服务地址（调用方固定传入）
        xmlRequest.setEsbUserPwd(new String[]{fmInterfaceUtils.getEsbuserpwdUser(),fmInterfaceUtils.getEsbuserpwdPwd()}); 		//设置请求服务的用户id（身份证号码）,密码串
        xmlRequest.setSys(fmInterfaceUtils.getSys());								//设置请求服务的机构编号
        xmlRequest.setVer(fmInterfaceUtils.getVer());

        xmlRequest.setSvid(fmInterfaceUtils.getSvid());//测试消息联通性服务编号（用于测试联通性，测试用）
        /**
         * 1、设置非批量部分参数集合
         *
         */
        //********************该服务只用于测试联通性，入参为非批量参数，需要注销2批量参数部分****************//
        xmlRequest.setParam(new String[]{"ossstr"});		//设置请求参数para部分（参数名）
        xmlRequest.setParamValue(new String[]{ossstr});		//设置请求参数para部分（参数值）


        //将发送报文放到结果集合中
        String requestXML="";
        try{
            requestXML=xmlRequest.genRequestMessage(fmInterfaceUtils.getEsbuserpwdUser(),fmInterfaceUtils.getEsbuserpwdPwd())+"";
        }catch (EsbException e){
            requestXML="获取请求报文失败";
        }
        resultMap.put("requestXML",requestXML);
        EsbResponse esbRsp = xmlRequest.postXmlRequest();		//发送SOAP数据，并获取响应
        String ls_return= esbRsp.getResponseData(); 			//读取响应报文内容
        //将接收报文放到结果集合中
        resultMap.put("responseXML",ls_return);


        EsbQueryFactory esbServiceFactory = new EsbQueryFactory();	    //new 一个xml报文结果解释对象
        QueryListService service = null;

        try {
            service = esbServiceFactory.getServiceResult(ls_return, QueryListService.class);
        } catch (EsbException e) {
            // TODO 报文解释异常
            e.printStackTrace();
            resultMap.put("errorMsg","获取返回报文中的service失败");
            return resultMap;
        }


        System.out.println("响应报文:\n"+ls_return);


        String msg="";
        String url="";
        //读取响应结果
        if(null != service){
            /**
             * 1、读取报文通用响应状态信息
             */
            //读取报文响应状态 true 成功，FALSE失败
            boolean isok = service.isOK();
            System.out.println("响应结果："+isok);

            //读取响应编码 000.000.000表示成功，其它表示失败
            String code = service.getFaultCode();
            System.out.println("响应编码："+code);

            //读取响应说明，读取由服务提供方反馈的响应结果描述
            msg = service.getFaultString();
            System.out.println("响应描述："+msg);

            if(isok){
                Map map=service.getResultMap();
                url=map.get("information")+"";
                System.out.println("map："+map.get("information"));
            }else{
                resultMap.put("errorMsg",code+":::"+msg);
                return resultMap;
            }

        }

        //需要UrlEncode解码
        String downLoadPath =getURLDecoderString(url);
        //获取到下载路径，下载文件
        File file = saveUrlAs(downLoadPath, fmInterfaceUtils.getDownloadpath());
        //判斷是否下載文件成功
        if(file!=null){
            resultMap.put("path",fmInterfaceUtils.getDownloadpath());
            return resultMap;
        }else {
            resultMap.put("errorMsg","从OSS上下载文件到本地失败");
            return resultMap;
        }
    }


    /**
     *
     * @param urlPath
     *            下载路径
     * @param downloadDir
     *            下载存放目录
     * @return 返回下载文件
     */
    public static File downloadFile(String urlPath, String downloadDir) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            //httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
//            int fileLength = httpURLConnection.getContentLength();

            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

//            System.out.println("file length---->" + fileLength);

            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return file;
        }

    }

    /** 
     * @功能 下载临时素材接口 
     * @param filePath 文件将要保存的目录 
     * @param method   请求方法，包括POST和GET
     * @param url 请求的路径 
     * @return 
     */ 
    public static File saveUrlAs(String url,String filePath){ 
        //System.out.println("fileName---->"+filePath); 
        //创建不同的文件夹目录 
        File file=new File(filePath); 
        //判断文件夹是否存在 
        if (!file.exists()) 
       { 
           //如果文件夹不存在，则创建新的的文件夹 
            file.mkdirs(); 
       } 
        FileOutputStream fileOut = null; 
        HttpURLConnection conn = null; 
        InputStream inputStream = null; 
        try 
       { 
            // 建立链接 
            URL httpUrl=new URL(url); 
            conn=(HttpURLConnection) httpUrl.openConnection(); 
            //以Post方式提交表单，默认get方式 
            conn.setRequestMethod("GET"); 
            conn.setDoInput(true);   
            conn.setDoOutput(true); 
            // post方式不能使用缓存  
            conn.setUseCaches(false); 
            //连接指定的资源  
            conn.connect(); 
            //获取网络输入流 
            inputStream=conn.getInputStream(); 
            BufferedInputStream bis = new BufferedInputStream(inputStream); 
            //判断文件的保存路径后面是否以/结尾 
            if (!filePath.endsWith("/")) { 
      
                filePath += "/"; 
      
                } 
            
            //得到文件名
            String filePathUrl = conn.getURL().getFile();
            filePathUrl=filePathUrl.substring(0,filePathUrl.lastIndexOf("?"));
            int i=filePathUrl.lastIndexOf(File.separatorChar)==-1?filePathUrl.lastIndexOf("/"):filePathUrl.lastIndexOf(File.separatorChar);
            filePathUrl=filePathUrl.substring(i+1,filePathUrl.length());
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称） 
            fileOut = new FileOutputStream(filePath+filePathUrl); 
            BufferedOutputStream bos = new BufferedOutputStream(fileOut); 
               
            byte[] buf = new byte[4096]; 
            int length = bis.read(buf); 
            //保存文件 
            while(length != -1) 
            { 
                bos.write(buf, 0, length); 
                length = bis.read(buf); 
            } 
            bos.close(); 
            bis.close(); 
            conn.disconnect(); 
       } catch (Exception e)
       { 
            e.printStackTrace(); 
            System.out.println("抛出文件下載异常！！");
            return null;
       } 
           
        return file; 
           
    }
    //对请求到的文件url进行解码
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "GBK");
            System.out.println(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
