package com.ufgov.sssfm.project.module.incomeinfo.service.impl;

import com.ufgov.sssfm.project.module.incomeinfo.mapper.IncomeMapper;
import com.ufgov.sssfm.project.module.incomeinfo.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("incomeService")
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeMapper incomeMapper;

    @Override
    public List query_ad68_pagedata(Map map) {
        return incomeMapper.query_ad68_pagedata(map);
    }

    @Override
    public int query_ad68_pagedata_total(Map map) {
        return incomeMapper.query_ad68_pagedata_total(map);
    }

    @Override
    public List query_ad68_pagedata_item(Map map) {
        return incomeMapper.query_ad68_pagedata_item(map);
    }

    @Override
    public int query_ad68_pagedata_item_total(Map map) {
        return incomeMapper.query_ad68_pagedata_item_total(map);
    }
}
