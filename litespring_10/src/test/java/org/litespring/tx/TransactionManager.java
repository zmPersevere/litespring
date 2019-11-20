package org.litespring.tx;

import org.litespring.util.MessageTracker;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 15:45 2019-11-16
 */
public class TransactionManager {

    public void start(){
        System.out.println("start tx");
        MessageTracker.addMsg("start tx");
    }
    public void commit(){
        System.out.println("commit tx");
        MessageTracker.addMsg("commit tx");
    }
    public void rollback(){
        System.out.println("rollback tx");
        MessageTracker.addMsg("rollback tx");
    }
}
