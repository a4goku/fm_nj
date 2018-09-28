package com.ufgov.sssfm.project.module.files.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ufgov.sssfm.common.utils.file.FileUploadUtils;
import com.ufgov.sssfm.framework.aspectj.lang.annotation.Log;
import com.ufgov.sssfm.framework.aspectj.lang.constant.BusinessType;
import com.ufgov.sssfm.framework.web.controller.BaseController;
import com.ufgov.sssfm.framework.web.domain.AjaxResult;
import com.ufgov.sssfm.framework.web.page.TableDataInfo;
import com.ufgov.sssfm.project.module.files.domain.Files;
import com.ufgov.sssfm.project.module.files.service.IFilesService;
import com.ufgov.sssfm.project.system.user.controller.ProfileController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 附件 信息操作处理
 *
 * @author fanhla
 * @date 2018-08-07
 */
@Api(value="附件",tags ="附件")
@Controller
@RequestMapping("/module/files")
public class FilesController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private String prefix = "module/files";

    @Autowired
    private IFilesService filesService;

    @RequiresPermissions("module:files:view")
    @GetMapping()
    public String files() {
        return prefix + "/files";
    }

    /**
     * 查询附件列表
     */
    @RequiresPermissions("module:files:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Files files) {
        startPage();
        List<Files> list = filesService.selectFilesList(files);
        return getDataTable(list);
    }

    /**
     * 新增附件
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存附件
     */
    @RequiresPermissions("module:files:add")
    @Log(title = "附件", action = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Files files) {
        return toAjax(filesService.insertFiles(files));
    }

    /**
     * 修改附件
     */
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") String fileId, ModelMap mmap) {
        Files files = filesService.selectFilesById(fileId);
        mmap.put("files", files);
        return prefix + "/edit";
    }

    /**
     * 修改保存附件
     */
    @RequiresPermissions("module:files:edit")
    @Log(title = "附件", action = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Files files) {
        return toAjax(filesService.updateFiles(files));
    }

    /**
     * 删除附件
     */
    @RequiresPermissions("module:files:remove")
    @Log(title = "附件", action = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(filesService.deleteFilesByIds(ids));
    }


    /**
     * 上传附件
     */
//    @Log(title = "个人信息", action = BusinessType.UPDATE)
    @PostMapping("/uploadFile")
    @ResponseBody
    @ApiOperation(value = "附件上传", notes = "附件上传")
    public AjaxResult updateAvatar(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest)request).getFile("file");

        Files files = new Files();

        try {
            String billId = request.getParameter("billId");
            if (!file.isEmpty()) {
                //获取文件类型
                String fileName = file.getOriginalFilename();
                //获取文件类型
                String suffix = file.getContentType();
                String avatar = FileUploadUtils.upload(file,fileName.substring(fileName.lastIndexOf(".")));
                files.setFileId(UUID.randomUUID().toString().trim().replace("-", ""));
                files.setFilePath(avatar);
                files.setFileName(fileName);
                files.setFileType(suffix);
                files.setCreatTime(new Date());
                files.setBillId(billId);
            }
            if (filesService.insertFiles(files) > 0) {
                return success();
            }
            return error();
        } catch (Exception e) {
            log.error("上传附件失败！", e);
            return error(e.getMessage());
        }
    }

    /**
     * 根据单据id查询附件列表
     */
    @PostMapping("/listByBillId")
    @ResponseBody
    @ApiOperation(value = "根据单据id查询附件列表", notes = "根据单据id查询附件列表")
    public String listByBillId(@RequestParam("billId") String billId) {
        JSONObject jsonObject=new JSONObject();
        List<Files> list = filesService.selectFilesListByBillId(billId);
        JSONArray jsonArray=(JSONArray)JSONObject.toJSON(list);
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject1=(JSONObject)jsonArray.get(i);
            SimpleDateFormat sb=new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss");
            jsonObject1.put("creatTime",sb.format(jsonObject1.get("creatTime")));
        }
        jsonObject.put("result",jsonArray);
        return jsonObject.toString();
    }

}
