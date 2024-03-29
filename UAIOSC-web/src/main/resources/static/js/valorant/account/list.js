$(function () {
   loadAllSelect();
});

function loadAllSelect() {
}

table.render({
    elem: '#data-table'
    ,id: 'dataTable'
    ,height: 500
    ,cols: [[ //表头
        {type: 'checkbox', fixed: 'left'}
        ,{field: 'userId', title: 'ID', sort: true, align: 'center', hide: false}
        ,{field: 'username', title: '用户名', sort: true, align: 'center'}
        ,{field: 'socialName', title: '拳头社交名称', sort: true, align: 'center'}
        ,{field: 'socialTag', title: '社交标签', sort: true, align: 'center'}
        ,{field: 'multiFactor', title: '是否开启两步验证', sort: true, align: 'center'}
        ,{title:'操作', fixed: 'right', toolbar: '#inlineToolbar', align: 'center'}
    ]]
    ,toolbar: '#toolbar'
    ,defaultToolbar: [] //清空默认的三个工具栏按钮
    ,totalRow: false

    ,url: app.util.api.getAPIUrl('valorant.account.query')
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
                username: $("#username").val(),
                socialName: $("#socialName").val(),
                socialTag: $("#socialTag").val(),
            }
        });
    },
    clear: function () {
        $("#username").val("");
        $("#socialName").val("");
        $("#socialTag").val("");
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
    app.util.ajax.delete(app.util.api.getAPIUrl('valorant.account.delete'),
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
                title: '新增拳头账号',
                type: 2,
                content: 'create.html',
                area: ['800px', '500px']
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
                        toBeDeletedIdList.push(row.userId);
                    }
                    deleteRows(toBeDeletedIdList);
                });
            }
            break;
    };
});

// 单元格内工具栏事件
table.on('tool(dataTable)', function(obj) {
    let rowData = obj.data;
    if(obj.event === 'del'){
        layer.confirm('确认删除此行？', {icon: 3, title:'提示'}, function(index) {
            let toBeDeletedIdList = [rowData.userId];
            deleteRows(toBeDeletedIdList);
        });
    } else if(obj.event === 'edit'){
        layer.open({
            title: '更新拳头账号',
            type: 2,
            content: 'update.html',
            area: ['800px', '500px'],
            success: function (layero, index) {
                // 往子页面添加内容
                // let body = layer.getChildFrame('body', index);
                // body.find('#content').append(editor.txt.html());
                // 执行子页面的函数
                let iframe = window[layero.find('iframe')[0]['name']];
                iframe.loadSelectAndSetRowData(rowData);
            }
        });
    } else if(obj.event === 'updateMultiFactor') {
        if(rowData.multiFactor !== 'true') {
            layer.msg("该账号未开启两步验证！", {icon: 2, time: 2000});
            return;
        }
        requestMultiFactor(rowData);
    }
});

// 点击更新两步验证码 行内按钮的时候，马上就请求一次，触发发送验证码
function requestMultiFactor(rowData) {
    app.util.ajax.post(app.util.api.getAPIUrl('valorant.account.multiFactor'),
        JSON.stringify(rowData),
        function (res) {
            // console.log(res);
            if(res.code === app.RESPONSE_CODE.SUCCESS) {
                parent.layer.msg('验证码发送成功', {icon: 1, time: 2000});
                layer.prompt({
                    formType: 0,
                    value: '',
                    title: '请输入邮箱中收到的验证码',
                }, function(value, index, elem){
                    updateMultiFactor(rowData, value, index);
                    // layer.close(index);
                });
            } else {
                parent.layer.msg(res.msg, {icon: 2, time: 2000});
            }
        },
        function (res) {
            parent.layer.msg('请求失败！', {icon:2, time: 2000});
        },
        null, null, null, 30000
    );
}

// 收到验证码之后进行更新
function updateMultiFactor(rowData, multiFactorCode, promptIndex) {
    rowData.multiFactor = multiFactorCode;
    app.util.ajax.post(app.util.api.getAPIUrl('valorant.account.updateMultiFactor'),
        JSON.stringify(rowData),
        function (res) {
            // console.log(res);
            if(res.code === app.RESPONSE_CODE.SUCCESS) {
                parent.layer.msg('更新成功。', {icon: 1, time: 2000});
                layer.close(promptIndex);
                refreshTable();
            } else {
                parent.layer.msg(res.msg, {icon: 2, time: 2000});
            }
        },
        function (res) {
            parent.layer.msg('更新失败！', {icon:2, time: 2000});
            refreshTable();
        },
        null, null, null, 30000
    );
}

// 触发表格复选框选择事件
// table.on('checkbox(accountTable)', function(obj){
//     console.log(obj)
// });
