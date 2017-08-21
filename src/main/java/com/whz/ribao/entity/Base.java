package com.whz.ribao.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/12.
 */
public class Base implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(
                this,
                ToStringStyle.MULTI_LINE_STYLE)
                .toString();
    }
}
