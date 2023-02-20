var upload;
layui.define(function(){
    upload = layui.upload;
});

$(function () {
    loadAllSelect();
});

function loadAllSelect() {
    loadSelect(app.util.api.getAPIUrl('game-register.account.list'), $('#accountId'), "username", "id");
    loadSelect(app.util.api.getAPIUrl('game-register.shop.list'), $('#shop'));
}

var uploadInst = upload.render({
    elem: "#selectJsonFile"
    ,accept: "file"
    ,acceptMime: "application/json"
    ,exts: "json"
    ,auto: false
    ,bindAction: "#importJson"
    ,size: "20480" // KB
    ,multiple: false // 这里设置了不能一次选择多文件，但是可以多次选择文件，所以在 choose() 回调里面要进行处理
    ,number: 1
    ,drag: true
    ,url: app.util.api.getAPIUrl("game-register.game.import.json")
    ,data: {
        // 请求接口时的额外参数
        accountId: $("#accountId").val(),
        shopId: $("#shop").val()
    }
    ,choose: function (obj) {
        // 选择文件后的回调
        let files = obj.pushFile();
        obj.preview(
            // 三个参数：文件索引、文件对象、文件base64编码
            function(index, file, result) {
                if(file.type !== "application/json") {
                    delete files[index];
                    return;
                }
                layui.$('#selectedFiles').html("<p>" + file.name + "</p>");
                // 只允许总共一个文件上传，所以要把之前选择的文件清除掉
                if(Object.keys(files).length > 1) {
                    for(let fileIndex in files) {
                        if(fileIndex !== index) {
                            delete files[fileIndex];
                        }
                    }
                }
                console.log("当前选择的文件：" + files[index].name);
            });
    }
    ,before: function(obj) {
        // 文件提交上传前的回调
        // this.layerIndex = layer.load(2, { shade: [0.2, '#ccc'] });
    }
    ,done: function(res){
        // 上传完毕回调
        // layer.close(this.layerIndex);
        if(res.code !== app.RESPONSE_CODE.SUCCESS) {
            parent.layer.msg('请求成功，但发生错误！', {time: 2000});
            return;
        }
        parent.layer.msg('导入成功。', {time: 2000});
        closeLayerWindow();
        parent.refreshTable();
    }
    ,error: function(){
        // 请求异常回调
        // layer.close(this.layerIndex);
        parent.layer.msg('导入失败！', {time: 2000});
    }
});

// 这个方法保证在导入出错未关闭弹窗时，弹窗不会被自动刷新，防止已填写的内容被重置
form.on('submit(importJson)', function(data) {
    return false;
});

form.on('submit(importAPI)', function(data) {
    app.util.ajax.post(app.util.api.getAPIUrl("game-register.game.import.api"),
        JSON.stringify(data.field),
        function (res) {
            if(res.code !== app.RESPONSE_CODE.SUCCESS) {
                parent.layer.msg('请求成功，但发生错误！', {time: 2000});
                return;
            }
            parent.layer.msg('导入成功。', {time: 2000});
            closeLayerWindow();
            parent.refreshTable();
        },
        function (res) {
            parent.layer.msg('导入失败！', {time: 2000});
            layer.close(this.layerIndex);
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
