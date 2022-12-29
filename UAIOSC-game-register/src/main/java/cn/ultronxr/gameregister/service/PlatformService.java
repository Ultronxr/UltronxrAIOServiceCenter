package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Platform;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/29 16:46
 * @description
 */
public interface PlatformService {

    int create(Platform platform);

    int delete(String id);

    int update(Platform platform);

    List<Platform> query(Platform platform);

}
