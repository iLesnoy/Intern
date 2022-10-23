package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PositionDao extends JpaRepository<Position, BigInteger> {
}
