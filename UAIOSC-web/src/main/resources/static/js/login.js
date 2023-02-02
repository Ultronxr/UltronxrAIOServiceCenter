$(function () {
});

form.on('submit(login)', function(data) {
    console.log(data.field);

    app.util.ajax.post(app.util.api.getAPIUrl('system.login'),
        JSON.stringify(data.field),
        function (res) {
            // console.log(res);
            switch (res.code) {
                case app.RESPONSE_CODE.SUCCESS: {
                    app.util.token.clear();
                    app.util.token.auth.set(res.data[app.util.token.usernameKey], res.data[app.util.token.authKey], null, null);
                    app.util.token.refresh.set(res.data[app.util.token.usernameKey], res.data[app.util.token.refreshKey], null, null);
                    window.location.href = '/index';
                    break;
                }
                case app.RESPONSE_CODE.UNAUTHORIZED: {
                    //layer.msg('登录失败！' + res.msg, {icon:5});
                    layer.msg('登录失败！', {icon:5});
                    break;
                }
                default: {
                    layer.msg(res.msg, {icon:4});
                    break;
                }
            }
        },
        function (res) {
            let msg;
            let resText = ' (' + res.status + ' ' + res.statusText + ')';
            switch (res.status) {
                case 0: msg = '登录超时！'; break;
                case 404: msg = '找不到地址！'; break;
                default: msg = ''; break;
            }
            // console.log(res);
            layer.msg(msg + resText, {icon:2});
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
