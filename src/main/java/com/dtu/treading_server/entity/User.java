package com.dtu.treading_server.entity;

import com.dtu.treading_server.domain.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "email")
    private String email;

    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    @Column(name = "role")
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

}