package com.whz.ribao.dao;

import com.whz.ribao.entity.Ribao;
import com.whz.ribao.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RibaoDao extends JpaRepository<Ribao,Long> {

    Page<Ribao> findAll(Pageable pageable);

    Page<Ribao> findByNameContainingAndDateBetween(String name, Date dateq, Date datez, Pageable pageable);

    int deleteByIdInAndUserInfo(List<Long> id, UserInfo userInfo);

    List<Ribao> findByDateBetween(Date dateq, Date datez);

    List<Ribao> findByDateGreaterThanEqual(Date date);
}