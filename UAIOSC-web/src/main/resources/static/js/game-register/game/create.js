var laydate;
layui.define(function(){
    laydate = layui.laydate;
});

$(function () {
    loadAllSelect();
    laydate.render({
        elem: '#purchaseDate'
    });
});

function loadAllSelect() {
    loadSelect(app.util.api.getAPIUrl('game-register.account.list'), $('#accountId'), "username", "id");
    loadSelect(app.util.api.getAPIUrl('game-register.shop.list'), $('#shop'));
    loadSelect(app.util.api.getAPIUrl('game-register.shop.list'), $('#actualPlayShop'));
    let gameVersionData = [
        {value: "本体", name: "本体"},
        {value: "DLC", name: "DLC"},
        {value: "捆绑包", name: "捆绑包"},
        {value: "其他", name: "其他"},
    ];
    loadSelectFromJson(gameVersionData, $("#version"), "name", "value");
}

form.on('submit(create)', function(data) {
    app.util.ajax.post(app.util.api.getAPIUrl('game-register.game.create'),
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

$('#cancel-btn').on('click', function() {
    closeLayerWindow();
});
