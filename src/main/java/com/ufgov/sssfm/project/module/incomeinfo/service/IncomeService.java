package com.ufgov.sssfm.project.module.incomeinfo.service;


import java.util.List;
import java.util.Map;

/** @Author xucy
 * @Description 收入要素业务处理层
 * @Date 21:22 2018/7/26
 * @Param
 * @return
 **/
public interface IncomeService {


    List query_ad68_pagedata(Map map);

    int query_ad68_pagedata_total(Map map);

    List query_ad68_pagedata_item(Map map);

    int query_ad68_pagedata_item_total(Map map);


}
