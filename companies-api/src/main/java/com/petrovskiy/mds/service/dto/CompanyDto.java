package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Data
@JsonPropertyOrder({"id", "name","email", "created","description"})
public class CompanyDto implements Serializable {

    @JsonProperty("id")
    private BigInteger id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("description")
    private String description;
}

