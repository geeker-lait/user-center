package com.lmt.framework.utils.random;

import java.util.Random;

/**
 * @Auther: lex
 * @Date: 2018/8/6 上午11:38
 * @Description:
 */
public class RandomUtils {
    // key
    private static String KEYS = "0123456789qwertyuiopasdfghjklzxcvbnm";

    /**
     * 唯一id生成工具类，12位字母+数字
     *
     * @param id 随机整数即可
     */
    public static String getRandomId(int id)
    {
        String ret = null;
        String num = String.format("%09d", id);
        Random random = new Random();
        int key = random.nextInt(10);
        int seed = random.nextInt(100);
        Caesar caesar = new Caesar(KEYS, seed);
        num = caesar.encode(key, num);
        ret = num + String.format("%01d", key) + String.format("%02x", seed);

        return ret;
    }

    /**
     * 获得特定长度的一串随机数字
     *
     * @param length
     * @return
     */
    public static String getRandomNumberStr(int length)
    {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    /**
     * 获得特定长度的一个随机字母数字混编字符串（所包含的字符包括0-9A-Z)
     *
     * @param length
     * @return
     */
    public static String getRandomAlphamericStr(int length)
    {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            int n = random.nextInt(36);
            if (n < 10)
            {
                builder.append(n);
            }
            else
            {
                builder.append((char) (n + 55));
            }
        }
        return builder.toString();
    }

    /**
     * 获得特定长度的一个随机字母数字混编字符串（所包含的字符包括0-9a-z)
     *
     * @param length
     * @return
     */
    public static String getRandomAlphamericStrNew(int length)
    {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            int n = random.nextInt(36);
            if (n < 10)
            {
                builder.append(n);
            }
            else
            {
                builder.append((char) (n + 87));
            }
        }
        return builder.toString();
    }

    /**
     * 获得特定长度的一个随机十六进制数字字母混编字符串(所包含的字符包括0-9A-F)
     *
     * @param length
     * @return
     */
    public static String getRandomHexStr(int length)
    {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            int n = random.nextInt(16);
            if (n < 10)
            {
                builder.append(n);
            }
            else
            {
                builder.append((char) (n + 55));
            }
        }
        return builder.toString();
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 100; i++)
        {
            System.out.println(RandomUtils.getRandomAlphamericStrNew(4));
            // String num = String.format("%09d", new Random().nextInt(100000));
            // System.out.println(num);
        }

    }
}
