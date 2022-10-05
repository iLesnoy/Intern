package com.petrovskiy.mds.intern.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    private String description;

    @Column(name = "email", nullable = false)
    private LocalDateTime created;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item_id")
    private List<Position> positionList;

}
