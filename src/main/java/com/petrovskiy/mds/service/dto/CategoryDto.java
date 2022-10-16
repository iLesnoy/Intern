package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigInteger;

@Data
@JsonPropertyOrder({"id", "name","parent_category", "description"})
public class CategoryDto {

    @JsonProperty("id")
    private BigInteger id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("parent_category")
    private String parent_category;

    @JsonProperty("description")
    private String description;
}
