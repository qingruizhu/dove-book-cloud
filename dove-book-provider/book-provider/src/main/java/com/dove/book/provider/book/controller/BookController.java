package com.dove.book.provider.book.controller;

import com.dove.book.bgd.model.Book;
import com.dove.book.bgd.service.IBookService;
import com.dove.book.provider.book.dto.BookDto;
import com.dove.book.provider.book.dto.BookQueryParam;
import com.dove.book.provider.book.enm.BookErrorEnum;
import com.dove.book.provider.book.exception.BookBaseException;
import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.validate.QueryGroup;
import com.dove.common.util.page.CommonPage;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 书籍
 * @Author qingruizhu
 * @Date 9:33 下午 2020/4/19
 **/
@Api(description = "ing", tags = "☻ 书籍")
@CommonResultAnnon
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService<BookDto> service;


    @ApiOperation("分页【书籍列表】")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonPage<Book> list(
            @RequestBody BookQueryParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        Book book = new Book();
        book.setName(param.getName());
        book.setCategoryId(param.getCategoryId());
        List<Book> books = service.list(book);
        return CommonPage.restPage(books);
    }

    @ApiOperation("创建【书籍】")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(
            @RequestBody @Validated(QueryGroup.Insert.class)
                    BookDto bookDto) {
        if (service.insertQ(bookDto) <= 0)
            throw new BookBaseException(BookErrorEnum.BOOK_ERROR_CREATE);
    }

    @ApiOperation("修改【书籍】")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(
            @RequestBody
            @Validated(QueryGroup.Update.class)
                    BookDto bookDto) {
        if (service.updateQ(bookDto) <= 0)
            throw new BookBaseException(BookErrorEnum.BOOK_ERROR_UPDATE);
    }

    @ApiOperation("删除【书籍】")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(
            @PathVariable("id") @ApiParam("书籍id") Long id) {
        if (service.delete(id) <= 0)
            throw new BookBaseException(BookErrorEnum.BOOK_ERROR_DEL);
    }
}
