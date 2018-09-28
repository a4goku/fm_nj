package com.ufgov.sssfm.common.utils.nx.bean;

/**
 * 请求报文的报文头参数实体
 * @author Administrator
 *
 */
public class MsgHeaderParamBean {
		private String esbUrl;					//人社政务网服务地址（调用方固定传入）
		private String[] esbUserPwd;				//设置请求服务的用户id（身份证号码）,密码串
		private String	sys;					//设置请求服务的机构编号
		private String ver;						//设置请求服务的版本号D
		private String org;						//访问服务的机构
		private String svid;					//测试消息联通性服务编号（用于测试联通性，测试用）
		
		
		public String getEsbUrl() {
			return esbUrl;
		}
		public void setEsbUrl(String esbUrl) {
			this.esbUrl = esbUrl;
		}

		public String[] getEsbUserPwd() {
			return esbUserPwd;
		}
		public void setEsbUserPwd(String[] esbUserPwd) {
			this.esbUserPwd = esbUserPwd;
		}
		public String getSys() {
			return sys;
		}
		public void setSys(String sys) {
			this.sys = sys;
		}
		public String getVer() {
			return ver;
		}
		public void setVer(String ver) {
			this.ver = ver;
		}
		public String getOrg() {
			return org;
		}
		public void setOrg(String org) {
			this.org = org;
		}
		public String getSvid() {
			return svid;
		}
		public void setSvid(String svid) {
			this.svid = svid;
		}
	 
}
