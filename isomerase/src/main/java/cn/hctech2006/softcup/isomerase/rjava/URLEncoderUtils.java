package cn.hctech2006.softcup.isomerase.rjava;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
 
public class URLEncoderUtils {
    public static String encode(String url) {
        try {
            String resultURL = "";
            //遍历字符串
            for (int i = 0; i < url.length(); i++) {
                char charAt = url.charAt(i);
                //只对汉字处理
                if (isChineseChar(charAt)) {
                    String encode = URLEncoder.encode(charAt + "", "UTF-8");
                    resultURL += encode;
                } else {
                    resultURL += charAt;
                }
            }
            return resultURL;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    //判断汉字的方法,只要编码在\u4e00到\u9fa5之间的都是汉字
    public static boolean isChineseChar(char c) {
        return String.valueOf(c).matches("[\u4e00-\u9fa5]");
    }
 
}