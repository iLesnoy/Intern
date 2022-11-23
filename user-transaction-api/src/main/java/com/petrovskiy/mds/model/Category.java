package com.petrovskiy.mds.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
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

    @Column(name = "parent_category")
    private String parent_category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Item> itemList;


    @Column(name = "description")
    private String description;

}
