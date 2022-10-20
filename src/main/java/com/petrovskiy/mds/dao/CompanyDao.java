package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Company;

import java.math.BigInteger;
import java.util.Optional;

public interface CompanyDao extends BaseDao<Company, BigInteger>{

    Optional<Company> findByName(String name);

}
