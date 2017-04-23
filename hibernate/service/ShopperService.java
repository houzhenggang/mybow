package com.taobao.mybow.hibernate.service;

import com.taobao.mybow.hibernate.pojo.Shopper;

/**
 * Created by qinanhg on 2017/1/14.
 */
public interface ShopperService extends BaseService<Shopper> {
    void findByDate(String date) throws Exception;
}
