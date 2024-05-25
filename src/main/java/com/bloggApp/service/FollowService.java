package com.bloggApp.service;

import com.bloggApp.dto.UserDTO;

public interface FollowService {

    void follow(UserDTO followDTO, UserDTO followingDTO);

    void unfollow(UserDTO followDTO, UserDTO followingDTO);
}
