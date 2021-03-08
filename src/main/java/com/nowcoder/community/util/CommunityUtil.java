package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    //生成随机字符串，用到UUID工具
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //MD5加密，对密码进行加密，只能加密，不能解密
    //hello -> abc123edf456
    //hello + 3e4a8 -> abc123def456abc（加上随机字符串进行加密）
    public static String md5(String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        //spring自带的一个工具，传入对象要求时一个byte
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
