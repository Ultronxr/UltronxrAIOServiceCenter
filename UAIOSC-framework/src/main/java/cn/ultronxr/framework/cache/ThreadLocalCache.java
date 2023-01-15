package cn.ultronxr.framework.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ultronxr
 * @date 2023/01/15 19:46
 * @description ThreadLocal 实现的缓存
 */
public class ThreadLocalCache {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    private static Map<String, Object> MAP = new HashMap<>();


    /**
     * 向 ThreadLocal 中存入一对键值对
     *
     * @param key   键
     * @param value 值
     * @param <T>   值的类型
     */
    public static <T> void put(String key, T value) {
        MAP.put(key, value);
        THREAD_LOCAL.set(MAP);
    }

    /**
     * 向 ThreadLocal 中存入不定数量的键值对<br/>
     * 请避免出现参数数量为奇数的情况，否则最后一个键的值会自动被设置成 {@code null}
     *
     * @param t 键或值，下标（从0开始）双数为键，下标单数为值
     */
    public static void put(Object... t) {
        for(int i = 0; i < t.length; i += 2) {
            // 特判，当 t.length 为奇数时，把末尾的落单 key 的 value 置 null
            if(i+1 == t.length) {
                MAP.put((String) t[i], null);
            } else {
                MAP.put((String) t[i], t[i+1]);
            }
        }
        if(MAP.size() > 0) {
            THREAD_LOCAL.set(MAP);
        }
    }

    /**
     * 获取指定 键 对应的 值
     *
     * @param key 键
     * @return 对应的值
     */
    public static Object get(String key) {
        MAP = THREAD_LOCAL.get();
        return MAP.get(key);
    }

    /**
     * 删除指定的 键 及其对应的 值
     *
     * @param key 键
     */
    public static void delete(String key) {
        MAP = THREAD_LOCAL.get();
        MAP.remove(key);
        THREAD_LOCAL.set(MAP);
    }

    /**
     * 清空 ThreadLocal 内容<br/>
     * 警告：该方法不仅会清空 MAP ， 而且会导致 {@code THREAD_LOCAL.get() == null}
     */
    public static void clear() {
        MAP.clear();
        THREAD_LOCAL.remove();
    }

}
