package com.blog.common.base.util;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MyDES2
{
    private static byte[] iv = {2,8,9,7,7,0,3,6};
    private static String encryptKey = "oP20Mt13";

    public MyDES2()
    {
    }

    public static String encryptDES(String encryptString)
    {
        try
        {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(),"DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
            return Base64.encode(encryptedData);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptDES(String decryptString)
    {
        try
        {
            byte[] byteMi = Base64.decode(decryptString);
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(),"DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte decryptedData[] = cipher.doFinal(byteMi);
            return new String(decryptedData);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



}
