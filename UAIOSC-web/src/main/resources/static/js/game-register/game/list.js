$(function () {
   loadAllSelect();
});

function loadAllSelect() {
    let gameVersionData = [
        {value: "本体", name: "本体"},
        {value: "DLC", name: "DLC"},
        {value: "捆绑包", name: "捆绑包"},
        {value: "其他", name: "其他"},
    ];
    loadSelectFromJson(gameVersionData, $("#version"), "name", "value");
    loadSelect(app.util.api.getAPIUrl('game-register.account.list'), $('#accountId'), "username", "id");
    loadSelect(app.util.api.getAPIUrl('game-register.shop.list'), $('#shop'));
}

table.render({
    elem: '#data-table'
    ,id: 'dataTable'
    ,height: 500
    ,cols: [[ //表头
        {type: 'checkbox', fixed: 'left'}
        ,{field: 'gameId', title: 'ID', sort: true, hide: true}
        ,{field: 'parentId', title: '父ID', sort: true, hide: true}
        ,{field: 'shop', title: '购买商城', sort: true}
        ,{field: 'version', title: '版本', sort: true}
        ,{field: 'name', title: '游戏名', sort: true}
        ,{field: 'nameEng', title: '英文名', sort: true}
        ,{field: 'description', title: '描述', sort: true}
        ,{field: 'tag', title: '标签', sort: true}
        ,{field: 'developer', title: '开发商', sort: true}
        ,{field: 'publisher', title: '发行商', sort: true}
        ,{field: 'purchaseDate', title: '购买日期', sort: true}
        ,{field: 'purchasePrice', title: '购买价格', sort: true}
        ,{field: 'note', title: '备注', sort: false}
        ,{title:'操作', width: 125, minWidth: 125, fixed: 'right', toolbar: '#inlineToolbar'}
    ]]
    ,toolbar: '#toolbar'
    ,defaultToolbar: [] //清空默认的三个工具栏按钮
    ,totalRow: false

    ,url: app.util.api.getAPIUrl('game-register.game.query')
    ,method: 'POST'
    ,contentType: 'application/json'
    ,headers: {}
    ,where: {}
    ,page: false //分页
    ,request: {
        // pageName: 'page' //页码的参数名称，默认：page
        // ,limitName: 'limit' //每页数据量的参数名，默认：limit
    }
    ,response: {
        statusName: 'code' //规定数据状态的字段名称，默认：code
        ,statusCode: 200 //规定成功的状态码，默认：0
        ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
        ,countName: 'count' //规定数据总数的字段名称，默认：count
        ,dataName: 'data' //规定数据列表的字段名称，默认：data
    }
    ,parseData(res) { //将返回的任意数据格式解析成 table 组件规定的数据格式
        //console.log(res);
        return {
            "code": res.code,
            "msg": res.msg,
            "count": res.total,
            "data": res.data
        };
    }
});

// 刷新表格，不包含任何条件，恢复到初始状态
function refreshTable() {
    table.reloadData('dataTable', {
        where: {} // 清空搜索条件内容
    });
}

// 按钮的点击事件，关键数据：data-type="reload/clear"
var active = {
    reload: function(){
        table.reloadData('dataTable', {
            // page: {
            //     curr: 1 //重新从第 1 页开始
            // },
            where: { //设定异步数据接口的额外参数
                accountId: $("#accountId").val(),
                shop: $("#shop").val(),
                version: $("#version").val(),
                name: $("#name").val(),
                description: $("#description").val(),
                tag: $("#tag").val(),
                developer: $("#developer").val(),
                publisher: $("#publisher").val(),
            }
        });
    },
    clear: function () {
        $("#accountId").val("");
        $("#shop").val("");
        $("#version").val("");
        $("#name").val(null);
        $("#description").val(null);
        $("#tag").val(null);
        $("#developer").val(null);
        $("#publisher").val(null);
        refreshTable();
        form.render('select');
    }
};

// 监听按钮的点击事件
$('#table-div .layui-btn').on('click', function(){
    let type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
});

// 删除选中的行
function deleteRows(toBeDeletedIdList) {
    app.util.ajax.delete(app.util.api.getAPIUrl('game-register.game.delete'),
        {idList: toBeDeletedIdList.join()},
        function (res) {
            // console.log(res);
            if(res.code === app.RESPONSE_CODE.SUCCESS) {
                layer.msg('删除成功。', {time: 2000});
                refreshTable();
            }
        },
        function (res) {
            layer.msg('删除失败！', {icon:2, time: 2000});
            refreshTable();
        }
    );
}

// 顶部工具栏事件
table.on('toolbar(dataTable)', function(obj){
    let id = obj.config.id; // 获取表格ID
    let checkStatus = table.checkStatus(id); // 获取表格中复选框选中的行
    switch(obj.event){
        case 'create' :
            layer.open({
                title: '新增游戏',
                type: 2,
                content: 'create.html',
                area: ['800px', '900px']
            });
            break;
        case 'delete':
            let checkedRows = checkStatus.data;
            // console.log(checkedRows);
            if(checkedRows.length === 0) {
                layer.msg('未选中任何一行！', {time: 2000});
            } else {
                layer.confirm('你选中了 ' + checkedRows.length + ' 行，确认删除？', {icon: 3, title:'提示'}, function (index) {
                    let toBeDeletedIdList = [];
                    for(let row of checkedRows) {
                        toBeDeletedIdList.push(row.gameId);
                    }
                    deleteRows(toBeDeletedIdList);
                });
            }
            break;
        case 'import':
            layer.open({
                title: '导入游戏',
                type: 2,
                content: 'import.html',
                area: ['650px', '500px']
            });
            break;
    };
});

// 单元格内工具栏事件
table.on('tool(dataTable)', function(obj) {
    let rowData = obj.data;
    if(obj.event === 'del'){
        layer.confirm('确认删除此行？', {icon: 3, title:'提示'}, function(index) {
            let toBeDeletedIdList = [rowData.gameId];
            deleteRows(toBeDeletedIdList);
        });
    } else if(obj.event === 'edit'){
        layer.open({
            title: '更新游戏信息',
            type: 2,
            content: 'update.html',
            area: ['800px', '900px'],
            success: function (layero, index) {
                // 往子页面添加内容
                // let body = layer.getChildFrame('body', index);
                // body.find('#content').append(editor.txt.html());
                // 执行子页面的函数
                let iframe = window[layero.find('iframe')[0]['name']];
                iframe.loadSelectAndSetRowData(rowData);
            }
        });
    }
});

// 触发表格复选框选择事件
// table.on('checkbox(accountTable)', function(obj){
//     console.log(obj)
// });
