package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTransactionDao extends JpaRepository<UserTransaction, UUID> {
}
