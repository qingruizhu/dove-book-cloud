package com.dove.common.shiro.dto;

import java.util.List;
import java.util.Set;

/**
 * 用户对象
 */
public class User {
    private static final long serialVersionUID = -9077975168976887742L;

    private Long id;
    private String username;
    private String password;
    private Set<String> roles;
    private Set<String> permissions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
