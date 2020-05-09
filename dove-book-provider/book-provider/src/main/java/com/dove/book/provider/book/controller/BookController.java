package com.dove.book.provider.book.controller;

import com.dove.book.bgd.model.Book;
import com.dove.book.bgd.service.IBookService;
import com.dove.book.provider.book.dto.BookDto;
import com.dove.book.provider.book.enm.BookErrorEnum;
import com.dove.book.provider.book.exception.BookBaseException;
import com.dove.book.api.sso.SsoCommonApi;
import com.dove.book.api.sso.SsoMeApi;
import com.dove.book.api.sso.vo.UserVo;
import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.base.validate.QueryGroup;
import com.dove.common.base.vo.CommonResult;
import com.dove.common.util.page.CommonPage;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private SsoCommonApi ssoCommonApi;
    @Resource
    private SsoMeApi ssoMeApi;

    @GetMapping("/test")
    public CommonResult test() {
//        return null;
        CommonResult<String> authCode = ssoCommonApi.getAuthCode(null, null, null);
        String data = authCode.getData();
        return (CommonResult) authCode;
    }

    @GetMapping("/me")
    public CommonResult me() {

//        UserVo userVo = ssoMeApi.info();
        Object user = ssoCommonApi.getUser();

        return (CommonResult) user;
//        if (null != info && info.isSuccess())
//            return info;
//        return CommonResult.failed("从sso获取个人信息失败");
    }

    @GetMapping("/me1")
    public UserVo me1() {
        Object user = ssoCommonApi.getUser1();
        return (UserVo) user;
    }


    @ApiOperation("分页【书籍列表】")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonPage<Book> list(
            @RequestBody BookDto bookDto) {
        PageHelper.startPage(bookDto.getPageNum(), bookDto.getPageSize());
        List<Book> books = service.listQ(bookDto);
        return CommonPage.restPage(books);
    }

    @ApiOperation("所有【书籍列表】")
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    public List<Book> listAll(
            @RequestBody BookDto bookDto) {
        return service.listQ(bookDto);
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
