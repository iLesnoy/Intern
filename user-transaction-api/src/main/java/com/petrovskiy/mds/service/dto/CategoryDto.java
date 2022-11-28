package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.math.BigInteger;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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
