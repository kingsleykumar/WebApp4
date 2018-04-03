package com.sb.services.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Kingsley Kumar on 19/11/2017.
 */
public class CryptoUtils {

    private static final String ALGORITHM = "";
    private static final String TRANSFORMATION = "";

    public static byte[] encrypt(String key, String pw)
            throws CryptoException {

        return new byte[0];
    }

    public static byte[] decrypt(String key, String encryptedPw)
            throws CryptoException {

        return new byte[0];
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] pw) throws CryptoException {
        return new byte[0];
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        return sb.toString().toUpperCase();
    }

    public static byte[] hexStringToByteArray(String s) {

        return new byte[0];
    }

    public static String generateKey() throws NoSuchAlgorithmException {

        return "";
    }


    public static String getOriginalPwFromEncrypted(String key, String encryptedPw) {

        String decryptedPwd = "";


        return decryptedPwd;

    }

    public static String getEncryptedPwFromOriginal(String key, String originalPw) {

        String encryptedPwd = originalPw;


        return encryptedPwd;

    }

    public static String getEncryptedDataFromOriginal(String key, String originalPw) throws CryptoException {

        String encryptedPwd = "";

        return encryptedPwd;

    }

    public static String getOriginalDataFromEncrypted(String key, String encryptedPw) throws CryptoException {

        String decryptedPwd = "";


        return decryptedPwd;
    }
}
