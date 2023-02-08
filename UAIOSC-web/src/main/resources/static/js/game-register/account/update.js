/**
 * 父页面调用 iframe 执行的方法，同步加载 select 下拉列表、并填充数据
 * @param rowData 父页面传递过来的表格中的某一行数据
 */
function loadSelectAndSetRowData(rowData) {
    loadSelect(app.util.api.getAPIUrl('game-register.platform.list'), $('#platform'), "name", "id", false);
    loadSelect(app.util.api.getAPIUrl('game-register.shop.list'), $('#shop'), "name", "id", false);
    setRowData(rowData);
}

/**
 * 把父页面传递过来的数据填入输入框、选项框<br/>
 * 此方法由父页面的 layer.open() 中的 success 回调函数执行。
 *
 * @param rowData 父页面传递过来的表格中的某一行数据
 */
function setRowData(rowData) {
    $('#id').val(rowData.id);
    $('#nick').val(rowData.nick);
    $('#username').val(rowData.username);
    $('#email').val(rowData.email);
    $('#phone').val(rowData.phone);
    $('#platform').val(rowData.platform);
    $('#shop').val(rowData.shop);
    $('#region').val(rowData.region);
    $('#note').val(rowData.note);
    form.render('select');
}

form.on('submit(update)', function(data) {
    app.util.ajax.put(app.util.api.getAPIUrl('game-register.account.update'),
        JSON.stringify(data.field),
        function (res) {
            // console.log(res);
            if(res.code === app.RESPONSE_CODE.SUCCESS) {
                parent.layer.msg('更新成功。', {time: 2000});
                closeLayerWindow();
                parent.refreshTable();
            }
        },
        function (res) {
            parent.layer.msg('更新失败！', {time: 2000});
        },
        function () {
            this.layerIndex = layer.load(2, { shade: [0.2, '#ccc'] });
        },
        function () {
            layer.close(this.layerIndex);
        }
    );

    return false;
});

$('#cancel-btn').on('click', function() {
    closeLayerWindow();
});
