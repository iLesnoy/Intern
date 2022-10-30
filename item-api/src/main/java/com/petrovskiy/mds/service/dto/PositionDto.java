package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({"id", "createdBy","created","amount", "item","company"})
public class PositionDto {

    @JsonProperty("id")
    private BigInteger id;

    @JsonProperty("createdBy")
    private String created_by;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("item")
    private ItemDto itemDto;

    @JsonProperty("company")
    private CompanyDto companyDto;
}
