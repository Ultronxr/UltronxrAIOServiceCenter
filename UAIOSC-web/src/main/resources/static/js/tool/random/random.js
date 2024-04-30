$(function () {
    loadAllSelect();
});

function loadAllSelect() {
}

form.on('submit(submit)', function(data) {
    app.util.ajax.post(app.util.api.getAPIUrl('tool.random.string'),
        JSON.stringify(data.field),
        function (res) {
            // console.log(res);
            if(res.code === app.RESPONSE_CODE.SUCCESS) {
                // parent.layer.msg('新增成功。', {time: 2000});
                // closeLayerWindow();
                // parent.refreshTable();
            }
        },
        function (res) {
            parent.layer.msg('请求失败！', {time: 2000});
        },
        function () {
            // this.layerIndex = layer.load(2, { shade: [0.2, '#ccc'] });
        },
        function () {
            // layer.close(this.layerIndex);
        }
    );

    return false;
});
