package com.ufgov.sssfm.project.module.outcomeinfo.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaymentPlanMapper {
        //获取银行名称，做下拉框
        List query_bankName();

        //获取银行信息,做下拉框
        List query_bankInfo(Map map);

        //修改主表信息的数据的银行信息
        int update_Gra_BankInfo(Map map);

        //修改生成的支付计划批次号和状态
        int update_createPaymentPlan(Map map);

}
