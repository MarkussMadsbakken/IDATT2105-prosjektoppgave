package edu.ntnu.stud.controller;

import edu.ntnu.stud.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class for handling image upload requests.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

  @Autowired
  private FileStorageService fileStorageService;

  /**
   * Endpoint for uploading an image file.
   *
   * @param file The image file to be uploaded.
   * @return ResponseEntity containing the path to the stored file.
   */
  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    String fileName = fileStorageService.storeFile(file);

    // Return the path to the stored file
    return ResponseEntity.ok().body(fileName);
  }
}
