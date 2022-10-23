package com.petrovskiy.mds.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<User> userList;*/
    /*@JoinColumn(name = "user_id")
    private Long userId;
*/
    @Column(name = "description", nullable = false)
    private String description;

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<Position> positionList;*/

    @PrePersist
    private void PrePersist(){
        created = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
