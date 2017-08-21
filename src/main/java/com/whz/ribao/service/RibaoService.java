package com.whz.ribao.service;

import com.whz.ribao.entity.Ribao;
import com.whz.ribao.entity.result.BaseWebModel;
import com.whz.ribao.entity.result.PageResult;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */
public interface RibaoService {


    Ribao save(Ribao ribao);

    PageResult find(BaseWebModel wm, String name, Date dateq, Date datez);

    int del(Long[] ids);

    void export(HttpServletResponse response);

    List<Ribao> findByDateGreaterThanEqual(Date date);

}
