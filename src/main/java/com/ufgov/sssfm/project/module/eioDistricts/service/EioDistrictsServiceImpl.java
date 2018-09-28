package com.ufgov.sssfm.project.module.eioDistricts.service;

import com.ufgov.sssfm.common.support.Convert;
import com.ufgov.sssfm.project.module.eioDistricts.domain.EioDistricts;
import com.ufgov.sssfm.project.module.eioDistricts.mapper.EioDistrictsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金委托运营地区 服务层实现
 * 
 * @author fanhla
 * @date 2018-08-02
 */
@Service
public class EioDistrictsServiceImpl implements IEioDistrictsService 
{
	@Autowired
	private EioDistrictsMapper eioDistrictsMapper;

	/**
     * 查询基金委托运营地区信息
     * 
     * @param nD 基金委托运营地区ID
     * @return 基金委托运营地区信息
     */
    @Override
	public EioDistricts selectEioDistrictsById(Integer nD)
	{
	    return eioDistrictsMapper.selectEioDistrictsById(nD);
	}
	
	/**
     * 查询基金委托运营地区列表
     * 
     * @param eioDistricts 基金委托运营地区信息
     * @return 基金委托运营地区集合
     */
	@Override
	public List<EioDistricts> selectEioDistrictsList(EioDistricts eioDistricts)
	{
	    return eioDistrictsMapper.selectEioDistrictsList(eioDistricts);
	}
	
    /**
     * 新增基金委托运营地区
     * 
     * @param eioDistricts 基金委托运营地区信息
     * @return 结果
     */
	@Override
	public int insertEioDistricts(EioDistricts eioDistricts)
	{
	    return eioDistrictsMapper.insertEioDistricts(eioDistricts);
	}
	
	/**
     * 修改基金委托运营地区
     * 
     * @param eioDistricts 基金委托运营地区信息
     * @return 结果
     */
	@Override
	public int updateEioDistricts(EioDistricts eioDistricts)
	{
	    return eioDistrictsMapper.updateEioDistricts(eioDistricts);
	}

	/**
     * 删除基金委托运营地区对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteEioDistrictsByIds(String ids)
	{
		return eioDistrictsMapper.deleteEioDistrictsByIds(Convert.toStrArray(ids));
	}
	
}
