package com.wybase.trans.serve.util;

import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weiyu
 * @date 2024/4/2
 */

@Data
public class RSAUtils {

    private static final String ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "PUBLIC_kEY";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";
    // 私钥
    public static final String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALm+cSdc/0APciN3BKzEwTp1cKEXTurXEhiNxT8w0yUMsMiv6/79JQ7iqSgeDKGerfr3rae49vFapKQYHoMykhDEsXtU+/U7jvL4agpvRdoLn5JZCuBeWs04FySvAVFVDWu5cUpgBwaXxhUbh9UvapHJTnyvrWAMyNtAUE/ZcByfAgMBAAECgYBXH82qtZPi4/vZR8fxFB9AwPpyDWFOhVAvJTyqrce0frXjhKUxR2NdDpNixmmNW8VmzUtuuzNVtXs5nzicuJlLJvF9wvLHK9HGyOCHwvW1dWbK+onb1i43ilgs5285xAKwgDHBq4YN9eQPaom2cM3WonhXT7/aihQcKqcscS38zQJBAPhlCcHVrXhdD5bxeTuSvkXf5+OfHZfYWeVbpmE+lBNu+fYt1oZ1T9XP8NfylX5wt0P5Fyg8+8HQCEevCs1EKn0CQQC/blYDuExt7EzpyBNS0zAQ+s+LLblff6rLQi0qUh3viEvsG+0GZ3E2fexptv49MD0YGj33mr6TKPkn8g+TfHJLAkAwmtTF8SH4o30hdC7c0WIaGi4uBDyuMc+lacGs1an0A4s1+NI5jUsJ856VIwrNqxCxZFR7szMwQZzdKgtbDzOhAkBnmQOtsbCTEH2zSxbCDQ6HMeTd0lnux72bkG226IfQ2LcVySsl+xc2wUEPGPxQCuoVQCZm+HTIWTU1m/c3/vIvAkAENZyStNxUIrbpmGp1DzIB4zPkVr1f0uiygAUipfj2qpHHh/QLG2PfGKzdImHQW52k5KWsJQr/WwfnjT3CM/S5";
    // 公钥
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5vnEnXP9AD3IjdwSsxME6dXChF07q1xIYjcU/MNMlDLDIr+v+/SUO4qkoHgyhnq36962nuPbxWqSkGB6DMpIQxLF7VPv1O47y+GoKb0XaC5+SWQrgXlrNOBckrwFRVQ1ruXFKYAcGl8YVG4fVL2qRyU58r61gDMjbQFBP2XAcnwIDAQAB";
    /**
     * 加密算法
     */
    private static final String CIPHER_DE = "RSA";
    /**
     * 解密算法
     */
    private static final String CIPHER_EN = "RSA";
    /**
     * 密钥长度
     */
    private static final Integer KEY_LENGTH = 1024;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成秘钥对，公钥和私钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> genKeyPair() throws NoSuchAlgorithmException {
        Map<String, Object> keyMap = new HashMap<String, Object>();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_LENGTH); // 秘钥字节数
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws InvalidKeySpecException
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        // 得到公钥
        byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key key = keyFactory.generatePublic(x509EncodedKeySpec);
        // 加密数据，分段加密
        Cipher cipher = Cipher.getInstance(CIPHER_EN);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLength - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        // 得到私钥
        byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key key = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
        // 解密数据，分段解密
        Cipher cipher = Cipher.getInstance(CIPHER_DE);
        cipher.init(Cipher.DECRYPT_MODE, key);
        int inputLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        byte[] tmp;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLength - offset);
            }
//            out.write(cache, 0, cache.length);
            out.write(cache);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 获取公钥
     *
     * @param keyMap
     * @return
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        String str = new String(Base64.encodeBase64(key.getEncoded()));
        return str;
    }

    /**
     * 获取私钥
     *
     * @param keyMap
     * @return
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        String str = new String(Base64.encodeBase64(key.getEncoded()));
        return str;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> keyMap = RSAUtils.genKeyPair();
//        String publicKey = RSAUtils.getPublicKey(keyMap);
//        String privateKey = RSAUtils.getPrivateKey(keyMap);
        System.out.println("publicKey：" + publicKey);
        System.out.println("privateKey：" + privateKey);
        // 公钥加密
        String sourceStr = "{\"username\":\"admin\",\"password\":\"123456\"}";
        System.out.println("加密前：" + sourceStr);
        byte[] encryptStrByte = RSAUtils.encryptByPublicKey(sourceStr.getBytes(), publicKey);
        byte[] btt = Base64.encodeBase64(encryptStrByte);
        String encryptStr = new String(btt);
        System.out.println("加密后：" + encryptStr);
        System.out.println("长度：" + encryptStr.length());

        // 私钥解密
        byte[] decryptStrByte = RSAUtils.decryptByPrivateKey(Base64.decodeBase64(encryptStr), privateKey);
        String sourceStr_1 = new String(decryptStrByte);
        System.out.println("解密后：" + sourceStr_1);

    }



    // 传入前端加密的密码,解密后输出
    public static String decrypt(String ciphertext) throws Exception {
        byte[] bytes = ciphertext.getBytes(StandardCharsets.UTF_8);
        byte[] passwordBytes = decryptByPrivateKey(Base64.decodeBase64(bytes), privateKey);
        String password = new String(passwordBytes);
        return password;
    }
}

