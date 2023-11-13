package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.FileUploadResponse.FileUploadResponse;
import com.example.workingbeesapp.services.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    //    post for single upload //
    @PostMapping("single/upload")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file, Long id) {

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(String.valueOf(file)).toUriString();

        String contentType = file.getContentType();
        String fileName = fileStorageService.storeFile(file, url, id);

        return new FileUploadResponse(fileName, contentType, url);
    }

    //    get for single download //
    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileStorageService.downLoadFile(fileName);

        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }


        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    //    get all names in directory //
    @GetMapping("/download/allNames")
    List<String> downLoadMultipleFileNames() {

        return fileStorageService.downLoad();
    }
}

