package cn.ultronxr.framework.cache.user;

import cn.ultronxr.framework.cache.ThreadLocalCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ultronxr
 * @date 2023/01/15 17:33
 * @description
 */
@Service
@Slf4j
public class UserCacheService {

    private static final String USER_MAP_KEY = "user";

    private static final String REMEMBER_ME_MAP_KEY = "rememberMe";


    public <T> void putUser(T user, boolean rememberMe) {
        ThreadLocalCache.put(USER_MAP_KEY, user, REMEMBER_ME_MAP_KEY, rememberMe);
    }

    public Object getUser() {
        return ThreadLocalCache.get(USER_MAP_KEY);
    }

    public boolean getRememberMe() {
        return (boolean) ThreadLocalCache.get(REMEMBER_ME_MAP_KEY);
    }

    public void deleteUser() {
        ThreadLocalCache.delete(USER_MAP_KEY);
        ThreadLocalCache.delete(REMEMBER_ME_MAP_KEY);
    }

}
