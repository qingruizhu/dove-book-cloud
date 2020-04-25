package com.dove.common.shiro.controller;

import com.dove.common.shiro.dto.ArticleDto;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    @RequiresPermissions("query")
    public ResponseEntity<List<ArticleDto>> list() {
        System.out.println(Thread.currentThread().getName());
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> read(@PathVariable Long id) {
        return null;
    }


}
