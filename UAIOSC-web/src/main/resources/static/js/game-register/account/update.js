$(function () {
    loadAllSelect();
});

function loadAllSelect() {
    loadSelect(getAPIUrl('game-register.platform.list'), $('#platform'));
    loadSelect(getAPIUrl('game-register.shop.list'), $('#shop'));
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
    ajaxPost(getAPIUrl('game-register.account.update'),
        JSON.stringify(data.field),
        function (res) {
            // console.log(res);
            if(res.code === RESPONSE_CODE.SUCCESS) {
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
