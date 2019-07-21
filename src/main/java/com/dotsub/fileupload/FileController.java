package com.dotsub.fileupload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*")
public class FileController {

  // Path were files will be stored
  private static String STORAGE_PATH = "/tmp";

  @Autowired
  private FileRepository fileRepository;

  @PostMapping("/files")
  File addFile(@RequestParam("title") String title, @RequestParam("desc") String desc,
      @RequestParam("date") String date, @RequestParam("file") MultipartFile file) throws IOException, ParseException {

    Date creationDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);  

    File uploadedFile = new File(title, desc, creationDate);
    fileRepository.save(uploadedFile);

    Path filePath = Paths.get(STORAGE_PATH + "/" + file.getOriginalFilename());
    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    return uploadedFile;
  }
}