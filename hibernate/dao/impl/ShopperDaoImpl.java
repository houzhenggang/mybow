package com.taobao.mybow.hibernate.dao.impl;

import com.taobao.mybow.hibernate.dao.ShopperDao;
import com.taobao.mybow.hibernate.pojo.Shopper;
import org.springframework.stereotype.Repository;

/**
 * Created by qinanhg on 2017/1/13.
 */
@Repository("ShopperDao")
public class ShopperDaoImpl extends BaseDaoImpl<Shopper> implements ShopperDao {

    public ShopperDaoImpl() {
        super(Shopper.class);
    }

    public void findByDate(String date) throws Exception {
    }
}
