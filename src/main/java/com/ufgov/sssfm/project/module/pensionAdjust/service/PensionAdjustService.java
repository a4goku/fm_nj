package com.ufgov.sssfm.project.module.pensionAdjust.service;

import com.ufgov.sssfm.project.module.pensionAdjust.entity.FmAdjustGold;
import com.ufgov.sssfm.project.module.pensionAdjust.entity.FmAdjustGoldItem;

import java.util.List;
import java.util.Map;

public interface PensionAdjustService {

   //插入养老调剂金主表数据
    int insert_fmAdjustGoldItem(FmAdjustGoldItem fmAdjustGoldItem);
    //删除养老调剂金子表根据id
    int deletefmAdjustGoldItemByPK(String id);
    int insert_fmAdjustGold(FmAdjustGold fmAdjustGold);
    //删除养老调剂金主表根据id
    int deletefmAdjustGoldByPK(String id);
   //查询养老调剂金主表
    List query_persionAdjust_pagedata(Map map);
   //查询养老调剂金子表
    List query_persionAdjust_item(String id);
    //提交
    //List tijiao_persionAdjust(FmAdjustGold fmAdjustGold);

    List query_persionAdjust_item_table(String id);

    int delete_persionAdjust(String id);

    int delete_persionAdjust_item(String id);
    int shenhe_persionAdjust(Map queryMap);
}
