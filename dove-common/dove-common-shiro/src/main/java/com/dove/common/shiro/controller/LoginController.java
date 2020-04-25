package com.dove.common.shiro.controller;

import com.dove.common.base.annotation.CommonResultAnnon;
import com.dove.common.shiro.dto.ArticleDto;
import com.dove.common.shiro.dto.User;
import com.dove.common.shiro.service.IUserService;
import com.dove.common.shiro.util.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.dove.common.shiro.filter.JwtAuthenFilter.AUTHORIZATION_HEADER;
import static com.dove.common.shiro.filter.JwtAuthenFilter.JWT_AUTHZSCHEME;

@CommonResultAnnon
@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private IUserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/admin")
    public ResponseEntity<List<ArticleDto>> list() {
        return null;
    }


    /**
     * 用户名密码登录
     *
     * @param request
     * @return token
     */
    @PostMapping(value = "/login")
    public void login(@RequestBody User loginInfo, HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
//        try {
        UsernamePasswordToken token = new UsernamePasswordToken(loginInfo.getUsername(), loginInfo.getPassword());
        subject.login(token);

        User user = (User) subject.getPrincipal();
        String newToken = jwtTokenUtil.sign(user.getId().toString());
//        response.setHeader("x-auth-token", newToken);
        response.setHeader(AUTHORIZATION_HEADER, JWT_AUTHZSCHEME + " " + newToken);
//        } catch (AuthenticationException e) {
//            logger.error("User {} login fail, Reason:{}", loginInfo.getUsername(), e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping(value = "/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() != null) {
            Object primaryPrincipal = subject.getPrincipals().getPrimaryPrincipal();
            User user = (User) primaryPrincipal;
//            userService.deleteLoginInfo(user.getUsername());
        }
        subject.logout();
    }

}
