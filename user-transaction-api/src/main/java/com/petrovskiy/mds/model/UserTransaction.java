package com.petrovskiy.mds.model;

import com.petrovskiy.mds.service.dto.OrderStatus;
import com.sun.istack.NotNull;
import lombok.*;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="user_transaction")
public class UserTransaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @NotNull
    private OrderStatus orderStatus;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @PrePersist
    private void PrePersist(){
        transactionDate = LocalDateTime.now();
    }

}

