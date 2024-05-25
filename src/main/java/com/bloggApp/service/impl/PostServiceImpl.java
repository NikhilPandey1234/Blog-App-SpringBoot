package com.bloggApp.service.impl;

import com.bloggApp.dto.PostDTO;
import com.bloggApp.exceptions.ResourceNotFoundException;
import com.bloggApp.modal.Category;
import com.bloggApp.modal.Follower;
import com.bloggApp.modal.Post;
import com.bloggApp.modal.User;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.payload.PostResponse;
import com.bloggApp.repository.CategoryRepository;
import com.bloggApp.repository.PostRepository;
import com.bloggApp.repository.UserRepository;
import com.bloggApp.service.EmailService;
import com.bloggApp.service.PostService;
import com.bloggApp.utils.ModelMappers;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMappers modelMappers;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public PostDTO createPost(PostDTO postDTO, Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with this id: "+userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with this id: "+categoryId));

        Post post = modelMappers.dtoToPostEntity(postDTO);
       // post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post savedPost = postRepository.save(post);

        // Get the followers' emails of the user
        List<String> emails = new ArrayList<>();
        for (Follower follower : user.getFollowers()) {
            emails.add(follower.getFollower().getEmail());
        }
        String subject = "New Blog Post";
        String message = "Check out the new blog post by " + user.getUserName() + ": " + postDTO.getPostTittle(); // Customize this message as needed

        try {
            emailService.sendEmail(emails.toArray(new String[0]), subject, message);
        } catch (Exception e) {
            // Handle the exception
            logger.error("Failed to send email notification to followers of user " + user.getUserName(), e);
        }


        return modelMappers.postEntityToPostDto(savedPost);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found with this id :" +id));
        post.setPostTittle(postDTO.getPostTittle());
        post.setContent(post.getContent());
        post.setImageNames(postDTO.getImageNames());
        Post updatedPost = postRepository.save(post);
        return modelMappers.postEntityToPostDto(updatedPost);
    }

    @Override
    public PostResponse findAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
       /* Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }*/
        Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(page);
        List<Post> posts = pagePost.getContent();
        List<PostDTO> postDTOS = posts.stream().map(post -> modelMappers.postEntityToPostDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDTO findByIdPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found with this id :"+id));
        return modelMappers.postEntityToPostDto(post);
    }

    @Override
    public ApiResponse deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found with this id :"   +id));
        LocalDateTime dateTime = LocalDateTime.now();
        postRepository.delete(post);
        return new ApiResponse("Post deleted successfully with id " + id, true, dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    @Override
    public PostResponse getPostByCategoryId(Long categoryId,Integer pageNumber,Integer pageSize) {
      Category category =  categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with this id :"+categoryId));
      Pageable page = PageRequest.of(pageNumber, pageSize);
      Page<Post> pagePosts = postRepository.findByCategory(category,page);
        List<PostDTO> postDTOS = pagePosts.getContent().stream()
                .map(modelMappers::postEntityToPostDto)
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getPostByUserId(Long userId,Integer pageNumber, Integer pageSize) {
       User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with this id :" +userId));
       Pageable page = PageRequest.of(pageNumber,pageSize);
       Page<Post> pagePosts = postRepository.findByUser(user, page);
       List<PostDTO> postDTOS = pagePosts.getContent().stream()
                .map(modelMappers::postEntityToPostDto)
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
       return postResponse;
    }


    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = this.postRepository.findByPostTittleContaining(keyword);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("No posts found with the keyword: " + keyword);
        }
        List<PostDTO> postDTOS = posts.stream().map(post -> this.modelMappers.postEntityToPostDto(post)).collect(Collectors.toList());
        return postDTOS;

    }

}
