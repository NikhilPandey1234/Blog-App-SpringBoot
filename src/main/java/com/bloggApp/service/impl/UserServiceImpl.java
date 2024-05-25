package com.bloggApp.service.impl;

import com.bloggApp.dto.UserDTO;
import com.bloggApp.exceptions.ResourceNotFoundException;
import com.bloggApp.modal.Role;
import com.bloggApp.modal.User;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.payload.JwtAuthRequest;
import com.bloggApp.payload.JwtAuthResponse;
import com.bloggApp.repository.RoleRepository;
import com.bloggApp.repository.UserRepository;
import com.bloggApp.security.JwtTokenHelper;
import com.bloggApp.service.UserService;
import com.bloggApp.utils.ModelMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private ModelMappers modelMappers;

    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {
        User user = modelMappers.dtoToUserEntity(userDTO);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        user.setRoles(user.getRoles());
        return this.modelMappers.userEntityToDto(savedUser);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMappers.dtoToUserEntity(userDTO);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMappers.userEntityToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
       User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with this id : " + id));
       user.setUserName(userDTO.getUserName());
       user.setEmail(userDTO.getEmail());
       user.setPassword(userDTO.getPassword());
       user.setAbout(userDTO.getAbout());
       User updateUser = userRepository.save(user);
       UserDTO updateUserDTO = modelMappers.userEntityToDto(updateUser);
        return updateUserDTO;
    }

    @Override
    public List<UserDTO> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMappers.userEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findUserById(Long id) {
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));
            return modelMappers.userEntityToDto(user);
    }

    @Override
    public ApiResponse deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with this id "+ id));
        LocalDateTime dateTime =LocalDateTime.now();
        userRepository.delete(user);
        return new ApiResponse("User deleted successfully with id " + id, true, dateTime.toLocalDate(), dateTime.toLocalTime());

    }

    /*@Override
    public JwtAuthResponse login(JwtAuthRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenHelper.generateToken(userDetails);
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            JwtAuthResponse response = JwtAuthResponse.builder()
                    .token(token)
                    .email(user.getEmail())
                    .username(user.getUserName())
                    .about(user.getAbout())
                    .role(user.getRoles())
                    .build();
            System.out.println("User logged in: " + authentication.getName());
            return response;
        } else {
            throw new ResourceNotFoundException("User not found with email: " + request.getEmail());
        }
    }*/

    @Override
    public JwtAuthResponse login(JwtAuthRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtTokenHelper.generateToken(userDetails);
            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                JwtAuthResponse response = JwtAuthResponse.builder()
                        .token(token)
                        .email(user.getEmail())
                        .username(user.getUserName())
                        .about(user.getAbout())
                        .role(user.getRoles())
                        .build();
                System.out.println("User logged in: " + authentication.getName());
                return response;
            } else {
                throw new ResourceNotFoundException("User not found with email: " + request.getEmail());
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
