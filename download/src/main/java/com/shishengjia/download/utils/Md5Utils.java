package com.shishengjia.download.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5算法加密url
 */

public class Md5Utils {

    public static String generateCode(String url) {
        if (TextUtils.isEmpty(url))
            return null;
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            //url转换为bytes，并进行加密
            digest.update(url.getBytes());
            //获取加密的bytes
            byte[] cipher = digest.digest();
            //转化为16进制
            for (byte b : cipher) {
                String hexStr = Integer.toHexString(b & 0xff);
                buffer.append(hexStr.length() == 1 ? "0" + hexStr : hexStr);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
