package com.whz.ribao.entity.result;

import java.util.List;

/**
 *
 */
public class BaseWebModel {

    private Integer page;// 要查找第几页
    private Integer pageSize;// 每页显示多少条

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setRows(Integer rows) {
        this.pageSize = rows;
    }
}
