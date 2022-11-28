package com.petrovskiy.mds.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.math.BigInteger;


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

    @Column(name = "description")
    private String description;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JoinColumn(name = "position_id")
    private BigInteger positionId;

    @PrePersist
    private void PrePersist(){
        created = LocalDateTime.now();
    }

}
