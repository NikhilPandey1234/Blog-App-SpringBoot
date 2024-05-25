package com.bloggApp.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String email;

    private String password;
}
