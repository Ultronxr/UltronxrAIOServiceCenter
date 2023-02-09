package cn.ultronxr.framework.cache.user;

import cn.hutool.core.util.ReflectUtil;
import cn.ultronxr.framework.cache.ThreadLocalCache;

/**
 * @author Ultronxr
 * @date 2023/01/15 17:33
 * @description 使用 ThreadLocalCache 实现的用户缓存
 */
public class UserCache {

    /** 向 ThreadLocal 中存入已登录用户对象（user）时使用的 key */
    private static final String USER_MAP_KEY = "user";


    /**
     * 向 ThreadLocal 缓存中存入已登录用户对象
     *
     * @param user       用户对象
     * @param <T>        用户对象的类型<br/>
     *                      （为什么这里使用类型 T 而不是 User ：<br/>
     *                      framework 模块处于maven依赖的上层，User 对象处于 system 模块（下层），直接使用 User 类型会导致循环依赖）
     */
    public static <T> void putUser(T user) {
        ThreadLocalCache.put(USER_MAP_KEY, user);
    }

    /**
     * 从 ThreadLocal 缓存中取出已登录用户对象
     *
     * @return 返回用户对象 {@code Object} ，需手动强转为 {@code User} 类型：<br/>
     *              {@code (User) UserCache.getUser()}
     */
    public static Object getUser() {
        return ThreadLocalCache.get(USER_MAP_KEY);
    }

    /**
     * 使用反射直接获取缓存中 User 对象的 username 属性值，<br/>
     * 不需要 {@code ((User) UserCache.getUser()).getUsername()} 强制转换之后再获取
     *
     * @return 当缓存中存在用户对象时，返回其 {@code username} 属性值<br/>
     *          当缓存中没有用户对象时，返回 {@code null}
     */
    public static String getUsername() {
        Object user = UserCache.getUser();
        if(null == user) {
            return null;
        }
        return (String) ReflectUtil.getFieldValue(user, "username");
    }

    /**
     * 删除 ThreadLocal 缓存中的已登录用户对象
     */
    public static void deleteUser() {
        ThreadLocalCache.delete(USER_MAP_KEY);
    }

}
