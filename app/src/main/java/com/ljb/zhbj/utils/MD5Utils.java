package com.ljb.zhbj.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @FileName: com.ljb.zhbj.utils.MD5Utils.java
 * @Author: Li Jingbo
 * @Date: 2016-05-30 10:35
 * @Version V1.0 MD5加密处理
 */

public class MD5Utils {
    private static final String TAG = "MD5Utils";

    public static String encode(String password) {
        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(password.getBytes());
            for ( byte b : digest ) {
                int i = b & 0xff; // 获取到第八位有效值
                String hexString = Integer.toHexString(i);
                if ( hexString.length() < 2 ) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch ( NoSuchAlgorithmException e ) {
            e.printStackTrace();
            return "";
        }
    }
}