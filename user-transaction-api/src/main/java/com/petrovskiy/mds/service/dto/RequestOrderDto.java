package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class RequestOrderDto {

    private UUID id;

    @JsonProperty("userId")
    private UUID userId;

    @JsonProperty("positions")
    private List<BigInteger> positionList;

    @DecimalMin("0.1")
    private BigDecimal amount;

}
