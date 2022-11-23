package com.petrovskiy.mds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="userTransaction")
public class UserTransaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "transactionDate", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Size(min = 1)
    @Column(name = "amount", nullable = false)
    private Long amount;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @PrePersist
    private void PrePersist(){
        transactionDate = LocalDateTime.now();
    }

}
