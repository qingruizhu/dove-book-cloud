package com.dove.book.bgd.service;

import com.dove.book.bgd.mapper.BookCategoryMapper;
import com.dove.book.bgd.model.BookCategory;
import com.dove.book.bgd.model.BookCategoryExample;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author qingruizhu
 * @Date 1:47 下午 2020/6/4
 **/
public abstract class AbsBookCategoryService implements IBookCategoryService {
    @Resource
    private BookCategoryMapper mapper;

    @Override
    public List<BookCategory> list(BookCategory bookCategory) {
        BookCategoryExample example = new BookCategoryExample();
        BookCategoryExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(bookCategory.getName())) {
            criteria.andNameLike(bookCategory.getName());
        }
        return mapper.selectByExample(example);
    }

    @Override
    public BookCategory select(Long primaryKey) {
        return mapper.selectByPrimaryKey(primaryKey);
    }

    @Override
    public BookCategory select(BookCategory bookCategory) {
        if (null != bookCategory.getId()) return mapper.selectByPrimaryKey(bookCategory.getId());
        List<BookCategory> categories = this.list(bookCategory);
        if (CollectionUtils.isNotEmpty(categories)) return categories.get(0);
        return null;
    }

    @Override
    @Transactional
    public int insert(BookCategory bookCategory) {
        return mapper.insertSelective(bookCategory);
    }

    @Override
    @Transactional
    public int update(BookCategory bookCategory) {
        if (null != bookCategory.getId()) return mapper.updateByPrimaryKeySelective(bookCategory);
        BookCategoryExample example = new BookCategoryExample();
        BookCategoryExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(bookCategory.getName())) criteria.andNameEqualTo(bookCategory.getName());
        return mapper.updateByExampleSelective(bookCategory, example);
    }

    @Override
    @Transactional
    public int delete(Long primaryKey) {
        return mapper.deleteByPrimaryKey(primaryKey);
    }

}
