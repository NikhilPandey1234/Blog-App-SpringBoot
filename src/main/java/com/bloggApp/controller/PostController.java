package com.bloggApp.controller;

import com.bloggApp.config.AppConstants;
import com.bloggApp.dto.PostDTO;
import com.bloggApp.payload.ApiResponse;
import com.bloggApp.payload.PostResponse;
import com.bloggApp.service.FileService;
import com.bloggApp.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO postDTO, @PathVariable Long categoryId, @PathVariable Long userId){
        PostDTO newPost = postService.createPost(postDTO,userId,categoryId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getByCategoryId(@PathVariable Long categoryId,
                                                         @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                         @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        PostResponse postDTO = postService.getPostByCategoryId(categoryId,pageNumber,pageSize);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getByUserId(@PathVariable Long userId,
                                                     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize){
        PostResponse postDTO = postService.getPostByUserId(userId,pageNumber,pageSize);
        return  new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        PostResponse postDTOS = postService.findAllPosts(pageNumber, pageSize,sortBy, sortDir);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        PostDTO postDTO = postService.findByIdPost(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId){
        return ResponseEntity.ok(postService.deletePost(postId));
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO , @PathVariable Long postId){
        PostDTO updatePost = postService.updatePost(postId,postDTO);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPosts(@PathVariable String keyword){
        List<PostDTO> postDTOS = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    //Post Image Upload
    /*@PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadImage(@RequestParam("image")MultipartFile image, @PathVariable Long postId) throws IOException {
        String fileName = this.fileService.uploadImage(path, image);
        PostDTO postDTO =   this.postService.findByIdPost(postId);
        postDTO.setImageName(fileName);
        PostDTO updatePost = this.postService.updatePost(postId,postDTO);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }*/

    @PostMapping("/post/image/upload/{postId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDTO> uploadImages(@RequestParam("images") MultipartFile[] images, @PathVariable Long postId) throws IOException {
        List<String> fileNames = this.fileService.uploadImages(path, images);
        PostDTO postDTO = this.postService.findByIdPost(postId);
        postDTO.setImageNames(fileNames);
        PostDTO updatePost = this.postService.updatePost(postId, postDTO);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }


    @GetMapping(value = "/post/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}
