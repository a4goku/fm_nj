package com.ufgov.sssfm.socket.utils;

import java.util.ArrayList;
import java.util.List;

public class TestMain {
    public static void main(String[] args){
        String t1 = "AAZ061, AAB001, AAB099, AAB999, AAB069, AAA121, AAE036, AAE019, Real_contr_balance, Contr_feedback, memo";
        String t2 = "00000000002, 00000000001, 苏机事险320311234406, 苏机事险320311234406, 徐州市泉山区环境卫生管理处, 补缴, 20160908, 188160.8700, 188160.8700, 失败";
        String t3 = "00000000001, 00000000001, 苏机事险320311234406, 苏机事险320311234406, 徐州市泉山区环境卫生管理处, 正常缴费, 20160908, 188160.8700, 188160.8700, 成功";
        String[] str1 = t1.split(",");
        String[] str2 = t2.split(",");
        String[] str3 = t3.split(",");

        List<List<String>> listA = new ArrayList<>();

        List<String> listb = new ArrayList<String>();
        for(int i = 0; i < str1.length; i++){
            listb.add(str1[0]);
        }
        listA.add(listb);

        List<String> listc = new ArrayList<String>();
        for(int i = 0; i < str2.length; i++){
            listc.add(str2[0]);
        }
        listA.add(listc);

        List<String> listd = new ArrayList<String>();
        for(int i = 0; i < str2.length; i++){
            listd.add(str2[0]);
        }
        listA.add(listd);

        System.out.println(listA.size());
    }
}
