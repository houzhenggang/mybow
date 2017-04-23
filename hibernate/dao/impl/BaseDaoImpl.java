package com.taobao.mybow.hibernate.dao.impl;

import com.taobao.mybow.hibernate.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by qinanhg on 2017/1/13.
 */
@Repository("BaseDao")
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

//    @Autowired
//    private SessionFactory sessionFactory;

    private Class<T> clatt; // 【实体类对应的Class对象】

    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public BaseDaoImpl(Class<T> clatt) {
        this.clatt = clatt;
    }

    @Autowired
    public void setBaseHibernateTemplate(HibernateTemplate hibernateTemplate) {
        super.setHibernateTemplate(hibernateTemplate);
    }

    public Session getSession() throws Exception {
        return getHibernateTemplate().getSessionFactory().openSession();
    }

    public long getTotalNum() throws Exception {
        DetachedCriteria criteria = DetachedCriteria.forClass(clatt);
        criteria.setProjection(Projections.rowCount());

        return (Long) getHibernateTemplate().findByCriteria(criteria).get(0);
    }

    public void save(T entity) throws Exception {
        getHibernateTemplate().save(entity);
    }

    public void update(T entity) throws Exception {
        getHibernateTemplate().update(entity);
    }

    public void delete(T entity) throws Exception {
        getHibernateTemplate().delete(entity);
    }

    public void saveOrUpdate(T entity) throws Exception {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public T findById(long id) throws Exception {
        return  getHibernateTemplate().get(clatt, id);
    }

    public List<T> findAll() throws Exception {
        return getHibernateTemplate().loadAll(clatt);
    }
}
