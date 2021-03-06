package com.example.forume.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subreddit {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "subreddit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    private Instant createdDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}