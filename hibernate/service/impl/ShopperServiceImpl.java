package com.taobao.mybow.hibernate.service.impl;

import com.taobao.mybow.hibernate.dao.ShopperDao;
import com.taobao.mybow.hibernate.pojo.Shopper;
import com.taobao.mybow.hibernate.service.BaseService;
import com.taobao.mybow.hibernate.service.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/1/14.
 */
@Service("ShopperService")
public class ShopperServiceImpl extends BaseServiceImpl<Shopper> implements ShopperService {

    @Autowired
    private ShopperDao shopperDao;

    public void findByDate(String date) throws Exception {
    }
}
