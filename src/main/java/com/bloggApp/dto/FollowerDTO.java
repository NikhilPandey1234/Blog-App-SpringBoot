package com.bloggApp.dto;

import com.bloggApp.modal.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowerDTO {

    private UserDTO follower;

    private UserDTO following;


}
