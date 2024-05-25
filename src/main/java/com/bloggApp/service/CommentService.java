package com.bloggApp.service;

import com.bloggApp.dto.CommentDTO;
import com.bloggApp.payload.ApiResponse;

import java.util.List;

public interface CommentService {

    CommentDTO create(CommentDTO commentDTO, Long postId);

    ApiResponse deleteComment(Long id);

    /*CommentDTO update(Long id, CommentDTO commentDTO);

    List<CommentDTO> findAll();

    CommentDTO findById(Long id);*/

}
