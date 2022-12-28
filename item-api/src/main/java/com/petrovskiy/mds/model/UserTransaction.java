package com.petrovskiy.mds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document("userTransaction")
public class UserTransaction {

    @Id
    @NotNull
    private String id;

    @NotNull
    @CreatedDate
    private LocalDateTime transactionDate;

    @NotNull
    private String userId;

    @Size(min = 1)
    @NotNull
    private Long amount;

    private Position position;
}
