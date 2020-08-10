package com.example.forume.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDto {
    private Long id;
    private String name;
    private String description;
    private Instant createdDate;
    private Integer numberOfPosts;
}
