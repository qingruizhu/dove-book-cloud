package com.dove.book.bgd.service;

import com.dove.book.bgd.model.Book;
import com.dove.book.bgd.model.User;
import com.dove.common.base.service.IBaseQService;
import com.dove.common.base.service.IBaseService;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/19 21:22
 */
public interface IUserService<Q> extends IBaseService<User>, IBaseQService<User, Q> {
}
