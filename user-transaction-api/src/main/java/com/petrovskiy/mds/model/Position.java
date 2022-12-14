package com.petrovskiy.mds.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private BigInteger id;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "amount", nullable = false)
    @DecimalMin("0.1")
    private BigDecimal amount;

    @JoinColumn(name = "item_id")
    private UUID itemId;

    @JoinColumn(name = "company_id")
    private BigInteger companyId;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "positionList")
    private Set<Order> orders;

    @PrePersist
    private void PrePersist(){
        created = LocalDateTime.now();
    }
}
