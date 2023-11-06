package com.example.workingbeesapp.services;

import com.example.workingbeesapp.models.FileDocument;
import com.example.workingbeesapp.repositories.DocFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileStorageService {

    private Path fileStoragePath;
    private final String fileStorageLocation;

    private final DocFileRepository docFileRepository;

    public FileStorageService(@Value("${my.upload_location}") String fileStorageLocation, DocFileRepository docFileRepository) {
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        this.fileStorageLocation = fileStorageLocation;
        this.docFileRepository = docFileRepository;

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("File directory cannot be created.");
        }

    }

    public String storeFile(MultipartFile file, String url, Long id) {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Path filePath = Paths.get(fileStoragePath + File.separator + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File cannot be stored", e);
        }

        docFileRepository.save(new FileDocument(id, file.getContentType(), url, fileName));

        return fileName;
    }

    public Resource downLoadFile(String fileName) {

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("File is unreadable.", e);
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("This file doesn't exist or is unreadable.");
        }
    }

    public List<String> downLoad() {
        var list = new ArrayList<String>();
        File folder = new File(fileStorageLocation);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile()) {
                String name = listOfFiles[i].getName();
                list.add(name);
            }
        }
        return list;
    }
}
