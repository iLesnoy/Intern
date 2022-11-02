package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.math.BigInteger;

@Data
@JsonPropertyOrder({"id", "name","parentCategory", "description"})
public class CategoryDto {

    @JsonProperty("id")
    private BigInteger id;

    @Pattern(regexp = "^[\\p{Alpha}А-Яа-я]{2,36}$")
    @JsonProperty("name")
    private String name;

    @JsonProperty("parentCategory")
    private String parentCategory;

    @JsonProperty("description")
    private String description;
}
