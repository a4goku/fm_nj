package com.ufgov.sssfm.project.module.queryutils.service;

import com.ufgov.sssfm.project.module.queryutils.bean.FmInterfaceUtils;

import java.util.Map;

public interface FmInterfaceUtilsService {

    FmInterfaceUtils selectByPrimaryKey(Map map);
}
