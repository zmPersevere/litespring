package org.litespring.util;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:16 PM 2018/12/13
 */
public class Assert {

    public static void notNull(Object object , String message){
        if (object == null){
            throw new IllegalArgumentException(message);
        }
    }

}
