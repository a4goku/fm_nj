$(function(){
    var allUrl={//后台交互URL
        save:ctx+'persionAdjust/PersionAdjustController/insert_PensionAdjust',//保存
        query:ctx+'persionAdjust/PersionAdjustController/query_persionAdjust_pagedata',//加载表格
        edit:ctx+'persionAdjust/PersionAdjustController/query_persionAdjust_item',//编辑
        submit:ctx+'persionAdjust/PersionAdjustController/shenhetijiao_persionAdjust',//提交审核
        del:ctx+'persionAdjust/PersionAdjustController/delete_persionAdjust'//删除
    };
    
    //单元格按钮事件
    window.operateEvents = {
        'click .btn-edit':function (e, value, row, index) {//修改
            $('#firstTable').bootstrapTable('uncheckAll');
            if(row.SP_STATUS!='00'){//判断若已经被审核后，则不可再次编辑
                commonJS.confirm('警告','已提交不可编辑！')
            }
            else{
                $('#win').modal('show');
                $('#btn-save,.appendRow').removeClass('hide');
                $('#win input').attr('readonly',false);
                $('#myModalLabel').html('编辑审核表');
                page.getEditTab(true,row);
            }
        },
        'click .btn-submit':function (e, value, row, index) {//提交
            $('#firstTable').bootstrapTable('uncheckAll');
            if(row.SP_STATUS!='00'){
                commonJS.confirm('警告','已经提交，不可再次提交！');
            }
            else{
                $.ajax({
                    url: allUrl.submit,
                    type:"post",
                    dataType:'json',
                    data:{
                        id:row.ID,
                        spStatus:'01',
                        sp_name:userName,
                        sp_status_name:'制单'
                    },
                    beforeSend:function (){
                        $('#myModal').modal('show');
                    },
                    success: function(result){
                        console.log(row.ID);
                        $('#myModal').modal('hide');
                        $('#firstTable').bootstrapTable('refresh');
                        commonJS.confirm('消息',result.result,result.msg);
                    }
                });
            }
        },
        'click .btn-del':function (e, value, row, index) {//删除
            $('#firstTable').bootstrapTable('uncheckAll');
            if(row.SP_STATUS!='00'){
                commonJS.confirm('警告','已经提交，不可删除！');
            }
            else{
                commonJS.confirm('信息','确认删除？','',function(){
                    $.ajax({
                        url: allUrl.del,
                        type:"post",
                        dataType:'json',
                        data:{
                            id:row.ID
                        },
                        beforeSend:function (){
                            $('#myModal').modal('show');
                        },
                        success: function(result){
                            $('#myModal').modal('hide');
                            $('#firstTable').bootstrapTable('refresh');
                            commonJS.confirm('消息',result.result,result.msg);
                        }
                    });
                });
            }
        },
        'click .btn-see':function (e, value, row, index){//查看
            $('#firstTable').bootstrapTable('uncheckAll');
            $('#win').modal('show');
            $('#win input').attr('readonly','readonly');//input不可编辑
            $('#btn-save,.appendRow').addClass('hide');
            $('#myModalLabel').html('查看审核表');
            page.getEditTab(false,row);

        },
        'click .btn-sh':function (e, value, row, index) {//审核
            $('#firstTable').bootstrapTable('uncheckAll');
            if(row.SP_STATUS=='00'){
                commonJS.confirm('警告','请先提交！');
            }
            else if(row.SP_STATUS!='01'){
                commonJS.confirm('警告','已审核，不可再次审核！');
            }
            else{
                $.ajax({
                    url: allUrl.submit,
                    type:"post",
                    dataType:'json',
                    data:{
                        id:row.ID,
                        spStatus:'02',
                        sp_name:userName,
                        sp_status_name:'审核'
                    },
                    beforeSend:function (){
                        $('#myModal').modal('show');
                    },
                    success: function(result){
                        $('#myModal').modal('hide');
                        $('#firstTable').bootstrapTable('refresh');
                        commonJS.confirm('消息',result.result,result.msg);
                    }
                });
            }
        },
        'click .btn-disagree':function (e, value, row, index) {//驳回
            if(row.SP_STATUS=='00'){
                commonJS.confirm('警告','请先提交！');
            }
            else{
                $.ajax({
                    url: allUrl.submit,
                    type:"post",
                    dataType:'json',
                    data:{
                        id:row.ID,
                        spStatus:'00',
                        sp_name:userName,
                        sp_status_name:'驳回'
                    },
                    beforeSend:function (){
                        $('#myModal').modal('show');
                    },
                    success: function(result){
                        $('#myModal').modal('hide');
                        $('#firstTable').bootstrapTable('refresh');
                        commonJS.confirm('消息',result.result,result.msg);
                    }
                });
            }
        }
    };

    var colEdit=[    //可编辑表格表头
        {field: 'ck', checkbox: true},//checkbox列
        {
            field: 'number', title: '序号', align: 'center', formatter: function (value, row, index) {
                return index + 1;
            }
        },
        {field: 'DWMC', title: '单位名称', align: 'center',editable:{type:'text'}},
        {field: 'SNJJJY', title: '上年末基金累计结余', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'DNJJSR', title: '当年基金预算收入', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'DNJJZC', title: '当年基金预算支出', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'YSJJJE', title: '预算应调剂补助基金金额', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'YJJJJY', title: '预计当年基金结余', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'YJJJSR', title: '预计当年基金征缴收入', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'DNYJZC', title: '当年基金预计支出', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'YJSZJY', title: '预计当年收支结余', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'SBKZS', title: '社保局审核预计动用历年累计结余控制数', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'SXJE', title: '市县申请调剂金额',align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'SBJE', title: '社保局审核调剂金额', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'CZJE', title: '财政厅核定调剂金额', align: 'right',halign:'center',editable:{type:'text'}},
        {field: 'ITEMID', title: '删除ID', align: 'center',visible:false}
    ];

    var mainColOne=[    //主页面表头1
        {field: 'ck', checkbox: true},//checkbox列
        {
            field: 'number', title: '序号', align: 'center', formatter: function (value, row, index) {
                return index + 1;
            }
        },
        {field: 'BZDATE', title: '编制日期', align: 'center'},
        {field: 'YEAR', title: '年份', align: 'center'},
        {field: 'QUARTER', title: '季度', align: 'center'},
        {field: 'SBJBR', title: '经办人', align: 'center'},
        {field: 'SP_STATUS_NAME', title: '审批状态', align: 'center'},
        {field: 'operate', title: '操作', align: 'center',events:operateEvents,formatter:function(row){
                return '<button class="btn btn-primary btn-edit">修改</button>&nbsp;'+
                    '<button class="btn btn-primary btn-submit">提交</button>&nbsp;'+
                    '<button class="btn btn-primary btn-del">删除</button>&nbsp;'+
                    '<button class="btn btn-primary btn-see">查看</button>&nbsp;'+
                    '<button class="btn btn-primary btn-sh">审核</button>&nbsp;'+
                    '<button class="btn btn-primary btn-disagree">驳回</button>'
            },
        }
    ];

    var mainColTwo=[    //主页面表头2
        {
            field: 'number', title: '序号', align: 'center', formatter: function (value, row, index) {
                return index + 1;
            }
        },
        {field: 'BZDATE', title: '编制日期', align: 'center'},
        {field: 'YEAR', title: '年份', align: 'center'},
        {field: 'QUARTER', title: '季度', align: 'center'},
        {field: 'SBJBR', title: '经办人', align: 'center'},
        {field: 'SP_STATUS_NAME', title: '审批状态', align: 'center'},
        {field: 'operate', title: '操作', align: 'center',events:operateEvents,formatter:function(row){
                return '<button class="btn btn-primary btn-see">查看</button>'
            },
        }
    ];

    var page = function () {

        return {
            // 加载表格
            getTab: function (id,url,cols,sendSta) {
                // 初始化第一个表格
                $('#'+id).bootstrapTable('destroy').bootstrapTable({
                    url: url,
                    queryParams: function(params){
                        return{
                            pageSize: params.limit, // 每页显示数量
                            pageNumber: params.offset / params.limit + 1, //当前页码
                            timeStart:$('#startTime').val().replace(/-/g, ''),
                            timeEnd:$('#endTime').val().replace(/-/g, ''),
                            sendStatus:sendSta
                        }
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
                    columns:cols
                });
            },

            //加载编辑单表格
            getWinTab:function(){
                $("#modalTable").bootstrapTable('destroy').bootstrapTable({
                    pageNumber: 1, //初始化加载第一页
                    pageSize: 10,
                    pagination: true, // 是否分页
                    clickToSelect:true,
                    singleSelect:true,
                    sidePagination: 'server',//server:服务器端分页|client：前端分页
                    paginationHAlign: 'left',//分页条水平方向的位置，默认right（最右），可选left
                    paginationDetailHAlign: 'right',//paginationDetail就是“显示第 1 到第 8 条记录，总共 15 条记录 每页显示 8 条记录”，默认left（最左），可选right
                    columns:colEdit,
                    data:[]
                });
            },

            //编辑、查看编辑单表格
            getEditTab:function(editable,row){
                $('#modalTable').bootstrapTable('destroy').bootstrapTable({//加载bootstrap
                    url: allUrl.edit,
                    queryParams: {
                        id:row.ID
                    },
                    method: 'post',
                    editable:editable,
                    contentType: "application/x-www-form-urlencoded",//当请求方法为post的时候,必须要有！！！！
                    dataField: "TABLE",//定义从后台接收的字段，包括result和total，这里我们取result
                    pageNumber: 1, //初始化加载第一页
                    pageSize: 10,
                    pagination: true, // 是否分页
                    clickToSelect:true,
                    singleSelect:true,
                    sidePagination: 'server',//server:服务器端分页|client：前端分页
                    paginationHAlign: 'left',//分页条水平方向的位置，默认right（最右），可选left
                    paginationDetailHAlign: 'right',//paginationDetail就是“显示第 1 到第 8 条记录，总共 15 条记录 每页显示 8 条记录”，默认left（最左），可选right
                    columns:colEdit
                });
                $.ajax({//加载input和span
                    url: allUrl.edit,
                    type:"post",
                    dataType:'json',
                    data:{
                        id:row.ID
                    },
                    beforeSend:function (){
                        $('#myModal').modal('show');
                    },
                    success: function(result){
                        $('#myModal').modal('hide');
                        $('#year').val(result.result[0].YEAR);
                        $('#quarter').val(result.result[0].QUARTER);
                        $('#bzDate').val(result.result[0].BZDATE);
                        $('#shDate').val(result.result[0].SHDATE);
                        $('#sbjz').html(result.result[0].SBJZ);
                        $('#sbld').html(result.result[0].SBLD);
                        $('#sbcz').html(result.result[0].SBCZ);
                        $('#sbjbr').html(result.result[0].SBJBR);
                        $('#czld').html(result.result[0].CZLD);
                        $('#czzr').html(result.result[0].CZZR);
                        $('#czjbr').html(result.result[0].CZJBR);
                    }
                });
            },

            //初始化其他组件
            getComponents: function () {
                //时间显示到日
                commonJS.showMonth('yyyy-mm-dd',2,'month',nowTime);
            },

            //初始化各类事件
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
                    //取当前active的标签页名
                    var activeTab = $('#myTab li.active').find('a').text();
                    if (activeTab == '未发财政') {
                        $('#firstTable').bootstrapTable('refresh');
                    }
                    else if (activeTab == '已发财政') {
                        $('#secondTable').bootstrapTable('refresh');
                    }
                });

                //新增
                $('#btn-add').on('click',function () {
                    $('#firstTable').bootstrapTable('uncheckAll');
                    $('#win').modal('show');
                    $('#btn-save,.appendRow').removeClass('hide');
                    $('#win input').val('').attr('readonly',false);
                    $('#myModalLabel').html('新增审核表');
                    $('#win td span').html('');//新增置空
                    page.getWinTab();//重绘Bootstrap
                });

                //可编辑表格新增行
                $('.btn-plus').on('click',function () {
                    $('#modalTable').bootstrapTable('insertRow',
                        {index:$('#modalTable').bootstrapTable('getData').length,row:{
                            'DWMC':'',
                            'SNJJJY':'',
                            'DNJJSR':'',
                            'DNJJZC':'',
                            'YSJJJE':'',
                            'YJJJJY':'',
                            'YJJJSR':'',
                            'DNYJZC':'',
                            'YJSZJY':'',
                            'SBKZS':'',
                            'SXJE':'',
                            'SBJE':'',
                            'CZJE':'',
                            'ITEMID':$('#modalTable').bootstrapTable('getData').length+1
                        }
                    })
                });

                //删除可编辑表格最后一行
                $('.btn-minus').on('click',function () {
                    if($('#modalTable').bootstrapTable('getSelections').length==0){
                        commonJS.confirm('警告','请选择一条数据删除！');
                    }
                    else {
                        $('#modalTable').bootstrapTable('remove', {
                            field: 'ITEMID',
                            values: [$('#modalTable').bootstrapTable('getSelections')[0].ITEMID]
                        })
                    }
                });

                //保存
                $('#btn-save').on('click',function () {
                    console.log($('#modalTable').bootstrapTable('getData'));
                    var jsonObj={
                        id:$('#firstTable').bootstrapTable('getSelections').length==0?'':$('#firstTable').bootstrapTable('getSelections')[0].ID,
                        year:$('#year').val(),
                        month:$('#month').val(),
                        quarter:$('#quarter').val(),
                        bzdate:$('#bzDate').val(),
                        shdate:$('#shDate').val(),
                        table:$('#modalTable').bootstrapTable('getData'),
                        sbjz:$('#sbjz').html(),
                        sbld:$('#sbld').html(),
                        sbcz:$('#sbcz').html(),
                        sbjbr:$('#sbjbr').html(),
                        czld:$('#czld').html(),
                        czzr:$('#czzr').html(),
                        czjbr:$('#czjbr').html()
                    };
                    $.ajax({
                        url: allUrl.save,
                        type:"post",
                        dataType:'json',
                        data:{
                            pensionAdjustJson:JSON.stringify(jsonObj)
                        },
                        beforeSend:function (){
                            $('#myModal').modal('show');
                        },
                        success: function(result){
                            $('#myModal,#win').modal('hide');
                            //重新加载一次表格
                            $('#firstTable').bootstrapTable('refresh');
                            commonJS.confirm('消息',result.result,result.msg);
                        }
                    });
                });

                //页签中的表格初始化
                $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                    // 获取已激活的标签页的名称
                    var activeTab = $(e.target).text();
                    if (activeTab == '未发财政') {
                        $('#firstTable').bootstrapTable('refresh');
                        $('#btn-add,#btn-appendix,#btn-sendCZ').prop('disabled',false);
                    }
                    else{
                        $('#secondTable').bootstrapTable('refresh');
                        $('#btn-add,#btn-appendix,#btn-sendCZ').prop('disabled',true);
                    }
                });
            },

            init: function () {
                if (typeof JSON == 'undefined') {
                    $('head').append($("<script type='text/javascript' src='@{/js/resource/json2.js}'>"));                }
                this.getComponents();
                //打开页面时先加载第一个表格
                this.getTab('firstTable',allUrl.query,mainColOne,'00');
                this.getTab('secondTable',allUrl.query,mainColTwo,'01');
                this.onEventListener();
            }
        }
    }();
    page.init();
});