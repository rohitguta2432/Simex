package com.softage.paytm.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

/**
 * Created by SS0085 on 31-01-2017.
 */
public class AESEncryption {

    public static SecretKey getSecretEncryptionKey() throws Exception{

        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits

        SecretKey secKey = generator.generateKey();

        return secKey;

    }
    public static byte[] encryptText(String plainText,SecretKey secKey) throws Exception{

        // AES defaults to AES/ECB/PKCS5Padding in Java 7

        Cipher aesCipher = Cipher.getInstance("AES");

        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);

        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());

        return byteCipherText;

    }


    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {

        // AES defaults to AES/ECB/PKCS5Padding in Java 7

        Cipher aesCipher = Cipher.getInstance("AES");

        aesCipher.init(Cipher.DECRYPT_MODE, secKey);

        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);

        return new String(bytePlainText);

    }
   public static String  bytesToHex(byte[] hash) {

        return DatatypeConverter.printHexBinary(hash);

    }



}
