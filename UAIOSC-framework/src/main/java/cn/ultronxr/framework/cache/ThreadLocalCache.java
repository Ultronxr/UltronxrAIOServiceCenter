package cn.ultronxr.framework.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ultronxr
 * @date 2023/01/15 19:46
 * @description ThreadLocal 实现的缓存<br/>
 *              <b>⚠警告：线程池中的线程不再使用时（不论会不会销毁），务必清空该线程的缓存内容，避免线程重用导致数据混乱</b>
 */
public class ThreadLocalCache {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    // 对于该引用 MAP 的操作会直接影响到 THREAD_LOCAL 中的内容
    private static final Map<String, Object> MAP = THREAD_LOCAL.get();


    /**
     * 向 ThreadLocal 中存入一对键值对
     *
     * @param key   键
     * @param value 值
     * @param <T>   值的类型
     */
    public static <T> void put(String key, T value) {
        MAP.put(key, value);
        //THREAD_LOCAL.set(MAP);
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
        //if(MAP.size() > 0) {
        //    THREAD_LOCAL.set(MAP);
        //}
    }

    /**
     * 获取指定 键 对应的 值
     *
     * @param key 键
     * @return 对应的值
     */
    public static Object get(String key) {
        //MAP = THREAD_LOCAL.get();
        return MAP.get(key);
    }

    /**
     * 删除指定的 键 及其对应的 值
     *
     * @param key 键
     */
    public static void delete(String key) {
        //MAP = THREAD_LOCAL.get();
        MAP.remove(key);
        //THREAD_LOCAL.set(MAP);
    }

    /**
     * 检查缓存中是否存在指定 key
     *
     * @param key 键
     * @return {@code true} - 存在；{@code false} - 不存在
     */
    public static boolean containsKey(String key) {
        return MAP.containsKey(key);
    }

    /**
     * 清空 ThreadLocal 内容<br/>
     * 警告：线程池中的线程不再使用时（不论会不会销毁），务必调用此方法清空该线程的缓存内容，避免线程重用导致数据混乱
     * TODO 2023年1月16日18点06分 用户线程废弃后调用此方法，清空缓存内容
     */
    public static void clear() {
        MAP.clear();
        //THREAD_LOCAL.remove();
    }

}
