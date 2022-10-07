package com.petrovskiy.mds.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Item> itemList;

    private String parent_category;

    private String description;

}
