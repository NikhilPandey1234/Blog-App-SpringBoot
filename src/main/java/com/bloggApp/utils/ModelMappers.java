package com.bloggApp.utils;

import com.bloggApp.dto.*;
import com.bloggApp.modal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMappers {

    @Autowired
    private ModelMapper modelMapper;

    public User dtoToUserEntity(UserDTO userDto) {
        User user = this.modelMapper.map(userDto,User.class);
        return user;
    }

    public UserDTO userEntityToDto(User user){
        UserDTO userDto = this.modelMapper.map(user,UserDTO.class);
        return userDto;

    }

    public Category dtoToCategoryEntity(CategoryDTO categoryDto) {
       Category category = this.modelMapper.map(categoryDto,Category.class);
       return category;
    }

    public CategoryDTO entityToCategoryDTO(Category category) {
        CategoryDTO categoryDto = this.modelMapper.map(category, CategoryDTO.class);
        return categoryDto;

    }

    public Post dtoToPostEntity(PostDTO postDto){
        Post post = this.modelMapper.map(postDto,Post.class);
        return post;
    }

    public PostDTO postEntityToPostDto(Post post){
        PostDTO postDTO = this.modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    public Comment dtoToCommentEntity(CommentDTO commentDTO){
        Comment comments = this.modelMapper.map(commentDTO, Comment.class);
        return  comments;
    }

    public CommentDTO commentEntityTOCommentDTO(Comment comment){
        CommentDTO commentDTO = this.modelMapper.map(comment, CommentDTO.class);
        return  commentDTO;
    }

    public Role roleDtoToRoleEntity(RoleDTO roleDTO){
        Role role = this.modelMapper.map(roleDTO, Role.class);
        return role;
    }

    public RoleDTO RoleEntityToRoleDto(Role role){
        RoleDTO roleDTO = this.modelMapper.map(role, RoleDTO.class);
        return roleDTO;
    }
}
