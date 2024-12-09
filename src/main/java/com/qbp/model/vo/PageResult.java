package com.qbp.model.vo;

import java.util.List;

/**
 * 分页结果
 * @param <T> 数据类型
 */
public class PageResult<T> {
    private int currentPage;  // 当前页码
    private int pageSize;     // 每页大小
    private int totalPages;   // 总页数
    private long totalRecords; // 总记录数
    private List<T> data;     // 数据列表

    public PageResult(int currentPage, int pageSize, long totalRecords, List<T> data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        this.data = data;
        this.totalPages = (int) Math.ceil((double) totalRecords / pageSize);
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
}
