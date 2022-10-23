package com.petrovskiy.mds.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "amount", nullable = false)
    @DecimalMin("0.1")
    private BigDecimal amount;

    @JoinColumn(name = "item_id")
    private UUID itemId;

    @JoinColumn(name = "company_id")
    private BigInteger companyId;

    @PrePersist
    private void PrePersist(){
        created = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
