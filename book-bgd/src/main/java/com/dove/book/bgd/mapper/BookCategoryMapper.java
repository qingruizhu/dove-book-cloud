package com.dove.book.bgd.mapper;

import com.dove.book.bgd.model.BookCategory;
import com.dove.book.bgd.model.BookCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookCategoryMapper {
    long countByExample(BookCategoryExample example);

    int deleteByExample(BookCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookCategory record);

    int insertSelective(BookCategory record);

    List<BookCategory> selectByExample(BookCategoryExample example);

    BookCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookCategory record, @Param("example") BookCategoryExample example);

    int updateByExample(@Param("record") BookCategory record, @Param("example") BookCategoryExample example);

    int updateByPrimaryKeySelective(BookCategory record);

    int updateByPrimaryKey(BookCategory record);
}