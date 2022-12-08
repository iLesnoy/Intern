package com.petrovskiy.mds.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@RedisHash("categories")
@Data
@Document("category")
public class Category{

    @Id
    @NotNull
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String name;

    private String parent_category;

    @DBRef
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Item> itemList;

    private String description;

}
