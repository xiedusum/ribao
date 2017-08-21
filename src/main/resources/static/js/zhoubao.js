/*
 * 代码生成工具自动生成
 * wanghz@sunmnet.com
 *
 */

var columns = [{field:'id',hidden:true},
    {field:'module',title:'模块',width:10,align:'left',sortable:true},
    {field:'description',title:'描述',width:40,align:'left',sortable:true},
    {field:'name',title:'姓名',width:5,align:'left',sortable:true},
    {field:'plan',title:'计划',width:5,align:'left',sortable:true},
    {field:'practical',title:'实际',width:5,align:'left',sortable:true},
    {field:'week',title:'周',width:10,align:'left',sortable:true},
    {field:'note',title:'备注',width:25,align:'left',sortable:true}];

var sel_url = path + 'zhoubao/find';
var addOrUpdate_url = path + 'zhoubao/save';
var del_url = path + 'zhoubao/del';
var export_url = path + 'zhoubao/export';

$(document).ready(function() {
    initDialog();
    loadGrid();
    registeEven();
});

function initDialog(){
    $('#dd').dialog({
        width: 600,
        height: 341,
        maximized: true,
        title: '添加',
        closed: true,
        cache: false,
        modal: true,
        buttons: buttons
    });
}


/**
 * 注册事件
 */
function registeEven(){
//	按钮点击事件
    $('#tb').on('a click','.easyui-linkbutton', function() {
        var operaType = $(this).attr("iconCls");
        if(operaType == "icon-add"){
            addDialog();
        } else if(operaType == "icon-edit"){
            updateDialog();
        } else if(operaType == "icon-remove"){
            sUtils.remove(del_url, '#tt');
        } else if(operaType == "icon-tip"){
            window.open(export_url);
        } else if(operaType == "icon-search"){
            sUtils.query('#tt', getParams());
        }
    });

    sUtils.enterSearch("#tb");
}

function getParams() {
    var params = {};
    params.name = $("#name_Search").textbox('getValue');
    params.weekq = $("#weekq_Search").textbox('getValue');
    params.weekz = $("#weekz_Search").textbox('getValue');
    return params;
}

/**
 * 加载列表
 */
function loadGrid(){

    $('#tt').datagrid({
        url:sel_url,
        columns:[columns],
        toolbar:'#tb',
        queryParams:getParams(),
        onDblClickRow: function(rowIndex,row){
            updateDialog();
        }
    });
}


var buttons = [{
    text:'确定',
    iconCls:'icon-ok',
    handler:function(){
        sUtils.addOrUpdate(addOrUpdate_url, '#tt', '#ff', '#dd');
    }
},{
    text:'取消',
    iconCls:'icon-no',
    handler:function(){
        $('#dd').dialog('close');
    }
}];


function addDialog(){
    sUtils.add('#ff','#dd',function(ff, dd){
        $("#name").textbox('setValue',$("#name_Search").textbox('getValue'));
        $("#week").textbox('setValue',$("#weekq_Search").textbox('getValue'));
    });
}

function updateDialog(){
    sUtils.update('#tt','#ff','#dd',function(tt, ff, dd, row){

    });
}
