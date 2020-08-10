package com.example.forume.service;

import com.example.forume.dto.CommentsDto;
import com.example.forume.model.Comment;
import com.example.forume.repository.CommentRepository;
import com.example.forume.repository.PostRepository;
import com.example.forume.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;

    public void save(CommentsDto commentsDto) {
        Comment comment = fromDto(commentsDto);

        commentRepository.save(comment);
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::toDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        return commentRepository.findAllByUserUsername(userName)
                .stream()
                .map(this::toDto)
                .collect(toList());
    }

    public Comment fromDto(CommentsDto commentsDto){
        Comment comment = new Comment();
        if(commentsDto.getId() != null){
            comment.setId(commentsDto.getId());
        }
        comment.setCreatedDate(Instant.now());
        comment.setPost(postRepository.getOne(commentsDto.getPostId()));
        comment.setUser(userRepository.findByUsername(authService.getCurrentUser().getUsername()));
        comment.setText(commentsDto.getText());

        return comment;
    }

    public CommentsDto toDto(Comment comment){
        CommentsDto commentDto = new CommentsDto();
        commentDto.setCreatedDate(comment.getCreatedDate());
        commentDto.setPostId(comment.getPost().getId());
        commentDto.setText(comment.getText());
        commentDto.setUserName(comment.getUser().getUsername());
        return commentDto;
    }
}
