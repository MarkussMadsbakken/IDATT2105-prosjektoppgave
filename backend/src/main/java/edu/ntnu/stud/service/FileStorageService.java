package edu.ntnu.stud.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service class for handling file storage operations.
 */
@Service
public class FileStorageService {
  private final Path fileStorageLocation;

  /**
   * Constructor for FileStorageService.
   * Initializes the file storage location based on the provided upload directory.
   */
  public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
    this.fileStorageLocation = Paths.get(uploadDir)
        .toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.fileStorageLocation);
    } catch (Exception ex) {
      throw new RuntimeException(
        "Could not create the directory where the uploaded files will be stored.", ex);
    }
  }

  /**
   * Stores the uploaded file in the specified location.
   *
   * @param file The file to be stored.
   * @return The name of the stored file.
   */
  public String storeFile(MultipartFile file) {
    // Normalize file name
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());

    try {
      // Check if the file's name contains invalid characters
      if (fileName.contains("..")) {
        throw new RuntimeException("Filename contains invalid path sequence " + fileName);
      }

      // Generate a unique filename to prevent duplicates
      String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

      // Copy file to the target location
      Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return uniqueFileName;
    } catch (IOException ex) {
      throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
    }
  }
}