package com.ufgov.sssfm.project.module.queryutils.mapper;


import com.ufgov.sssfm.project.module.queryutils.bean.FmInterfaceUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface FmInterfaceUtilsMapper {

    FmInterfaceUtils selectByPrimaryKey(Map map);

}
