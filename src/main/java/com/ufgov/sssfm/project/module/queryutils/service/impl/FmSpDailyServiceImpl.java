package com.ufgov.sssfm.project.module.queryutils.service.impl;

import com.ufgov.sssfm.project.module.queryutils.bean.FmSpDaily;
import com.ufgov.sssfm.project.module.queryutils.mapper.FmSpDailyMapper;
import com.ufgov.sssfm.project.module.queryutils.service.FmSpDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 审批日志表
 * @Author xucy
 * @Date 2018/8/11
 **/
@Service("fmSpDailyService")
public class FmSpDailyServiceImpl implements FmSpDailyService {

    @Autowired
    private FmSpDailyMapper fmSpDailyMapper;

    @Override
    public int insert(FmSpDaily record) {
        return fmSpDailyMapper.insert(record);
    }

    @Override
    public List select(Map map) {
        return fmSpDailyMapper.select(map);
    }
}
