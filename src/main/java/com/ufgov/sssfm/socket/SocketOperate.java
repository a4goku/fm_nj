package com.ufgov.sssfm.socket;

import com.ufgov.sssfm.socket.constant.Constant;
import com.ufgov.sssfm.socket.dao.BaseDAO;
import com.ufgov.sssfm.socket.utils.FtpUtils;
import com.ufgov.sssfm.socket.utils.XMLUtil;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

public class SocketOperate extends Thread {
    private Socket socket;

    public SocketOperate(Socket socket) {
        this.socket = socket;
    }

    @SuppressWarnings("unused")
    public void run() {
        System.out.println("---------------进入---------------");
        /*
        try{
            InputStream in= socket.getInputStream();
            PrintWriter out=new PrintWriter(socket.getOutputStream());
            while(true){
                //读取客户端发送的信息
                String strXML = "";
                byte[] temp = new byte[1024];
                int length = 0;
                while((length = in.read(temp)) != -1){
                    strXML += new String(temp,0,length);
                }
                if("end".equals(strXML)){
                    System.out.println("准备关闭socket");
                    break;
                }
                if("".equals(strXML))
                    continue;
                System.out.println("客户端发来："+strXML.toString());
                // 发送给客户端数据
                //DataOutputStream out2 = new DataOutputStream(socket.getOutputStream());
                //out2.writeUTF("hi,i am hserver!i say:" + "是你吗 小伙子");
                out.print("Hi, I have received your message!");
                out.flush();
                out.close();
            }
            socket.close();
            System.out.println("socket stop.....");
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{

        }
        */

        try {
            // 读取客户端数据
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientInputStr = input.readLine();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
            // 处理客户端数据
            System.out.println("客户端发过来的内容:" + clientInputStr);
            XMLUtil util = new XMLUtil();
            String appCode = "";
            String typeCode = util.analysisTypeCode(clientInputStr);
            String fileName = util.analysisFileName(clientInputStr);

            if (typeCode.equals("22215")) {
                FtpUtils ftpUtil = new FtpUtils("192.168.20.18", 21, "administrator", "ufida");
                ftpUtil.open();
                //ftpUtil.readTxtFile("/FTP/123.txt");
                List listdata = ftpUtil.readTxtFile(fileName) ; //获取Ftp s缴费信息数据
                BaseDAO dao = new BaseDAO();
                String tableName="RETURNINFO";           //通过sockek 获取要插入的表
                dao.insertReturnInfo(listdata, fileName);  //数据入库

                //缴费汇总----20180928联调测试用，后期此方法由前台界面按钮出发
                dao.gatherInfo();
            } else if (typeCode.equals("njc4")) {
                // 缴费汇总
                appCode = util.analysisTotalContrXml(clientInputStr);
            } else if (typeCode.equals("njc6")) {
                // 汇款指令
                appCode = util.analysisInstructXml(clientInputStr);
            } else {
                appCode = "9999";
            }
            String appsg = "";
            String[][] arr = Constant.RETURNCODE;
            for (String[] str : arr) {
                if (str[0].equals(appCode)) {
                    appsg = str[1];
                }
            }
            Document returnDoc = util.getReturnXML(clientInputStr, appCode, appsg);
            DOMSource source = new DOMSource(returnDoc);


            // 向客户端回复信息
            PrintStream out = new PrintStream(socket.getOutputStream());
            //System.out.print("请输入:\t");
            // 发送键盘输入的一行
            //String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.println("I have received!");

            /*
            String returnXml = out.getBuffer().toString().replaceAll("standalone=\"no\"", "");
            lenStr = Integer.toHexString(returnXml.getBytes("UTF-8").length);
            if (lenStr.length() < 8) {
                for ( int i = lenStr.length(); i < 8 ; i++) {
                    lenStr = "0" + lenStr;
                }
            }
            returnXml = lenStr + returnXml;
            out.println(returnXml);
            */
            out.close();
            input.close();
        } catch (Exception e) {
            System.out.println("服务器 run 异常: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    socket = null;
                    System.out.println("服务端 finally 异常:" + e.getMessage());
                }
            }
        }
    }
}
