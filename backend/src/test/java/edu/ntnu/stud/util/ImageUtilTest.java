package edu.ntnu.stud.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import javax.sql.rowset.serial.SerialBlob;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class ImageUtilTest {

    @Test
    void testConvertBlobToBase64() throws SQLException {
        String originalString = "Test String";
        byte[] bytes = originalString.getBytes();
        Blob blob = new SerialBlob(bytes);

        String base64String = ImageUtil.convertBlobToBase64(blob);

        assertEquals(Base64.getEncoder().encodeToString(bytes), base64String);
    }

    @Test
    void testConvertMultipartFileToBlob() throws IOException, SQLException {
        String fileName = "testFile.txt";
        String content = "This is a test file.";
        byte[] contentBytes = content.getBytes();
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "text/plain", contentBytes);

        Blob blob = ImageUtil.convertMultipartFileToBlob(multipartFile);

        assertNotNull(blob);
        assertEquals(contentBytes.length, blob.length());
        assertArrayEquals(contentBytes, blob.getBytes(1, (int) blob.length()));
    }

  @Test
  void testImageIntegrityWithFile() throws IOException, SQLException {
      ClassLoader classLoader = getClass().getClassLoader();
      byte[] originalBytes = classLoader.getResourceAsStream("testImage.jpg").readAllBytes();
      MultipartFile multipartFile = new MockMultipartFile("testImage.jpg", "testImage.jpg", "image/png", originalBytes);

      Blob blob = ImageUtil.convertMultipartFileToBlob(multipartFile);
      String base64String = ImageUtil.convertBlobToBase64(blob);
      byte[] processedBytes = Base64.getDecoder().decode(base64String);

      assertArrayEquals(originalBytes, processedBytes, "The image bytes should remain the same before and after processing.");
  }
}