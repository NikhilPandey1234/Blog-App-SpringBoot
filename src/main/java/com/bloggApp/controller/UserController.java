package com.bloggApp.controller;

import com.bloggApp.dto.UserDTO;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.service.FollowService;
import com.bloggApp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private FollowService followerService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @PathVariable ("userId") Long userId, @RequestBody UserDTO userDto){
        UserDTO updateUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") Long userId){
        return  ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.findAllUser());
    }

   @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<ApiResponse> followUser(@PathVariable("followerId") Long followerId, @PathVariable("followingId") Long followingId) {
        UserDTO followerDTO = userService.findUserById(followerId);
        UserDTO followingDTO = userService.findUserById(followingId);
        followerService.follow(followerDTO, followingDTO);
        return ResponseEntity.ok(new ApiResponse("User with id " + followerId + " is now following user with id " + followingId, true));
    }

    @DeleteMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity<ApiResponse> unfollowUser(@PathVariable("followerId") Long followerId, @PathVariable("followingId") Long followingId) {
        UserDTO followerDTO = userService.findUserById(followerId);
        UserDTO followingDTO = userService.findUserById(followingId);
        followerService.unfollow(followerDTO, followingDTO);
        return ResponseEntity.ok(new ApiResponse("User with id " + followerId + " has unfollowed user with id " + followingId, true));
    }
}
