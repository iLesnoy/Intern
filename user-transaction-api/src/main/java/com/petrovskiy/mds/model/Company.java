package com.petrovskiy.mds.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.time.LocalDateTime;
import javax.persistence.GenerationType;
import javax.persistence.PrePersist;
import java.math.BigInteger;


@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "description", nullable = false)
    private String description;

    @PrePersist
    private void PrePersist(){
        created = LocalDateTime.now();
    }
}
