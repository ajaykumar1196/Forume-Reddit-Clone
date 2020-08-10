package com.example.forume.service;

import com.example.forume.dto.SubredditDto;
import com.example.forume.model.Subreddit;
import com.example.forume.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(fromDto(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) throws Exception {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new Exception("No subreddit found with ID - " + id));
        return toDto(subreddit);
    }


    public Subreddit fromDto(SubredditDto subredditDto){
       Subreddit subreddit = new Subreddit();

       if(subredditDto.getId() != null){
           subreddit = subredditRepository.getOne(subredditDto.getId());
       }
       subreddit.setDescription(subredditDto.getDescription());
       subreddit.setCreatedDate(Instant.now());
       subreddit.setName(subredditDto.getName());
       return subreddit;
    }

    public SubredditDto toDto(Subreddit subreddit){
        SubredditDto subredditDto = new SubredditDto();
        subredditDto.setId(subreddit.getId());
        subredditDto.setDescription(subreddit.getDescription());
        subredditDto.setCreatedDate(Instant.now());
        subredditDto.setName(subreddit.getName());
        subredditDto.setNumberOfPosts(subreddit.getPosts().size());
        return subredditDto;
    }

}
