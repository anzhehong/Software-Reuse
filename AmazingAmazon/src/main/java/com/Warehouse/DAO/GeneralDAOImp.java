package com.Warehouse.DAO;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by fowafolo on 15/5/18.
 */
public abstract class GeneralDAOImp<T> implements GeneralDAO<T>{
    private Class<T> entityClass;

    public GeneralDAOImp(Class<T> classes){
        this.entityClass = classes;
    }

    @Autowired
    protected SessionFactory sessionFactory;


    public void insert(T t){
        sessionFactory.getCurrentSession().save(t);
    }

    public void delete(T t){
        sessionFactory.getCurrentSession().delete(t);
    }
    public void update(T t){
        sessionFactory.getCurrentSession().update(t);
    }

    @SuppressWarnings("unchecked")
    public T queryById(String id){
        return (T) sessionFactory.getCurrentSession().get(entityClass,id);
    }

    @Override
    public T queryByIntId(int id) {
        return (T) sessionFactory.getCurrentSession().get(entityClass,id);
    }

    public List<T> queryAll(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
        return criteria.list();
    }
}
