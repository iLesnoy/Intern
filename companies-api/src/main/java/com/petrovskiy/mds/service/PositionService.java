package com.petrovskiy.mds.service;

import com.petrovskiy.mds.service.dto.PositionDto;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface PositionService extends BaseService<PositionDto, BigInteger>{
    PositionDto findPositionByIdAndAmount(BigInteger id, BigDecimal amount);
}
