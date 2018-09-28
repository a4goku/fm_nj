package com.ufgov.sssfm.project.module.outcomeinfo.mapper;

import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07Fa;
import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07GraFa;
import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07Son;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OutcomeMapper {
    //查询征缴主表记录
    List query_jf07_pagedata(Map map);

    int query_jf07_pagedata_total(Map map);

    //查询征缴子表记录
    List query_jf07_pagedata_item(Map map);

    int query_jf07_pagedata_item_total(Map map);

    //查询孙子表记录
    List query_jf07_pagedata_child(Map map);

    int query_jf07_pagedata_child_total(Map map);

    //根据主表主键查出唯一一条记录
    Jf07GraFa selectJf07GraFaByPk(Map map);

    //查出主表记录
    List selectJf07GraFaS(Map map);

    //根据主表主键查出子表中的数据
    List selectJf07FaByGraPk(Map map);

    //根据子表主键查出孙子表中的数据
    List selectJf07SonByFaPk(Map map);

    //根据主表主键修改发送状态
    int updateGraSendStatusByPk(Map map);

    //插入主表
    int insert_Gra(Jf07GraFa jf07GraFa);
    //插入子表
    int insert_Fa(Jf07Fa jf07Fa);
    //插入孙子表
    int insert_Son(Jf07Son jf07Son);
}
