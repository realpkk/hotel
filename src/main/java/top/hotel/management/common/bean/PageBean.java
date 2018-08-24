package top.hotel.management.common.bean;

import org.springframework.data.domain.Page;
import top.hotel.management.entity.server.Room;

import java.util.List;

/**
 *  自定义页面数据封装
 * @param <T>
 */
public class PageBean<T> {

    private int pageSize;

    private int pageNumber;

    private int totalPages;

    private long totalElements;

    private List<T> content;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public PageBean(int pageNumber, int pageSize, int totalPages, long totalElements, List<T> content) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.content = content;
    }
}
