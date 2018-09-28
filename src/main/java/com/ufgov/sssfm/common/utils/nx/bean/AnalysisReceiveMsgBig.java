package com.ufgov.sssfm.common.utils.nx.bean;

import java.util.List;

/**
 * 解析接收报文实体类（总信息）
 * @author Administrator
 *
 */
public class AnalysisReceiveMsgBig {
   private String msgid;     			//消息流水号	
   private String oldmsgid;   			//原消息流水号	
   private String senderno;				//发送方编号
   private String recieverno;			//接收方编号
   private String bse173;				//业务代码	
   private String bse174;				//业务ID	
   private String aae036;				//消息发送时间	
   private String abe100;				//消息状态			
   private String msgtype;				//消息类型		
   private String msgcontent;				//消息内容	Text
   private String md5msgcode;				//MD5特征码	
   private String fjnum;				//附件条数	
   
   private List<AnalysisReceiveMsgSmall> analysisReceiveMsgSmallList;

	public String getMsgid() {
		return msgid;
	}
	
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	public String getOldmsgid() {
		return oldmsgid;
	}
	
	public void setOldmsgid(String oldmsgid) {
		this.oldmsgid = oldmsgid;
	}
	
	public String getSenderno() {
		return senderno;
	}
	
	public void setSenderno(String senderno) {
		this.senderno = senderno;
	}
	
	public String getRecieverno() {
		return recieverno;
	}
	
	public void setRecieverno(String recieverno) {
		this.recieverno = recieverno;
	}
	
	public String getBse173() {
		return bse173;
	}
	
	public void setBse173(String bse173) {
		this.bse173 = bse173;
	}
	
	public String getBse174() {
		return bse174;
	}
	
	public void setBse174(String bse174) {
		this.bse174 = bse174;
	}
	
	public String getAae036() {
		return aae036;
	}
	
	public void setAae036(String aae036) {
		this.aae036 = aae036;
	}
	
	public String getAbe100() {
		return abe100;
	}
	
	public void setAbe100(String abe100) {
		this.abe100 = abe100;
	}
	
	public String getMsgtype() {
		return msgtype;
	}
	
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	public String getMsgcontent() {
		return msgcontent;
	}
	
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	
	public String getMd5msgcode() {
		return md5msgcode;
	}
	
	public void setMd5msgcode(String md5msgcode) {
		this.md5msgcode = md5msgcode;
	}
	
	public String getFjnum() {
		return fjnum;
	}
	
	public void setFjnum(String fjnum) {
		this.fjnum = fjnum;
	}

	public List<AnalysisReceiveMsgSmall> getAnalysisReceiveMsgSmallList() {
		return analysisReceiveMsgSmallList;
	}

	public void setAnalysisReceiveMsgSmallList(List<AnalysisReceiveMsgSmall> analysisReceiveMsgSmallList) {
		this.analysisReceiveMsgSmallList = analysisReceiveMsgSmallList;
	}
	
}
