package com.ufgov.sssfm.project.module.queryutils.service.impl;

import com.ufgov.sssfm.project.module.queryutils.bean.FmInterfaceUtils;
import com.ufgov.sssfm.project.module.queryutils.mapper.FmInterfaceUtilsMapper;
import com.ufgov.sssfm.project.module.queryutils.service.FmInterfaceUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 查询易联众规定的报文header
 * @Author xucy
 * @Date 2018/07/30
 **/
@Service("fmInterfaceUtilsService")
public class FmInterfaceUtilsServiceImpl implements FmInterfaceUtilsService {

    @Autowired
    private FmInterfaceUtilsMapper fmInterfaceUtilsMapper;


    @Override
    public FmInterfaceUtils selectByPrimaryKey(Map map) {
        return fmInterfaceUtilsMapper.selectByPrimaryKey(map);
    }
}
