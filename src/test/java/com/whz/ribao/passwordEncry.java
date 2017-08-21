package com.whz.ribao;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

/**
 * Created by m1897 on 2017/8/14.
 */
public class passwordEncry {

    //随机数生成器
    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    //指定散列算法为md5
    private String algorithmName = "MD5";
    //散列迭代次数
    private final int hashIterations = 2;

    /**
     * 生成随机盐值对密码进行加密
     * @return
     */
    @Test
    public void encrypt() {

        encrypt("zhangchi");
        encrypt("chenshaohong");
        encrypt("lizhiyong");
        encrypt("lianxianliang");
        encrypt("qiuzhongjian");
    }

    public void encrypt(String username) {
        String salt =randomNumberGenerator.nextBytes().
                toHex();
        String newPassword =
                new SimpleHash(algorithmName,"123456",
                        ByteSource.Util.bytes(username+salt),hashIterations).toHex();

        System.out.println(newPassword);
        System.out.println(salt);

    }
}
