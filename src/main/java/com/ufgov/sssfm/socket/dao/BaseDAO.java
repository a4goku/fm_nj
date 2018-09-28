package com.ufgov.sssfm.socket.dao;



import com.ufgov.sssfm.socket.constant.Constant;
import com.ufgov.sssfm.socket.utils.ConvertUtil;
import com.ufgov.sssfm.socket.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BaseDAO {
    /*
     * 根据业务执行不同的sql，最终反馈listmap
     */
    public List<Map<String, String>> getData(String type) throws SQLException {
        Connection conn = null;
        ResultSet resultSet = null;
        List<Map<String, String>> lists = null;
        String sql = null;
        try {
            conn = DBUtils.getConnection();
            String[][] arr = Constant.INTFACESQL;
            for (String[] str : arr) {
                if (str[0].equals(type)) {
                    sql = str[1];
                }
            }
            PreparedStatement pst = conn.prepareStatement(sql);
            resultSet = pst.executeQuery();
            lists = ConvertUtil.rsToMaps(resultSet);
        } finally {
            DBUtils.close(conn, resultSet);
        }
        return lists;
    }

    /*
     * 根据业务执行不同的sql，最终反馈listmap
     */
    public boolean updateData(String type, String para) throws SQLException {
        Connection conn = null;
        int resultSet = 0;
        String sql = null;
        try {
            conn = DBUtils.getConnection();
            String[][] arr = Constant.INTFACESQL;
            for (String[] str : arr) {
                if (str[0].equals(type)) {
                    sql = str[1];
                }
            }
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, para);
            resultSet = pst.executeUpdate();
        } finally {
            DBUtils.close(conn, null);
        }
        return resultSet == 1;
    }

    /*
     * 根据业务执行不同的sql，最终反馈listmap
     */
    public List<Map<String, String>> getDataWhere(String type, String para) throws SQLException {
        Connection conn = null;
        ResultSet resultSet = null;
        List<Map<String, String>> lists = null;
        String sql = null;
        try {
            conn = DBUtils.getConnection();
            String[][] arr = Constant.INTFACESQL;
            for (String[] str : arr) {
                if (str[0].equals(type)) {
                    sql = str[1];
                }
            }
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, para);
            resultSet = pst.executeQuery();
            lists = ConvertUtil.rsToMaps(resultSet);
        } finally {
            DBUtils.close(conn, resultSet);
        }
        return lists;
    }

    /*
     * 插入日志表
     */
    public void insertLog(String sendContent, String result, String type, String returnContent, String pk) {
        Connection conn = null;
        String sql = "insert into interface_log(sendcontent,result,type,returncontent,pk) values (?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, sendContent);
            pst.setString(2, result);
            pst.setString(3, type);
            pst.setString(4, returnContent);
            pst.setString(5, pk);
            pst.executeUpdate();
        } catch (SQLException e) {
            // 插入日志异常

        } finally {
            DBUtils.close(conn, null);
        }
    }

    /*
     * 插入缴费汇总信息
     */
    public String insertTotalContr(String total_serial_no,
                                   String areamanagecode, String areamanagename, String type,
                                   String transfer_mon_date, String total_date, String contr_state,
                                   String contr_total, String actual_cash, String memo, String trancode) {
        Connection conn = null;
        String sql = "insert into totalcontrrecord(total_serial_no,areamanagecode,areamanagename,type,transfer_mon_date,total_date,contr_state,contr_total,actual_cash,memo,trancode,sendstatus) values (?,?,?,?,?,?,?,?,?,?,?,'0')";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, total_serial_no);
            pst.setString(2, areamanagecode);
            pst.setString(4, type);
            pst.setString(3, areamanagename);
            pst.setString(5, transfer_mon_date);
            pst.setString(6, total_date);
            pst.setString(7, contr_state);
            pst.setString(8, contr_total);
            pst.setString(9, actual_cash);
            pst.setString(10, memo);
            pst.setString(11, trancode);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "9900";
        } finally {
            DBUtils.close(conn, null);
        }
        return "0000";
    }

    /*
     * 更新到账信息
     */
    public String updateContr(String total_serial_no, String transfer_mon_date,
                              String a_contr_total, String contr_state) {
        Connection conn = null;
        String querySql = "select * from totalcontrrecord where total_serial_no=?";
        String updatesql = "update totalcontrrecord set transfer_mon_date=?,contr_state=?,a_contr_total=? where total_serial_no=?";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(querySql);
            pst.setString(1, total_serial_no);
            ResultSet set = pst.executeQuery();
            if (set != null && set.next() == true) {
                PreparedStatement ps = conn.prepareStatement(updatesql);
                ps.setString(4, total_serial_no);
                ps.setString(1, transfer_mon_date);
                ps.setString(3, a_contr_total);
                ps.setString(2, contr_state);
                ps.executeUpdate();
                return "0000";
            } else {
                return "5001";
            }
        } catch (SQLException e) {
            return "9900";
        } finally {
            DBUtils.close(conn, null);
        }
    }

    /*
     * 插入汇款指令
     */
    public String insertInstruct(String instruct_code, String instruct_date,
                                 String paybankaccname, String paybankaccno, String paybank,
                                 String gatheraccname, String gatherbankno, String gatherbank,
                                 String contrbalance, String memo) {
        Connection conn = null;
        String sql = "insert into instructrecord(instruct_code, instruct_date, paybankaccname,paybankaccno, paybank, gatheraccname, gatherbankno, gatherbank,	contrbalance, memo,sendstatus) values (?,?,?,?,?,?,?,?,?,?,'0')";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, instruct_code);
            pst.setString(2, instruct_date);
            pst.setString(3, paybankaccname);
            pst.setString(4, paybankaccno);
            pst.setString(5, paybank);
            pst.setString(6, gatheraccname);
            pst.setString(7, gatherbankno);
            pst.setString(8, gatherbank);
            pst.setString(9, contrbalance);
            pst.setString(10, memo);
            pst.executeUpdate();
        } catch (SQLException e) {
            return "9900";
        } finally {
            DBUtils.close(conn, null);
        }
        return "0000";
    }


    public String insertNjinfo(List<List> list, String  tableName) {

        Connection conn = null;
        String sql = " insert into "+ tableName+" ( ";
        try {
            conn = DBUtils.getConnection();
            String sqlvalue="";
            if(list.get(0).size() > 0) {
                for(int i = 0; i < list.get(0).size(); i++){
                    if(i == list.get(0).size() - 1){
                        sqlvalue = sqlvalue+list.get(0).get(i) + " )values( ";
//                        for(int m = 0; m < valueslist.get(0).size(); m++){
//                            if(m == list.get(0).size() - 1) {
//                                sqlvalue = sqlvalue + "?)";
//                            }else{
//                                sqlvalue = sqlvalue + "?";
//                            }
//                        }
                    }else {
                        sqlvalue = sqlvalue + list.get(0).get(i);
                    }
                }
            }
            PreparedStatement pst = conn.prepareStatement(sql);
            for(int n = 1; n < list.size(); n++) {
                for(int k=0; k < list.get(n).size(); k++){
                    for(int j=0; j<list.get(0).size(); j++) {
                        //pst.setString(j, list.get(n).get(k));
                    }
                }
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "9900";
        } finally {
            DBUtils.close(conn, null);
        }
        return "0000";
    }

    /*
     * 插入数据库（通用）
     */
    public String insertNjinfo2(List <List> list, String  tableName) {

        Connection conn = null;
        String sql = " insert into "+ tableName+" ( ";
        try {
            conn = DBUtils.getConnection();
            String sqlvalue="";
            if(list.get(0).size() > 0) {
                for(int i = 0; i < list.get(0).size(); i++){
                    if(i == list.get(0).size() - 1){
                        sqlvalue = sqlvalue+list.get(0).get(i) + " ) values ( ";
                        for(int m = 0; m < list.get(0).size(); m++){
                            if(m == list.get(0).size() - 1) {
                                sqlvalue = sqlvalue + " ? ) ";
                            }else{
                                sqlvalue = sqlvalue + " ?,  ";
                            }
                        }
                    }else {
                        sqlvalue = sqlvalue + list.get(0).get(i) + ",";
                    }
                }
            }
            System.out.println(sql + sqlvalue);
            String ttt = "INSERT INTO RETURNINFO ( AAZ061, AAB001, AAB099, AAB999, AAB069, AAA121, AAE036, AAE019, Real_contr_balance, Contr_feedback, memo )\n" +
                    "VALUES\n" +
                    "\t( '3', 'a', 'b', 'c', 'd', 'e', '2', '3', '4', '5', 'f' )";
            PreparedStatement pst = conn.prepareStatement(ttt);
//            pst.setInt(0,1);
//            pst.setString(1,"a");
//            pst.setString(2,"b");
//            pst.setString(3,"c");
//            pst.setString(4,"d");
//            pst.setString(5,"e");
//            pst.setInt(6,2);
//            pst.setInt(7,3);
//            pst.setInt(8,4);
//            pst.setInt(9,5);
//            pst.setString(10,"f");

//            for(int n = 1; n < list.size(); n++) {
//                for(int k=0; k < list.get(n).size(); k++){
//                    for(int j=0; j < list.get(0).size(); j++) {

//                        pst.setString(j, (String) list.get(n).get(k));
//                    }
//                }
//            }
            System.out.println(pst.toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "9900";
        } finally {
            DBUtils.close(conn, null);
        }
        return "0000";
    }

    public void  insertNjinfo3(List <List> list, String  tableName) {

        Connection conn = null;
        String sql = " insert into "+ tableName+" ( ";
        try {
            conn = DBUtils.getConnection();
            String sqlvalue="";
            if(list.get(0).size() > 0) {
                for(int i = 0; i < list.get(0).size(); i++){
                    if(i == list.get(0).size() - 1){
                        sqlvalue = sqlvalue+list.get(0).get(i) + " ) values ( ";
                        for(int m = 0; m < list.get(0).size(); m++){
                            if(m == list.get(0).size() - 1) {
                                sqlvalue = sqlvalue + " ? ) ";
                            }else{
                                sqlvalue = sqlvalue + " ?,  ";
                            }
                        }
                    }else {
                        sqlvalue = sqlvalue + list.get(0).get(i) + ",";
                    }
                }
            }
            System.out.println(sql + sqlvalue);
            //String ttt = "INSERT INTO RETURNINFO ( AAZ061, AAB001, AAB099, AAB999, AAB069, AAA121, AAE036, AAE019, Real_contr_balance, Contr_feedback, memo )\n" +
            //"VALUES\n" +
            //"\t( '3', 'a', 'b', 'c', 'd', 'e', '2', '3', '4', '5', 'f' )";
            PreparedStatement pst = conn.prepareStatement(sql + sqlvalue);
//            pst.setInt(0,1);
//            pst.setString(1,"a");
//            pst.setString(2,"b");
//            pst.setString(3,"c");
//            pst.setString(4,"d");
//            pst.setString(5,"e");
//            pst.setInt(6,2);
//            pst.setInt(7,3);
//            pst.setInt(8,4);
//            pst.setInt(9,5);
//            pst.setString(10,"f");

            for(int n = 1; n < list.size(); n++) {
                for(int k=0; k < list.get(n).size(); k++){
                    for(int j=0; j < list.get(0).size(); j++) {
                        String  tableCloumn = (String) list.get(0).get(j);
//                        if(tableCloumn.equals("AAZ061") || tableCloumn.equals("AAE036") || tableCloumn.equals("AAE019")||
//                                tableCloumn.equals("Contr_feedback")|| tableCloumn.equals("CONTR_TOTAL")|| tableCloumn.equals("CONTRBALANCE")|| tableCloumn.equals("ACTUAL_TOTAL")||
//                                tableCloumn.equals("ACTUAL_BALANCE")|| tableCloumn.equals("PK_IDENTITY_TYPE")){
//                            //pst.setBigDecimal(j, (BigDecimal) list.get(n).get(k));
//                            pst.setBigDecimal(j, new BigDecimal((String)list.get(n).get(k)));
//                        }else {
                            System.out.println((String) list.get(n).get(k));
                            pst.setString(j, (String) list.get(n).get(k));
//                        }
                    }
                    //pst.setString(j, (String) list.get(n).get(k));
                }
            }
            pst.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public void  insertNjinfo4(List <List> list, String  tableName) {

        Connection conn = null;
        String sql = " insert into "+ tableName+" ( ";
        try {
            conn = DBUtils.getConnection();
            String sqlvalue="";
            if(list.get(0).size() > 0) {
                for(int i = 0; i < list.get(0).size(); i++){
                    if(i == list.get(0).size() - 1){
                        sqlvalue = sqlvalue+list.get(0).get(i) + " ) values ( ";
                        for(int m = 0; m < list.get(0).size(); m++){
                            if(m == list.get(0).size() - 1) {
                                sqlvalue = sqlvalue + " ? ) ";
                            }else{
                                sqlvalue = sqlvalue + " ?,  ";
                            }
                        }
                    }else {
                        sqlvalue = sqlvalue + list.get(0).get(i) + ",";
                    }
                }
            }
            PreparedStatement pst = conn.prepareStatement(sql + sqlvalue);


            for(int n = 1; n < list.size(); n++) {
                for(int k=0; k < list.get(n).size(); k++){
                    Object tableCloumn = list.get(n).get(k);
                    System.out.println(tableCloumn);
                    try {
                        //pst.setObject(k+1, new String(tableCloumn.toString().getBytes("UTF-8"),"UTF-8"));
                        pst.setObject(k + 1, tableCloumn);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //System.out.print(pst.toString());
                pst.executeUpdate();
            }


        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public void gatherInfo(){
        Connection conn = null;

        ResultSet rsMain = null;
        ResultSet rsSub = null;

        List<Map<String,String>> rsMainList = null;
        List<Map<String,String>> rsSubList = null;

        String selectSumSql = "SELECT AREAMANAGECODE, sum(Real_contr_balance) as sumCount FROM RETURNINFO GROUP BY AREAMANAGECODE;";
        String selectDetailSql = "SELECT AAZ061, AAB001, AAB099, AAB999, AAB069, AAA121, AAE036, AAE019, Real_contr_balance, Contr_feedback, memo FROM RETURNINFO where AREAMANAGECODE = ";

        //String param = "SELECT ?, AREAMANAGECODE, AREAMANAGECODE, ?, ?, sum(Real_contr_balance), ? from RETURNINFO GROUP BY AREAMANAGECODE";
        String mainSql = "insert into AREAMANAGESUM (TOTAL_CODE , AREAMANAGECODE , AREAMANAGENAME, TYPE, TRANSFER_MON_DATE, CONTR_TOTAL, MEMO) values  ( ?, ?, ?, ?, ?, ?, ? )";
        String subSql = "insert into UNITDETAIL (TOTAL_CODE, CONTR_SERIAL_NO, CORP_JGB_UNIQUE_CODE, CORP_NAME, CONTR_BALANCE, MEMO) values (?, ?, ?, ?, ?, ?)";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pstMainQuery = conn.prepareStatement(selectSumSql);
            rsMain = pstMainQuery.executeQuery();
            rsMainList = ConvertUtil.rsToMaps(rsMain);
            PreparedStatement pstMain = conn.prepareStatement(mainSql);

            for(int i = 0; i < rsMainList.size(); i++){
                String mainID = rsMainList.get(i).get("areamanagecode")+"CC"+"20180928"+(int)(Math.random()*9000)+1000;
                pstMain.setString(1, mainID);     //TOTAL_CODE
                pstMain.setString(2, rsMainList.get(i).get("areamanagecode"));       //AREAMANAGECODE
                pstMain.setString(3, "BBB");                                      //AREAMANAGENAME
                pstMain.setString(4, "CCC");                                      //TYPE
                pstMain.setString(5, "DDD");                                      //TRANSFER_MON_DATE
                pstMain.setString(6, rsMainList.get(i).get("sumcount"));             //CONTR_TOTAL
                pstMain.setString(7, "FFF");                                      //MEMO
                pstMain.executeUpdate();

                PreparedStatement pstSubQuery = conn.prepareStatement(selectDetailSql + rsMainList.get(i).get("areamanagecode"));
                rsSub = pstSubQuery.executeQuery();
                rsSubList = ConvertUtil.rsToMaps(rsSub);

                PreparedStatement pstSub = conn.prepareStatement(subSql);
                for(int j = 0; j < rsSubList.size(); j++){
                    pstSub.setString(1, mainID);                                            //TOTAL_CODE
                    pstSub.setString(2, "" + (int)(Math.random()*9000)+1000);            //CONTR_SERIAL_NO
                    pstSub.setString(3, rsSubList.get(i).get("aab999"));                   //CORP_JGB_UNIQUE_CODE
                    pstSub.setString(4, rsSubList.get(i).get("aab069"));                   //CORP_NAME
                    pstSub.setString(5, rsSubList.get(i).get("real_contr_balance"));       //CONTR_BALANCE
                    pstSub.setString(6, "KKK");                                           //MEMO
                    pstSub.executeUpdate();
                }
            }
        } catch (SQLException e) {
            // 插入日志异常
            e.printStackTrace();
        } finally {
            DBUtils.close(conn, null);
        }
    }

    //数据入库 returninfo 表
    public void insertReturnInfo(List <List> list, String  filename) {

        Connection conn = null;
        String  sql =  "insert into RETURNINFO ( AAZ061,AAB001,AAB099,AAB999,AAB069,AAA121,AAE036,AAE019,Real_contr_balance,Contr_feedback,memo,AREAMANAGECODE,gatherState,accountState ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            for(int n = 1; n < list.size(); n++) {
                String[] strs = filename.split("_");
                String areamanagec = strs[1];
                System.out.println(areamanagec);
                for(int k=0; k < list.get(n).size(); k++){
                    Object  tableCloumn = list.get(n).get(k);
                    System.out.println(tableCloumn);
                    pst.setObject(k+1, tableCloumn);

                }
                pst.setObject(12, areamanagec);   //统筹区代码一开始没有
                pst.setObject(13, 0);         //状态一开始没有
                pst.setObject(14, 0);            //状态一开始没有
                System.out.println(pst.toString());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //数据入库 AREAMANAGE_TOTAL_INFO 表  统筹区汇总信息
    public void  insertAreamanageTotalInfo(List <List> list) {

        Connection conn = null;
        String  sql =  "insert into AREAMANAGE_TOTAL_INFO ( ACTUAL_SERIAL_NO,AREAMANAGECODE,AREAMANAGENAME,ACTUAL_TYPE,ACTUAL_TOTAL,MEMO，PINGZHENGHAO，ACTUALSTATE ) values (?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            for(int n = 1; n < list.size(); n++) {
                for(int k=0; k < list.get(n).size(); k++){
                    Object  tableCloumn = list.get(n).get(k);
                    System.out.println(tableCloumn);
                    pst.setObject(k+1, tableCloumn);
                }
                pst.setObject(7, "");  //PINGZHENGHAO 开始接收得为空
                pst.setObject(8, 0);  //状态 开始接收默认为0
                System.out.println(pst.toString());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //数据入库 CORP_DETAIL 表  统筹区汇总信息单位明细
    public void  insertCorpDetail(List <List> list) {

        Connection conn = null;
        String  sql =  "insert into CORP_DETAIL ( ACTUAL_SERIAL_NO,ACTUAL_CORP_SERIAL_NO,CORP_JGB_UNIQUE_CODE," +
                "CORP_NAME,CORP_SI_CODE,ACTUAL_START_DATE,ACTUAL_END_DATE,ACTUAL_BALANCE,MEMO ) " +
                "values (?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            for(int n = 1; n < list.size(); n++) {
                for(int k=0; k < list.get(n).size(); k++){
                    Object  tableCloumn = list.get(n).get(k);
                    System.out.println(tableCloumn);
                    pst.setObject(k+1, tableCloumn);
                }
                System.out.println(pst.toString());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    //数据入库 CORP_DETAIL_DEFAULT 表  统筹区汇总信息单位明细默认纪实/补记单位明细
    public void  insertCorpDetailDefault(List <List> list) {

        Connection conn = null;
        String  sql =  "insert into CORP_DETAIL_DEFAULT ( ACTUAL_CORP_SERIAL_NO,AREAMANAGECODE,AREAMANAGENAME," +
                "ACTUAL_TYPE,CORP_JGB_UNIQUE_CODE,CORP_NAME,CORP_SI_CODE,ACTUAL_TOTAL,MEMO ) " +
                "values (?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            for(int n = 1; n < list.size(); n++) {
                for(int k=0; k < list.get(n).size(); k++){
                    Object  tableCloumn = list.get(n).get(k);
                    System.out.print(tableCloumn);
                    pst.setObject(k+1, tableCloumn);
                }
                System.out.print(pst.toString());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    //数据入库 CORP_DETAIL 表  统筹区汇总信息个人明细
    public void  insertPersonDetail(List <List> list) {

        Connection conn = null;
        String  sql =  "insert into PERSON_DETAIL ( ACTUAL_CORP_SERIAL_NO,PSN_JGB_UNIQUE_CODE,NAME," +
                "PK_IDENTITY_TYPE,IDENTITY,ACTUAL_BANLANCE,MEMO ) " +
                "values (?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            for(int n = 1; n < list.size(); n++) {
                for(int k=0; k < list.get(n).size(); k++){
                    Object  tableCloumn = list.get(n).get(k);
                    System.out.print(tableCloumn);
                    pst.setObject(k+1, tableCloumn);
                }
                System.out.print(pst.toString());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //数据入库 CORP_DETAIL 表  统筹区汇总信息个人明细默认记实补记
    public void  insertPersonDetailDefault(List <List> list) {

        Connection conn = null;
        String  sql =  "insert into PERSON_DETAIL_DEFAULT ( ACTUAL_CORP_SERIAL_NO,PSN_JGB_UNIQUE_CODE,NAME," +

                "PK_IDENTITY_TYPE,IDENTITY_NO,ACTUAL_BALANCE,MEMO ) " +"values (?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            for(int n = 1; n < list.size(); n++) {
                for(int k=0; k < list.get(n).size(); k++){
                    Object  tableCloumn = list.get(n).get(k);
                    System.out.print(tableCloumn);
                    pst.setObject(k+1, tableCloumn);
                }
                System.out.print(pst.toString());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        //gatherInfo();
    }
}
