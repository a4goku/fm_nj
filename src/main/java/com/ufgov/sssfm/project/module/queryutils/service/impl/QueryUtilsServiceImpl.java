package com.ufgov.sssfm.project.module.queryutils.service.impl;

import com.ufgov.sssfm.project.module.queryutils.mapper.QueryUtilsMapper;
import com.ufgov.sssfm.project.module.queryutils.service.QueryUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 查询工具类业务层（对日志表，代码转换表的操作）
 * @Author xucy
 * @Date 2018/7/27 15:59
 **/
@Service("queryUtilsService")
public class QueryUtilsServiceImpl implements QueryUtilsService {

    @Autowired
    private QueryUtilsMapper queryUtilsMapper;
    public List query_combobox(Map map){

        return queryUtilsMapper.query_combobox(map);
    }

}
