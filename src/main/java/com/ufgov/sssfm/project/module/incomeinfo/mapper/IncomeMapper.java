package com.ufgov.sssfm.project.module.incomeinfo.mapper;


import com.ufgov.sssfm.project.module.incomeinfo.entity.Ad68Fa;
import com.ufgov.sssfm.project.module.incomeinfo.entity.Ad68Son;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IncomeMapper {

    List query_ad68_pagedata(Map map);

    int query_ad68_pagedata_total(Map map);

    List query_ad68_pagedata_item(Map map);

    int query_ad68_pagedata_item_total(Map map);

    int insert_Fa(Ad68Fa ad68Fa);

    int insert_Son(Ad68Son ad68Son);
}
