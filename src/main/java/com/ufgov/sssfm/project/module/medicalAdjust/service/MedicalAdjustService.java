package com.ufgov.sssfm.project.module.medicalAdjust.service;

import com.ufgov.sssfm.project.module.medicalAdjust.entity.FmMedicalAdjust;
import com.ufgov.sssfm.project.module.medicalAdjust.entity.FmMedicalAdjustItem;

import java.util.List;
import java.util.Map;

public interface MedicalAdjustService {
    //查询养老调剂金主表
    List query_medicalAdjust_pagedata(Map map);

    List query_medicalAdjust_item_table(String id);

    List query_medicalAdjust_item(String id);

    int delete_medicalAdjust(String id);

    int delete_medicalAdjust_item(String id);

    int shenhe_medicalAdjust(Map queryMap);

    int insert_fmMedicalAdjust(FmMedicalAdjust fmMedicalAdjust);

    int insert_fmMedicalAdjustItem(FmMedicalAdjustItem fmMedicalAdjustItem);

    int  deletefmMedicalAdjustItemByPK(String id);
}
