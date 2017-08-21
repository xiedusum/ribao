package com.whz.ribao.service;

import com.whz.ribao.entity.Ribao;
import com.whz.ribao.entity.Zhoubao;
import com.whz.ribao.entity.result.BaseWebModel;
import com.whz.ribao.entity.result.PageResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */
public interface ZhoubaoService {


    Zhoubao save(Zhoubao ribao);

    PageResult find(BaseWebModel wm, String name, String weekq, String weekz);

    int del(Long[] ids);

    void export(HttpServletResponse response);

    List<Zhoubao> findByDateGreaterThanEqual(String week);

}
