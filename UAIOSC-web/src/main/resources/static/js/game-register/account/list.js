$(function () {
   loadAllSelect();
});

function loadAllSelect() {
    loadSelect(app.util.api.getAPIUrl('game-register.platform.list'), $('#platform'));
    loadSelect(app.util.api.getAPIUrl('game-register.shop.list'), $('#shop'));
}

table.render({
    elem: '#account-table'
    ,id: 'accountTable'
    ,height: 500
    ,cols: [[ //表头
        {type: 'checkbox', fixed: 'left'}
        ,{field: 'id', title: 'ID', sort: true, hide: true}
        ,{field: 'nick', title: '昵称', sort: true}
        ,{field: 'username', title: '用户名', sort: true}
        ,{field: 'email', title: '邮箱', sort: true}
        ,{field: 'phone', title: '手机号', sort: true}
        ,{field: 'platform', title: '平台', sort: true}
        ,{field: 'shop', title: '商城', sort: true}
        ,{field: 'region', title: '地区', sort: true}
        ,{field: 'note', title: '备注', sort: false}
        ,{title:'操作', width: 125, minWidth: 125, fixed: 'right', toolbar: '#inlineToolbar'}
    ]]
    ,toolbar: '#toolbar'
    ,defaultToolbar: [] //清空默认的三个工具栏按钮
    ,totalRow: false

    ,url: app.util.api.getAPIUrl('game-register.account.query')
    ,method: 'POST'
    ,contentType: 'application/json'
    ,headers: {}
    ,where: {}
    ,page: false //分页
    ,before: function(req) {
        let authToken = app.util.token.auth.get();
        if(app.util.string.isNotEmpty(authToken)) {
            req.headers["Authorization"] = "Bearer " + authToken;
        }
    }
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
    table.reloadData('accountTable', {
        where: {} // 清空搜索条件内容
    });
}

// 按钮的点击事件，关键数据：data-type="reload/clear"
var active = {
    reload: function(){
        table.reloadData('accountTable', {
            // page: {
            //     curr: 1 //重新从第 1 页开始
            // },
            where: { //设定异步数据接口的额外参数
                nick: $('#nick').val(),
                username: $('#username').val(),
                email: $('#email').val(),
                phone: $('#phone').val(),
                platform: $('#platform').val(),
                shop: $('#shop').val(),
                region: $('#region').val(),
                note: $('#note').val()
            }
        });
    },
    clear: function () {
        $('#nick').val(null);
        $('#username').val(null);
        $('#email').val(null);
        $('#phone').val(null);
        $('#platform').val('');
        $('#shop').val('');
        $('#region').val(null);
        $('#note').val(null);
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
    app.util.ajax.delete(app.util.api.getAPIUrl('game-register.account.delete'),
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
table.on('toolbar(accountTable)', function(obj){
    let id = obj.config.id; // 获取表格ID
    let checkStatus = table.checkStatus(id); // 获取表格中复选框选中的行
    switch(obj.event){
        case 'create' :
            layer.open({
                title: '新增游戏帐户',
                type: 2,
                content: 'create.html',
                area: ['800px', '700px']
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
                        toBeDeletedIdList.push(row.id);
                    }
                    deleteRows(toBeDeletedIdList);
                });
            }
            break;
    };
});

// 单元格内工具栏事件
table.on('tool(accountTable)', function(obj) {
    let rowData = obj.data;
    if(obj.event === 'del'){
        layer.confirm('确认删除此行？', {icon: 3, title:'提示'}, function(index) {
            let toBeDeletedIdList = [rowData.id];
            deleteRows(toBeDeletedIdList);
        });
    } else if(obj.event === 'edit'){
        layer.open({
            title: '更新游戏帐户',
            type: 2,
            content: 'update.html',
            area: ['800px', '700px'],
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
