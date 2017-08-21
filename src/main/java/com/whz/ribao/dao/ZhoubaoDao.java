package com.whz.ribao.dao;

import com.whz.ribao.entity.UserInfo;
import com.whz.ribao.entity.Zhoubao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ZhoubaoDao extends JpaRepository<Zhoubao,Long> {

    Page<Zhoubao> findAll(Pageable pageable);

    Page<Zhoubao> findByNameContainingAndWeekBetween(String name, String weekq, String weekz, Pageable pageable);

    int deleteByIdInAndUserInfo(List<Long> id, UserInfo userInfo);

    List<Zhoubao> findByWeekBetween(String weekq, String weekz);

    List<Zhoubao> findByWeekGreaterThanEqual(String week);
}