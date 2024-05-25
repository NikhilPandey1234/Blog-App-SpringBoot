package com.bloggApp.dto;

import com.bloggApp.modal.Follower;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private long id;

    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters")
    private String userName;

    @Email(message = "Email address is not Valid!!")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password be must be minimum of 3 chars and max of 10 chars !!")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=])\\S+$", message = "Password must contain at least one letter, one number, and one special character")
    private String password;

    @NotEmpty
    private String about;

    private Set<RoleDTO> roles=new HashSet<>();

//    private List<Follower> followers = new ArrayList<>();
//
//    private List<Follower> following = new ArrayList<>();

}
