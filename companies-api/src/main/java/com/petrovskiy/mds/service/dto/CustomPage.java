package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode
@JsonPropertyOrder({"records", "pageSize", "pageNumber", "totalPages", "totalRecordCount", "firstPage", "lastPage", "empty"})
public class CustomPage<T> implements Page<T> {
    @JsonProperty("records")
    private final List<T> content = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final Pageable pageable;
    @JsonProperty("totalRecordCount")
    private final long total;

    public CustomPage(List<T> content, Pageable pageable, long total) {
        this.content.addAll(content);
        this.pageable = pageable;
        this.total = total;
    }

    @Override
    @JsonProperty("totalPages")
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public long getTotalElements() {
        return total;
    }

    @Override
    @JsonProperty("pageNumber")
    public int getNumber() {
        return pageable.isPaged() ? pageable.getPageNumber() : 0;
    }

    @Override
    @JsonProperty("pageSize")
    public int getSize() {
        return pageable.isPaged() ? pageable.getPageSize() : content.size();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int getNumberOfElements() {
        return content.size();
    }

    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public boolean hasContent() {
        return !content.isEmpty();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Sort getSort() {
        return pageable.getSort();
    }

    @Override
    @JsonProperty("firstPage")
    public boolean isFirst() {
        return !hasPrevious();
    }

    @Override
    @JsonProperty("lastPage")
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    @Override
    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    @Override
    public Pageable nextPageable() {
        return hasNext() ? pageable.next() : Pageable.unpaged();
    }

    @Override
    public Pageable previousPageable() {
        return hasPrevious() ? pageable.previousOrFirst() : Pageable.unpaged();
    }

    @Override
    public <U> Page <U> map(Function<? super T, ? extends U> converter) {
        return new CustomPage<>(getConvertedContent(converter), getPageable(), total);
    }

    private  <U> List <U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null!");
        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }
}