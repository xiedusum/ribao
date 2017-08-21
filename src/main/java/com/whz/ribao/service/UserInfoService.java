package com.whz.ribao.service;


import com.whz.ribao.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    /**通过username查找用户信息;*/
    public UserInfo findByUsername(String username);

    List<UserInfo> findAll();
}