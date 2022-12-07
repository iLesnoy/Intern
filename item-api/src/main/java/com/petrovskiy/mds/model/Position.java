package com.petrovskiy.mds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    private BigInteger id;

    private String created_by;

    @NotNull
    private LocalDateTime created;

    @DecimalMin("0.1")
    private BigDecimal amount;

    @NotNull
    private UUID itemId;

    @NotNull
    private BigInteger companyId;
}
