package com.dove.book.bgd.mapper;

import com.dove.book.bgd.model.BookRent;
import com.dove.book.bgd.model.BookRentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookRentMapper {
    long countByExample(BookRentExample example);

    int deleteByExample(BookRentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookRent record);

    int insertSelective(BookRent record);

    List<BookRent> selectByExample(BookRentExample example);

    BookRent selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookRent record, @Param("example") BookRentExample example);

    int updateByExample(@Param("record") BookRent record, @Param("example") BookRentExample example);

    int updateByPrimaryKeySelective(BookRent record);

    int updateByPrimaryKey(BookRent record);
}