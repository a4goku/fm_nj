package com.ufgov.sssfm.project.module.queryutils.controller;


import com.alibaba.fastjson.JSONObject;
import com.ufgov.sssfm.project.module.queryutils.bean.FmBankXmlLog;
import com.ufgov.sssfm.project.module.queryutils.service.FmBankXmlLogService;
import com.ufgov.sssfm.project.module.queryutils.service.QueryUtilsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 查询工具类控制层（对日志表，代码转换表的操作）
 * @Author xucy
 * @Date 2018/7/27 15:59
 **/
@RestController
@Api(value = "查询工具类(对日志表，代码转换表的操作)",tags = "查询工具类(对日志表，代码转换表的操作)")
@RequestMapping("queryutils/QueryUtilsController")
public class QueryUtilsController {

    @Autowired
    private QueryUtilsService queryUtilsService;

    @Autowired
    private FmBankXmlLogService fmBankXmlLogService;

    @PostMapping("query_combobox")
    @ApiOperation(value = "查询险种下拉框",notes = "查询险种下拉框")
    public String  query_combobox(String valset_id, String val_id){

        JSONObject jsonObject=new JSONObject();
        Map map=new HashMap();
        map.put("valset_id",valset_id);
        map.put("val_id",val_id);

        List queryList=queryUtilsService.query_combobox(map);
        jsonObject.put("result",queryList);
        return jsonObject.toString();
    }


    @PostMapping("insertFmBankXmlLog")
    @ApiOperation(value = "插入日志表",notes = "插入日志表")
    public String  insertFmBankXmlLog(){

        JSONObject jsonObject=new JSONObject();
        FmBankXmlLog fmBankXmlLog=new FmBankXmlLog();
        fmBankXmlLog.setDataId("2");
        fmBankXmlLog.setDataType("1");
        fmBankXmlLog.setErrorMessage("12123");
        fmBankXmlLog.setPayDate(new Date());
        fmBankXmlLog.setSendDate(new Date());
        fmBankXmlLog.setSerialNum(new BigDecimal(12));
        fmBankXmlLog.setRequestXml("1212312321321ss2222");
        fmBankXmlLog.setResultXml("1231324213214321");
        int  queryList=fmBankXmlLogService.insertFmBankXmlLog(fmBankXmlLog);
        jsonObject.put("result",queryList);

        return jsonObject.toString();
    }
}
