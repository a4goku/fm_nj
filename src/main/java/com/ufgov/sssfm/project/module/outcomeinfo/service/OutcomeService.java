package com.ufgov.sssfm.project.module.outcomeinfo.service;

import java.util.List;
import java.util.Map;

public interface OutcomeService {

    List query_jf07_pagedata(Map map);

    int query_jf07_pagedata_total(Map map);

    //查询征缴子表记录
    List query_jf07_pagedata_item(Map map);

    int query_jf07_pagedata_item_total(Map map);

    //查询孙子表记录
    List query_jf07_pagedata_child(Map map);

    int query_jf07_pagedata_child_total(Map map);

    String[] collageDataToLocal(Map map);

    //根据主表主键修改发送状态
    int updateGraSendStatusByPk(Map map);
}
