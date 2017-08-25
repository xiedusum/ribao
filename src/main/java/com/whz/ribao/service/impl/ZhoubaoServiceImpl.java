package com.whz.ribao.service.impl;

import com.whz.ribao.core.SessionContext;
import com.whz.ribao.dao.ZhoubaoDao;
import com.whz.ribao.entity.BaseBao;
import com.whz.ribao.entity.UserInfo;
import com.whz.ribao.entity.Zhoubao;
import com.whz.ribao.entity.result.BaseWebModel;
import com.whz.ribao.entity.result.PageResult;
import com.whz.ribao.service.ZhoubaoService;
import com.whz.ribao.utils.DateUtil;
import com.whz.ribao.utils.poi.PoiRibaoTemplateExportUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/8/12.
 */
@Transactional
@Service
public class ZhoubaoServiceImpl implements ZhoubaoService {

    @Resource
    private ZhoubaoDao dao;

    @Override
    public Zhoubao save(Zhoubao zhoubao) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(SessionContext.getUserInfo().getUid());
        zhoubao.setUserInfo(userInfo);
        return dao.save(zhoubao);
    }

    @Override
    public PageResult find(BaseWebModel wm, String name, String weekq, String weekz) {
        Sort sort = new Sort(Sort.Direction.ASC, "name", "week");
        Pageable pageable = new PageRequest(wm.getPage()-1,wm.getPageSize(),sort);
        Page<Zhoubao> zhoubaos = dao.findByNameContainingAndWeekBetween(name,weekq,weekz,pageable);
        return new PageResult(zhoubaos.getContent(), wm.getPage(), wm.getPageSize(), zhoubaos.getTotalElements());
    }

    @Override
    public int del(Long[] ids) {
        return dao.deleteByIdInAndUserInfo(Arrays.asList(ids), SessionContext.getUserInfo());
    }

    @Override
    public void export(HttpServletResponse response) {
        String fileName = DateUtil.getNowDate() + "_周报_平台组.xlsx";
        try {
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-Disposition", "attachment;filename="
                + fileName);
        response.setContentType("application/vnd.ms-excel;charset=gb2312");

        //查询这周的日报
        long week = DateUtil.getFragmentInWeek(new Date());
        String weekq = Year.now() + "" + week;
        String weekz = DateUtil.getNextWeek(weekq);
        List<Zhoubao> zhoubaos = dao.findByWeekBetween(weekq, weekz);
        if (CollectionUtils.isNotEmpty(zhoubaos)){
            try {
                Workbook book = PoiRibaoTemplateExportUtil
                        .exportExcel(zhoubaos.stream()
                                .collect(Collectors.groupingBy(BaseBao::getGroupBy)));
                book.write(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Zhoubao> findByDateGreaterThanEqual(String week) {
        return dao.findByWeekGreaterThanEqual(week);
    }
}
