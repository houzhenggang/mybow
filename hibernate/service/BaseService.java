package com.taobao.mybow.hibernate.service;

import org.hibernate.Session;

import java.util.List;

/**
 * Created by qinanhg on 2017/1/14.
 */
public interface BaseService<T> {
    /**
     * 由session工厂获取当前session对象
     * @return
     */
    Session getSession() throws Exception;

    /**
     * <p>获取表中数据总数</p>
     * @return 表的记录数
     * @throws Exception  操作数据库失败抛出的错误
     */
    long getTotalNum() throws Exception;

    /**
     * 将实体对象保存到数据库中
     * @param t 待保存的实体对象
     * @return 实体对象的ID
     */
    void save(T entity) throws Exception;

    /**
     *  <p>更新数据库对象</p>
     * @param entity 更新的对象
     * @throws Exception 操作数据库失败抛出的错误
     */
    void update(T entity) throws Exception;

    /**
     * <p>删除数据对象</p>
     * @param entity 删除的对象
     * @throws Exception 操作数据库失败抛出的错误
     */
    void delete(T entity) throws Exception;

    /**
     * <p>添加或者更新对象</p>
     * @param entity 添加或更新的对象
     * @throws Exception 操作数据库失败抛出的错误
     */
    void saveOrUpdate(T entity) throws Exception;

    /**
     * <p>按ID查找对象</p>
     * @param id 对象ID
     * @return  查找到的对象
     * @throws Exception 操作数据库失败抛出的错误
     */
    T findById(long id) throws Exception;

    /**
     * <p>查找全部数据</p>
     * @return 数据库中表的全部数据
     * @throws Exception 操作数据库失败抛出的错误
     */
    List<T> findAll() throws Exception;
}
