package com.blog.common.base.util;

/**
 * Created by OP-T-PC-0026 on 2017/4/10.
 */
public class PasswordUtil {
    public static long encryptPW(String password)
    {
        long lPWD;
        int i;
        if(password == null)
            return -1;
        if(password.equals(""))
            return 0;
        lPWD=0;
        for(i=0;i<password.length();i++)
            lPWD=lPWD+(int) password.charAt(i)*7*(password.length()-i);
        return lPWD;
    }

    public final static String encryptPWMD5(String s)
    {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try
        {
            byte[] strTemp = s.getBytes();
            java.security.MessageDigest mdTemp = java.security.MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
