function logout() {
    app.util.ajax.get(app.util.api.getAPIUrl('system.logout'),
        null,
        function (res) {
            switch (res.code) {
                case app.RESPONSE_CODE.SUCCESS: {
                    app.util.token.clear();
                    window.location.href = '/login';
                    break;
                }
                case app.RESPONSE_CODE.UNAUTHORIZED: {
                    app.util.token.clear();
                    layer.msg(res.msg, {icon:5});
                    break;
                }
                default: {
                    layer.msg(res.msg, {icon:4});
                    break;
                }
            }
        },
        function (res) {
            let resText = ' (' + res.status + ' ' + res.statusText + ')';
            layer.msg(resText, {icon:2});
        },
    );
}