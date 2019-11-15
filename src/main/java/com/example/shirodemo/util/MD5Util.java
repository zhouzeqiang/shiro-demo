package com.example.shirodemo.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author zhouzeqiang
 * @date 2019/11/15
 * @description
 */
public class MD5Util {
    public static String MD5Pwd(String username, String pwd) {
        // 加密算法MD5
        // salt盐 username + salt
        // 迭代次数
        SimpleHash simpleHash = new SimpleHash(Md5Hash.ALGORITHM_NAME, pwd,
                ByteSource.Util.bytes(username + "salt"), 1);
        String md5Pwd = simpleHash.toBase64();
        return md5Pwd;
    }

    public static void main(String[] args) {
        System.out.println(MD5Pwd("admin","123456"));
        System.out.println(MD5Pwd("user","123456"));
    }
}
