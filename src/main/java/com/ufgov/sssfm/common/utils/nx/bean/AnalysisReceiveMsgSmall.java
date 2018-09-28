package com.ufgov.sssfm.common.utils.nx.bean;
/**
 * 解析接收报文实体类（明细文件信息）
 * @author Administrator
 *
 */
public class AnalysisReceiveMsgSmall {
	private String fjid;  			//附件编号	
	private String fjtype;			//附件类型
	private String fjname;			//附件名称	
	private String ossstr;			//附件地址
	private String md5fjcode;		//MD5特征码
	public String getFjid() {
		return fjid;
	}
	public void setFjid(String fjid) {
		this.fjid = fjid;
	}
	public String getFjtype() {
		return fjtype;
	}
	public void setFjtype(String fjtype) {
		this.fjtype = fjtype;
	}
	public String getFjname() {
		return fjname;
	}
	public void setFjname(String fjname) {
		this.fjname = fjname;
	}
	public String getOssstr() {
		return ossstr;
	}
	public void setOssstr(String ossstr) {
		this.ossstr = ossstr;
	}
	public String getMd5fjcode() {
		return md5fjcode;
	}
	public void setMd5fjcode(String md5fjcode) {
		this.md5fjcode = md5fjcode;
	}
}
