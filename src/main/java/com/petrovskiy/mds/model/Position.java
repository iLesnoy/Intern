package com.petrovskiy.mds.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private BigInteger id;

                                 /* TODO add JPA audit for this feature?*/
    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "amount", nullable = false)
    @DecimalMin("0.1")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @PrePersist
    private void PrePersist(){
        created = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
