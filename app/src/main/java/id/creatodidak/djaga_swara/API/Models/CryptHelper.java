package id.creatodidak.djaga_swara.API.Models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptHelper {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encryptString(Context context, String plainText) {
        try {
            String encryptionKey = readKeyFromFile(context);
            byte[] keyBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            keyBytes = sha.digest(keyBytes);
            byte[] ivBytes = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(ivBytes);

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] encryptedTextBytes = new byte[ivBytes.length + encryptedBytes.length];
            System.arraycopy(ivBytes, 0, encryptedTextBytes, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, encryptedTextBytes, ivBytes.length, encryptedBytes.length);

            return Base64.encodeToString(encryptedTextBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decryptString(Context context, String encryptedText) {
        try {
            String encryptionKey = readKeyFromFile(context);
            if (encryptionKey == null) {
                throw new Exception("Failed to read encryption key from file");
            }

            byte[] encryptedTextBytes = Base64.decode(encryptedText, Base64.DEFAULT);
            byte[] keyBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            keyBytes = sha.digest(keyBytes);

            byte[] ivBytes = new byte[16];
            System.arraycopy(encryptedTextBytes, 0, ivBytes, 0, ivBytes.length);

            byte[] encryptedBytes = new byte[encryptedTextBytes.length - ivBytes.length];
            System.arraycopy(encryptedTextBytes, ivBytes.length, encryptedBytes, 0, encryptedBytes.length);

            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("GAGAL")
                    .setMessage(e.getMessage())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
            return null;
        }
    }


    private static String readKeyFromFile(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("key.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder key = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                key.append(line);
            }

            bufferedReader.close();
            inputStream.close();

            return key.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}