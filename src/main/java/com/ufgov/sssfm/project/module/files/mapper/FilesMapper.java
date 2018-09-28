package com.ufgov.sssfm.project.module.files.mapper;

import com.ufgov.sssfm.project.module.files.domain.Files;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 附件 数据层
 * 
 * @author fanhla
 * @date 2018-08-07
 */
@Mapper
public interface FilesMapper 
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
     * 删除附件
     * 
     * @param fileId 附件ID
     * @return 结果
     */
	public int deleteFilesById(String fileId);
	
	/**
     * 批量删除附件
     * 
     * @param fileIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteFilesByIds(String[] fileIds);

	/**
	 * 根据单据id查询附件列表
	 */
	public List<Files>  selectFilesListByBillId(String billId);
	
}