package cn.ultronxr.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ultronxr
 * @date 2022/12/15 23:02
 * @description
 */
@Slf4j
public class CipherUtils {

    /**
     * 生成随机秘钥
     *
     * @param keyBitSize 字节大小
     * @param algorithmName 算法名称
     * @return 创建密匙
     */
    public static Key generateNewKey(int keyBitSize, String algorithmName) {
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            log.error("无法获取 {} 算法实例！", algorithmName);
            e.printStackTrace();
            return null;
        }
        kg.init(keyBitSize);
        return kg.generateKey();
    }

}
