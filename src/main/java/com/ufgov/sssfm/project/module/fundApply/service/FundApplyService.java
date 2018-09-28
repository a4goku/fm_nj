package com.ufgov.sssfm.project.module.fundApply.service;

import com.ufgov.sssfm.project.module.fundApply.entity.FmBkApply;

import java.util.List;
import java.util.Map;

public interface FundApplyService {

    int insert_FmBkApply(FmBkApply fmBkApply);

    FmBkApply selectBKApplyByPK(String bkdId);

    List selectAllBkApplyTime(Map map);

    int deleteBKApplyByPK(String bkdId);

    int updateBkdSpStatus(Map map);

    int updateBkdPdfAddress(Map map);

    int updateBkdSendStatus(String bkdId);
}
