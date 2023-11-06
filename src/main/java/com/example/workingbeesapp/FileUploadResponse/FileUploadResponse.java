package com.example.workingbeesapp.FileUploadResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

public class FileUploadResponse {

    String fileName;
    String contentType;
    String url;
}
