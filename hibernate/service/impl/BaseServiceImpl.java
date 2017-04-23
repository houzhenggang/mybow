package com.taobao.mybow.hibernate.service.impl;

import com.taobao.mybow.hibernate.dao.BaseDao;
import com.taobao.mybow.hibernate.service.BaseService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by qinanhg on 2017/1/14.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    private BaseDao<T> baseDao;

    public Session getSession() throws Exception {
        return baseDao.getSession();
    }

    public long getTotalNum() throws Exception {
        return baseDao.getTotalNum();
    }

    public void save(T entity) throws Exception {
        baseDao.save(entity);
    }

    public void update(T entity) throws Exception {
        baseDao.update(entity);
    }

    public void delete(T entity) throws Exception {
        baseDao.delete(entity);
    }

    public void saveOrUpdate(T entity) throws Exception {
        baseDao.saveOrUpdate(entity);
    }

    public T findById(long id) throws Exception {
        return baseDao.findById(id);
    }

    public List<T> findAll() throws Exception {
        return baseDao.findAll();
    }
}
