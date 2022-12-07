package com.petrovskiy.mds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    private BigInteger id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private String email;

    @NotNull
    private LocalDateTime created;

    private String description;

}
