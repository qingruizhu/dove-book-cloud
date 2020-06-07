package com.dove.book.provider.book.controller;

import com.dove.book.bgd.model.BookCategory;
import com.dove.book.bgd.service.IBookCategoryService;
import com.dove.common.base.annotation.CommonResultAnnon;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 书籍种类
 * @Author qingruizhu
 * @Date 1:40 下午 2020/6/4
 **/
@Api(description = "ing", tags = "☻ 书籍种类")
@CommonResultAnnon
@RestController
@RequestMapping("/bookCategory")
public class BookCategoryController {

    @Autowired
    private IBookCategoryService service;


    @ApiOperation("分页【书籍种类】列表")
    @GetMapping(value = "/listAll")
    public List<BookCategory> listAll() {
        List<BookCategory> categories = service.list(new BookCategory());
        return categories;
    }


}
