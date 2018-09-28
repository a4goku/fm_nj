package com.ufgov.sssfm.common.utils.nx;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/** @Author xucy
 * @Description md5 对文件内容的加密以及对文本的加密
 * @Date 16:04 2018/8/9
 * @Param 
 * @return 
 **/

public class Md5Util {
	public static String fileMD5(String inputFile) throws IOException {

		// 缓冲区大小（这个可以抽出一个参数）

		int bufferSize = 256 * 1024;

		FileInputStream fileInputStream = null;

		DigestInputStream digestInputStream = null;

		try {

			// 拿到一个MD5转换器（同样，这里可以换成SHA1）

			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			// 使用DigestInputStream

			fileInputStream = new FileInputStream(inputFile);

			digestInputStream = new DigestInputStream(fileInputStream,
					messageDigest);

			// read的过程中进行MD5处理，直到读完文件

			byte[] buffer = new byte[bufferSize];

			while (digestInputStream.read(buffer) > 0)
				;

			// 获取最终的MessageDigest

			messageDigest = digestInputStream.getMessageDigest();

			// 拿到结果，也是字节数组，包含16个元素

			byte[] resultByteArray = messageDigest.digest();

			// 同样，把字节数组转换成字符串

			return byteArrayToHex(resultByteArray);

		} catch (NoSuchAlgorithmException e) {

			return null;

		} finally {

			try {

				digestInputStream.close();

			} catch (Exception e) {

			}

			try {

				fileInputStream.close();

			} catch (Exception e) {

			}

		}

	}

	public static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符

		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

		char[] resultCharArray = new char[byteArray.length * 2];

		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

		int index = 0;

		for (byte b : byteArray) {

			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

			resultCharArray[index++] = hexDigits[b & 0xf];

		}

		// 字符数组组合成字符串返回

		return new String(resultCharArray);
	}

	 /**
	  * 生成32位md5码
	  * @param password
	  * @return
	  */
	 public static String md5Password(String password) {

		 try {
			 // 得到一个信息摘要器
			 MessageDigest digest = MessageDigest.getInstance("md5");
			 byte[] result = digest.digest(password.getBytes());
			 StringBuffer buffer = new StringBuffer();
			 // 把每一个byte 做一个与运算 0xff;
			 for (byte b : result) {
				 // 与运算
				 int number = b & 0xff;// 加盐
				 String str = Integer.toHexString(number);
				 if (str.length() == 1) {
					 buffer.append("0");
				 }
				 buffer.append(str);
			 }

			 // 标准的md5加密后的结果
			 return buffer.toString();
		 } catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();
			 return "";
		 }

	 }
	public static void main(String[] args) {
//		try {
//			System.out.println(fileMD5("D:/SDFSDF.txt"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println(md5Password("徐晨阳"));

		System.out.println((UUID.randomUUID()+"").replaceAll("-",""));
	}
}
