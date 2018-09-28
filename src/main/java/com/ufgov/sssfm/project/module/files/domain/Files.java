package com.ufgov.sssfm.project.module.files.domain;

import com.ufgov.sssfm.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 附件表 fm_files
 * 
 * @author fanhla
 * @date 2018-08-07
 */
public class Files extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 附件ID */
	private String fileId;
	/** 附件名称 */
	private String fileName;
	/** 附件类型 */
	private String fileType;
	/** 附件类型 */
	private String filePath;
	/** 单据ID */
	private String billId;
	/** 排序号 */
	private Integer seqNo;
	/** 创建时间 */
	private Date creatTime;
	/** 备注 */
	private String remark;

	public void setFileId(String fileId) 
	{
		this.fileId = fileId;
	}

	public String getFileId() 
	{
		return fileId;
	}
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}

	public String getFileName() 
	{
		return fileName;
	}
	public void setFileType(String fileType) 
	{
		this.fileType = fileType;
	}

	public String getFileType() 
	{
		return fileType;
	}
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getFilePath()
	{
		return filePath;
	}
	public void setBillId(String billId) 
	{
		this.billId = billId;
	}

	public String getBillId() 
	{
		return billId;
	}
	public void setSeqNo(Integer seqNo) 
	{
		this.seqNo = seqNo;
	}

	public Integer getSeqNo() 
	{
		return seqNo;
	}
	public void setCreatTime(Date creatTime) 
	{
		this.creatTime = creatTime;
	}

	public Date getCreatTime() 
	{
		return creatTime;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}

	public String getRemark() 
	{
		return remark;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("fileName", getFileName())
            .append("fileType", getFileType())
			.append("filePath", getFilePath())
            .append("billId", getBillId())
            .append("seqNo", getSeqNo())
            .append("creatTime", getCreatTime())
            .append("remark", getRemark())
            .toString();
    }
}
