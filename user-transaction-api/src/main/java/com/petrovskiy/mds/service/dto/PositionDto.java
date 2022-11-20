package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.petrovskiy.mds.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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

    @JsonProperty("order")
    private Order order;
}
