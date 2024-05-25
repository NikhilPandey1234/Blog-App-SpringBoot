package com.bloggApp.service.impl;

import com.bloggApp.dto.UserDTO;
import com.bloggApp.exceptions.ResourceNotFoundException;
import com.bloggApp.modal.Follower;
import com.bloggApp.modal.User;
import com.bloggApp.repository.FollowerRepository;
import com.bloggApp.repository.UserRepository;
import com.bloggApp.service.FollowService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerServiceImpl implements FollowService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Override
    @Transactional
    public void follow(UserDTO followDTO, UserDTO followingDTO) {
        User follower = userRepository.findById(followDTO.getId()).orElseThrow(()-> new ResourceNotFoundException("Follower not found with this id :"+followDTO.getId()));
        User following = userRepository.findById(followingDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Following not found with id: " + followingDTO.getId()));

        // Check if the follower is already following the following user
        if (follower.getFollowing().stream().anyMatch(f -> f.getId() == following.getId())) {
            throw new IllegalArgumentException("Already following user with id: " + following.getId());
        }

        Follower newFollower = new Follower();
        newFollower.setFollower(follower);
        newFollower.setFollowing(following);

        follower.getFollowing().add(newFollower);
        following.getFollowers().add(newFollower);

        followerRepository.save(newFollower);

    }

    @Override
    @Transactional
    public void unfollow(UserDTO followDTO, UserDTO followingDTO) {

        User follower = userRepository.findById(followDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Follower not found with id: " + followDTO.getId()));

        User following = userRepository.findById(followingDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Following not found with id: " + followingDTO.getId()));

        Follower existingFollower = follower.getFollowing().stream()
                .filter(f -> f.getFollowing().getId() == following.getId())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Follower not found for following user with id: " + following.getId()));

        follower.getFollowing().remove(existingFollower);
        following.getFollowers().remove(existingFollower);

        followerRepository.delete(existingFollower);


    }
}
