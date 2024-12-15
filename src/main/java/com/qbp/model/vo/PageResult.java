package com.qbp.model.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qbp.constant.HttpStatus;
import com.qbp.model.entity.Resource;

import java.util.List;

/**
 * 分页结果
 *
 * @param <T> 数据类型
 */
public class PageResult<T> {
    private int currentPage;  // 当前页码
    private int pageSize;     // 每页大小
    private int totalPages;   // 总页数
    private long totalRecords; // 总记录数
    private List<T> data;     // 数据列表
    private int code; // 消息状态码

    private String msg; // 消息内容

    public PageResult(IPage<T> page) {
        this.currentPage = (int) page.getCurrent();
        this.pageSize = (int) page.getSize();
        this.totalRecords = page.getTotal();
        this.data = page.getRecords();
        this.totalPages = (int) page.getPages();
        this.code = HttpStatus.SUCCESS;
        this.msg = "查询成功";
    }

    // Getters and Setters
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
