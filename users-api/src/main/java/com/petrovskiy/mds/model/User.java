package com.petrovskiy.mds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Pattern(regexp = "^[\\p{Alpha}А-Яа-я]{2,36}$")
    @Column(name = "username", nullable = false)
    private String username;

    @Email
    @Size(max = 32)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[\\p{Alpha}А-Яа-я]{2,36}$")
    private String name;

    @Enumerated
    @Column(name = "role_id", nullable = false)
    private Role role;

    @JoinColumn(name = "company_id")
    private Long companyId;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;


    @PrePersist
    private void PrePersist(){
        created = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        updated = created;
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}
