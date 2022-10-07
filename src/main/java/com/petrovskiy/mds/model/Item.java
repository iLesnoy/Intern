package com.petrovskiy.mds.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
