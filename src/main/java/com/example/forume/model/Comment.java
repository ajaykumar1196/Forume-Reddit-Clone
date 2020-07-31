package com.example.forume.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", referencedColumnName = "id")
    private Post post;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;


}
