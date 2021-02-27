package com.yusiyu.springbootshiro.utils;

import java.util.Random;

public class SaltUtils {
    //盐生成工具
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char aChar = chars[new Random().nextInt(chars.length)];   //从上述字符串中随机抽取n个字符作为盐
            sb.append(aChar);
        }
        return sb.toString();
    }
}
