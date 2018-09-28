package com.ufgov.sssfm.project.module.incomeinfo.controller;


import com.alibaba.fastjson.JSONObject;
import com.ufgov.sssfm.project.module.incomeinfo.service.IncomeService;
import com.ufgov.sssfm.project.module.queryutils.service.FmBankXmlLogService;
import com.ufgov.sssfm.project.module.queryutils.service.FmInterfaceUtilsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(value = "收入信息",tags="收入信息")
@RequestMapping("Incomeinfo/IncomeController")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private FmInterfaceUtilsService fmInterfaceUtilsService;

    @Autowired
    private FmBankXmlLogService fmBankXmlLogService;

     /** @Author xucy
      * @Description 征缴页面查询表格数据
      * @Date 11:03 2018/7/31
      * @Param  pageNumber 当前页, String pageSize页面大小,String AAE140险种
      *             ,String AAE003 费款所属期,String AAA116 业务编码
      * @return 
      **/
    @PostMapping("/query_ad68_pagedata")
    @ApiOperation(value="征缴页面查询表格数据", notes="征缴页面查询表格数据")
    private String  query_ad68_pagedata( String pageNumber, String pageSize,String AAE140,String AAE003,String AAA116){

        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        int page=Integer.parseInt(pageNumber);
        int rows=Integer.parseInt(pageSize);
        int pageStart=(rows*(page-1));

        map.put("PAGESTART",pageStart );
        map.put("PAGESIZE", rows);
        map.put("AAE140", AAE140);
        map.put("AAE003", AAE003);
        map.put("AAA116", AAA116);

        List query_list= incomeService.query_ad68_pagedata(map);
        int total=incomeService.query_ad68_pagedata_total(map);
        jsonObject.put("total",total);
        jsonObject.put("result",query_list);
        return jsonObject.toString();
    }

    @PostMapping("/query_ad68_pagedata_item")
    @ApiOperation(value="征缴页面子表查询表格数据", notes="征缴页面子表查询表格数据")
    private String  query_ad68_pagedata_item( String pageNumber, String pageSize,String BIE001){

        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        int page=Integer.parseInt(pageNumber);
        int rows=Integer.parseInt(pageSize);
        int pageStart=(rows*(page-1));

        map.put("PAGESTART",pageStart );
        map.put("PAGESIZE", rows);
        map.put("BIE001", BIE001);

        List query_list= incomeService.query_ad68_pagedata_item(map);
        int total=incomeService.query_ad68_pagedata_item_total(map);
        jsonObject.put("total",total);
        jsonObject.put("result",query_list);
        return jsonObject.toString();
    }
}
