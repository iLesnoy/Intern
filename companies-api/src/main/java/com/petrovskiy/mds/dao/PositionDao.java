package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PositionDao extends JpaRepository<Position, BigInteger> {
}
