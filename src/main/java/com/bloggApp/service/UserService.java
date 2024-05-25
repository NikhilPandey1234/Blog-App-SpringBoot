package com.bloggApp.service;

import com.bloggApp.dto.UserDTO;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.payload.JwtAuthRequest;
import com.bloggApp.payload.JwtAuthResponse;

import java.util.List;

public interface  UserService {

    UserDTO registerNewUser(UserDTO userDTO);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id,UserDTO userDTO);

    List <UserDTO> findAllUser();

    UserDTO findUserById(Long id);

    ApiResponse deleteUser(Long id);

    JwtAuthResponse login(JwtAuthRequest request);
}
