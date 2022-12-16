package cn.ultronxr.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ultronxr
 * @date 2022/12/15 22:28
 * @description
 */
@Slf4j
public class MD5Utils {

    /**
     * 返回32位的MD5结果
     * @param plainText 待加密文本
     * @return {@code String} 32位MD5结果
     */
    public static String md5(@NotNull String plainText) {
        return DigestUtils.md5DigestAsHex(plainText.getBytes());
    }

    /**
     * 返回32位的MD5结果（加盐salt）
     * @param plainText 待加密文本
     * @param salt      盐值
     * @return {@code String} 32位MD5结果
     */
    public static String md5WithSalt(@NotNull String plainText, @NotNull String salt) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            log.error("无法获取 MD5 算法实例！");
            ex.printStackTrace();
            return "";
        }
        if(StringUtils.isNotEmpty(salt)) {
            md.update(salt.getBytes());
        }
        md.update(plainText.getBytes());
        return encodeHex(md.digest());
    }

    /**
     * 把字节数组转化成16进制字符串
     * @param byteData md5的结果字节数组
     * @return 16进制字符串
     * @see org.springframework.util.DigestUtils#encodeHex(byte[])
     */
    private static String encodeHex(byte[] byteData) {
        StringBuilder sb = new StringBuilder();
        for (byte byteD : byteData) {
            sb.append(Integer.toString((byteD & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
