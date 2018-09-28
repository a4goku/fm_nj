package com.ufgov.sssfm.project.module.outcomeinfo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ufgov.sssfm.project.module.outcomeinfo.service.PaymentPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 生成支付计划控制层
 * @Author xucy
 * @Date 2018/8/2
 **/
@RestController
@Api(value = "生成支付计划" ,tags = "生成支付计划")
@RequestMapping("outcomeinfo/PaymentPlanController")
public class PaymentPlanController {

        @Autowired
        private PaymentPlanService paymentPlanService;

        //获取银行名称下拉框
        @PostMapping("/query_bankName")
        @ApiOperation(value = "获取银行名称下拉框",notes = "获取银行名称下拉框")
        public String  query_bankName(){
            JSONObject jsonObject=new JSONObject();
            List resultList= paymentPlanService.query_bankName();
            jsonObject.put("result",resultList);
            return jsonObject.toString();
        }

        //获取银行信息
        @PostMapping("/query_bankInfo")
        @ApiOperation(value = "获取银行信息",notes = "获取银行信息")
        public String  query_bankInfo(String bankCode){
            JSONObject jsonObject=new JSONObject();
            Map map=new HashMap();
            map.put("bankCode",bankCode);
            List resultList= paymentPlanService.query_bankInfo(map);
            jsonObject.put("result",resultList);
            return jsonObject.toString();
        }

        //修改主表信息的数据的银行信息
        @PostMapping("/update_Gra_BankInfo")
        public String update_Gra_BankInfo(String AAZ031Json,String bankCode,String bankAccount){

            List AAZ031List = JSONArray.parseArray(AAZ031Json);
            JSONObject jsonObject=new JSONObject();
            try{
                for(int i=0;i<AAZ031List.size();i++){
                    Map map=new HashMap();
                    String AAZ031=AAZ031List.get(i)+"";
                    map.put("AAZ031",AAZ031);
                    map.put("bankCode",bankCode);
                    map.put("bankAccount",bankAccount);
                    paymentPlanService.update_Gra_BankInfo(map);
                }
            }catch (Exception e){
                jsonObject.put("result","修改数据失败"+e);
                return jsonObject.toString();
            }
            jsonObject.put("result","修改数据成功");
            return jsonObject.toString();
        }

        //生成支付计划
        @PostMapping("/update_createPaymentPlan")
        public String update_createPaymentPlan(String AAZ031Json){
            List AAZ031List = JSONArray.parseArray(AAZ031Json);
            JSONObject jsonObject=new JSONObject();
            try{
                Map updateMap=new HashMap();
                String BatchNo=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"001" ;
                updateMap.put("BatchNo",BatchNo);
                for(int i=0;i<AAZ031List.size();i++){
                    String AAZ031=AAZ031List.get(i)+"";
                    updateMap.put("AAZ031",AAZ031);
                    paymentPlanService.update_createPaymentPlan(updateMap);
                }
            }catch (Exception e){
                jsonObject.put("result","生成支付计划失败"+e);
                return jsonObject.toString();
            }

            jsonObject.put("result","生成支付计划成功");
            return jsonObject.toString();
        }
}
