package com.bloggApp.service.impl;

import com.bloggApp.dto.CommentDTO;
import com.bloggApp.dto.PostDTO;
import com.bloggApp.exceptions.ResourceNotFoundException;
import com.bloggApp.modal.Comment;
import com.bloggApp.modal.Post;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.repository.CommentRepository;
import com.bloggApp.repository.PostRepository;
import com.bloggApp.service.CommentService;
import com.bloggApp.utils.ModelMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMappers modelMappers;
    @Override
    public CommentDTO create(CommentDTO commentDTO, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post not found with this id :" + postId));
        Comment comment = this.modelMappers.dtoToCommentEntity(commentDTO);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return modelMappers.commentEntityTOCommentDTO(savedComment);
    }

    @Override
    public ApiResponse deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Comment Not Found with this id :"+ id));
        LocalDateTime dateTime = LocalDateTime.now();
        this.commentRepository.delete(comment);
        return new ApiResponse("Comment deleted successfully with id " + id, true, dateTime.toLocalDate(), dateTime.toLocalTime());
    }
}
