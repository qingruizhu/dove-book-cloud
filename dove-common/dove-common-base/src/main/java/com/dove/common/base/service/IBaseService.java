package com.dove.common.base.service;

import java.util.List;

/**
 * @Description: 基层
 * @Auther: qingruizhu
 * @Date: 2020/4/10 13:49
 */
public interface IBaseService<T extends Object> {
    List<T> list(T t);

    T select(Integer primaryKey);

    T select(T t);

    int insert(T t);

    int update(T t);

    int delete(Integer primaryKey);
}
