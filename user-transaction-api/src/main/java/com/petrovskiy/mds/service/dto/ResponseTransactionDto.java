package com.petrovskiy.mds.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
public class ResponseTransactionDto {

    private UUID id;

    private LocalDateTime transactionDate;

    private UserDto user;

    private RequestOrderDto orderDto;
}
