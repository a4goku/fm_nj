package com.ufgov.sssfm.project.module.queryutils.mapper;

import com.ufgov.sssfm.project.module.queryutils.bean.FmBankXmlLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FmBankXmlLogMapper {
    List selectFmBankXmlLog(Map map);

    int insertFmBankXmlLog(FmBankXmlLog fmBankXmlLog);
}
