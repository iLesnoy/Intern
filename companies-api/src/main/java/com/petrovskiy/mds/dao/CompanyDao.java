package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface CompanyDao extends JpaRepository<Company, BigInteger> {
    Optional<Company> findByName(String name);

}
