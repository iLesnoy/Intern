package com.petrovskiy.mds.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@RedisHash("categories")
@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {

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
