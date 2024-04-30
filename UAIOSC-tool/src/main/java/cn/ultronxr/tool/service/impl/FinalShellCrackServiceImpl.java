package cn.ultronxr.tool.service.impl;

import cn.ultronxr.tool.bean.FinalShellCrackActivateKey;
import cn.ultronxr.tool.service.FinalShellCrackService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Scanner;

/**
 * @author Ultronxr
 * @date 2024/04/28 16:56:00
 * @description
 */
@Service
@Slf4j
public class FinalShellCrackServiceImpl implements FinalShellCrackService {

    static {
        // 添加 Java Cryptography Architecture (JCA) 加密算法提供者
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Override
    public FinalShellCrackActivateKey getActivateKey(String machineCode) {
        if(StringUtils.isBlank(machineCode)) {
            log.error("机器码为空");
            return null;
        }
        machineCode = machineCode.trim();

        FinalShellCrackActivateKey key = new FinalShellCrackActivateKey();
        key.setOldVersionAdvanced(md5("61305" + machineCode + "8552"));
        key.setOldVersionProfessional(md5("2356" + machineCode + "13593"));
        key.setNewVersionAdvanced(keccak384(machineCode + "hSf(78cvVlS5E"));
        key.setNewVersionProfessional(keccak384(machineCode + "FF3Go(*Xvbb5s2"));

        return key;
    }

    public static String md5(String msg) {
        byte[] bytes = msg.getBytes();
        MD5Digest md5Digest = new MD5Digest();
        md5Digest.update(bytes, 0, bytes.length);
        byte[] hash = new byte[md5Digest.getDigestSize()];
        md5Digest.doFinal(hash, 0);
        return bytesToHex(hash).substring(8, 24);
    }

    public static String keccak384(String msg) {
        byte[] bytes = msg.getBytes();
        Keccak.Digest384 keccak384 = new Keccak.Digest384();
        keccak384.update(bytes, 0, bytes.length);
        byte[] hash = keccak384.digest();
        return bytesToHex(hash).substring(12, 28);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入机器码: ");
        String code = scanner.nextLine();

        System.out.println("版本号 < 3.9.6 (旧版)");
        System.out.println("高级版: " + md5("61305" + code + "8552"));
        System.out.println("专业版: " + md5("2356" + code + "13593"));

        System.out.println("版本号 >= 3.9.6 (新版)");
        System.out.println("高级版: " + keccak384(code + "hSf(78cvVlS5E"));
        System.out.println("专业版: " + keccak384(code + "FF3Go(*Xvbb5s2"));

        scanner.close();
    }

}
