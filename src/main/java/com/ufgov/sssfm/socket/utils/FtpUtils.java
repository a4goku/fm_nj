package com.ufgov.sssfm.socket.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
/**
 * FTP¹¤¾ßÀà,Ö÷ÒªÊµÏÖFTPÉÏ´«¡¢ÏÂÔØ¡¢É¾³ýÎÄ¼þµÈ
 * @author
 */
public class FtpUtils {
    //public static final Logger _logger = LoggerFactory.getLogger(FTPUtil.class);
    private FTPClient ftpClient = null;
    private String server;
    private int port;
    private String userName;
    private String userPassword;

    public FtpUtils(String server, int port, String userName, String userPassword) {
        this.server = server;
        this.port = port;
        this.userName = userName;
        this.userPassword = userPassword;
    }
    /**
     * Á¬½Ó·þÎñÆ÷
     * @return Á¬½Ó³É¹¦Óë·ñ true:³É¹¦£¬ false:Ê§°Ü
     */
    public boolean open() {
        if (ftpClient != null && ftpClient.isConnected()) {
            return true;
        }
        try {
            ftpClient = new FTPClient();
            // Á¬½Ó
            ftpClient.connect(this.server, this.port);
            ftpClient.login(this.userName, this.userPassword);
            setFtpClient(ftpClient);
            // ¼ì²âÁ¬½ÓÊÇ·ñ³É¹¦
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                this.close();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
            //System.out.println("open FTP success:" + this.server + ";port:" + this.port + ";name:" + this.userName + ";pwd:" + this.userPassword);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // ÉèÖÃÉÏ´«Ä£Ê½.binally  or ascii
            return true;
        } catch (Exception ex) {
            this.close();
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * ÉÏ´«ÎÄ¼þµ½FTP·þÎñÆ÷
     * @param localDirectoryAndFileName ±¾µØÎÄ¼þÄ¿Â¼ºÍÎÄ¼þÃû
     * @param ftpFileName ÉÏ´«µ½·þÎñÆ÷µÄÎÄ¼þÃû
     * @param ftpDirectory FTPÄ¿Â¼Èç:/path1/pathb2/,Èç¹ûÄ¿Â¼²»´æÔÚ»á×Ô¶¯´´½¨Ä¿Â¼
     * @return
     */
    public boolean upload(String localDirectoryAndFileName, String ftpFileName, String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        ftpClient.enterLocalPassiveMode();
        if (ftpClient != null) {
            File srcFile = new File(localDirectoryAndFileName);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(srcFile);
                // ´´½¨Ä¿Â¼
                this.mkDir(ftpDirectory);
                ftpClient.setBufferSize(100000);
                ftpClient.setControlEncoding("UTF-8");
                // ÉèÖÃÎÄ¼þÀàÐÍ£¨¶þ½øÖÆ£©
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // ÉÏ´«
                flag = ftpClient.storeFile(new String(ftpFileName.getBytes(), "utf-8"), fis);
            } catch (Exception e) {
                this.close();
                e.printStackTrace();
                return false;
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //  _logger.info("ÉÏ´«±¾µØÎÄ¼þ£º " + localDirectoryAndFileName + "³É¹¦£¬ÉÏ´«µ½Ä¿Â¼£º" + ftpDirectory + "/" + ftpFileName);
        return flag;
    }

    public boolean upload(File srcFile, String ftpDirectory, String charSet) throws IOException {

        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        ftpClient.enterLocalPassiveMode();
        if (ftpClient != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(srcFile);
                // ´´½¨Ä¿Â¼
//                this.mkDir(ftpDirectory);
                // ÇÐ»»µ½¸¸Ä¿Â¼
                this.changeToParentDir();
                // ½øÈë×ÓÄ¿Â¼
                this.cd(ftpDirectory);
                ftpClient.setBufferSize(100000);
                ftpClient.setControlEncoding(charSet);
                // ÉèÖÃÎÄ¼þÀàÐÍ£¨¶þ½øÖÆ£©
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                // ÉÏ´«
                flag = ftpClient.storeFile(srcFile.getName(), fis);
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // _logger.info("ÉÏ´«±¾µØÎÄ¼þ£º " + srcFile.getName() + "³É¹¦£¬ÉÏ´«µ½Ä¿Â¼£º" + ftpDirectory + "/" );
        return flag;

    }
    public void downloadFile(String ftpFile, String relativeLocalPath, String absolutePath) {
        if (ftpFile.indexOf("?") == -1) {
            OutputStream outputStream = null;
            try {
                File entryDir = new File(relativeLocalPath);
                // Èç¹ûÎÄ¼þ¼ÐÂ·¾¶²»´æÔÚ£¬Ôò´´½¨ÎÄ¼þ¼Ð
                if (!entryDir.exists() || !entryDir.isDirectory()) {
                    entryDir.mkdirs();
                }
                File locaFile = new File(relativeLocalPath + ftpFile);
                // ÅÐ¶ÏÎÄ¼þÊÇ·ñ´æÔÚ£¬´æÔÚÔò·µ»Ø
                if (locaFile.exists()) {
                    return;
                } else {
                    outputStream = new FileOutputStream(relativeLocalPath + ftpFile);
                    ftpClient.retrieveFile(absolutePath + ftpFile, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                }
            }
        }
    }
    /**
     * Ñ­»·´´½¨Ä¿Â¼£¬²¢ÇÒ´´½¨ÍêÄ¿Â¼ºó£¬ÉèÖÃ¹¤×÷Ä¿Â¼Îªµ±Ç°´´½¨µÄÄ¿Â¼ÏÂ
     * @param ftpPath ÐèÒª´´½¨µÄÄ¿Â¼
     * @return
     */
    public boolean mkDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            // ½«Â·¾¶ÖÐµÄÐ±¸ÜÍ³Ò»
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            System.out.println("ftpPath:" + ftpPath);
            if (ftpPath.indexOf('/') == -1) {
                // Ö»ÓÐÒ»²ãÄ¿Â¼
                ftpClient.makeDirectory(new String(ftpPath.getBytes(), "utf-8"));
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "utf-8"));
            } else {
                // ¶à²ãÄ¿Â¼Ñ­»·´´½¨
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.makeDirectory(new String(paths[i].getBytes(), "utf-8"));
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "utf-8"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ´ÓFTP·þÎñÆ÷ÉÏÏÂÔØÎÄ¼þ
     * @param ftpDirectoryAndFileName ftp·þÎñÆ÷ÎÄ¼þÂ·¾¶£¬ÒÔ/dirÐÎÊ½¿ªÊ¼
     * @param localDirectoryAndFileName ±£´æµ½±¾µØµÄÄ¿Â¼
     * @return
     */
    public boolean get(String ftpDirectoryAndFileName, String localDirectoryAndFileName) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        ftpClient.enterLocalPassiveMode(); // passivemode ×¢Òâ:Ã¿´ÎÊý¾ÝÁ¬½ÓÖ®Ç°£¬ftpclient¸æËßftp server¿ªÍ¨Ò»¸ö¶Ë¿ÚÀ´´«ÊäÊý¾Ý
        try {
            // ½«Â·¾¶ÖÐµÄÐ±¸ÜÍ³Ò»
            char[] chars = ftpDirectoryAndFileName.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpDirectoryAndFileName = sbStr.toString();
            String filePath = ftpDirectoryAndFileName.substring(0, ftpDirectoryAndFileName.lastIndexOf("/"));
            String fileName = ftpDirectoryAndFileName.substring(ftpDirectoryAndFileName.lastIndexOf("/") + 1);
            this.changeDir(filePath);
            flag = ftpClient.retrieveFile(new String(fileName.getBytes(), "utf-8"),
                    new FileOutputStream(localDirectoryAndFileName)); // ÏÂÔØ
            // file
            System.out.println(ftpClient.getReplyString()); // check result
            System.out.println("´Óftp·þÎñÆ÷ÉÏÏÂÔØÎÄ¼þ£º" + ftpDirectoryAndFileName + "£¬ ±£´æµ½£º" + localDirectoryAndFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return flag;
        } finally {
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("¹Ø±ÕFTPÁ¬½Ó·¢ÉúÒì³££¡", e);
            }
        }
        return flag;
    }



    public boolean get(String ftpDirAndFileName, String localDirectoryAndFileName, String charSet) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean flag = false;
        ftpClient.enterLocalPassiveMode(); // passivemode ×¢Òâ:Ã¿´ÎÊý¾ÝÁ¬½ÓÖ®Ç°£¬ftpclient¸æËßftp server¿ªÍ¨Ò»¸ö¶Ë¿ÚÀ´´«ÊäÊý¾Ý
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(localDirectoryAndFileName);
            // ½«Â·¾¶ÖÐµÄÐ±¸ÜÍ³Ò»
            char[] chars = ftpDirAndFileName.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpDirAndFileName = sbStr.toString();
            String filePath = ftpDirAndFileName.substring(0, ftpDirAndFileName.lastIndexOf("/"));
            String fileName = ftpDirAndFileName.substring(ftpDirAndFileName.lastIndexOf("/") + 1);
            this.changeToParentDir();
            this.changeDir(filePath);
            flag = ftpClient.retrieveFile(new String(fileName.getBytes(), charSet),
                    fos); // ÏÂÔØ
            // file
            System.out.println(ftpClient.getReplyString()); // check result
            System.out.println("´Óftp·þÎñÆ÷ÉÏÏÂÔØÎÄ¼þ£º" + ftpDirAndFileName + "£¬ ±£´æµ½£º" + localDirectoryAndFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return flag;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("¹Ø±ÕFTPÁ¬½Ó·¢ÉúÒì³££¡", e);
            }
        }
        return flag;
    }

    /**
     * É¾³ýFTPÉÏµÄÎÄ¼þ
     * @param ftpDirAndFileName Â·¾¶¿ªÍ·²»ÄÜ¼Ó/£¬±ÈÈçÓ¦¸ÃÊÇtest/filename1
     * @return
     */
    public boolean deleteFile(String ftpDirAndFileName) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            return ftpClient.deleteFile(ftpDirAndFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * É¾³ýFTPÄ¿Â¼
     * @param ftpDirectory
     * @return
     */
    public boolean deleteDirectory(String ftpDirectory) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            return ftpClient.removeDirectory(ftpDirectory);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ÇÐ»»µ½¸¸Ä¿Â¼
     * @return ÇÐ»»½á¹û true£º³É¹¦£¬ false£ºÊ§°Ü
     */
    private boolean changeToParentDir() {
        try {
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ¸Ä±äµ±Ç°Ä¿Â¼µ½Ö¸¶¨Ä¿Â¼
     * @param dir Ä¿µÄÄ¿Â¼
     * @return ÇÐ»»½á¹û true£º³É¹¦£¬false£ºÊ§°Ü
     */
    private boolean cd(String dir) {
        try {
            return ftpClient.changeWorkingDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ²ã²ãÇÐ»»¹¤×÷Ä¿Â¼
     * @param ftpPath Ä¿µÄÄ¿Â¼
     * @return ÇÐ»»½á¹û
     */
    public boolean changeDir(String ftpPath) {
        if (!ftpClient.isConnected()) {
            return false;
        }
        try {
            // ½«Â·¾¶ÖÐµÄÐ±¸ÜÍ³Ò»
            char[] chars = ftpPath.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpPath = sbStr.toString();
            if (ftpPath.indexOf('/') == -1) {
                // Ö»ÓÐÒ»²ãÄ¿Â¼
                ftpClient.changeWorkingDirectory(new String(ftpPath.getBytes(), "iso-8859-1"));
            } else {
                // ¶à²ãÄ¿Â¼Ñ­»·´´½¨
                String[] paths = ftpPath.split("/");
                for (int i = 0; i < paths.length; i++) {
                    ftpClient.changeWorkingDirectory(new String(paths[i].getBytes(), "iso-8859-1"));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * »ñÈ¡FTPÄ¿Â¼ÏÂµÄÎÄ¼þÁÐ±í
     * @param pathName
     * @return
     */
    public String[] getFileNameList(String pathName) {
        try {
            return ftpClient.listNames(pathName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * »ñÈ¡Ä¿Â¼ÏÂËùÓÐµÄÎÄ¼þÃû³Æ
     *
     * @param filePath Ö¸¶¨µÄÄ¿Â¼
     * @return ÎÄ¼þÁÐ±í,»òÕßnull
     */
    private FTPFile[] getFileList(String filePath) {
        try {
            return ftpClient.listFiles(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ¹Ø±ÕÁ´½Ó
     */
    public void close() {
        try {
            if (ftpClient != null && ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            // System.out.println("³É¹¦¹Ø±ÕÁ¬½Ó£¬·þÎñÆ÷ip:" + this.server + ", ¶Ë¿Ú:" + this.port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public FTPClient getFtpClient() {
        return ftpClient;
    }
    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }


    public List readTxtFile(String ftpDirectoryAndFileName) {
        if (!ftpClient.isConnected()) {
            return null;
        }
        ArrayList listALL=new ArrayList();
        //  boolean flag = false;
        ftpClient.enterLocalPassiveMode(); // passivemode ×¢Òâ:Ã¿´ÎÊý¾ÝÁ¬½ÓÖ®Ç°£¬ftpclient¸æËßftp server¿ªÍ¨Ò»¸ö¶Ë¿ÚÀ´´«ÊäÊý¾Ý
        try {
            // ½«Â·¾¶ÖÐµÄÐ±¸ÜÍ³Ò»
            char[] chars = ftpDirectoryAndFileName.toCharArray();
            StringBuffer sbStr = new StringBuffer(256);
            for (int i = 0; i < chars.length; i++) {
                if ('\\' == chars[i]) {
                    sbStr.append('/');
                } else {
                    sbStr.append(chars[i]);
                }
            }
            ftpDirectoryAndFileName = sbStr.toString();
            String filePath = ftpDirectoryAndFileName.substring(0, ftpDirectoryAndFileName.lastIndexOf("/"));
            String fileName = ftpDirectoryAndFileName.substring(ftpDirectoryAndFileName.lastIndexOf("/") + 1);
            this.changeDir(filePath);
            InputStream ins = ftpClient.retrieveFileStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ins, "GBK"));
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                String[] s=lineTxt.split("\\|");
                ArrayList listA=new ArrayList();
                for (int i = 0; i < s.length; i++) {
                    listA.add((s[i].toString()).trim());
                    if(i==s.length-1){
                        listALL.add(listA);
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("¹Ø±ÕFTPÁ¬½Ó·¢ÉúÒì³££¡", e);
            }
        }
        return listALL;
    }

    public static void main(String[] args) {
		/*	FTPUtil ftpUtil = new FTPUtil("10.72.31.202", 21, "lilei", "1230");
		boolean ss = ftpUtil.open();
		System.out.println(ss);
		ftpUtil.upload("D:\\123\\123.txt", "aaddda.txt", "/sbtoyh");
		ftpUtil.get("/adc/apc/aaddda.txt", "D:\\123\\5553.txt");*/

        FtpUtils ftpUtil = new FtpUtils("192.168.20.18", 21, "administrator", "ufida");
        ftpUtil.open();
        ftpUtil.readTxtFile("/FTP/123.txt");
    }
}
