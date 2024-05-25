package com.bloggApp.service.impl;

import com.bloggApp.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

   /* @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File Name
        String name = file.getOriginalFilename();
        //abc.png

        String randomId = UUID.randomUUID().toString();
        String newFileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        //Full Path
        String filePath = path + File.separator + newFileName;


        //Create Folder if not exists
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //File copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return newFileName;

    }*/

//Multiple file uploading
    @Override
    public List<String> uploadImages(String path, MultipartFile[] files) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();
            String randomId = UUID.randomUUID().toString();
            String newFileName = randomId.concat(name.substring(name.lastIndexOf(".")));
            String filePath = path + File.separator + newFileName;

            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }

            Files.copy(file.getInputStream(), Paths.get(filePath));
            fileNames.add(newFileName);
        }
        return fileNames;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
