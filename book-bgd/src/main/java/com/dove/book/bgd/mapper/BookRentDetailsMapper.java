package com.dove.book.bgd.mapper;

import com.dove.book.bgd.model.BookRentDetails;
import com.dove.book.bgd.model.BookRentDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookRentDetailsMapper {
    long countByExample(BookRentDetailsExample example);

    int deleteByExample(BookRentDetailsExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BookRentDetails record);

    int insertSelective(BookRentDetails record);

    List<BookRentDetails> selectByExample(BookRentDetailsExample example);

    BookRentDetails selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BookRentDetails record, @Param("example") BookRentDetailsExample example);

    int updateByExample(@Param("record") BookRentDetails record, @Param("example") BookRentDetailsExample example);

    int updateByPrimaryKeySelective(BookRentDetails record);

    int updateByPrimaryKey(BookRentDetails record);
}