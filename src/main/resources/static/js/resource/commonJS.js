//	时间全局变量
var nowDate=new Date();
var nowYear=nowDate.getFullYear();
var nowMonth=nowDate.getMonth()+1;
var nowDay=nowDate.getDate();
var nowTime=nowYear+'-'+(nowMonth<10?'0'+nowMonth:nowMonth)+'-'+(nowDay<10?'0'+nowDay:nowDay);//现在日期
var nowTimeToMonth=nowYear+'-'+(nowMonth<10?'0'+nowMonth:nowMonth);//存储只到月份的现在日期
$.fn.datetimepicker.dates['zh'] = {  //datetimepicker汉化
    days:       ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"],
    daysShort:  ["日", "一", "二", "三", "四", "五", "六","日"],
    daysMin:    ["日", "一", "二", "三", "四", "五", "六","日"],
    months:     ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月","十二月"],
    monthsShort:  ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月","十二月"],
    meridiem:    ["上午", "下午"],
    today:       "今天"
};
var commonJS={

    //浮点数求和
    adds: function (arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length
        } catch (e) {
            r2 = 0
        }
        m = Math.pow(10, Math.max(r1, r2))
        return (arg1 * m + arg2 * m) / m;
    },

    //数字或者日期月日转换为大写金额
    moneyToBig:function(money){
        //汉字的数字
        var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
        //基本单位
        var cnIntRadice = new Array('', '拾', '佰', '仟');
        //对应整数部分扩展单位
        var cnIntUnits = new Array('', '万', '亿', '兆');
        //对应小数部分单位
        var cnDecUnits = new Array('角', '分', '毫', '厘');
        //整数金额时后面跟的字符
        var cnInteger = '整';
        //整型完以后的单位
        var cnIntLast = '元';
        //最大处理的数字
        var maxNum = 999999999999999.9999;
        //金额整数部分
        var integerNum;
        //金额小数部分
        var decimalNum;
        //输出的中文金额字符串
        var chineseStr = '';
        //分离金额后用的数组，预定义
        var parts;
        if (money == '') { return ''; }
        money = parseFloat(money);
        if (money >= maxNum) {
            //超出最大处理数字
            return '';
        }
        if (money == 0) {
            chineseStr = cnNums[0] + cnIntLast + cnInteger;
            return chineseStr;
        }
        //转换为字符串
        money = money.toString();
        if (money.indexOf('.') == -1) {
            integerNum = money;
            decimalNum = '';
        } else {
            parts = money.split('.');
            integerNum = parts[0];
            decimalNum = parts[1].substr(0, 4);
        }
        //获取整型部分转换
        if (parseInt(integerNum, 10) > 0) {
            var zeroCount = 0;
            var IntLen = integerNum.length;
            for (var i = 0; i < IntLen; i++) {
                var n = integerNum.substr(i, 1);
                var p = IntLen - i - 1;
                var q = p / 4;
                var m = p % 4;
                if (n == '0') {
                    zeroCount++;
                } else {
                    if (zeroCount > 0) {
                        chineseStr += cnNums[0];
                    }
                    //归零
                    zeroCount = 0;
                    chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
                }
                if (m == 0 && zeroCount < 4) {
                    chineseStr += cnIntUnits[q];
                }
            }
            chineseStr += cnIntLast;
        }
        //小数部分
        if (decimalNum != '') {
            var decLen = decimalNum.length;
            for (var i = 0; i < decLen; i++) {
                var n = decimalNum.substr(i, 1);
                if (n != '0') {
                    chineseStr += cnNums[Number(n)] + cnDecUnits[i];
                }
                else if(n=='0'){
                    chineseStr += cnNums[Number(n)] + cnDecUnits[i].replace('角','');
                }
            }
        }
        if (chineseStr == '') {
            chineseStr += cnNums[0] + cnIntLast + cnInteger;
        } else if (decimalNum == '') {
            chineseStr += cnInteger;
        }
        return chineseStr;
    },

    //日期年转换为大写数字
    yearToBig:function(year){
        var chinese = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖','拾'];
        var result='';
        for (var i = 0; i < year.length; i++) {
            result += chinese[year.charAt(i)];
        }
        return result;
    },

    //金额加千分位
    thousandPoint:function(num){
        if(num){
            num = (num+"").replace(/\$|\,/g,'');
            if(''==num || isNaN(num)){return 'Not a Number ! ';}
            var sign = num.indexOf("-")> 0 ? '-' : '';
            var cents = num.indexOf(".")> 0 ? num.substr(num.indexOf(".")) : '';
            cents = cents.length>1 ? cents : '' ;//注意：这里如果是使用change方法不断的调用，小数是输入不了的
            num = num.indexOf(".")>0 ? num.substring(0,(num.indexOf("."))) : num ;
            if('' == cents){ if(num.length>1 && '0' == num.substr(0,1)){return 'Not a Number ! ';}}
            else{if(num.length>1 && '0' == num.substr(0,1)){return 'Not a Number ! ';}}
            for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++){
                num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
            }
            return (sign + num + cents);
        }
    },


    //分隔金额符到元
    splitMoney:function(num,numBottom){
        if(num){
            var result;
            if(num/numBottom>=10){result=parseInt((num-parseInt(num/(10*numBottom))*10*numBottom)/numBottom)}
            else if(num/numBottom>=1&&num/numBottom<10){result=Math.floor(num/numBottom)}
            else if(num/numBottom<1&&num/numBottom>=0.1){result='￥'}
            else{
                result='';
            }
            return result;
        }
    },

    //分隔金额符角分
    splitCent:function(num,numBottom){
        if(num){
            var result;
            var num2=parseFloat(num.toFixed(2).toString().split('.')[1]);
            if(num2/numBottom>=1&&num2/numBottom<10){
                result=Math.floor(num2/numBottom);
            }
            else if(num2/numBottom>=10){
                result=num2-Math.floor(num2/(numBottom*10))*10;
            }
            else{
                if(parseFloat(num.toFixed(2).toString().split('.')[0])>0){
                    result='0';
                }
                else if(parseFloat(num.toFixed(2).toString().split('.')[0])==0){
                    result='￥';
                }
            }
            return result;
        }
    },

    showMonth:function(format,startView,minView,val){//月份下拉框函数
        //				所属月份隐藏日期
        $('.datetimepicker').datetimepicker({
            bootcssVer:3,
            format: format,//显示格式
            todayHighlight: 1,//今天高亮
            startView:startView,//打开时为月份
            minView: minView,//操作年月
            language:'zh',//中文
            autoclose: 1//选择后自动关闭
        }).val(val);
    },

    confirm:function(title,back,error,okAction){//封装提示函数
        $.confirm({
            title: title,
            content: back?back:error,
            buttons: {
                ok: {
                    text: '确认',
                    btnClass: 'btn-primary',
                    action: okAction
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary'
                }
            }
        });
    },

    getCookie:function(c_name){
        if (document.cookie.length>0)
        {
            c_start=document.cookie.indexOf(c_name + "=");
            if (c_start!=-1)
            {
                c_start=c_start + c_name.length+1;
                c_end=document.cookie.indexOf(";",c_start);
                if (c_end==-1) c_end=document.cookie.length;
                return unescape(document.cookie.substring(c_start,c_end))
            }
        }
        return "";
    }
};
var userName=commonJS.getCookie('userName');//获取当前用户
