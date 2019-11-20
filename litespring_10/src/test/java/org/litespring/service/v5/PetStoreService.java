package org.litespring.service.v5;

import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.stereotype.Component;
import org.litespring.util.MessageTracker;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 7:53 PM 2018/12/25
 */
@Component(value = "petStore")
public class PetStoreService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public void placeOrder(){
        System.out.println("place order");

        MessageTracker.addMsg( "place order" );
    }

    public void placeOrderWithException(){
        throw new NullPointerException();
    }
}
