var laydate;
layui.define(function(){
    laydate = layui.laydate;
});

$(function () {
    laydate.render({
        elem: '#purchaseDate'
    });
});

/**
 * 父页面调用 iframe 执行的方法，同步加载 select 下拉列表、并填充数据
 * @param rowData 父页面传递过来的表格中的某一行数据
 */
function loadSelectAndSetRowData(rowData) {
    loadSelect(app.util.api.getAPIUrl('game-register.account.list'), $('#accountId'), "username", "id", false);
    loadSelect(app.util.api.getAPIUrl('game-register.shop.list'), $('#shop'), "name", "id", false);
    loadSelect(app.util.api.getAPIUrl('game-register.shop.list'), $('#actualPlayShop'), "name", "id", false);
    let gameVersionData = [
        {value: "本体", name: "本体"},
        {value: "DLC", name: "DLC"},
        {value: "捆绑包", name: "捆绑包"},
        {value: "其他", name: "其他"},
    ];
    loadSelectFromJson(gameVersionData, $("#version"), "name", "value");
    setRowData(rowData);
}

/**
 * 把父页面传递过来的数据填入输入框、选项框<br/>
 * 此方法由父页面的 layer.open() 中的 success 回调函数执行。
 *
 * @param rowData 父页面传递过来的表格中的某一行数据
 */
function setRowData(rowData) {
    $("#gameId").val(rowData.gameId);
    $("#parentId").val(rowData.parentId);
    $("#accountId").val(rowData.accountId);
    $("#shop").val(rowData.shop);
    $("#actualPlayShop").val(rowData.actualPlayShop);
    $("#appId").val(rowData.appId);
    $("#version").val(rowData.version);
    $("#name").val(rowData.name);
    $("#nameEng").val(rowData.nameEng);
    $("#nameNick").val(rowData.nameNick);
    $("#description").val(rowData.description);
    $("#tag").val(rowData.tag);
    $("#url").val(rowData.url);
    $("#logo").val(rowData.logo);
    $("#img").val(rowData.img);
    $("#developer").val(rowData.developer);
    $("#publisher").val(rowData.publisher);
    $("#lowestPriceCurrency").val(rowData.lowestPriceCurrency);
    $("#lowestPrice").val(rowData.lowestPrice);
    $("#lowestPriceRmb").val(rowData.lowestPriceRmb);
    $("#purchaseDate").val(rowData.purchaseDate);
    $("#purchasePriceCurrency").val(rowData.purchasePriceCurrency);
    $("#purchasePrice").val(rowData.purchasePrice);
    $("#purchasePriceRmb").val(rowData.purchasePriceRmb);
    $("#note").val(rowData.note);
    form.render("select");
}

form.on('submit(update)', function(data) {
    app.util.ajax.put(app.util.api.getAPIUrl('game-register.game.update'),
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
