package com.ufgov.sssfm.project.module.queryutils.service.impl;

import com.ufgov.sssfm.project.module.queryutils.bean.FmBankXmlLog;
import com.ufgov.sssfm.project.module.queryutils.mapper.FmBankXmlLogMapper;
import com.ufgov.sssfm.project.module.queryutils.service.FmBankXmlLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 对日志表的操作
 * @Author  xucy
 * @Date
 **/

@Service("fmBankXmlLogService")
public class FmBankXmlLogServiceImpl implements FmBankXmlLogService {

    @Autowired
    private FmBankXmlLogMapper fmBankXmlLogMapper;
    @Override
    public List selectFmBankXmlLog(Map map) {
        return fmBankXmlLogMapper.selectFmBankXmlLog(map);
    }

    @Override
    public int insertFmBankXmlLog(FmBankXmlLog fmBankXmlLog) {
        return fmBankXmlLogMapper.insertFmBankXmlLog(fmBankXmlLog);
    }
}
