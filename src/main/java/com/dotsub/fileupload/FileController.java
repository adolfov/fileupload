package com.dotsub.fileupload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<File> addFile(@RequestParam("title") String title, @RequestParam("desc") String desc,
      @RequestParam("date") String date, @RequestParam("file") MultipartFile file) {

    Date creationDate;
    try {
      creationDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
    } catch (ParseException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // store file object with metadata
    File uploadedFile = new File(title, desc, creationDate, file.getOriginalFilename());
    fileRepository.save(uploadedFile);

    // save file in file system
    Path filePath = Paths.get(STORAGE_PATH + "/" + file.getOriginalFilename());
    try {
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioe) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(uploadedFile, HttpStatus.OK);
  }

  @GetMapping("/files")
  public ResponseEntity<List<File>> getFiles() {
    List<File> files = (List<File>) fileRepository.findAll();
    return new ResponseEntity<>(files, HttpStatus.OK);
  }

}