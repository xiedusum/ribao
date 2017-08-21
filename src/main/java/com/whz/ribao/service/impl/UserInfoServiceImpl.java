package com.whz.ribao.service.impl;

import com.whz.ribao.dao.UserInfoDao;
import com.whz.ribao.entity.UserInfo;
import com.whz.ribao.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;
    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }

    @Override
    public List<UserInfo> findAll() {
        return userInfoDao.findAll();
    }
}