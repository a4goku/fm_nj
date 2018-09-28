package com.ufgov.sssfm.project.module.eioDistricts.service;

import com.ufgov.sssfm.project.module.eioDistricts.domain.EioDistricts;

import java.util.List;

/**
 * 基金委托运营地区 服务层
 * 
 * @author fanhla
 * @date 2018-08-02
 */
public interface IEioDistrictsService 
{
	/**
     * 查询基金委托运营地区信息
     * 
     * @param nD 基金委托运营地区ID
     * @return 基金委托运营地区信息
     */
	public EioDistricts selectEioDistrictsById(Integer nD);
	
	/**
     * 查询基金委托运营地区列表
     * 
     * @param eioDistricts 基金委托运营地区信息
     * @return 基金委托运营地区集合
     */
	public List<EioDistricts> selectEioDistrictsList(EioDistricts eioDistricts);
	
	/**
     * 新增基金委托运营地区
     * 
     * @param eioDistricts 基金委托运营地区信息
     * @return 结果
     */
	public int insertEioDistricts(EioDistricts eioDistricts);
	
	/**
     * 修改基金委托运营地区
     * 
     * @param eioDistricts 基金委托运营地区信息
     * @return 结果
     */
	public int updateEioDistricts(EioDistricts eioDistricts);
		
	/**
     * 删除基金委托运营地区信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteEioDistrictsByIds(String ids);
	
}
