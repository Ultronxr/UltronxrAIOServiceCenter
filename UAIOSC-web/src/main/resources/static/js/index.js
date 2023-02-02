var element, util, $ = layui.$;
layui.define(function() {
    element = layui.element;
    util = layui.util;
});

$(function () {
    let username = app.util.token.getUsername();
    $("#username").text(username);
});

//头部事件
// util.event('lay-header-event', {
//     //左侧菜单事件
//     menuLeft: function(othis){
//         layer.msg('展开左侧菜单的操作', {icon: 0});
//     }
//     ,menuRight: function(){
//         layer.open({
//             type: 1
//             ,content: '<div style="padding: 15px;">处理右侧面板的操作</div>'
//             ,area: ['260px', '100%']
//             ,offset: 'rt' //右上角
//             ,anim: 5
//             ,shadeClose: true
//         });
//     }
// });

var active = {
    /**
     * 点击左侧垂直导航菜单栏时触发的事件：自动联动tab标签页
     */
    addTab: function(menuUrl, menuName, menuID) {
        //新增一个Tab项
        if(app.util.string.isEmpty(menuUrl)) {
            menuUrl = $(this).attr('data-url');
        }
        if(app.util.string.isEmpty(menuName)) {
            menuName = $(this).attr('data-name');
        }
        if(app.util.string.isEmpty(menuID)) {
            menuID = $(this).attr('data-id');
        }

        // 先判断是否已经有了这个 tab
        // 把当前已打开的所有 tab 的 ID 全部存入一个数组，然后检查即将打开的 tab ID 是否存在于这个数组内
        let arrayObj = [];
        $(".layui-tab-title").find('li').each(function() {
            let tabLayID = $(this).attr("lay-id");
            arrayObj.push(tabLayID);
        });

        let have = $.inArray(menuID, arrayObj);
        if (have >= 0) {
            // 已有相同tab
            element.tabChange('tabFilter', menuID); // 切换到当前点击的页面
            // console.log("跳转已有tab选项卡，layMenuID=" + menuID);
        } else{
            // 没有相同tab
            let contentHtml = '<iframe style="width: 100%;height: 100%;" src='+menuUrl+' ></iframe>';
            // 首页
            if(menuID === "layMenuID00") {
                contentHtml = $("#indexTab").html();
            }
            element.tabAdd('tabFilter', {
                title: menuName
                ,content: contentHtml
                ,id: menuID
            });
            element.tabChange('tabFilter', menuID); // 切换到当前点击的页面
            // console.log("新建tab选项卡，layMenuID=" + menuID);
        }
        element.render("tab", "tabFilter");
    }
};

/**
 * 点击左侧垂直导航菜单栏时触发的事件：自动联动tab标签页
 */
element.on('nav(leftNavFilter)', function(elem){
    // 需要联动 tab 选项卡的 nav 菜单栏必须加上 class="left-nav-menu" 才能生效
    if($(elem).hasClass("left-nav-menu")) {
        let menuUrl = $(elem).attr('data-url'),
            menuName = $(elem).attr('data-name'),
            menuID = $(elem).attr('data-id');
        active.addTab(menuUrl, menuName, menuID);
    }
});

/**
 * 点击tab标签页时触发的事件：自动联动左侧垂直导航菜单栏
 */
element.on('tab(tabFilter)', function(data) {
    let layID = $(this).attr("lay-id");
    let navTree = $(".layui-nav-tree");

    // 移除其他选中的 nav 菜单，并且折叠菜单组
    navTree.find(".layui-this").removeClass("layui-this");
    navTree.find(".layui-nav-itemed").removeClass("layui-nav-itemed");

    // 选中点击的 tab 对应的 nav 菜单，并且展开菜单组
    let kv = "a[data-id='" + layID + "']";
    navTree.find(kv).parent().addClass("layui-this");
    navTree.find(kv).parents(".layui-nav-item").addClass("layui-nav-itemed");

    element.render("nav", "navFilter");
    // console.log("联动左侧垂直导航栏，layMenuID=" + layID);
});

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