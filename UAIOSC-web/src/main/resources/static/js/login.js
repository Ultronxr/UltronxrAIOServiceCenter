var layer, form, table;
layui.define(function(){
    layer = layui.layer;
    form = layui.form;
    // table = layui.table;

    console.log('layui 模块加载完成。');
    //exports('index', {});
});

form.on('submit(login)', function(data){
    $.ajaxSetup({
        url: '/ajaxLogin',
        method: 'POST',
        timeout: 10000,
        headers: {
            'Content-Type': 'application/json'
        },
        data: JSON.stringify(data.field),
        beforeSend: function () {
            this.layerIndex = layer.load(2, { shade: [0.2, '#ccc'] });
        },
        complete: function () {
            layer.close(this.layerIndex);
        },
        success: function (res) {
            // console.log(res);
            switch (res.code) {
                case RESPONSE_CODE.SUCCESS: {
                    window.location.href = '/index'
                    break;
                }
                case RESPONSE_CODE.FAIL: {
                    layer.msg('登录失败！' + res.msg, {icon:5});
                    break;
                }
                default: {
                    layer.msg(res.msg, {icon:4});
                    break;
                }
            }
        },
        error: function (res) {
            let msg;
            let resText = ' (' + res.status + ' ' + res.statusText + ')';
            switch (res.status) {
                case 0: msg = '登录超时！'; break;
                case 404: msg = '找不到地址！'; break;
                default: msg = ''; break;
            }
            console.log(res);
            layer.msg(msg + resText, {icon:2});
        }
    });
    $.ajax();

    return false;
});
