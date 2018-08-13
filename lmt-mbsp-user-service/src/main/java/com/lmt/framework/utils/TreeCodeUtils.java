package com.lmt.framework.utils;

import com.lmt.framework.utils.StringUtils;

/*
 * @描述：
 * @作者：Tangsm
 * @创建时间：2018-07-18 11:01:41
 */
public class TreeCodeUtils {

    public static void main(String[] args) {
        System.out.println(splitCode("001001001", 3, "|"));
    }

    /**
     * 将当前code按指定长度及分隔符将父级code获取出来拼装字符串并返回（例：传入010101、2、|，返回|01|0101|010101|）
     *
     * @param code  有级别的编码
     * @param length    按指定长度进行分割
     * @param splitChar 指定分割符
     * @return String
     */
    public static String splitCode(String code, int length, String splitChar){
        if (!StringUtils.isNullOrEmpty(code)){
            String content = "";
            String result = splitChar;
            int tempLen = code.length()/length;
            int j = 0;
            for (int i = 1; i <= code.length(); i++){
                content += code.charAt(i - 1);

                if (i % length == 0){
                    if (j < tempLen){
                        result += content + splitChar;
                        j++;
                    }
                }
            }

            return result;
        }else {
            return "";
        }
    }
}
