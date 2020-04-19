package com.dove.book.bgd.service;

import com.dove.book.bgd.mapper.UserMapper;
import com.dove.book.bgd.model.User;
import com.dove.book.bgd.model.UserExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/19 21:02
 */
public abstract class AbsUserService<Q> implements IUserService<Q> {
    @Resource
    private UserMapper mapper;

    @Override
    public List<User> list(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (null != user.getId()) criteria.andIdEqualTo(user.getId());
        if (StringUtils.isNotEmpty(user.getName())) criteria.andNameEqualTo(user.getName());
        if (null != user.getAccount()) criteria.andAccountEqualTo(user.getAccount());
        if (null != user.getPassword()) criteria.andPasswordEqualTo(user.getPassword());
        if (null != user.getSex()) criteria.andSexEqualTo(user.getSex());
        if (null != user.getGrowth()) criteria.andGrowthEqualTo(user.getGrowth());
        if (null != user.getStar()) criteria.andStarEqualTo(user.getStar());
        return mapper.selectByExample(example);
    }

    @Override
    public User select(Long primaryKey) {
        return mapper.selectByPrimaryKey(primaryKey);
    }

    @Override
    public User select(User user) {
        if (null != user.getId()) return mapper.selectByPrimaryKey(user.getId());
        List<User> users = this.list(user);
        if (CollectionUtils.isNotEmpty(users)) return users.get(0);
        return null;
    }

    @Override
    public int insert(User user) {
        return mapper.insertSelective(user);
    }

    @Override
    public int update(User user) {
        if (null != user.getId()) return mapper.updateByPrimaryKeySelective(user);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(user.getName())) criteria.andNameEqualTo(user.getName());
        return mapper.updateByExampleSelective(user, example);
    }

    @Override
    public int delete(Long primaryKey) {
        return mapper.deleteByPrimaryKey(primaryKey);
    }
}
