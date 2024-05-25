package com.bloggApp.controller;

import com.bloggApp.dto.UserDTO;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.payload.JwtAuthRequest;
import com.bloggApp.payload.JwtAuthResponse;
import com.bloggApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request){
        JwtAuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addUser")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO){
//        System.out.println("Hi");
        UserDTO addUser = userService.registerNewUser(userDTO);
        return new ResponseEntity<>(addUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
       // SecurityContextHolder.clearContext(); // Clear the security context
        return ResponseEntity.ok(new ApiResponse("Logout successful", true));
    }

}
