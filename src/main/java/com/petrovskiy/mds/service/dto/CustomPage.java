package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Collection;

@Data
@JsonPropertyOrder({"content", "size", "total_elements", "total_pages", "number"})
public class CustomPage<T> {

    @JsonProperty("content")
    private Collection<T> content;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("total_elements")
    private Long totalElements;
    @JsonProperty("total_pages")
    private Long totalPages;
    @JsonProperty("number")
    private Integer number;

    public CustomPage(Collection<T> content, CustomPageable pageable, Long totalElements) {
        this.content = content;
        this.size = pageable.getSize();
        this.totalElements = totalElements;
        this.totalPages = totalElements != 0 ? (long) Math.ceil((double) totalElements / pageable.getSize()) : 0;
        this.number = totalElements != 0 ? pageable.getPage() + 1 : 0;
    }
}