package com.bloggApp.service;

import com.bloggApp.dto.CategoryDTO;
import com.bloggApp.dto.PostDTO;
import com.bloggApp.dto.UserDTO;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.payload.PostResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId);

    PostDTO updatePost(Long id, PostDTO postDTO);

    PostResponse findAllPosts(Integer pageNumber, Integer pageSize, String sort, String sortDir);

    PostDTO findByIdPost(Long id);

    PostResponse getPostByCategoryId(Long categoryId,Integer pageNumber, Integer pageSize);

    PostResponse getPostByUserId(Long userId,Integer pageNumber, Integer pageSize);

    ApiResponse deletePost(Long id);

    List<PostDTO> searchPosts(String Keyword);

}
