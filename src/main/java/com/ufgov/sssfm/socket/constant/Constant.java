package com.ufgov.sssfm.socket.constant;

public class Constant {
    public static final String[][] INTFACESQL = {
            //划款结果
            {"transfer", "select b.total_code,b.areamanagecode,b.areamanagename,b.type,b.transfer_mon_date,b.contr_total,b.memo,Appseriono.Nextval appseriono from transferrecord b where b.sendstatus='0'"},
            //单位明细
            {"deptinfo", "select a.total_code,a.contr_serial_no,a.corp_jgb_unique_code,a.corp_name,a.contr_balance,a.memo from contrrecord a where a.total_code=?"},
            //缴费汇总
            {"contr", "select a.*,Appseriono.Nextval appseriono from totalcontrrecord a where a.sendstatus='0' and a.contr_state is null"},
            //到账结果
            {"contr1", "select a.*,Appseriono.Nextval appseriono from totalcontrrecord a where a.sendstatus='0' and a.contr_state is not null"},
            //资金划款指令
            {"instruct", "select a.*,Appseriono.Nextval appseriono from instructrecord  a where a.sendstatus='0'"},
            //更新划款结果
            {"updatetransferrecord", "update transferrecord set sendstatus='1' where total_code=?"},
            //更新资金划款指令
            {"updateinstruct", "update instructrecord set sendstatus='1' where total_code=?"},
            //更新缴费汇总/到账结果
            {"updatecontr", "update totalcontrrecord set sendstatus='1' where total_serial_no=?"}};

    public static final String[][] RETURNCODE = {
            {"0000", "正常"},
            {"1001", "后续未列出的其他原因的失败"},
            {"1002", "重复的报文标识"},
            {"1003", "通讯报文头格式错误"},
            {"1004", "通讯业务体格式错误"},
            {"1005", "报文签名验证失败"},
            {"1006", "密钥不一致"},
            {"5001", "原交易不存在"},
            {"9900", "备用自定义编码"},
            {"9999", "因不符合业务规则引发的失败"}};

    public static final String[][] TRANSFERTYPE = {
            {"njc1", "记实/补记明细信息"},
            {"njc2", "统筹区归集户划款结果"},
            {"njc3", "存量应缴明细信息"},
            {"njc4", "统筹区汇总单"},
            {"njc5", "省归集户到账信息"},
            {"njc6", "资金划款指令"}};

    public static final String[][] TYPE1 = {
            {"njc1", "记实/补记明细信息"},
            {"njc2", "统筹区归集户划款结果"},
            {"njc3", "存量应缴明细信息"},
            {"njc4", "统筹区汇总单"},
            {"njc5", "省归集户到账信息"},
            {"njc6", "资金划款指令"}};

    public static final String[][] TYPE = {
            {"01", "正常缴费"},
            {"02", "记实/补记"},
            {"03", "转入"},
            {"04", "归集户利息"},
            {"05", "存量缴费"}};

    public static final String[][] FLAG = {
            {"01", "未到账"},
            {"02", "已到账"},
            {"03", "金额不符"},
            {"04", "业务类型不符"}};

    public static final String[][] TRANSFERINTYPE = {
            {"01", "主动记实"},
            {"02", "主动记实利息"},
            {"03", "默认记实"},
            {"04", "默认记实利息"},
            {"05", "补记"}};

    public static final String[][] CONTRTYPE = {
            {"01", "缴费资金收账"},
            {"02", "做实资金收账"},
            {"03", "转入资金收账"},
            {"04", "归集户利息收账"},
            {"05", "其它"}};

    public static final String[][] IDCARDTYPE = {
            {"01", "居民身份证（户口簿）"},
            {"04", "香港特区护照/港澳居民来往内地通行证"},
            {"05", "澳门特区护照/港澳居民来往内地通行证"},
            {"06", "台湾居民来往大陆通行证"},
            {"99", "其他身份证件"},
            {"08", "外国人护照"},
            {"07", "外国人永久居留证"}};
}
