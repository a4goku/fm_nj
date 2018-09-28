package com.ufgov.sssfm.common.utils.nx;

import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgBig;
import com.ufgov.sssfm.common.utils.nx.bean.AnalysisReceiveMsgSmall;
import com.ufgov.sssfm.common.utils.nx.bean.MsgHeaderParamBean;
import com.ylzinfo.esb.client.XMLRequest;
import com.ylzinfo.esb.util.XMLUtil;
import org.apache.axiom.om.OMElement;
import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 解析接收报文   以及拼装发送报文
 * @author Administrator
 *
 */
public class AnalysisMsgUtil 
{
	/**
	 *  解析接收报文
	 * @param element
	 * @return
	 */
	public static AnalysisReceiveMsgBig getRecieveMessage(OMElement element) {
		AnalysisReceiveMsgBig analysisReceiveMsgBig=new AnalysisReceiveMsgBig();
		/**
		 * 1、解析报文，读取数据
		 *
		 */
		Map<String,String> paraMap=null;
		Map<String,ArrayList> pramlistMap=null;

		try {
			//调用XMLUtil. parseBatchData ()方法解释请求报文里的批量数据
			List<?> infoList= XMLUtil.parseBatchData(element.getFirstElement());
			//获取请求报文里的para参数集合
			paraMap= XMLUtil.getPara4BatchData(infoList);
			//获取请求报文里的paralist参数集合
			pramlistMap= XMLUtil.getParalist4BatchData(infoList);
		}catch (Exception e){
			System.out.println("解析业务方发送报文失败!");
			throw e;
		}



		/**
		 * 2、数据处理，业务流程
		 */
		//TODO 调用调用业务组件处理数据
		analysisReceiveMsgBig.setMsgid(paraMap.get("msgid"));
		analysisReceiveMsgBig.setOldmsgid(paraMap.get("oldmsgid"));
		analysisReceiveMsgBig.setSenderno(paraMap.get("senderno"));
		analysisReceiveMsgBig.setRecieverno(paraMap.get("recieverno"));
		analysisReceiveMsgBig.setBse173(paraMap.get("bse173"));
		analysisReceiveMsgBig.setBse174(paraMap.get("bse174"));
		analysisReceiveMsgBig.setAae036(paraMap.get("aae036"));
		analysisReceiveMsgBig.setAbe100(paraMap.get("abe100"));
		analysisReceiveMsgBig.setMsgtype(paraMap.get("msgtype"));
		analysisReceiveMsgBig.setMsgcontent(paraMap.get("msgcontent"));
		analysisReceiveMsgBig.setMd5msgcode(paraMap.get("md5msgcode"));
		analysisReceiveMsgBig.setFjnum(paraMap.get("fjnum"));


		/**
		 * 向子对象中去塞入每一个文件的信息，再加入到主对象中
		 */
		ArrayList arrList=pramlistMap.get("paralist");
		List<AnalysisReceiveMsgSmall> analysisReceiveMsgSmallList=new ArrayList<AnalysisReceiveMsgSmall>();

		for(int i=0;i<arrList.size();i++){
			AnalysisReceiveMsgSmall analysisReceiveMsgSmall=new AnalysisReceiveMsgSmall();
			Map<String,String> map=(Map) arrList.get(i);
			analysisReceiveMsgSmall.setFjid(map.get("fjid"));
			analysisReceiveMsgSmall.setFjtype(map.get("fjtype"));
			analysisReceiveMsgSmall.setFjname(map.get("fjname"));
			analysisReceiveMsgSmall.setOssstr(map.get("ossstr"));
			analysisReceiveMsgSmall.setMd5fjcode(map.get("md5fjcode"));

			analysisReceiveMsgSmallList.add(analysisReceiveMsgSmall);
		}

		//加入到主对象中
		analysisReceiveMsgBig.setAnalysisReceiveMsgSmallList(analysisReceiveMsgSmallList);

		return analysisReceiveMsgBig;

	}

	/**
	 * 拼装上传到oss上的xml文件
	 */
	
	public static String CompleteMsgToFileAd68(Object objFa,Class clazzFa,Class clazzSon,String filePath){
		
		String head = "<?xml version=\"1.0\" encoding=\"GBK\" ?><Data>";
		File file = new File(filePath);
        try {
        	
        	FileWriterWithEncoding fileWriter = null;
    		try {
    			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
    			fileWriter =new FileWriterWithEncoding(file, "GBK", true);
    			//fw = new FileWriter(file, true);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		//直接将报文往文件中去写入
			fileWriter.append(head);
			
			for(int z=0;z<((List<Object>) objFa).size();z++){
				fileWriter.append("<Main>");
				//拼装业务数据报文
				Field[] fields = getBeanChildName(clazzFa);
				for(Field f : fields) {
					f.setAccessible(true);
					try {
						Object object = f.get(((List<Object>) objFa).get(z));
						if(object==null){
							fileWriter.append("<"+f.getName()+">"+""+"</"+f.getName()+">");
						}else if(object instanceof String){
							fileWriter.append("<"+f.getName()+">"+object+"</"+f.getName()+">");
						}else if(object instanceof Date){
							fileWriter.append("<"+f.getName()+">"+object+"</"+f.getName()+">");
						}else if(object instanceof BigDecimal){
							fileWriter.append("<"+f.getName()+">"+object+"</"+f.getName()+">");
						}else if(object instanceof List){
							fileWriter.append("<Details>");
							for(int i=0;i<((List)object).size();i++){
								fileWriter.append("<Detail>");
								//利用反射的机制  得到bean里面的属性 已经属性值，并拼接到文件中
								Field[] fieldsSon = getBeanChildName(clazzSon);
								for(Field fSon : fieldsSon) {
									fSon.setAccessible(true);
									Object objectSon = fSon.get(((List)object).get(i));
									if(objectSon==null){
										fileWriter.append("<"+fSon.getName()+">"+""+"</"+fSon.getName()+">");
									}else if(objectSon instanceof String){
										fileWriter.append("<"+fSon.getName()+">"+objectSon+"</"+fSon.getName()+">");
									}else if(objectSon instanceof Date){
										fileWriter.append("<"+fSon.getName()+">"+objectSon+"</"+fSon.getName()+">");
									}else if(objectSon instanceof BigDecimal){
										fileWriter.append("<"+fSon.getName()+">"+objectSon+"</"+fSon.getName()+">");
									}
								}
								fileWriter.append("</Detail>");
							}
							fileWriter.append("</Details>");
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				    System.out.println(f.getName());//打印每个属性的类型名字
				}
				fileWriter.append("</Main>");
			}
			
			fileWriter.append("</Data>");
	        fileWriter.close();
		} catch (IOException e) {
			System.out.println("创建文件写对象的时候出错");
			e.printStackTrace();
			return "创建文件写对象的时候出错";
		}
		
		return "拼装成功";
	}

	/**
	 * 拼装拨款单发送报文
	 */

	public static String[] CompleteBkdMsg(Object objFa,Class clazz){
		String[] result=new String[2];
		StringBuilder sb=new StringBuilder();
		try {
			sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?><Main><Details>");
			for(int z=0;z<((List<Object>) objFa).size();z++){
				sb.append("<Detail>");
				//拼装业务数据报文
				Field[] fields = getBeanChildName(clazz);
				for(Field f : fields) {
					f.setAccessible(true);
					try {
						Object object = f.get(((List<Object>) objFa).get(z));
						if(object==null){
							sb.append("<"+f.getName()+">"+""+"</"+f.getName()+">");
						}else if(object instanceof String){
							sb.append("<"+f.getName()+">"+object+"</"+f.getName()+">");
						}else if(object instanceof Date){
							sb.append("<"+f.getName()+">"+object+"</"+f.getName()+">");
						}else if(object instanceof BigDecimal){
							sb.append("<"+f.getName()+">"+object+"</"+f.getName()+">");
						}else if(object instanceof List){

						}
					} catch (IllegalArgumentException e) {
						System.out.println("拼装拨款单报文的时候出错");
						result[0]="01";
						result[1]="拼装拨款单报文的时候出错";
						e.printStackTrace();
						return result;
					} catch (IllegalAccessException e) {
						System.out.println("拼装拨款单报文的时候出错");
						result[0]="01";
						result[1]="拼装拨款单报文的时候出错";
						e.printStackTrace();
						return result;
					}
					System.out.println(f.getName());//打印每个属性的类型名字
				}
				sb.append("</Detail>");
			}

			sb.append("</Details></Main>");
		} catch (Exception e) {
			System.out.println("拼装拨款单报文的时候出错");
			result[0]="01";
			result[1]="拼装拨款单报文的时候出错";
			e.printStackTrace();
			return result;
		}
		result[0]="00";
		result[1]=sb.toString();
		return result;
	}


	/**
	 * 拼装上传到oss上的xml文件
	 */
	
	public static String[] CompleteMsgToFileJF07(Object objGraFa,Class clazzGraFa,Class clazzFa,Class clazzSon,String filePath){

		String[] result=new String[2];
		String head = "<?xml version=\"1.0\" encoding=\"GBK\" ?><Data>";
		File file = new File(filePath);
        try {
        	
        	FileWriterWithEncoding fileWriter = null;
    		try {
    			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
    			fileWriter =new FileWriterWithEncoding(file, "GBK", true);
    		} catch (IOException e) {
    			e.printStackTrace();
				result[0]="01";
				result[1]="找不到文件路径";
				return result;
    		}
    		//直接将报文往文件中去写入
			fileWriter.append(head);
			
			for(int z=0;z<((List<Object>) objGraFa).size();z++){
				fileWriter.append("<Main>");
				//拼装业务数据报文
				Field[] fields = getBeanChildName(clazzGraFa);
				for(Field f : fields) {
					f.setAccessible(true);
					try {
						Object graObject = f.get(((List<Object>) objGraFa).get(z));
						if(graObject==null){
							fileWriter.append("<"+f.getName()+">"+""+"</"+f.getName()+">");
						}else if(graObject instanceof String){
							fileWriter.append("<"+f.getName()+">"+graObject+"</"+f.getName()+">");
						}else if(graObject instanceof Date){
							fileWriter.append("<"+f.getName()+">"+graObject+"</"+f.getName()+">");
						}else if(graObject instanceof BigDecimal){
							fileWriter.append("<"+f.getName()+">"+graObject+"</"+f.getName()+">");
						}else if(graObject instanceof List){
							fileWriter.append("<Details>");
							for(int i=0;i<((List)graObject).size();i++){
								fileWriter.append("<Detail>");
								//利用反射的机制  得到bean里面的属性 已经属性值，并拼接到文件中
								Field[] fieldsFa = getBeanChildName(clazzFa);
								for(Field fFa : fieldsFa) {
									fFa.setAccessible(true);
									Object objectFa = fFa.get(((List)graObject).get(i));
									if(objectFa==null){
										fileWriter.append("<"+fFa.getName()+">"+""+"</"+fFa.getName()+">");
									}else if(objectFa instanceof String){
										fileWriter.append("<"+fFa.getName()+">"+objectFa+"</"+fFa.getName()+">");
									}else if(objectFa instanceof Date){
										fileWriter.append("<"+fFa.getName()+">"+objectFa+"</"+fFa.getName()+">");
									}else if(objectFa instanceof BigDecimal){
										fileWriter.append("<"+fFa.getName()+">"+objectFa+"</"+fFa.getName()+">");
									}else if(objectFa instanceof List){
										fileWriter.append("<Children>");
										for(int j=0;j<((List)objectFa).size();j++){
											fileWriter.append("<Child>");
											//利用反射的机制  得到bean里面的属性 已经属性值，并拼接到文件中
											Field[] fieldsSon = getBeanChildName(clazzSon);
											for(Field fSon : fieldsSon) {
												fSon.setAccessible(true);
												Object objectSon = fSon.get(((List)objectFa).get(j));
												if(objectSon==null){
													fileWriter.append("<"+fSon.getName()+">"+""+"</"+fSon.getName()+">");
												}else if(objectSon instanceof String){
													fileWriter.append("<"+fSon.getName()+">"+objectSon+"</"+fSon.getName()+">");
												}else if(objectSon instanceof Date){
													fileWriter.append("<"+fSon.getName()+">"+objectSon+"</"+fSon.getName()+">");
												}else if(objectSon instanceof BigDecimal){
													fileWriter.append("<"+fSon.getName()+">"+objectSon+"</"+fSon.getName()+">");
												}
											}
											fileWriter.append("</Child>");
										}
										fileWriter.append("</Children>");
									}
								}
								fileWriter.append("</Detail>");
							}
							fileWriter.append("</Details>");
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				fileWriter.append("</Main>");
			}
			
			fileWriter.append("</Data>");
	        fileWriter.close();
		} catch (IOException e) {
			System.out.println("创建文件写对象的时候出错");
			e.printStackTrace();
			result[0]="01";
			result[1]="创建文件写对象的时候出错";
			return result;
		}

		result[0]="00";
		result[1]="文件拼装成功";
		return result;
	}
	
	//得到javabean类中的所有属性名
	public static <T> Field[] getBeanChildName(Class clazz){
			//将map中的属性赋值到对象中区
	        Field[] subFields = clazz.getDeclaredFields();
	        
	        Class<? super T> superclass = clazz.getSuperclass();
	        Field[] superFields = superclass.getDeclaredFields();
	        Field[] fields = new Field[subFields.length+superFields.length];
	        int i = 0;
	        for (Field field:superFields){
	            fields[i++] = field;
	        }
	        for (Field field:subFields){
	            fields[i++] = field;
	        }
			return fields;
	}
	
	
	/**
	 * 拼装发送报文
	 * @param args 
	 */
	
	public static XMLRequest getSendXMLRequest(MsgHeaderParamBean msgHeaderParam, AnalysisReceiveMsgBig analysisReceiveMsgBig){
		
		//设置请求报文header中的参数
		XMLRequest xmlRequest = new XMLRequest();									//new一个客户端请求对象
		xmlRequest.setEsbUrl(msgHeaderParam.getEsbUrl());							//人社政务网服务地址（调用方固定传入）
	    xmlRequest.setEsbUserPwd(msgHeaderParam.getEsbUserPwd()); 					//设置请求服务的用户id（身份证号码）,密码串
	    xmlRequest.setSys(msgHeaderParam.getSys());									//设置请求服务的机构编号
	    xmlRequest.setVer(msgHeaderParam.getVer());									//设置请求服务的版本号D
	    xmlRequest.setOrg(msgHeaderParam.getOrg());									//访问服务的机构
	    xmlRequest.setSvid(msgHeaderParam.getSvid());								//测试消息联通性服务编号（用于测试联通性，测试用）
		
	    
	    /**
	      * 1、设置非批量部分参数集合
	      *
	      */
	    String[] bodyParam =new String[30];
	    String[] bodyParamValue=new String[30];
	    List<AnalysisReceiveMsgSmall> listSmall=null;
	    Field[] fields=getBeanChildName(AnalysisReceiveMsgBig.class);
	    
	    for(int i=0;i<fields.length;i++){
	    	Field field=fields[i];
	    	field.setAccessible(true);
	    	
	    	bodyParam[i]=field.getName();
	    	try {
	    		
	    		Object obj=field.get(analysisReceiveMsgBig);
	    		if(obj==null){
	    			bodyParamValue[i]="";
	    		}else if(obj instanceof String){
	    			bodyParamValue[i]=obj+"";
	    		}else if(obj instanceof List){
	    			listSmall=(List<AnalysisReceiveMsgSmall>) obj;
	    		}
	    		
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	    }
	    
	    
	    	xmlRequest.setParam(bodyParam);						//设置请求参数para部分（参数名）		
	    	xmlRequest.setParamValue(bodyParamValue);			//设置请求参数para部分（参数值）
	    
	      /**
		  * 2、设置批量部分参数集合
		  * 说明：若服务入参没有批量集合，则需要注销此代码块
		  */
	    	
	    	List<Map> listInfo1 = new ArrayList<Map>();		//批量子集
	    	
	    	for(int i=0;i<listSmall.size();i++){
	    		AnalysisReceiveMsgSmall msgSmall=listSmall.get(i);
	    		Map<String,String> map =new HashMap<String,String>();
	    		Field[] fieldSmall=getBeanChildName(AnalysisReceiveMsgSmall.class);
	    	    
	    	    for(int j=0;j<fieldSmall.length;j++){
	    	    	Field field=fieldSmall[j];
	    	    	field.setAccessible(true);
	    	    	try {
	    	    		
	    	    		Object obj=field.get(msgSmall);
	    	    		
	    	    		if(obj==null){
	    	    		    map.put(field.getName(), "");					//设置字段值
	    	    		}else if(obj instanceof String){
	    	    			map.put(field.getName(), obj.toString());		//设置字段值
	    	    		}
	    	    		
	    			} catch (IllegalArgumentException e) {
	    				e.printStackTrace();
	    			} catch (IllegalAccessException e) {
	    				e.printStackTrace();
	    			}
	    	    }
	    	    listInfo1.add(map);
	    	}
	    	
	     
	     	ArrayList paralist = new ArrayList();	//批量集合参数对象
	     
		    paralist.add("retrieve");						//设置参数集合（paralist）的名称（name）为retrieve
		    paralist.add(listInfo1);					//设置参数集合（paralist）的值。注意设置顺序：1、先设置参数名，再设置参数值
		    
		    
		    xmlRequest.setParamList(paralist);			//设置paralist参数
	    
		    return xmlRequest;
	}
	
}
