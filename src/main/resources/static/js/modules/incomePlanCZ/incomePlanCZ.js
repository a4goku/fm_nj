$(function(){

    var page;
    var allUrlInCome={//后台交互URL
        pullDown:ctx+'queryutils/QueryUtilsController/query_combobox',//获取下拉菜单
        query:ctx+'Incomeinfo/IncomeController/query_ad68_pagedata',//加载表格
        querySon:ctx+'Incomeinfo/IncomeController/query_ad68_pagedata_item'//加载子表
    };
    page = function () {

        return {
            // 加载表格
            getTab: function (id,url) {
                // 初始化第一个表格
                $('#'+id).bootstrapTable({
                    url: url,
                    queryParams: function (params) {
                        var temp = {//如果是在服务器端实现分页，limit、offset这两个参数是必须的
                            pageSize: params.limit, // 每页显示数量
                            pageNumber: params.offset / params.limit + 1, //当前页码
                            "AAE140": $('#safeSelect').selectpicker('val'),//险种
                            "AAA116":$('#jfSelect').selectpicker('val'),//缴费类型
                            "AAE003": $('.datetimepicker').val().replace('-', '')//所属月份
                        };
                        return temp;
                    },
                    method: 'post',
                    contentType: "application/x-www-form-urlencoded",//当请求方法为post的时候,必须要有！！！！
                    dataField: "result",//定义从后台接收的字段，包括result和total，这里我们取result
                    pageNumber: 1, //初始化加载第一页
                    pageSize: 10,
                    pagination: true, // 是否分页
                    sidePagination: 'server',//server:服务器端分页|client：前端分页
                    paginationHAlign: 'left',//分页条水平方向的位置，默认right（最右），可选left
                    paginationDetailHAlign: 'right',//paginationDetail就是“显示第 1 到第 8 条记录，总共 15 条记录 每页显示 8 条记录”，默认left（最左），可选right
                    columns: [    //表头
                        //{field: 'ck', checkbox: true},//checkbox列
                        {
                            field: 'number', title: '序号', align: 'center', formatter: function (value, row, index) {
                                return index + 1;
                            }
                        },
                        {field: 'AAE003', title: '费款所属期', align: 'center'},
                        {field: 'BIE001', title: '核定流水号', align: 'center'},
                        {field: 'AAE140', title: '险种', align: 'center'},
                        {field: 'AAA116', title: '业务编码', align: 'center'},
                        {field: 'AAA028', title: '缴费主体类型', align: 'center'},
                        {field: 'AAZ010', title: '缴费主体编号', align: 'center'},
                        {field: 'AAB003', title: '缴费主体身份代码', align: 'center'},
                        {field: 'AAB069', title: '缴费主体名称', align: 'center'},
                        {
                            field: 'AAE020', title: '应收金额合计', align: 'right', halign: 'center',
                            formatter: function (value) {
                                var num=parseFloat(value);
                                return commonJS.thousandPoint(num.toFixed(2));
                            }
                        },
                        {field: 'AAB119', title: '应缴笔数', align: 'center'},
                        {field: 'AAE002', title: '结算期', align: 'center'},
                        {field: 'AAB034', title: '社会保险经办机构编码', align: 'center'},
                        {field: 'AAC128', title: '缴费方式', align: 'center'},
                        {field: 'AAE080', title: '实缴金额', align: 'right', halign: 'center',
                            formatter:function (value) {
                                var num=parseFloat(value);
                                commonJS.thousandPoint(num.toFixed(2));
                            }
                        },
                        {field: 'AAE017', title: '核销标志', align: 'center'},
                        {field: 'AAE018', title: '核销时间', align: 'center'},
                        {field: 'AAB190', title: '到账标志', align: 'center'},
                        {field: 'AAB191', title: '到账日期', align: 'center'},
                        {field: 'AAE011', title: '经办人', align: 'center'},
                        {field: 'AAE036', title: '经办日期', align: 'center'}
                    ],
                    //双击打开子表
                    onDblClickRow: function (row) {
                        $('#win').modal('show');
                        $('#clickData').bootstrapTable('destroy').bootstrapTable({
                            url: allUrlInCome.querySon,
                            queryParams: function (params) {
                                var temp = {//如果是在服务器端实现分页，limit、offset这两个参数是必须的
                                    pageSize: params.limit, // 每页显示数量
                                    pageNumber: params.offset / params.limit + 1, //当前页码
                                    "BIE001":row.BIE001
                                };
                                return temp;
                            },
                            method: 'post',
                            contentType: "application/x-www-form-urlencoded",//当请求方法为post的时候,必须要有！！！！
                            dataField: "result",//定义从后台接收的字段，包括result和total，这里我们取result
                            pageNumber: 1, //初始化加载第一页
                            pageSize: 10,
                            pagination: true, // 是否分页
                            sidePagination: 'server',//server:服务器端分页|client：前端分页
                            paginationHAlign: 'left',//分页条水平方向的位置，默认right（最右），可选left
                            paginationDetailHAlign: 'right',//paginationDetail就是“显示第 1 到第 8 条记录，总共 15 条记录 每页显示 8 条记录”，默认left（最左），可选right
                            columns: [    //表头
                                //{field: 'ck', checkbox: true},//checkbox列
                                {
                                    field: 'number', title: '序号', align: 'center', formatter: function (value, row, index) {
                                        return index + 1;
                                    }
                                },
                                {field: 'BIE001', title: '核定流水号', align: 'center'},
                                {field: 'AAZ223', title: '征缴明细ID', align: 'center'},
                                {field: 'AAA028', title: '缴费主体类型', align: 'center'},
                                {field: 'AAZ010', title: '缴费主体编号', align: 'center'},
                                {field: 'AAB003', title: '缴费主体身份代码', align: 'center'},
                                {field: 'AAB069', title: '缴费主体名称', align: 'center'},
                                {field: 'AAE002', title: '结算期', align: 'center'},
                                {field: 'AAE003', title: '费款所属期', align: 'center'},
                                {field: 'AAE140', title: '险种类型', align: 'center'},
                                {field: 'AAA115', title: '缴费类型', align: 'center'},
                                {field: 'AAA157', title: '款项', align: 'center'},
                                {field: 'BAE151', title: '基金来源', align: 'center'},
                                {field: 'AAE180', title: '缴费基数', align: 'center'},
                                {field: 'AAE020', title: '应收金额', align: 'center'},
                                {field: 'AAE080', title: '实缴金额', align: 'center'},
                                {field: 'AAE017', title: '核销标志', align: 'center'},
                                {field: 'AAE018', title: '核销日期', align: 'center'},
                                {field: 'BAE181', title: '数据来源', align: 'center'},
                                {field: 'AAB114', title: '实账标志', align: 'center'},
                                {field: 'ZBX001', title: '指标项目1', align: 'center'},
                                {field: 'ZBX002', title: '指标项目2', align: 'center'},
                                {field: 'ZBX003', title: '指标项目3', align: 'center'},
                                {field: 'ZBX004', title: '指标项目4', align: 'center'},
                                {field: 'ZBX005', title: '指标项目5', align: 'center'}
                            ]
                        });
                    }
                });
            },

            //初始化其他组件
            getComponents: function () {
                //险种
                $.post(allUrlInCome.pullDown,{
                    "valset_id": "AAE140"
                },function(result){
                    $('#safeSelect').append("<option value='-1'>全部</option>");
                    for (var i = 0; i < JSON.parse(result).result.length; i++) {
                        $('#safeSelect').append("<option value='"+JSON.parse(result).result[i].VAL_ID+"'>"
                            + JSON.parse(result).result[i].VAL + "</option>");
                    }
                    $('#safeSelect').selectpicker('refresh');
                });
                //缴费类型
                $.post(allUrlInCome.pullDown,{
                    "valset_id": "AAA116"
                },function(result){
                    $('#jfSelect').append("<option value='-1'>全部</option>");
                    for (var i = 0; i < JSON.parse(result).result.length; i++) {
                        $('#jfSelect').append("<option value='"+JSON.parse(result).result[i].VAL_ID+"'>"
                            + JSON.parse(result).result[i].VAL + "</option>");
                    }
                    $('#jfSelect').selectpicker('refresh');
                });

                //时间显示到月
                commonJS.showMonth('yyyy-mm',3,'year',nowTimeToMonth);
            },

            //初始化点击事件
            onEventListener: function () {
                //事件图标触发日期选择
                $('.glyphicon-time').on('click', function () {
                    $('.datetimepicker').eq(0).trigger('focus');
                });

//				查询
                $('#btn-query').on('click', function () {
                    $('#firstTable').bootstrapTable('refresh');
                });
            },
            init: function () {
                if (typeof JSON == 'undefined') {
                    $('head').append($("<script type='text/javascript' src='js/resource/json2.js'>"));
                }
                this.getComponents();
                //打开页面时先加载第一个表格
                this.getTab('firstTable',allUrlInCome.query);
                this.onEventListener();
            }
        }
    }();
	page.init();
});