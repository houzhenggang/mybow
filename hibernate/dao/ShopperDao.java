package com.taobao.mybow.hibernate.dao;

import com.taobao.mybow.hibernate.pojo.Shopper;

/**
 * Created by qinanhg on 2017/1/13.
 */
public interface ShopperDao extends BaseDao<Shopper> {
    void findByDate(String date) throws Exception;
}
