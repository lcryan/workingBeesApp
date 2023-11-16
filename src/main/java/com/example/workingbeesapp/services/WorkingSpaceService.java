package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.WorkingSpaceDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.FileDocument;
import com.example.workingbeesapp.models.WorkingSpace;
import com.example.workingbeesapp.repositories.DocFileRepository;
import com.example.workingbeesapp.repositories.SubscriptionRepository;
import com.example.workingbeesapp.repositories.WorkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingSpaceService {
    private final WorkingSpaceRepository workingSpaceRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final DocFileRepository docFileRepository;

    public WorkingSpaceService(WorkingSpaceRepository workingSpaceRepository, SubscriptionRepository subscriptionRepository, DocFileRepository docFileRepository) {
        this.workingSpaceRepository = workingSpaceRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.docFileRepository = docFileRepository;
    }

    //--- get all working spaces ---//
    public List<WorkingSpaceDto> getAllWorkingSpaces() {
        List<WorkingSpace> workingSpaces = workingSpaceRepository.findAll();
        List<WorkingSpaceDto> workingSpaceDtoList = new ArrayList<>();
        for (WorkingSpace workingSpace : workingSpaces) {
            WorkingSpaceDto workingSpaceDto = transferWorkingSpaceToWorkingSpaceDto(workingSpace);
            workingSpaceDtoList.add(workingSpaceDto);
        }
        return workingSpaceDtoList;
    }

    //--- get all working spaces by company name ---//
    public List<WorkingSpaceDto> getWorkingSpacesByCompanyName(String companyName) {
        List<WorkingSpace> workingSpaceList = workingSpaceRepository.findAllByCompanyNameEqualsIgnoreCase(companyName);
        return transferWorkingSpaceListToWorkingSpaceDtoList(workingSpaceList);
    }

    //--- get one working space by id ---//
    public WorkingSpaceDto getOneWorkingSpace(Long id) {
        Optional<WorkingSpace> optionalWorkingSpace = workingSpaceRepository.findById(id);
        if (optionalWorkingSpace.isPresent()) {
            WorkingSpaceDto workingSpace = transferWorkingSpaceToWorkingSpaceDto(optionalWorkingSpace.get());
            return workingSpace;
        } else {
            throw new RecordNotFoundException("Item of type WorkingSpace with id: " + id + " could not be found.");
        }
    }

    //--- create working space ---//
    public WorkingSpaceDto createWorkingSpace(WorkingSpaceDto workingSpaceDto) {
        WorkingSpace newWorkingSpace = transferWorkingSpaceDtoToWorkingSpace(workingSpaceDto);
        workingSpaceRepository.save(newWorkingSpace);
        return transferWorkingSpaceToWorkingSpaceDto(newWorkingSpace);
    }

    //--- update working space ---//
    public WorkingSpaceDto updateWorkingSpace(Long id, WorkingSpaceDto workingSpaceDto) {

        Optional<WorkingSpace> optionalWorkingSpace = workingSpaceRepository.findById(id);

        if (optionalWorkingSpace.isPresent()) {

            WorkingSpace workingSpace = optionalWorkingSpace.get();
            WorkingSpace updatedWorkingSpace = transferWorkingSpaceDtoToWorkingSpace(workingSpaceDto);
            updatedWorkingSpace.setId(workingSpace.getId());
            workingSpaceRepository.save(updatedWorkingSpace);

            return transferWorkingSpaceToWorkingSpaceDto(updatedWorkingSpace);
        } else {
            throw new RecordNotFoundException("Item of type WorkingSpace with id: " + id + " could not be found.");
        }

    }

    //--- delete working space ---//
    public void deleteWorkingSpace(Long id) {
        if (workingSpaceRepository.existsById(id)) {
            Optional<WorkingSpace> optionalWorkingSpace = workingSpaceRepository.findById(id);
            WorkingSpace obsoleteWorkingSpace = optionalWorkingSpace.get();

            workingSpaceRepository.delete(obsoleteWorkingSpace);
        } else {
            throw new RecordNotFoundException("Item of type WorkingSpace with id: " + id + " could not be found.");
        }
    }

    //--- assign subscription to working space ---//
    public void assignSubscriptionToWorkingSpace(Long id, Long subscriptionId) {
        var optionalWorkingSpace = workingSpaceRepository.findById(id);
        var optionalSubscription = subscriptionRepository.findById(subscriptionId);

        if (optionalWorkingSpace.isPresent() && optionalSubscription.isPresent()) {
            var workingSpace = optionalWorkingSpace.get();
            var subscription = optionalSubscription.get();

            workingSpace.setSubscription(subscription);
            workingSpaceRepository.save(workingSpace);
        } else {
            throw new RecordNotFoundException("Item not found.");
        }
    }

    //--- transfer helper method for working space to working space dto ---//
    public WorkingSpaceDto transferWorkingSpaceToWorkingSpaceDto(WorkingSpace workingSpace) {

        WorkingSpaceDto workingSpaceDto = new WorkingSpaceDto();

        workingSpaceDto.setId(workingSpace.getId());
        workingSpaceDto.setWorkingSpace(workingSpace.getWorkingSpace());
        workingSpaceDto.setType(workingSpace.getType());
        workingSpaceDto.setCapacity(workingSpace.getCapacity());
        workingSpaceDto.setDuration(workingSpace.getDuration());
        workingSpaceDto.setStartDate(workingSpace.getStartDate());
        workingSpaceDto.setEndDate(workingSpace.getEndDate());
        workingSpaceDto.setRentalPrice(workingSpace.getRentalPrice());
        workingSpaceDto.setCompanyName(workingSpace.getCompanyName());
        if (workingSpace.getFile() != null) {
            workingSpaceDto.setFile(workingSpace.getFile());
        }

        return workingSpaceDto;
    }

    //--- transfer helper method for working space dto to working space ---//
    public WorkingSpace transferWorkingSpaceDtoToWorkingSpace(WorkingSpaceDto workingSpaceDto) {

        WorkingSpace workingSpace = new WorkingSpace();

        workingSpace.setId(workingSpaceDto.getId());
        workingSpace.setWorkingSpace(workingSpaceDto.getWorkingSpace());
        workingSpace.setType(workingSpaceDto.getType());
        workingSpace.setCapacity(workingSpaceDto.getCapacity());
        workingSpace.setDuration(workingSpaceDto.getDuration());
        workingSpace.setStartDate(workingSpaceDto.getStartDate());
        workingSpace.setEndDate(workingSpaceDto.getEndDate());
        workingSpace.setRentalPrice(workingSpaceDto.getRentalPrice());
        workingSpace.setCompanyName(workingSpaceDto.getCompanyName());

        workingSpace.setFile(workingSpaceDto.getFile());

        return workingSpace;
    }

    //---transfer helper method from working space list to working space dto list ---//
    public List<WorkingSpaceDto> transferWorkingSpaceListToWorkingSpaceDtoList(List<WorkingSpace> workingSpaceList) {
        List<WorkingSpaceDto> workingSpaceDtoList = new ArrayList<>();
        for (WorkingSpace workingSpaces : workingSpaceList) {
            workingSpaceDtoList.add(transferWorkingSpaceToWorkingSpaceDto(workingSpaces));
        }
        return workingSpaceDtoList;
    }

    //--- assign image to working space ---//
    public void assignImageToWorkingSpace(String fileName, Long id) {
        Optional<WorkingSpace> optionalWorkingSpace = workingSpaceRepository.findById(id);
        Optional<FileDocument> fileUploadResponse = Optional.ofNullable(docFileRepository.findByFileName(fileName));
        if (optionalWorkingSpace.isPresent() && fileUploadResponse.isPresent()) {
            FileDocument image = fileUploadResponse.get();
            WorkingSpace workingSpace = optionalWorkingSpace.get();
            workingSpace.setFile(image);
            workingSpaceRepository.save(workingSpace);
        }
    }

    //--- delete image from working space ---//
    //TODO: delete image ? Do I really want to do this ?//
    public void deleteImageFromWorkingSpace(Long id) {
        Optional<WorkingSpace> optionalWorkingSpace = workingSpaceRepository.findById(id);
        if (optionalWorkingSpace.isPresent()) {
            WorkingSpace workingSpace = optionalWorkingSpace.get();
            workingSpace.setFile(null);
            workingSpaceRepository.save(workingSpace);
        }
    }
}
