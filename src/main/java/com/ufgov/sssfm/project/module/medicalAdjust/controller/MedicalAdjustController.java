package com.ufgov.sssfm.project.module.medicalAdjust.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ufgov.sssfm.project.module.medicalAdjust.entity.FmMedicalAdjust;
import com.ufgov.sssfm.project.module.medicalAdjust.entity.FmMedicalAdjustItem;
import com.ufgov.sssfm.project.module.medicalAdjust.service.MedicalAdjustService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 医疗调剂金
 * @Author xucy
 * @Date 2018/8/11
 **/
@RestController
@Api(value = "医疗调剂金" ,tags = "医疗调剂金")
@RequestMapping("medicalAdjust/MedicalAdjustController")
public class MedicalAdjustController {
    @Autowired
    private MedicalAdjustService medicalAdjustService;
    //医疗调剂金查询主表
    @PostMapping("/query_medicalAdjust_pagedata")
    public String  query_medicalAdjust_pagedata(String timeStart, String timeEnd,String sendStatus){
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap();
        map.put("timeStart", timeStart);
        map.put("timeEnd", timeEnd);
        map.put("sendStatus", sendStatus);
        List query_list= medicalAdjustService.query_medicalAdjust_pagedata(map);
        jsonObject.put("result",query_list);
        return jsonObject.toString();
    }
    //医疗调剂金修改查询主子表
    @PostMapping("/query_medicalAdjust_item")
    public String  query_medicalAdjust_item(String id){
        JSONObject jsonObject = new JSONObject();
        List query_list= medicalAdjustService.query_medicalAdjust_item(id);
        List TABLE = medicalAdjustService.query_medicalAdjust_item_table(id);
        //query_list.add(TABLE);
        jsonObject.put("result",query_list);
        jsonObject.put("TABLE",TABLE);
        return jsonObject.toString();
    }
    //医疗调剂金删除
    @PostMapping("/delete_medicalAdjust")
    public String  delete_medicalAdjust(String id){
        JSONObject jsonObject = new JSONObject();
        try{
            medicalAdjustService.delete_medicalAdjust(id);
            medicalAdjustService.delete_medicalAdjust_item(id);
        }catch (Exception e){
            jsonObject.put("result","数据库删除失败");
            return jsonObject.toString();
        }
        jsonObject.put("result","医疗调剂金单删除成功");
        return jsonObject.toString();
    }
    //修改拨款单的审批状态
    @PostMapping("/shenhetijiao_medicalAdjust")
    public String updateBkdSpStatus(String id,String spStatus,String sp_name,String sp_status_name){
        JSONObject jsonObject=new JSONObject();
        Map queryMap=new HashMap();
        queryMap.put("id",id);
        queryMap.put("sp_status",spStatus);
        queryMap.put("sp_name",sp_name);
       /* SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        String shdate =(df.format(new Date())).toString();
        if(sp_status_name.equals("审核")){
            queryMap.put("shdate",shdate);
        }*/
        try{
            medicalAdjustService.shenhe_medicalAdjust(queryMap);
        }catch (Exception e){
            jsonObject.put("result",sp_status_name+"失败");
            return jsonObject.toString();
        }
        jsonObject.put("result",sp_status_name+"成功");
        return jsonObject.toString();
    }

   @PostMapping("/insert_medicalAdjust")
    //新增和编辑  的保存
    public String insert_medicalAdjust(String medicalAdjustJson){
        JSONObject jsonObject=new JSONObject();
        try{
            FmMedicalAdjust fmMedicalAdjust = JSON.parseObject(medicalAdjustJson,FmMedicalAdjust.class);
            JSONObject jsonObject1=(JSONObject)JSONArray.parseObject(medicalAdjustJson);
            JSONArray personObject = (JSONArray) jsonObject1.get("table");
            //判断是否存在拨款单Id,如果存在，则先删除，后插入。不存在，直接插入
            String id = (UUID.randomUUID()+"").replaceAll("-","");
            if((fmMedicalAdjust.getId().length()<=0)){
                fmMedicalAdjust.setId(id);
                fmMedicalAdjust.setSpStatus("00");
                medicalAdjustService.insert_fmMedicalAdjust(fmMedicalAdjust);
                for(int i=0;i<personObject.size();i++){
                    String personOb = personObject.get(i)+"";
                    FmMedicalAdjustItem fmMedicalAdjustItem = JSON.parseObject(personOb,FmMedicalAdjustItem.class);
                    fmMedicalAdjustItem.setId(id);
                    String itemid = (UUID.randomUUID()+"").replaceAll("-","");
                    fmMedicalAdjustItem.setItemid(itemid);
                    medicalAdjustService.insert_fmMedicalAdjustItem(fmMedicalAdjustItem);
                }
            }else{
                //删除
                medicalAdjustService.delete_medicalAdjust(fmMedicalAdjust.getId());
                //插入
                fmMedicalAdjust.setSpStatus("00");
                medicalAdjustService.insert_fmMedicalAdjust(fmMedicalAdjust);
                medicalAdjustService.deletefmMedicalAdjustItemByPK(fmMedicalAdjust.getId());
                //插入
                for( int m=0;m<personObject.size();m++){
                    String personOb = personObject.get(m)+"";
                    FmMedicalAdjustItem fmAdjustGoldItem = JSON.parseObject(personOb,FmMedicalAdjustItem.class);
                    fmAdjustGoldItem.setId(fmMedicalAdjust.getId());
                    String itemid = (UUID.randomUUID()+"").replaceAll("-","");
                    fmAdjustGoldItem.setItemid(itemid);
                    medicalAdjustService.insert_fmMedicalAdjustItem(fmAdjustGoldItem);
                }
            }

        }catch (Exception e){
            jsonObject.put("result","插入数据库失败");
            return jsonObject.toString();
        }
        jsonObject.put("result","生成医疗调剂金单成功");
        return jsonObject.toString();

    }

}
