const app = {
    // 业务响应代码（cn.ultronxr.common.bean.ResponseCode）
    RESPONSE_CODE: {
        SUCCESS: 200,
        FAIL: 500
    },

    // 后端接口地址
    API: {
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
        },
    },

    // 通用方法类库
    util: {
        api: {
            /**
             * 获取 API 对象中，指定路径经过的 url 拼接字符串
             *
             * 例：getAPIUrl('game-register.account.query')
             */
            getAPIUrl: function (location) {
                return this.deepSearchAPI(app.API, location, 'url')
                    .join('');
            },

            /**
             * 获取根对象中，指定路径经过的、指定名称的 所有属性值 数组
             *
             * @param mainObj      根对象
             * @param locations    指定的路径（以.分隔的字符串形式，不包含根对象名称）
             * @param propToOutput 指定的属性名称
             */
            deepSearchAPI: function (mainObj, locations, propToOutput) {
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
            },
        },

        ajax: {
            get: function (url, data, success, error) {
                let method = "GET",
                    contentType = "application/x-www-form-urlencoded",
                    dataType = "json";
                this.request(method, url, null, data, success, error, null, null, null, null, contentType, dataType);
            },

            post: function (url, data, success, error, beforeSend, complete, async) {
                let method = "POST",
                    contentType = "application/json; charset=UTF-8",
                    dataType = "json";
                if(!app.util.string.isJsonStringify(data)) {
                    data = JSON.stringify(data);
                }
                this.request(method, url, null, data, success, error, beforeSend, complete, async, null, contentType, dataType);
            },

            request: function (method, url, headers, data, success, error, beforeSend, complete, async, timeout, contentType, dataType) {
                if(!app.util.function.isFunction(success)) {
                    success = function (res) {};
                }
                if(!app.util.function.isFunction(error)) {
                    error = function (res) {};
                }
                if(!app.util.function.isFunction(beforeSend)) {
                    beforeSend = function (request) {};
                }
                if(!app.util.function.isFunction(complete)) {
                    complete = function (res) {};
                }

                if(headers == null) {
                    let token = "";
                    headers = {
                        "Authorization": token,
                    };
                }
                if(async == null) {
                    async = true;
                }
                if(timeout == null) {
                    timeout = 10000;
                }
                if(contentType == null) {
                    contentType = "application/json; charset=UTF-8";
                }
                if(dataType == null) {
                    dataType = "json";
                }

                $.ajaxSetup({
                    url: url,
                    method: method,
                    async: async,
                    timeout: timeout,
                    headers: headers,
                    contentType: contentType,
                    dataType: dataType,
                    data: data,
                    beforeSend: beforeSend,
                    success: success,
                    error: error,
                    complete: complete,
                });
                $.ajax();
            },

            upload: function (url, data, success, error) {

            },

        },

        string: {
            /**
             * 判断一个字符串是否是正确的JSON字符串
             */
            isJsonStringify: function (str) {
                if(typeof str === "string") {
                    try {
                        let obj = JSON.parse(str);
                        return typeof obj === "object" && obj != null;
                    } catch (e) {
                        return false;
                    }
                }
                return false;
            },

        },

        function: {
            /**
             * 判断一个对象是否是 function 函数
             */
            isFunction: function (func) {
                return func !== null && func !== undefined && typeof func === "function";
            },

        },

        user: {
            /**
             * 每个需要鉴权的页面都要调用这个方法，验证用户是否已经登录，以及用户登录是否合法，否则跳转到 login 页面
             */
            validate: function () {

            },
            getUsername: function () {

            },
        },

        token: {
            options: {
                expires: 7,
                path: "/",
                domain: "localhost",
                secure: false
            },

            get: function () {
                return $.cookie("token");
            },

            save: function (userID, username, token, role) {
                $.cookie("token", token, this.options);
            },

            remove: function () {
                $.removeCookie("username", this.options);
                $.removeCookie("userID", this.options);
                $.removeCookie("token", this.options);
                $.removeCookie("role", this.options);
            },

        },

    },

};
