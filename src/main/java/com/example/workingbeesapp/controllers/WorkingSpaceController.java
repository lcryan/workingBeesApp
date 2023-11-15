package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.WorkingSpaceDto;
import com.example.workingbeesapp.services.FileStorageService;
import com.example.workingbeesapp.services.WorkingSpaceService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/workingspaces")
public class WorkingSpaceController {
    private final WorkingSpaceService workingSpaceService;

    private final FileStorageService fileStorageService;

    public WorkingSpaceController(WorkingSpaceService workingSpaceService, FileStorageService fileStorageService) {
        this.workingSpaceService = workingSpaceService;
        this.fileStorageService = fileStorageService;
    }

    // GET LIST OF WORKING SPACES ALPHABETICALLY SORTED BY COMPANY NAME, IF COMPANY NAME APPLICABLE //
    @GetMapping("")
    @Transactional // this is needed to be able to link an image to the WorkingSpace //
    public ResponseEntity<List<WorkingSpaceDto>> getWorkingSpacesByCompanyName(@RequestParam(value = "companyName", required = false) Optional<String> companyName) {
        List<WorkingSpaceDto> workingSpaceDtos;
        if (companyName.isEmpty()) {
            workingSpaceDtos = workingSpaceService.getAllWorkingSpaces();
        } else {
            workingSpaceDtos = workingSpaceService.getWorkingSpacesByCompanyName(companyName.get());
        }

        workingSpaceDtos.sort(Comparator.comparing(WorkingSpaceDto::getCompanyName));
        return ResponseEntity.ok().body(workingSpaceDtos);
    }

    // GET ONE TEAM //
    @GetMapping("/{id}")
    @Transactional // see comment above //
    public ResponseEntity<WorkingSpaceDto> getWorkingSpace(@PathVariable Long id) {
        WorkingSpaceDto workingSpaceDto = workingSpaceService.getOneWorkingSpace(id);
        return ResponseEntity.ok(workingSpaceDto);
    }


    // CREATE TEAM --- CHECKED//
    @PostMapping("")
    public ResponseEntity<Object> createNewWorkingSpace(@Validated @RequestBody WorkingSpaceDto workingSpaceDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            WorkingSpaceDto workingSpaceDto1 = workingSpaceService.createWorkingSpace(workingSpaceDto);
            return ResponseEntity.created(null).body(workingSpaceDto1);
        }
    }

    // UPDATE WORKING SPACE//
    @PutMapping("/{id}")
    public ResponseEntity<WorkingSpaceDto> updateWorkingSpace(@PathVariable Long id, @Validated @RequestBody WorkingSpaceDto newTeam) {
        WorkingSpaceDto workingSpaceDto1 = workingSpaceService.updateWorkingSpace(id, newTeam);
        return ResponseEntity.ok().body(workingSpaceDto1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWorkingSpace(@PathVariable Long id) {
        workingSpaceService.deleteWorkingSpace(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/{subscriptionId}")
    public ResponseEntity<Object> assignSubscriptionToWorkingSpace(@PathVariable("id") Long id, @PathVariable("subscriptionId") Long subscriptionId) {
        workingSpaceService.assignSubscriptionToWorkingSpace(id, subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Object> assignImageToWorkingSpace(@PathVariable("id") Long id, @RequestBody MultipartFile multipartFile) {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects.requireNonNull(multipartFile.getOriginalFilename())).toUriString();
        String image = fileStorageService.storeFile(multipartFile, url, id);
        workingSpaceService.assignImageToWorkingSpace(image, id);
        return ResponseEntity.noContent().build();
    }
}
