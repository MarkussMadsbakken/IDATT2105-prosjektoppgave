package edu.ntnu.stud.util;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.web.multipart.MultipartFile;


/**
 * Utility class for image-related operations.
 */
public class ImageUtil {

  /**
   * Converts a Blob to a Base64 encoded string.
   *
   * @param blob the Blob to convert
   * @return the Base64 encoded string
   * @throws SQLException if there is an error accessing the Blob
   */
  public static String convertBlobToBase64(Blob blob) throws SQLException {
    byte[] bytes = blob.getBytes(1, (int) blob.length());
    return Base64.getEncoder().encodeToString(bytes);
  }

  /**
   * Converts a MultipartFile object to a Blob.
   *
   * @param file the MultipartFile object to convert
   * @return the converted Blob object
   * @throws IOException if an I/O error occurs
   * @throws SQLException if a database access error occurs
   */
  public static Blob convertMultipartFileToBlob(MultipartFile file)
      throws IOException, SQLException {
    byte[] fileBytes = file.getBytes();
    return new SerialBlob(fileBytes);
  }
}