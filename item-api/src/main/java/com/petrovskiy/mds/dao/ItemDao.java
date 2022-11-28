package com.petrovskiy.mds.dao;

import com.petrovskiy.mds.model.Item;
import com.petrovskiy.mds.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItemDao extends JpaRepository<Item, UUID> {
}
