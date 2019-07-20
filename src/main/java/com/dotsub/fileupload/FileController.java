package com.dotsub.fileupload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class FileController {
 
    @Autowired
    private FileRepository fileRepository;
  
    @PostMapping("/files")
    void addFile(@RequestBody File file) {
      fileRepository.save(file);
    }
}