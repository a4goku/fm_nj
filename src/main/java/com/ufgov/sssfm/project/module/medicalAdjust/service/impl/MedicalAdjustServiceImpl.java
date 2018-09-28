package com.ufgov.sssfm.project.module.medicalAdjust.service.impl;

import com.ufgov.sssfm.project.module.medicalAdjust.entity.FmMedicalAdjust;
import com.ufgov.sssfm.project.module.medicalAdjust.entity.FmMedicalAdjustItem;
import com.ufgov.sssfm.project.module.medicalAdjust.mapper.MedicalAdjustMapper;
import com.ufgov.sssfm.project.module.medicalAdjust.service.MedicalAdjustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 医疗调剂金业务层
 * @Author xucy
 * @Date 2018/8/11
 **/
@Service("medicalAdjustService")
public class MedicalAdjustServiceImpl implements MedicalAdjustService {

    @Autowired
    private MedicalAdjustMapper medicalAdjustMapper;
    @Override
    public List query_medicalAdjust_pagedata(Map map){
        return medicalAdjustMapper.query_medicalAdjust_pagedata(map);
    }
    @Override
    public List  query_medicalAdjust_item_table(String id){
        return medicalAdjustMapper.query_medicalAdjust_item_table(id);
    }
    @Override
    public List  query_medicalAdjust_item(String id){
        return medicalAdjustMapper.query_medicalAdjust_item(id);
    }
    @Override
    public int delete_medicalAdjust(String id){
        return medicalAdjustMapper.delete_medicalAdjust(id);
    }
    @Override
    public int delete_medicalAdjust_item(String id){
        return medicalAdjustMapper.delete_medicalAdjust_item(id);
    }
    @Override
    public int  shenhe_medicalAdjust(Map queryMap){
        return medicalAdjustMapper.shenhe_medicalAdjust(queryMap);
    }
    @Override
    public int  insert_fmMedicalAdjust(FmMedicalAdjust fmMedicalAdjust){
        return medicalAdjustMapper.insert_fmMedicalAdjust(fmMedicalAdjust);
    }
    @Override
    public int  insert_fmMedicalAdjustItem(FmMedicalAdjustItem fmMedicalAdjustItem){
        return medicalAdjustMapper.insert_fmMedicalAdjustItem(fmMedicalAdjustItem);
    }
    @Override
    public int  deletefmMedicalAdjustItemByPK(String id){
        return medicalAdjustMapper.deletefmMedicalAdjustItemByPK(id);
    }
}
