package com.mergeco.oiljang.product;

import com.mergeco.oiljang.product.controller.FileUploadController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FileUploadControllerTest {

    @InjectMocks
    private FileUploadController fileUploadController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFileWithValidFile() throws IOException {

        MockMultipartFile fakeFile = new MockMultipartFile("userUploadedFile", "test-file.txt", "text/plain", "Hello, HanSung~!".getBytes());

        ResponseEntity<String> responseEntity = fileUploadController.uploadFile(fakeFile);

        //응답 확인
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody(), "File uploaded successfully!");
    }

    @Test
    public void testUploadFileWithEmptyFile() throws IOException {
        MockMultipartFile emptyFile = new MockMultipartFile("userUploadedFile", new byte[0]);
        ResponseEntity<String> responseEntity = fileUploadController.uploadFile(emptyFile);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody(), "No file uploaded.");
    }

}
