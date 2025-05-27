package com.example.springauth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;



@Controller
public class UploadController {



    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
    // user.dir - working directory, user.home - users home directory
    private static final String UPLOAD_DIRECTORY = System.getProperty("user.home") + SEPARATOR + "uploads";;


    @GetMapping("/upload")
    public String uploadFile(Model model){
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image") MultipartFile file, Model model){
        String subDirectory = "profiles";
        try{
            String fileExtension = this.getFileExtension(file);
            System.out.println("The file extension is: " + fileExtension);

            if ((new File(UPLOAD_DIRECTORY)).isDirectory() && (fileExtension != null)) {
                String newFileName = this.getNewFileName(fileExtension);
                String originalFIleName = file.getOriginalFilename(); // to be saved in some cases
                Path path = Paths.get(
                           UPLOAD_DIRECTORY + SEPARATOR +
                                subDirectory + SEPARATOR +
                                newFileName
                            );
                Files.write(path, file.getBytes());
            }else {
                // log any failures
                System.out.println("Upload directory not found.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            model.addAttribute("message", "Error uploading file" + e.getMessage());
        }
        return "upload";
    }

    private String getFileExtension(MultipartFile file){
        String contentType = file.getContentType();
        String[] infoArray = contentType != null ? contentType.split("/") : null;
        return (infoArray != null) ? infoArray[infoArray.length - 1] : null;
    }

    private String getNewFileName(String extension){
        return UUID.randomUUID().toString() + "." + extension;
    }

}
