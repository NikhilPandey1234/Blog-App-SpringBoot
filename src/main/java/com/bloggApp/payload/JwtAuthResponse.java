package com.bloggApp.payload;

import com.bloggApp.dto.RoleDTO;
import com.bloggApp.modal.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthResponse {

    private String token;

    private String email;

    private String username;

    private String about;

    private Set<Role> role;
}
