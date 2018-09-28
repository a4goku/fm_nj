package com.ufgov.sssfm.project.module.fundApply.mapper;

import com.ufgov.sssfm.project.module.fundApply.entity.FmBkApply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FunApplyMapper {

    int insert_FmBkApply(FmBkApply fmBkApply);

    FmBkApply selectBKApplyByPK(String bkdId);

    List selectAllBkApplyTime(Map map);

    int deleteBKApplyByPK(String bkdId);

    int updateBkdSpStatus(Map map);

    int updateBkdPdfAddress(Map map);

    int updateBkdSendStatus(String bkdId);
}
