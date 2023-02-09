package cn.ultronxr.gameregister.service;

import cn.ultronxr.gameregister.bean.mybatis.bean.Platform;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2022/12/29 16:46
 * @description
 */
public interface PlatformService extends IService<Platform> {

    List<Platform> listPlatform(Platform platform);

}
