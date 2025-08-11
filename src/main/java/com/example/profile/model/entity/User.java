package com.example.profile.model.entity;

import com.example.profile.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "description")
    private String description;

    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    private int points;
}
