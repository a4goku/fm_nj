package com.ufgov.sssfm.project.module.medicalAdjust.mapper;

import com.ufgov.sssfm.project.module.medicalAdjust.entity.FmMedicalAdjust;
import com.ufgov.sssfm.project.module.medicalAdjust.entity.FmMedicalAdjustItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description 医疗保险持久层
 * @Author xucy
 * @Date 2018/8/11
 **/
@Mapper
public interface MedicalAdjustMapper {
    List query_medicalAdjust_pagedata(Map map);

    List query_medicalAdjust_item(String id);

    List query_medicalAdjust_item_table(String id);

    int delete_medicalAdjust_item(String id);

    int delete_medicalAdjust(String id);

    int shenhe_medicalAdjust(Map queryMap);

    int deletefmMedicalAdjustItemByPK(String id);

    int insert_fmMedicalAdjustItem(FmMedicalAdjustItem fmMedicalAdjustItem);

    int insert_fmMedicalAdjust(FmMedicalAdjust fmMedicalAdjust);
}
