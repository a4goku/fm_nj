package com.ufgov.sssfm.project.module.files.service;

import com.ufgov.sssfm.project.module.files.domain.Files;

import java.util.List;

/**
 * 附件 服务层
 * 
 * @author fanhla
 * @date 2018-08-07
 */
public interface IFilesService 
{
	/**
     * 查询附件信息
     * 
     * @param fileId 附件ID
     * @return 附件信息
     */
	public Files selectFilesById(String fileId);
	
	/**
     * 查询附件列表
     * 
     * @param files 附件信息
     * @return 附件集合
     */
	public List<Files> selectFilesList(Files files);
	
	/**
     * 新增附件
     * 
     * @param files 附件信息
     * @return 结果
     */
	public int insertFiles(Files files);
	
	/**
     * 修改附件
     * 
     * @param files 附件信息
     * @return 结果
     */
	public int updateFiles(Files files);
		
	/**
     * 删除附件信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteFilesByIds(String ids);

	/**
	 * 根据单据id查询附件列表
	 */
	public List<Files>  selectFilesListByBillId(String billId);
	
}
