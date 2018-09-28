package com.ufgov.sssfm.project.module.queryutils.mapper;


import com.ufgov.sssfm.project.module.queryutils.bean.FmSpDaily;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FmSpDailyMapper {

    int insert(FmSpDaily record);

    List select(Map map);

}