package com.bloggApp.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileService {

//    String uploadImage(String path, MultipartFile file) throws IOException;

    List<String> uploadImages(String path, MultipartFile[] files) throws IOException;

    InputStream getResource(String path, String fileName) throws FileNotFoundException;


}
