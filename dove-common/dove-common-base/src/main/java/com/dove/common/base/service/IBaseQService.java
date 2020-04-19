package com.dove.common.base.service;

import java.util.List;

/**
 * @Description: 基层
 * @Auther: qingruizhu
 * @Date: 2020/4/10 13:49
 */
public interface IBaseQService<T extends Object, Q extends Object> {
    List<T> listQ(Q q);

    T selectQ(Q q);

    int insertQ(Q q);

    int updateQ(Q q);

    T copyQ(Q q);


}
