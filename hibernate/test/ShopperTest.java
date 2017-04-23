package com.taobao.mybow.hibernate.test;

import com.taobao.mybow.hibernate.pojo.Shopper;
import com.taobao.mybow.hibernate.service.ShopperService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ShopperTest extends AbstractJUnit4SpringContextTests {

    @Resource(name="ShopperService")
    private ShopperService shopperService;

    @Test
    @Transactional
    public void addTest() {

        Shopper shopper = null;

        try {
            for(int i = 0; i < 10; i++) {
                shopper = new Shopper();
                shopper.setCredit(i);
                shopper.setFavour(i);
                shopper.setName("name" + i);
                shopper.setQuantity(i);
                shopper.setDate(new Date());

                shopperService.save(shopper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @Transactional
    public void queryTest() {
        try {
            long l = shopperService.getTotalNum();
            System.out.println(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
