$(function(){

    var allUrl={//后台交互URL
        query:ctx+'fundApply/FundApplyController/selectAllBkApplyTime',//加载表格
        sp:ctx+'fundApply/FundApplyController/updateBkdSpStatus',//审批
        edit:ctx+'fundApply/FundApplyController/selectBKApplyByPK',//查看
        daily:ctx + 'fundApply/FundApplyController/query_sp_daily',//审批日志
    };

    //单元格按钮事件
    window.operateEvents = {
        'click .btn-see':function (e, value, row, index) {//查看
            $('#firstTable').bootstrapTable('uncheckAll'); //取消所有勾选项
            $('#win').modal('show');
            $('#win input').attr('readonly','readonly');//不可编辑
            $.ajax({
                url: allUrl.edit,
                type:"post",
                dataType:'json',
                data:{
                    bkdId:row.BKD_ID
                },
                beforeSend:function (){
                    $('#myModal').modal('show');
                },
                success: function(result){
                    $('#myModal').modal('hide');
                    // 插数
                    $('#year').val(result.year);
                    $('#month').val(result.month);
                    $('#day').val(result.day);
                    $('#xz').val(result.xz);
                    $('#monthend').val(result.monthend);
                    $('#lastyearlast').val(result.lastyearlast);
                    $('#thisyearpre').val(result.thisyearpre);
                    $('#thisyearplus').val(result.thisyearplus);
                    $('#monthplus').val(result.monthplus);
                    $('#lastmonthlast').val(result.lastmonthlast);
                    $('#thismonthapply').val(result.thismonthapply);
                    $('#bz').val(result.bz);
                    $('#tsbkone').val(result.tsbkone);
                    $('#tsbktwo').val(result.tsbktwo);
                    $('#tsbkthree').val(result.tsbkthree);
                    $('#accountone').val(result.accountone);
                    $('#batchnoone').val(result.batchnoone);
                    $('#bankone').val(result.bankone);
                    $('#moneybig').val(result.moneybig);
                    $('#moneysmall').val(result.moneysmall);
                    $('#accounttwo').val(result.accounttwo);
                    $('#batchnotwo').val(result.batchnotwo);
                    $('#banktwo').val(result.banktwo);
                    $('#sqdwfzr').html(result.sqdwfzr);
                    $('#sqdwshr').html(result.sqdwshr);
                    $('#sqdwjbr').html(result.sqdwjbr);
                    $('#czsbld').html(result.czsbld);
                    $('#czsbshr').html(result.czsbshr);
                    $('#czsbzg').html(result.czsbzg);
                    $('#gkone').html(result.gkone);
                    $('#gktwo').html(result.gktwo);
                    $('#gkthree').html(result.gkthree);
                }
            });
        },
        'click .btn-sp':function (e, value, row, index){//审批
            if(row.SP_STATUS!='02'){
                commonJS.confirm('警告','已审批，不可再次审批！');
            }
            else{
                page.updateState()
            }
        },
        'click .btn-disagree':function (e, value, row, index){//驳回
            if(row.SEND_STATUS=='01'){
                page.updateState(row,'03',userName,'审批');
            }
            else{
                page.updateState(row,'00',userName,'驳回');
            }
        },
        'click .btn-daily':function (e, value, row, index) {//审批日志
            layer.open({
                type: 1,
                title: '审批日志',
                closeBtn: 1,
                area: ['350px', '400px'],
                shadeClose: true,
                skin: 'yourclass',
                content: $('#daily')
            });
            $('#dailyTable').bootstrapTable('destroy').bootstrapTable({
                url: allUrl.daily,
                queryParams: {
                    bkdId:row.BKD_ID
                },
                method: 'post',
                contentType: "application/x-www-form-urlencoded",//当请求方法为post的时候,必须要有！！！！
                dataField: "result",//定义从后台接收的字段，包括result和total，这里我们取result
                pageNumber: 1, //初始化加载第一页
                pageSize: 10,
                pagination: true, // 是否分页
                clickToSelect:true,
                sidePagination: 'server',//server:服务器端分页|client：前端分页
                paginationHAlign: 'left',//分页条水平方向的位置，默认right（最右），可选left
                paginationDetailHAlign: 'right',//paginationDetail就是“显示第 1 到第 8 条记录，总共 15 条记录 每页显示 8 条记录”，默认left（最左），可选right
                columns:[    //表头
                    {
                        field: 'number', title: '序号', align: 'center', formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },
                    {field: 'czFs', title: '操作', align: 'center'},
                    {field: 'czPeople', title: '操作人', align: 'center'},
                    {field: 'czTime', title: '操作时间', align: 'center'}
                ]
            });
        }
    };

    var page = function () {

        return {
            // 加载表格
            getTab: function (id,url) {
                // 初始化第一个表格
                $('#'+id).bootstrapTable({
                    url: url,
                    queryParams: function (params) {
                        return{
                            pageSize: params.limit, // 每页显示数量
                            pageNumber: params.offset / params.limit + 1, //当前页码
                            timeStart:$('#startTime').val().replace(/-/g, ''),
                            timeEnd:$('#endTime').val().replace(/-/g, ''),
                            sp_status:'02',
                            send_status:'00'
                        }
                    },
                    method: 'post',
                    contentType: "application/x-www-form-urlencoded",//当请求方法为post的时候,必须要有！！！！
                    dataField: "result",//定义从后台接收的字段，包括result和total，这里我们取result
                    pageNumber: 1, //初始化加载第一页
                    pageSize: 10,
                    pagination: true, // 是否分页
                    clickToSelect:true,
                    singleSelect:true,
                    sidePagination: 'server',//server:服务器端分页|client：前端分页
                    paginationHAlign: 'left',//分页条水平方向的位置，默认right（最右），可选left
                    paginationDetailHAlign: 'right',//paginationDetail就是“显示第 1 到第 8 条记录，总共 15 条记录 每页显示 8 条记录”，默认left（最左），可选right
                    columns:[    //表头
                        // {field: 'ck', checkbox: true},//checkbox列
                        {
                            field: 'number', title: '序号', align: 'center', formatter: function (value, row, index) {
                                return index + 1;
                            }
                        },
                        {field: 'XZ', title: '险种', align: 'center'},
                        {field: 'SQSJ', title: '申请时间', align: 'center'},
                        {field: 'ACCOUNTONE', title: '申请单位账户名称', align: 'center'},
                        {field: 'BATCHNOONE', title: '申请单位银行账号', align: 'center'},
                        {field: 'BANKONE', title: '申请单位开户行', align: 'center'},
                        {
                            field: 'THISMONTHAPPLY', title: '本月申请金额（万元）', align: 'right', halign: 'center',
                            formatter: function (value) {
                                var num=parseFloat(value);
                                return commonJS.thousandPoint(num.toFixed(2));
                            }
                        },
                        {field: 'PDF_ADDRESS', title: '生成PDF地址', align: 'center'},
                        {field: 'SP_STATUS_NAME', title: '状态', align: 'center'},
                        {field: 'operate', title: '操作', align: 'center',events:operateEvents,formatter:function(row){
                                return '<button class="btn btn-primary btn-see">查看</button>&nbsp;'+
                                    '<button class="btn btn-primary btn-sp">审批</button>&nbsp;'+
                                    '<button class="btn btn-primary btn-disagree">驳回</button>&nbsp;'+
                                    '<button class="btn btn-primary btn-daily">审批日志</button>'
                            },
                        }
                    ]
                });
            },

            //审核状态改变
            updateState:function(row,spSta,spName,spStaName){
                $.ajax({
                    url: allUrl.sp,
                    type:"post",
                    dataType:'json',
                    data:{
                        bkdId:row.BKD_ID,
                        sp_status:spSta,
                        sp_name:spName,
                        sp_status_name:spStaName
                    },
                    beforeSend:function (){
                        $('#myModal').modal('show');
                    },
                    success: function(result){
                        $('#myModal').modal('hide');
                        //重新加载一次表格
                        $('#firstTable').bootstrapTable('refresh');
                        commonJS.confirm('消息',result.result,result.msg);
                    }
                });
            },

            //初始化其他组件
            getComponents: function () {
                //时间显示到日
                commonJS.showMonth('yyyy-mm-dd',2,'month',nowTime);
            },

            //初始化点击事件
            onEventListener: function () {
                //事件图标触发日期选择
                $('#timeIconOne').on('click',function () {
                    $('#startTime').trigger('focus');
                });
                $('#timeIconTwo').on('click',function () {
                    $('#endTime').trigger('focus');
                });

                //查询
                $('#btn-query').on('click', function () {
                    $('#firstTable').bootstrapTable('refresh');
                });
            },

            init: function () {
                if (typeof JSON == 'undefined') {
                    $('head').append($("<script type='text/javascript' src='@{/js/resource/json2.js}'>"));
                }
                this.getComponents();
                //打开页面时先加载第一个表格
                this.getTab('firstTable',allUrl.query);
                this.onEventListener();
            }
        }
    }();
    page.init();
});