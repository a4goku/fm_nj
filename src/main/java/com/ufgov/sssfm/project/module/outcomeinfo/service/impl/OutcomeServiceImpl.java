package com.ufgov.sssfm.project.module.outcomeinfo.service.impl;

import com.ufgov.sssfm.common.utils.nx.AnalysisMsgUtil;
import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07Fa;
import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07GraFa;
import com.ufgov.sssfm.project.module.outcomeinfo.bean.Jf07Son;
import com.ufgov.sssfm.project.module.outcomeinfo.mapper.OutcomeMapper;
import com.ufgov.sssfm.project.module.outcomeinfo.service.OutcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description 支出模块业务层
 * @Author xucy
 * @Date 2018/7/30 14:42
 **/

@Service("outcomeService")
public class OutcomeServiceImpl implements OutcomeService {

    @Autowired
    private OutcomeMapper outcomeMapper;


    @Override
    public List query_jf07_pagedata(Map map) {
        return outcomeMapper.query_jf07_pagedata(map);
    }

    @Override
    public int query_jf07_pagedata_total(Map map) {
        return outcomeMapper.query_jf07_pagedata_total(map);
    }


    @Override
    public List query_jf07_pagedata_item(Map map) {
        return outcomeMapper.query_jf07_pagedata_item(map);
    }

    @Override
    public int query_jf07_pagedata_item_total(Map map) {
        return outcomeMapper.query_jf07_pagedata_item_total(map);
    }

    @Override
    public List query_jf07_pagedata_child(Map map) {
        return outcomeMapper.query_jf07_pagedata_child(map);
    }

    @Override
    public int query_jf07_pagedata_child_total(Map map) {
        return outcomeMapper.query_jf07_pagedata_child_total(map);
    }

    //组织数据到本地路径
    public String[] collageDataToLocal(Map map){
        String[] result=new String[2];
        List listAAZ031 = (List) map.get("AAZ031LIST");
        List<Jf07GraFa> jf07GraFas=new ArrayList<Jf07GraFa>();
        try{
            for(int i=0;i<listAAZ031.size();i++){
                String AAZ031=listAAZ031.get(i)+"";
                map.put("AAZ031", AAZ031);
                //根据主表主键查出主表数据
                Jf07GraFa jf07GraFa= outcomeMapper.selectJf07GraFaByPk(map);

                //根据主表与子表关联
                List<Jf07Fa> jf07FaList=outcomeMapper.selectJf07FaByGraPk(map);
                for(int j=0;j<jf07FaList.size();j++){
                    Jf07Fa jf07Fa=(Jf07Fa) jf07FaList.get(j);

                    //得到孙子表数据，放到子表数据中
                    String AAZ220=jf07Fa.getAaz220();
                    map.put("AAZ220", AAZ220);
                    List<Jf07Son> jf07SonList=outcomeMapper.selectJf07SonByFaPk(map);
                    jf07Fa.setJf07SonList(jf07SonList);
                }
                //将数据放入到主表中
                jf07GraFa.setJf07FaList(jf07FaList);
                jf07GraFas.add(jf07GraFa);
            }
        }catch(RuntimeException e){
            System.out.println("查询主子孙表数据，并组织数据进Jf07GraFas集合中的时候出错");
            result[0]="01";
            result[1]="查询主子孙表数据，并组织数据进Jf07GraFas集合中的时候出错";
            return result;
        }

        result=AnalysisMsgUtil.CompleteMsgToFileJF07(jf07GraFas,Jf07GraFa.class,Jf07Fa.class,Jf07Son.class,map.get("path")+"");

        return result;
    }


    @Override
    public int updateGraSendStatusByPk(Map map) {
        return outcomeMapper.updateGraSendStatusByPk(map);
    }
}
