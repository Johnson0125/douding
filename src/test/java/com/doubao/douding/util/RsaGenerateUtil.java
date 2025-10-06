package com.doubao.douding.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Johnson
 * @description generate ras util
 */
@Slf4j
public class RsaGenerateUtil {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器
        keyPairGen.initialize(2048, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        log.info(publicKeyString);
        log.info(privateKeyString);

        System.out.println("-----BEGIN PUBLIC KEY-----");
        printFormatted(publicKeyString);
        System.out.println("-----END PUBLIC KEY-----");

        System.out.println("-----BEGIN PRIVATE KEY-----");
        printFormatted(privateKeyString);
        System.out.println("-----END PRIVATE KEY-----");
    }

    private static void printFormatted(String input) {
        int length = 64;
        for (int i = 0; i < input.length(); i += length) {
            int end = Math.min(i + length, input.length());
            System.out.println(input.substring(i, end));
        }
    }
}
