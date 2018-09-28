package com.ufgov.sssfm.project.module.outcomeinfo.service;


import java.util.List;
import java.util.Map;

public interface PaymentPlanService {

        List query_bankName();

        //获取银行信息,做下拉框
        List query_bankInfo(Map map);

        //修改主表信息的数据的银行信息
        int update_Gra_BankInfo(Map map);

        //修改生成的支付计划批次号和状态
        int update_createPaymentPlan(Map map);
}
