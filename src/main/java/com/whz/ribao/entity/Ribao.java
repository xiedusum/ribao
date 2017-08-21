package com.whz.ribao.entity;

import com.whz.ribao.utils.DateUtil;

import javax.persistence.*;
import java.util.Date;

/**
 * 日报
 */
@Entity
public class Ribao extends BaseBao {

    @Temporal(TemporalType.DATE)
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getGroupBy() {
        return DateUtil.dateToString(date, DateUtil.PATTERN_DAY);
    }
}
