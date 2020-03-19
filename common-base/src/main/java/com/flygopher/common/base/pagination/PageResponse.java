package com.flygopher.common.base.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private boolean last;
    private List<T> content;

    public PageResponse(PageResponse pageResponse, List<T> content) {
        this.number = pageResponse.getNumber();
        this.size = pageResponse.getSize();
        this.totalPages = pageResponse.getTotalPages();
        this.totalElements = pageResponse.getTotalElements();
        this.first = pageResponse.isFirst();
        this.last = pageResponse.isLast();
        this.content = content;
    }
}
