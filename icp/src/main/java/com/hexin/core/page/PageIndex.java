package com.hexin.core.page;

import java.io.Serializable;

/**
 * 类PageIndex.java的实现描述：分页的分页索引
 * 
 * @author Administrator 2012-12-3 下午6:32:43
 */
public class PageIndex implements Serializable{

    private long startIndex;
    private long endIndex;

    /**
     * full constructor
     * 
     * @param startIndex 开始索引
     * @param endIndex 结束索引
     */
    public PageIndex(long startIndex, long endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * 获取分页索引
     * 
     * @param pageRange 分页索引范围
     * @param currentPage 当前页
     * @param totalPage 总页数
     * @return
     */
    public static PageIndex getPageIndex(long pageRange, int currentPage, long totalPage) {
        long startPage = currentPage - (pageRange % 2 == 0 ? pageRange / 2 - 1 : pageRange / 2);
        long endPage = currentPage + pageRange / 2;
        if (startPage < 1) {
            startPage = 1;
            if (totalPage >= pageRange) {
                endPage = pageRange;
            } else {
                endPage = totalPage;
            }
        }
        if (endPage > totalPage) {
            endPage = totalPage;
            if ((endPage - pageRange) > 0) {
                startPage = endPage - pageRange + 1;
            } else {
                startPage = 1;
            }
        }

        return new PageIndex(startPage, endPage);
    }

    /**
     * @return the startIndex
     */
    public long getStartIndex() {
        return startIndex;
    }

    /**
     * @param startIndex the startIndex to set
     */
    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * @return the endIndex
     */
    public long getEndIndex() {
        return endIndex;
    }

    /**
     * @param endIndex the endIndex to set
     */
    public void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }

}
