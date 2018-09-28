package com.ufgov.sssfm.project.module.queryutils.service;

import com.ufgov.sssfm.project.module.queryutils.bean.FmSpDaily;

import java.util.List;
import java.util.Map;

public interface FmSpDailyService {

    int insert(FmSpDaily record);

    List select(Map map);
}
