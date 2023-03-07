$(function () {
    loadAllSelect();
});

function loadAllSelect() {
}

form.on('submit(create)', function(data) {
    app.util.ajax.post(app.util.api.getAPIUrl('valorant.account.create'),
        JSON.stringify(data.field),
        function (res) {
            // console.log(res);
            if(res.code === app.RESPONSE_CODE.SUCCESS) {
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

form.on('submit(multiFactor)', function(data) {
    app.util.ajax.post(app.util.api.getAPIUrl('valorant.account.multiFactor'),
        JSON.stringify(data.field),
        function (res) {
            parent.layer.msg(res.msg, {time: 2000});
        },
        function (res) {
            parent.layer.msg('请求错误！', {time: 2000});
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
