package com.ufgov.sssfm.project.module.pensionAdjust.mapper;

import com.ufgov.sssfm.project.module.pensionAdjust.entity.FmAdjustGold;
import com.ufgov.sssfm.project.module.pensionAdjust.entity.FmAdjustGoldItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description 养老调剂金持久层
 * @Author xucy
 * @Date 2018/8/9
 **/
@Mapper
public interface PensionAdjustMapper {
    int deleteByPrimaryKey(String id);

    int insert(FmAdjustGold record);

    int insertSelective(FmAdjustGold record);

    FmAdjustGold selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FmAdjustGold record);

    int updateByPrimaryKey(FmAdjustGold record);

    int insert_fmAdjustGoldItem(FmAdjustGoldItem fmAdjustGoldItem);
    int insert_fmAdjustGold(FmAdjustGold fmAdjustGold);
    int deletepensionAdjustMapperZhuByPK(String id);
    int deletepensionAdjustMapperByPK(String id);
    List query_persionAdjust_pagedata(Map map);

    List query_persionAdjust_item(String id);

    //List update_fmAdjustGold(FmAdjustGold fmAdjustGold);

    List query_persionAdjust_item_table(String id);

    int delete_persionAdjust(String id);

    int delete_persionAdjust_item(String id);



    int update_fmAdjustGold(String id, String spStatus);

    int shenhe_persionAdjust(Map queryMap);
}
