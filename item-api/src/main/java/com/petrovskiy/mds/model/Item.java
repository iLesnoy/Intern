package com.petrovskiy.mds.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.redis.core.RedisHash;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("item")
public class Item {

    @Id
    @NotNull
    private String id;

    @Field(name = "name")
    @NotNull
    private String name;

    @Field(name = "description")
    private String description;

    @Field(name = "created")
    @NotNull
    @CreatedDate
    private LocalDateTime created;

    private String categoryId;

    @DBRef
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Position> positions;

}
