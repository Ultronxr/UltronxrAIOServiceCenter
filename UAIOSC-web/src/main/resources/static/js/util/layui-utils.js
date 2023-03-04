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
 * @param selectName   json数据中用于 select选项 显示名称 的对象属性名，缺省为"name"
 * @param selectValue  json数据中用于 select选项 实际值 的对象属性名，缺省为"id"
 * @param async        请求数据时是异步还是同步：
 *                          true - 异步（缺省）；false - 同步
 * @param callback     请求完数据之后执行的回调函数
 * @param excludeEmptyOption 是否排除空选项（即默认的 “--- 请选择 ---” 选项）：
 *                              true - 排除（即不包含默认的“请选择”选项）；false - 不排除（缺省，即包含默认的“请选择”选项）
 */
function loadSelect(url, targetSelect, selectName, selectValue, async, callback, excludeEmptyOption) {
    app.util.ajax.get(url,
        null,
        function (res) {
            loadSelectFromJson(res.data, targetSelect, selectName, selectValue, excludeEmptyOption);
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

/**
 * 使用现成的 json 数据加载 select 选项框的选项
 *
 * @param json         现成的数据，json对象 或 json字符串
 * @param targetSelect 目标 select 选项框的jquery对象
 * @param selectName   json数据中用于 select选项 显示名称 的对象属性名，缺省为"name"
 * @param selectValue  json数据中用于 select选项 实际值 的对象属性名，缺省为"id"
 * @param excludeEmptyOption 是否排除空选项（即默认的 “--- 请选择 ---” 选项）：
 *                              true - 排除（即不包含默认的“请选择”选项）；false - 不排除（缺省，即包含默认的“请选择”选项）
 */
function loadSelectFromJson(json, targetSelect, selectName, selectValue, excludeEmptyOption) {
    if(app.util.string.isEmpty(selectName)) {
        selectName = "name";
    }
    if(app.util.string.isEmpty(selectValue)) {
        selectValue = "id";
    }

    let jsonObj = json;
    if(app.util.string.isJsonStringify(json)) {
        jsonObj = JSON.parse(json);
    }
    let options = !excludeEmptyOption ? '<option value="">--- 请选择 ---</option>' : '';
    for (let i in jsonObj) {
        options += '<option value="' + jsonObj[i][selectValue] + '">' + jsonObj[i][selectName] + '</option>';
    }
    targetSelect.html(options);
    form.render('select');
}
