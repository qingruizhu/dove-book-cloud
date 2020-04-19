package com.dove.book.service;

import com.dove.book.bgd.model.Book;
import com.dove.book.bgd.service.AbsBookService;
import com.dove.book.dto.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2020/4/19 21:35
 */
@Service
public class BookServiceImpl extends AbsBookService<BookDto> {
    @Override
    public List<Book> listQ(BookDto bookDto) {
        return super.list(copyQ(bookDto));
    }

    @Override
    public Book selectQ(BookDto bookDto) {
        return super.select(copyQ(bookDto));
    }

    @Override
    public int insertQ(BookDto bookDto) {
        return super.insert(copyQ(bookDto));
    }

    @Override
    public int updateQ(BookDto bookDto) {
        return super.update(copyQ(bookDto));
    }

}
