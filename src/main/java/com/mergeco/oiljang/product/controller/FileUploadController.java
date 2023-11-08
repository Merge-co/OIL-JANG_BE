package com.mergeco.oiljang.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    public ResponseEntity<String> uploadFile(@RequestParam("userUploadedFile")MultipartFile userUploadedFile) {
        if (userUploadedFile.isEmpty()) {
            return new ResponseEntity<>("No file uploaded.", HttpStatus.BAD_REQUEST);
        }

        try {
            // 업로드된 파일 처리 로직
            String fileName = userUploadedFile.getOriginalFilename();
            byte[] bytes = userUploadedFile.getBytes();

            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
