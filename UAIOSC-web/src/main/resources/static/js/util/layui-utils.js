var layer, form, table;
layui.define(function(){
    layer = layui.layer;
    form = layui.form;
    table = layui.table;
    console.log('layui 模块加载完成。');
});

/**
 * 关闭窗口
 */
function closeLayerWindow() {
    let index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

/**
 * 加载 select 选项框的选项
 * @param url          请求地址
 * @param targetSelect 目标 select 选项框的jquery对象
 * @param async        请求数据时是异步还是同步：
 *                          true - 异步（缺省）；false - 同步
 * @param callback     请求完数据之后执行的回调函数
 */
function loadSelect(url, targetSelect, async, callback) {
    app.util.ajax.get(url,
        null,
        function (res) {
            let options = '<option value="">--- 请选择 ---</option>';
            for (let i in res.data) {
                options += '<option value="' + res.data[i].id + '">' + res.data[i].name + '</option>';
            }
            targetSelect.html(options);
            form.render('select');
        },
        function () {
            let msg = targetSelect.selector + ' select 数据加载失败！';
            console.log(msg)
            layer.msg(msg, {time: 2000});
        },
        async
    );

    if(app.util.function.isFunction(callback)) {
        callback();
    }
}
