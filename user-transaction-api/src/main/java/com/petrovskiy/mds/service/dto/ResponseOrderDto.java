package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class ResponseOrderDto {

    private UUID id;

    private LocalDateTime transactionDate;

    private UserDto user;

    @JsonProperty("positions")
    private List<PositionDto> positionDtoList;
}
