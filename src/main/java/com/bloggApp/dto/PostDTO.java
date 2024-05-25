package com.bloggApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long postId;

    private String postTittle;

    private String content;

//    private String imageName;

    private List<String> imageNames;

    private Date addedDate;

    private CategoryDTO category;

    private UserDTO user;

    private List<CommentDTO> comments = new ArrayList<>();


}
