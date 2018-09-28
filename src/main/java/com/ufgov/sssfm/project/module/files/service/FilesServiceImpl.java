package com.ufgov.sssfm.project.module.files.service;

import com.ufgov.sssfm.common.support.Convert;
import com.ufgov.sssfm.project.module.files.domain.Files;
import com.ufgov.sssfm.project.module.files.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 附件 服务层实现
 * 
 * @author fanhla
 * @date 2018-08-07
 */
@Service
public class FilesServiceImpl implements IFilesService
{
	@Autowired
	private FilesMapper filesMapper;

	/**
     * 查询附件信息
     * 
     * @param fileId 附件ID
     * @return 附件信息
     */
    @Override
	public Files selectFilesById(String fileId)
	{
	    return filesMapper.selectFilesById(fileId);
	}
	
	/**
     * 查询附件列表
     * 
     * @param files 附件信息
     * @return 附件集合
     */
	@Override
	public List<Files> selectFilesList(Files files)
	{
	    return filesMapper.selectFilesList(files);
	}
	
    /**
     * 新增附件
     * 
     * @param files 附件信息
     * @return 结果
     */
	@Override
	public int insertFiles(Files files)
	{
	    return filesMapper.insertFiles(files);
	}
	
	/**
     * 修改附件
     * 
     * @param files 附件信息
     * @return 结果
     */
	@Override
	public int updateFiles(Files files)
	{
	    return filesMapper.updateFiles(files);
	}

	/**
     * 删除附件对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteFilesByIds(String ids)
	{
		return filesMapper.deleteFilesByIds(Convert.toStrArray(ids));
	}


	/**
	 * 根据单据id查询附件列表
	 */
	@Override
	public List<Files>  selectFilesListByBillId(String  billId){
		return filesMapper.selectFilesListByBillId(billId);
	}
}
