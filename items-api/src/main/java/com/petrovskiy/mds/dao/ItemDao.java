package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemDao extends JpaRepository<Item, UUID> {
}
