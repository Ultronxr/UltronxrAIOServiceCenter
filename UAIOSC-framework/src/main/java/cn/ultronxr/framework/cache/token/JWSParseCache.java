package cn.ultronxr.framework.cache.token;

import cn.ultronxr.framework.bean.JWSParseResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author Ultronxr
 * @date 2023/01/18 20:18
 * @description JWS token 解析缓存，防止一段时间内重复解析相同 token 浪费资源
 */
@Component
public class JWSParseCache {

    private static final HashMap<String, JWSParseResult> TOKEN_PARSE_CACHE = new HashMap<>();


    public static void put(String token, JWSParseResult parseResult) {
        TOKEN_PARSE_CACHE.put(token, parseResult);
    }

    public static JWSParseResult get(String token) {
        return TOKEN_PARSE_CACHE.getOrDefault(token, null);
    }

    public static boolean contains(String token) {
        return TOKEN_PARSE_CACHE.containsKey(token);
    }

    public static void remove(String token) {
        if(contains(token)) {
            TOKEN_PARSE_CACHE.remove(token);
        }
    }

    public static void clear() {
        if(TOKEN_PARSE_CACHE.size() > 0) {
            TOKEN_PARSE_CACHE.clear();
        }
    }

    // 每七天的 00:00:00 执行一次
    @Scheduled(cron = "0 0 0 1/7 * ? ")
    private void clearCacheScheduled() {
        clear();
    }

}
