package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class CustomPageable {
    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer DEFAULT_PAGE = 0;

    @JsonProperty("size")
    private Integer size = DEFAULT_SIZE;
    @JsonProperty("page")
    private Integer page = DEFAULT_PAGE;

    public void setSize(Integer size) {
        this.size = Objects.nonNull(size) ? size: DEFAULT_SIZE;;
    }

    public void setPage(Integer page) {
        this.page = Objects.nonNull(page) ? page: DEFAULT_PAGE;
    }
}