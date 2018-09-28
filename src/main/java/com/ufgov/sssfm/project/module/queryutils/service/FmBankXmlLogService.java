package com.ufgov.sssfm.project.module.queryutils.service;

import com.ufgov.sssfm.project.module.queryutils.bean.FmBankXmlLog;

import java.util.List;
import java.util.Map;

public interface FmBankXmlLogService {

    List selectFmBankXmlLog(Map map);

    int insertFmBankXmlLog(FmBankXmlLog fmBankXmlLog);
}
