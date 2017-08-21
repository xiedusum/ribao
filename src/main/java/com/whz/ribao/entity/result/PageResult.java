package com.whz.ribao.entity.result;

import java.util.List;

/**
 * 封装分页结果集
 * 
 */
public class PageResult extends Result {
	
	private Integer page;// 要查找第几页
	private Integer pageSize;// 每页显示多少条
	private List rows;// 结果集
	private Long total;      // 总记录数

    public PageResult() {
    }

    public PageResult(List rows, Integer page, Integer pageSize, Long total) {
        setRows(rows);
        setPage(page);
        setPageSize(pageSize);
        setTotal(total);
    }

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

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
        if (rows == null)
            exceptionMsg(ExceptionMsg.FAILED);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
