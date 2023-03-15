var laydate;
layui.define(function(){
    laydate = layui.laydate;
});

$(function () {
   loadAllSelect();
    laydate.render({
        elem: '#date',
        value: new Date()
    });
});

function loadAllSelect() {
    loadSelect(app.util.api.getAPIUrl('valorant.account.select'), $('#userId'), "username", "userId", false, null, true);
}

table.render({
    elem: '#data-table'
    ,id: 'dataTable'
    // ,height: 500
    ,cols: [[ //表头
        {field: 'weaponSkin.displayName', title: '皮肤名称', sort: false, align: 'center', width: '10%', style: 'height:150px;',
            templet: '<div>{{=d.weaponSkin.displayName}}</div>'}
        ,{field: 'cost', title: '价格', sort: false, align: 'center', width: '5%', style: 'height:150px;',
            templet: '<div><del>{{=d.cost}} VP</del><br/>-{{=d.discountPercent}}%<br/>{{=d.discountCost}} VP</div>'}
        ,{field: 'weaponSkin.displayIcon', title: '皮肤图片', sort: false, align: 'center', width:'20%', style: 'height:150px;',
            templet: '<div><img src="{{=d.weaponSkin.displayIcon}}" style="height:auto; width:auto; max-height:100%; max-width:100%;"></div>'}
        ,{field: 'weaponSkin.streamedVideo', title: '皮肤本体预览', sort: false, align: 'center', width:'20%', style: 'height:150px',
            templet: function (d) {
                if(d.weaponSkin.streamedVideo == null) {
                    return '<div>无预览视频</div>';
                }
                let videoUrl = d.weaponSkin.streamedVideo,
                    imgUrl = d.weaponSkin.displayIcon,
                    displayName = d.weaponSkin.displayName;
                if(videoUrl != null) {
                    return '<a onclick="layerOpenVideo(\'' + videoUrl + '\')" class="layui-table-link">' + displayName + '</a>';
                } else {
                    return '<a onclick="layerOpenImg(\'' + imgUrl + '\')">' + displayName + '</a>';
                }
            }}
        ,{field: 'weaponSkinLevels', title: '皮肤升级', sort: false, align: 'center', width:'20%', style: 'height:150px',
            templet: function(d) {
                let html = '';
                for(let index in d.weaponSkinLevels) {
                    if(index > 0) {
                        let videoUrl = d.weaponSkinLevels[index].streamedVideo,
                            imgUrl = d.weaponSkinLevels[index].displayIcon,
                            displayName = d.weaponSkinLevels[index].displayName;
                        if(videoUrl != null) {
                            html += '<a onclick="layerOpenVideo(\'' + videoUrl + '\')" class="layui-table-link">' + displayName + '</a>';
                        } else {
                            html += '<a onclick="layerOpenImg(\'' + imgUrl + '\')">' + displayName + '</a>';
                        }
                        html += '<br/>';
                    }
                }
                return html;
            }}
        ,{field: 'weaponSkinChromas', title: '皮肤炫彩', sort: false, align: 'center', width:'20%', style: 'height:150px',
            templet: function(d) {
                let html = '';
                for(let index in d.weaponSkinChromas) {
                    if(index > 0) {
                        let videoUrl = d.weaponSkinChromas[index].streamedVideo,
                            imgUrl = d.weaponSkinChromas[index].displayIcon,
                            displayName = d.weaponSkinChromas[index].displayName;
                        if(videoUrl != null) {
                            html += '<a onclick="layerOpenVideo(\'' + videoUrl + '\')" class="layui-table-link">' + displayName + '</a>';
                        } else {
                            html += '<a onclick="layerOpenImg(\'' + imgUrl + '\')">' + displayName + '</a>';
                        }
                        html += '<br/>';
                    }
                }
                return html;
            }}
    ]]
    ,totalRow: false

    ,url: app.util.api.getAPIUrl('valorant.storefront.bonus')
    ,method: 'GET'
    // ,contentType: 'application/json'
    ,headers: {}
    ,where: {
        userId: $("#userId").val(),
        date: $("#date").val(),
    }
    ,page: false //分页
    ,before: function(req) {
        let authToken = app.util.token.auth.get();
        if(app.util.string.isNotEmpty(authToken)) {
            req.headers["Authorization"] = "Bearer " + authToken;
        }
    }
    ,request: {
        // pageName: 'page' //页码的参数名称，默认：page
        // ,limitName: 'limit' //每页数据量的参数名，默认：limit
    }
    ,response: {
        statusName: 'code' //规定数据状态的字段名称，默认：code
        ,statusCode: 200 //规定成功的状态码，默认：0
        ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
        ,countName: 'count' //规定数据总数的字段名称，默认：count
        ,dataName: 'data' //规定数据列表的字段名称，默认：data
    }
    ,parseData(res) { //将返回的任意数据格式解析成 table 组件规定的数据格式
        //console.log(res);
        return {
            "code": res.code,
            "msg": res.msg,
            "count": res.total,
            "data": res.data
        };
    }
});

// 刷新表格，不包含任何条件，恢复到初始状态
function refreshTable() {
    table.reloadData('dataTable', {
        where: {} // 清空搜索条件内容
    });
}

// 按钮的点击事件，关键数据：data-type="reload/clear"
var active = {
    reload: function(){
        table.reloadData('dataTable', {
            // page: {
            //     curr: 1 //重新从第 1 页开始
            // },
            where: { //设定异步数据接口的额外参数
                userId: $("#userId").val(),
                date: $("#date").val(),
            }
        });
    },
};

// 监听按钮的点击事件
$('#table-div .layui-btn').on('click', function(){
    let type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
});

function layerOpenVideo(videoUrl) {
    layer.open({
        title: '皮肤预览',
        type: 1,
        content: '<video src="' + videoUrl + '" controls style="height:auto; width:auto; max-height:100%; max-width:100%;" preload="metadata">',
        area: ['900px', '560px'],
        shadeClose: true
    });
}

function layerOpenImg(imgUrl) {
    layer.open({
        title: '皮肤预览',
        type: 1,
        content: '<img src="' + imgUrl + '" controls style="height:auto; width:auto; max-height:100%; max-width:100%;">',
        area: ['900px', '560px'],
        shadeClose: true
    });
}
