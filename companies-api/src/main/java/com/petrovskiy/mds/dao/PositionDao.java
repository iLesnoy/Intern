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

    @Modifying
    @Query("update Position p set p.amount = p.amount - :amount where p.id = :id")
    Optional<Position> updatePositionAmount(@Param("id") BigInteger positionId, @Param("amount") BigDecimal amount);
}
