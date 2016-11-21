package br.com.utfpr.pb.util;

import java.security.MessageDigest;

/**
 * Created by João on 11/11/2016.
 */
public class EncryptPasswordUtil {
    private static EncryptPasswordUtil ourInstance = new EncryptPasswordUtil();

    public static EncryptPasswordUtil getInstance() {
        return ourInstance;
    }

    private EncryptPasswordUtil() {
    }

    public String encrypt(String senha) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
