package com.petrovskiy.mds.intern.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<User> userList;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company_id")
    private List<Position> positionList;
}
