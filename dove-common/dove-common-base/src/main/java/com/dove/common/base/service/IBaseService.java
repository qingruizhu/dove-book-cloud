package com.dove.common.base.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Description: 基层
 * @Auther: qingruizhu
 * @Date: 2020/4/10 13:49
 */
@Validated
public interface IBaseService<T extends Object> {
    List<T> list(T t);

    T select(@NotBlank(message = "【id】不能为空") Long primaryKey);

    T select(T t);

    int insert(T t);

    int update(T t);

    int delete(@NotBlank(message = "【id】不能为空") Long primaryKey);
}
