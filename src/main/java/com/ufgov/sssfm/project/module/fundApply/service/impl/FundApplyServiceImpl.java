package com.ufgov.sssfm.project.module.fundApply.service.impl;

import com.ufgov.sssfm.project.module.fundApply.entity.FmBkApply;
import com.ufgov.sssfm.project.module.fundApply.mapper.FunApplyMapper;
import com.ufgov.sssfm.project.module.fundApply.service.FundApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 生成用款单业务处理层
 * @Author xucy
 * @Date 2018/8/5
 **/
@Service("fundApplyService")
public class FundApplyServiceImpl implements FundApplyService {

    @Autowired
    private FunApplyMapper funApplyMapper;

    @Override
    public int insert_FmBkApply(FmBkApply fmBkApply) {
        return funApplyMapper.insert_FmBkApply(fmBkApply);
    }

    @Override
    public FmBkApply selectBKApplyByPK(String bkdId) {
        return funApplyMapper.selectBKApplyByPK(bkdId);
    }

    @Override
    public List selectAllBkApplyTime(Map map) {
        return funApplyMapper.selectAllBkApplyTime(map);
    }

    @Override
    public int deleteBKApplyByPK(String bkdId) {
        return funApplyMapper.deleteBKApplyByPK(bkdId);
    }

    @Override
    public int updateBkdSpStatus(Map map) {
        return funApplyMapper.updateBkdSpStatus(map);
    }

    @Override
    public int updateBkdPdfAddress(Map map) {
        return funApplyMapper.updateBkdPdfAddress(map);
    }

    @Override
    public int updateBkdSendStatus(String bkdId) {
        return funApplyMapper.updateBkdSendStatus(bkdId);
    }
}
