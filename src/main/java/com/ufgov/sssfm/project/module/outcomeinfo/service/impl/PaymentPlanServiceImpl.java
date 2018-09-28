package com.ufgov.sssfm.project.module.outcomeinfo.service.impl;

import com.ufgov.sssfm.project.module.outcomeinfo.mapper.PaymentPlanMapper;
import com.ufgov.sssfm.project.module.outcomeinfo.service.PaymentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 生成支付计划业务层
 * @Author xucy
 * @Date 2018/8/2
 **/
@Service("paymentPlanService")
public class PaymentPlanServiceImpl implements PaymentPlanService {

        @Autowired
        private PaymentPlanMapper paymentPlanMapper;

        @Override
        public List query_bankName() {
                return paymentPlanMapper.query_bankName();
        }

        @Override
        public List query_bankInfo(Map map) {
                return paymentPlanMapper.query_bankInfo(map);
        }

        @Override
        public int update_Gra_BankInfo(Map map) {
                return paymentPlanMapper.update_Gra_BankInfo(map);
        }

        @Override
        public int update_createPaymentPlan(Map map) {
                return paymentPlanMapper.update_createPaymentPlan(map);
        }
}
