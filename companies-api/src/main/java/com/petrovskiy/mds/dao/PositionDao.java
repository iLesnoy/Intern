package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface PositionDao extends JpaRepository<Position, BigInteger> {

    Optional<Position> findPositionsByIdAndAmountIsAfterOrAmountEquals(BigInteger id, BigDecimal amount,BigDecimal amount2);
}
