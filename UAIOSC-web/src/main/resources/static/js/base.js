// 业务响应代码（cn.ultronxr.common.bean.ResponseCode）
const RESPONSE_CODE = {
    SUCCESS: 200,
    FAIL: 500
}

// 后端接口地址
const API = {
    url: '',
    system: {
        url: '',
        login: '/ajaxLogin',
        logout: ''
    },
    'game-register': {
        url: '/game-register',
        account: {
            url: '/account',
            create: '/create',
            delete: '/delete',
            update: '/update',
            query: '/query'
        },
        game: {
            url: '/game',
            create: '/create',
            delete: '/delete',
            update: '/update',
            query: '/query'
        },
        platform: {
            url: '/platform',
            create: '/create',
            delete: '/delete',
            update: '/update',
            query: '/query',
            list: '/list'
        },
        shop: {
            url: '/shop',
            create: '/create',
            delete: '/delete',
            update: '/update',
            query: '/query',
            list: '/list'
        },
    }
}

/**
 * 获取根对象中，指定路径经过的、指定名称的 所有属性值 数组
 *
 * @param mainObj      根对象
 * @param locations    指定的路径（以.分隔的字符串形式，不包含根对象名称）
 * @param propToOutput 指定的属性名称
 */
function deepSearchAPI(mainObj, locations, propToOutput) {
    const props = locations.split('.');
    const outputs = [];

    // 搜索某一个属性
    function recurseSearchAPI(obj, props) {
        if(props.length === 0) {
            return;
        }
        // 把搜索到的属性值加入结果集
        if(obj[props[0]].hasOwnProperty(propToOutput)) {
            outputs.push(obj[props[0]][propToOutput]);
        } else {
            outputs.push(obj[props[0]]);
        }
        // 递归搜索某一个属性
        recurseSearchAPI(obj[props[0]], props.slice(1));
    }
    recurseSearchAPI(mainObj, props);
    return outputs;
}

/**
 * 获取 API 对象中，指定路径经过的 url 拼接字符串
 *
 * 例：getAPIUrl('game-register.account.query')
 */
function getAPIUrl(location) {
    return deepSearchAPI(API, location, 'url')
        .join('');
}

function ajaxRequest(method, url, headers, data, success, error, beforeSend, complete) {
    $.ajaxSetup({
        url: url,
        method: method,
        timeout: 10000,
        headers: headers,
        data: data,
        beforeSend: function (res) {
            if(beforeSend !== undefined && typeof beforeSend === "function")
                beforeSend(res);
        },
        success: function (res) {
            if(success !== undefined && typeof success === "function")
                success(res);
        },
        error: function (res) {
            if(error !== undefined && typeof error === "function")
                error(res);
        },
        complete: function (res) {
            if(complete !== undefined && typeof complete === "function")
                complete(res);
        }
    });
    $.ajax();
}

function ajaxPost(url, data, success, error, beforeSend, complete) {
    if(!isJsonStringify(data)) {
        data = JSON.stringify(data);
    }
    let headers = {
        'Content-Type': 'application/json'
    };
    ajaxRequest('POST', url, headers, data, success, error, beforeSend, complete);
}

function ajaxGet(url, data, success, error, beforeSend, complete) {
    ajaxRequest('GET', url, null, data, success, error, beforeSend, complete);
}

/**
 * 判断一个字符串是否是正确的JSON字符串
 */
function isJsonStringify(str) {
    if(typeof str === "string") {
        try {
            let obj = JSON.parse(str);
            return typeof obj === "object" && obj != null;
        } catch (e) {
            return false;
        }
    }
    return false;
}
