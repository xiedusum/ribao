if($.fn.combo){
    $.fn.combo.defaults.editable=false;
}
if ($.fn.datagrid){
    $.fn.datagrid.defaults.idField = "id";
    $.fn.datagrid.defaults.fit = true;
    $.fn.datagrid.defaults.fitColumns = true;
    $.fn.datagrid.defaults.border = false;
    $.fn.datagrid.defaults.singleSelect = true;
    $.fn.datagrid.defaults.selectOnCheck = false;//check不会跟着select变
    $.fn.datagrid.defaults.checkOnSelect = false;//select不会check
    $.fn.datagrid.defaults.pagination = true;
    $.fn.datagrid.defaults.rownumbers = true;
    $.fn.datagrid.defaults.pageSize = 15;//每页显示的记录条数，默认为10
    $.fn.datagrid.defaults.pageList = [15,30,45];//可以设置每页记录条数的列表 ;
    $.fn.datagrid.defaults.remoteSort = false;
    $.fn.datagrid.defaults.multiSort = true;
    $.fn.datagrid.defaults.frozenColumns = [[{field:'ck',checkbox:true}]];
    $.fn.datagrid.defaults.onLoadSuccess = function(data){
        $(".datagrid-cell").each(function(){
            $(this).attr("title",$(this).text());
        });
    };
    $.fn.datagrid.defaults.onCheck = function(index, row){
        $(this).datagrid('selectRow', index);
    };
}
if ($.fn.treegrid){
    $.fn.treegrid.defaults.fit = true;
    $.fn.treegrid.defaults.fitColumns = true;
    $.fn.treegrid.defaults.autoRowHeight = false;
    $.fn.treegrid.defaults.border = false;
    $.fn.treegrid.defaults.singleSelect = true;
    $.fn.treegrid.defaults.selectOnCheck = false;//check不会跟着select变
    $.fn.treegrid.defaults.checkOnSelect = false;//select不会check
    $.fn.treegrid.defaults.pagination = true;
    // $.fn.treegrid.defaults.rownumbers = true;
    $.fn.treegrid.defaults.pageSize = 15;//每页显示的记录条数，默认为10
    $.fn.treegrid.defaults.pageList = [15,30,45];//可以设置每页记录条数的列表 ;
    $.fn.treegrid.defaults.remoteSort = false;
    $.fn.treegrid.defaults.multiSort = true;
    $.fn.treegrid.defaults.frozenColumns = [[{field:'ck',checkbox:false}]];
    $.fn.treegrid.defaults.onLoadSuccess = function(data){
        $(".datagrid-cell").each(function(){
            $(this).attr("title",$(this).text());
        });
    };
    $.fn.treegrid.defaults.onCheck = function(row){
        $(this).treegrid('select', row.id);
    };
}