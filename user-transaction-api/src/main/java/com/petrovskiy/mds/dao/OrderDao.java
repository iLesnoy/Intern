package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDao extends JpaRepository<Order, UUID> {
}
