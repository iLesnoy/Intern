package com.petrovskiy.mds.intern.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

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
    private BigDecimal amount;  /*BigDecimal ????*/

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item_id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Item company_id;

}
