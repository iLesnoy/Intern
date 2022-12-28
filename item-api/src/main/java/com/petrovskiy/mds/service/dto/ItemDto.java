package com.petrovskiy.mds.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@JsonPropertyOrder({"id", "name","description", "created","category"})
public class ItemDto implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("categoryId")
    private String categoryId;
}
