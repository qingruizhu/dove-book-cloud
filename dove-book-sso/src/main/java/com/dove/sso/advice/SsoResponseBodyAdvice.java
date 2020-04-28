package com.dove.sso.advice;

import com.dove.common.shiro.advice.ShiroResponseBodyAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public final class SsoResponseBodyAdvice extends ShiroResponseBodyAdvice {
}
