package com.example.forume.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    private String username;
    private String password;
    private String email;
    private Instant created;
    private boolean enabled;
}