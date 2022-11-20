package com.petrovskiy.mds.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @DecimalMin("0.1")
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="position_has_orders"
            ,joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "position_id",referencedColumnName = "id"))
    private List<Position> positionList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<UserTransaction> userTransactions;

}
