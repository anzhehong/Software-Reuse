package com.Warehouse.DAO;

import java.util.List;

/**
 * Created by fowafolo on 15/5/18.
 */
public interface GeneralDAO<T> {
    /**
     * 通过String类型主键查询
     * @param id 主键
     * @return
     */
    T queryById(String id);

    /**
     * 通过Int类型主键查询
     * @param id 主键
     * @return
     */
    T queryByIntId(int id);

    /**
     * 列出所有T类型数据
     * @return
     */
    List<T> queryAll();

    /**
     * 插入
     * @param t T类型实例
     */
    void insert(T t);

    /**
     * 删除指定实例
     * @param t T类型实例
     */
    void delete(T t);

    /**
     * 更新指定实例
     * @param t T类型实例
     */
    void update(T t);
}
