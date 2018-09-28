package com.ufgov.sssfm.project.module.queryutils.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 日志bean
 * @Author xucy
 * @Date
 **/
public class FmBankXmlLog implements Serializable {

    private static final long serialVersionUID = -2747219554773923842L;

    private String dataType; // VARCHAR2	报文类别
    private String senderId; // VARCHAR2	发送人id
    private Date sendDate; // DATE	发送时间
    private BigDecimal serialNum; // NUMBER	流水号
    private Date payDate; // DATE	支付时间
    private BigDecimal recNo = new BigDecimal(1); // NUMBER	报文序号（报文长度>4000时，报文将分为多条记录）
    private String requestXml; // VARCHAR2	发送报文
    private String resultXml; // VARCHAR2	返回报文
    private String errorMessage;  // VARCHAR2 错误信息
    private String dataId;//报文发送时的数据唯一主键

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public BigDecimal getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(BigDecimal serialNum) {
        this.serialNum = serialNum;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getRecNo() {
        return recNo;
    }

    public void setRecNo(BigDecimal recNo) {
        this.recNo = recNo;
    }

    public String getRequestXml() {
        return requestXml;
    }

    public void setRequestXml(String requestXml) {
        this.requestXml = requestXml;
    }

    public String getResultXml() {
        return resultXml;
    }

    public void setResultXml(String resultXml) {
        this.resultXml = resultXml;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
