package com.whz.ribao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 周报
 */
@Entity
public class Zhoubao extends BaseBao {
    private String week;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Override
    public String getGroupBy() {
        return week;
    }
}
