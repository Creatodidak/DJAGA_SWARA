package id.creatodidak.djaga_swara.Helper;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {

    public static String encrypt(String data) {
        String key = "6E2E4F48FF1904A4B8F97A8E3CED8009BFE1227A7827566B3953444A758D1877";
        String iv = "E37340E7F424BC3C77BBAA2596DB1BA9";
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(key), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(hexStringToByteArray(iv));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedData) {

        String key = "6E2E4F48FF1904A4B8F97A8E3CED8009BFE1227A7827566B3953444A758D1877";
        String iv = "E37340E7F424BC3C77BBAA2596DB1BA9";

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(hexStringToByteArray(key), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(hexStringToByteArray(iv));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decodedBytes = Base64.decode(encryptedData, Base64.DEFAULT);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }
}