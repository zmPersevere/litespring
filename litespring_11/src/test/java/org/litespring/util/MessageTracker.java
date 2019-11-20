package org.litespring.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 16:15 2019-11-16
 */
public class MessageTracker {

    private static List<String> MESSAGES = new ArrayList<>();

    public static void addMsg(String msg){
        MESSAGES.add(msg);
    }
    public static void clearMsgs(){
        MESSAGES.clear();
    }
    public static List<String> getMsgs(){
        return MESSAGES;
    }
}
