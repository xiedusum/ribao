package com.whz.ribao.service.impl;

import com.whz.ribao.core.SessionContext;
import com.whz.ribao.dao.RibaoDao;
import com.whz.ribao.entity.BaseBao;
import com.whz.ribao.entity.Ribao;
import com.whz.ribao.entity.UserInfo;
import com.whz.ribao.entity.result.BaseWebModel;
import com.whz.ribao.entity.result.PageResult;
import com.whz.ribao.service.RibaoService;
import com.whz.ribao.utils.DateUtil;
import com.whz.ribao.utils.poi.PoiRibaoTemplateExportUtil;
import org.apache.commons.lang3.time.DateUtils;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/8/12.
 */
@Transactional
@Service
public class RibaoServiceImpl implements RibaoService {

    @Resource
    private RibaoDao dao;

    @Override
    public Ribao save(Ribao ribao) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(SessionContext.getUserInfo().getUid());
        ribao.setUserInfo(userInfo);
        return dao.save(ribao);
    }

    @Override
    public PageResult find(BaseWebModel wm, String name, Date dateq, Date datez) {
        Sort sort = new Sort(Sort.Direction.ASC, "name", "date");
        Pageable pageable = new PageRequest(wm.getPage()-1,wm.getPageSize(),sort);
        Page<Ribao> ribaos = dao.findByNameContainingAndDateBetween(name,dateq,datez,pageable);
        return new PageResult(ribaos.getContent(), wm.getPage(), wm.getPageSize(), ribaos.getTotalElements());
    }

    @Override
    public int del(Long[] ids) {
        return dao.deleteByIdInAndUserInfo(Arrays.asList(ids), SessionContext.getUserInfo());
    }

    @Override
    public void export(HttpServletResponse response) {
        String fileName = DateUtil.getNowDate() + "_日报_平台组.xlsx";
        try {
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Content-Disposition", "attachment;filename="
                + fileName);
        response.setContentType("application/vnd.ms-excel;charset=gb2312");

        //查询这周的日报
        Date mon = DateUtil.firstDayOfWeek(DateUtil.stringToDate(DateUtil.getNowDate()));
        Date sun = DateUtils.addDays(mon,6);
        List<Ribao> ribaos = dao.findByDateBetween(mon, sun);
        try {
            Workbook book = PoiRibaoTemplateExportUtil
                    .exportExcel(ribaos.stream().collect(Collectors.groupingBy(BaseBao::getGroupBy)));
            book.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ribao> findByDateGreaterThanEqual(Date date) {
        return dao.findByDateGreaterThanEqual(date);
    }
}
