package com.ufgov.sssfm.project.module.eioDistricts.domain;

import com.ufgov.sssfm.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 基金委托运营地区表 sys_eio_districts
 * 
 * @author fanhla
 * @date 2018-08-02
 */
public class EioDistricts extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 年度 */
	private Integer nD;
	/** 行政区划代码 */
	private String distCode;
	/** 行政区划名称 */
	private String distName;
	/** 单位代码 */
	private String coCode;
	/** 单位名称 */
	private String coName;
	/** 是否财政直管县 */
	private String isPfManage;
	/** 是否贫困县 */
	private String isPoor;
	/** 贫困县类别 */
	private String poorType;
	/** 是否资源枯竭工矿区 */
	private String isResdepArea;
	/** 录入人ID */
	private String inputorId;
	/** 录入人 */
	private String inputorName;
	/** 录入日期 */
	private Date cDATE;
	/** 备注 */
	private String rEMARK;
	/**  */
	private String createBatch;
	/**  */
	private String splitBatch;
	/**  */
	private String logicPDistCode;
	/** 是否可用 */
	private String isUsed;
	/** 父代码 */
	private String pDistCode;
	/** 级次 */
	private String levelNum;
	/** 是否末级 */
	private String isLeaf;
	/** 国定贫困县 */
	private String isPoorCountry;
	/** 省定贫困县 */
	private String isPoorPro;
	/** 燕山太行山连片贫困县 */
	private String isPoorOther;

	public void setND(Integer nD) 
	{
		this.nD = nD;
	}

	public Integer getND() 
	{
		return nD;
	}
	public void setDistCode(String distCode) 
	{
		this.distCode = distCode;
	}

	public String getDistCode() 
	{
		return distCode;
	}
	public void setDistName(String distName) 
	{
		this.distName = distName;
	}

	public String getDistName() 
	{
		return distName;
	}
	public void setCoCode(String coCode) 
	{
		this.coCode = coCode;
	}

	public String getCoCode() 
	{
		return coCode;
	}
	public void setCoName(String coName) 
	{
		this.coName = coName;
	}

	public String getCoName() 
	{
		return coName;
	}
	public void setIsPfManage(String isPfManage) 
	{
		this.isPfManage = isPfManage;
	}

	public String getIsPfManage() 
	{
		return isPfManage;
	}
	public void setIsPoor(String isPoor) 
	{
		this.isPoor = isPoor;
	}

	public String getIsPoor() 
	{
		return isPoor;
	}
	public void setPoorType(String poorType) 
	{
		this.poorType = poorType;
	}

	public String getPoorType() 
	{
		return poorType;
	}
	public void setIsResdepArea(String isResdepArea) 
	{
		this.isResdepArea = isResdepArea;
	}

	public String getIsResdepArea() 
	{
		return isResdepArea;
	}
	public void setInputorId(String inputorId) 
	{
		this.inputorId = inputorId;
	}

	public String getInputorId() 
	{
		return inputorId;
	}
	public void setInputorName(String inputorName) 
	{
		this.inputorName = inputorName;
	}

	public String getInputorName() 
	{
		return inputorName;
	}
	public void setCDATE(Date cDATE) 
	{
		this.cDATE = cDATE;
	}

	public Date getCDATE() 
	{
		return cDATE;
	}
	public void setREMARK(String rEMARK) 
	{
		this.rEMARK = rEMARK;
	}

	public String getREMARK() 
	{
		return rEMARK;
	}
	public void setCreateBatch(String createBatch) 
	{
		this.createBatch = createBatch;
	}

	public String getCreateBatch() 
	{
		return createBatch;
	}
	public void setSplitBatch(String splitBatch) 
	{
		this.splitBatch = splitBatch;
	}

	public String getSplitBatch() 
	{
		return splitBatch;
	}
	public void setLogicPDistCode(String logicPDistCode) 
	{
		this.logicPDistCode = logicPDistCode;
	}

	public String getLogicPDistCode() 
	{
		return logicPDistCode;
	}
	public void setIsUsed(String isUsed) 
	{
		this.isUsed = isUsed;
	}

	public String getIsUsed() 
	{
		return isUsed;
	}
	public void setPDistCode(String pDistCode) 
	{
		this.pDistCode = pDistCode;
	}

	public String getPDistCode() 
	{
		return pDistCode;
	}
	public void setLevelNum(String levelNum) 
	{
		this.levelNum = levelNum;
	}

	public String getLevelNum() 
	{
		return levelNum;
	}
	public void setIsLeaf(String isLeaf) 
	{
		this.isLeaf = isLeaf;
	}

	public String getIsLeaf() 
	{
		return isLeaf;
	}
	public void setIsPoorCountry(String isPoorCountry) 
	{
		this.isPoorCountry = isPoorCountry;
	}

	public String getIsPoorCountry() 
	{
		return isPoorCountry;
	}
	public void setIsPoorPro(String isPoorPro) 
	{
		this.isPoorPro = isPoorPro;
	}

	public String getIsPoorPro() 
	{
		return isPoorPro;
	}
	public void setIsPoorOther(String isPoorOther) 
	{
		this.isPoorOther = isPoorOther;
	}

	public String getIsPoorOther() 
	{
		return isPoorOther;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("nD", getND())
            .append("distCode", getDistCode())
            .append("distName", getDistName())
            .append("coCode", getCoCode())
            .append("coName", getCoName())
            .append("isPfManage", getIsPfManage())
            .append("isPoor", getIsPoor())
            .append("poorType", getPoorType())
            .append("isResdepArea", getIsResdepArea())
            .append("inputorId", getInputorId())
            .append("inputorName", getInputorName())
            .append("cDATE", getCDATE())
            .append("rEMARK", getREMARK())
            .append("createBatch", getCreateBatch())
            .append("splitBatch", getSplitBatch())
            .append("logicPDistCode", getLogicPDistCode())
            .append("isUsed", getIsUsed())
            .append("pDistCode", getPDistCode())
            .append("levelNum", getLevelNum())
            .append("isLeaf", getIsLeaf())
            .append("isPoorCountry", getIsPoorCountry())
            .append("isPoorPro", getIsPoorPro())
            .append("isPoorOther", getIsPoorOther())
            .toString();
    }
}
