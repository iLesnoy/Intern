package com.petrovskiy.mds.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum OrderStatus {

    ACCEPTED(HttpStatus.ACCEPTED),
    IN_PROGRESS(HttpStatus.NO_CONTENT),
    FAILED(HttpStatus.NOT_ACCEPTABLE);


    private final HttpStatus httpStatus;
}
