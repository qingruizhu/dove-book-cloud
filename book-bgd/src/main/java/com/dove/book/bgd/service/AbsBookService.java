package com.dove.book.bgd.service;

import com.dove.book.bgd.mapper.BookMapper;
import com.dove.book.bgd.model.Book;
import com.dove.book.bgd.model.BookExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/19 21:02
 */
public abstract class AbsBookService<Q> implements IBookService<Q> {
    @Resource
    private BookMapper mapper;

    @Override
    public List<Book> list(Book book) {
        BookExample example = new BookExample();
        BookExample.Criteria criteria = example.createCriteria();
        if (null != book.getId()) criteria.andIdEqualTo(book.getId());
        if (StringUtils.isNotEmpty(book.getName())) criteria.andNameLike(book.getName());
        if (null != book.getUserId()) criteria.andUserIdEqualTo(book.getUserId());
        if (null != book.getPrice()) criteria.andPriceEqualTo(book.getPrice());
        if (null != book.getRentPrice()) criteria.andRentPriceEqualTo(book.getRentPrice());
        if (null != book.getStatus()) criteria.andStatusEqualTo(book.getStatus());
        return mapper.selectByExample(example);
    }

    @Override
    public Book select(Long primaryKey) {
        return mapper.selectByPrimaryKey(primaryKey);
    }

    @Override
    public Book select(Book book) {
        if (null != book.getId()) return mapper.selectByPrimaryKey(book.getId());
        List<Book> books = this.list(book);
        if (CollectionUtils.isNotEmpty(books)) return books.get(0);
        return null;
    }

    @Override
    @Transactional
    public int insert(Book book) {
        return mapper.insertSelective(book);
    }

    @Override
    @Transactional
    public int update(Book book) {
        if (null != book.getId()) return mapper.updateByPrimaryKeySelective(book);
        BookExample example = new BookExample();
        BookExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(book.getName())) criteria.andNameEqualTo(book.getName());
        if (null != book.getUserId()) criteria.andUserIdEqualTo(book.getUserId());
        return mapper.updateByExampleSelective(book, example);
    }

    @Override
    @Transactional
    public int delete(Long primaryKey) {
        return mapper.deleteByPrimaryKey(primaryKey);
    }

    @Override
    public Book copyQ(Q q) {
        Book book = new Book();
        BeanUtils.copyProperties(q, book);
        return book;
    }
}
