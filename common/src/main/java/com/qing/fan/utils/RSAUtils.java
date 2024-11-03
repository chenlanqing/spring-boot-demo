package com.qing.fan.utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author QingFan 2021-05-11
 * @version 1.0.0
 */
public class RSAUtils {

    public static PrivateKey privateKey(String privateKeyStr) {
        return privateKey(Base64.getDecoder().decode(privateKeyStr));
    }

    public static PublicKey publicKey(String publicKeyStr) {
        return publicKey(Base64.getDecoder().decode(publicKeyStr));
    }

    private static PrivateKey privateKey(byte[] bytes) {
        try {
            PKCS8EncodedKeySpec privateKey = new PKCS8EncodedKeySpec(bytes);
            KeyFactory rsa = KeyFactory.getInstance("RSA");
            return rsa.generatePrivate(privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm ! ", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid private key ! ", e);
        }
    }

    private static PublicKey publicKey(byte[] bytes) {
        try {
            X509EncodedKeySpec publicKey = new X509EncodedKeySpec(bytes);
            KeyFactory rsa = KeyFactory.getInstance("RSA");
            return rsa.generatePublic(publicKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm ! ", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid public key ! ", e);
        }
    }
}
