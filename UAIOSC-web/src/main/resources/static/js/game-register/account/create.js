$(function () {
    loadAllSelect();
});

function loadAllSelect() {
    loadSelect(getAPIUrl('game-register.platform.list'), $('#platform'));
    loadSelect(getAPIUrl('game-register.shop.list'), $('#shop'));
}

form.on('submit(create)', function(data) {
    ajaxPost(getAPIUrl('game-register.account.create'),
        JSON.stringify(data.field),
        function (res) {
            // console.log(res);
            if(res.code === RESPONSE_CODE.SUCCESS) {
                parent.layer.msg('新增成功。', {time: 2000});
                closeLayerWindow();
                parent.refreshTable();
            }
        },
        function (res) {
            parent.layer.msg('新增失败！', {time: 2000});
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
