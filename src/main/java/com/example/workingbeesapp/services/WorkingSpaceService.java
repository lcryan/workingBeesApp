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

    // GET LIST OF WORKING SPACES //
    public List<WorkingSpaceDto> getAllWorkingSpaces() {
        List<WorkingSpace> workingSpaces = workingSpaceRepository.findAll();
        List<WorkingSpaceDto> workingSpaceDtoList = new ArrayList<>();
        for (WorkingSpace workingSpace : workingSpaces) {
            WorkingSpaceDto workingSpaceDto = transferWorkingSpaceToWorkingSpaceDto(workingSpace);
            workingSpaceDtoList.add(workingSpaceDto);
        }
        return workingSpaceDtoList;
    }

    // GET LIST OF WORKING SPACES BY COMPANY NAME //

    public List<WorkingSpaceDto> getWorkingSpacesByCompanyName(String companyName) {
        List<WorkingSpace> workingSpaceList = workingSpaceRepository.findAllByCompanyNameEqualsIgnoreCase(companyName);
        return transferWorkingSpaceListToWorkingSpaceDtoList(workingSpaceList);
    }

    // FUNCTION FOR GET ONE COMPANY //

    public WorkingSpaceDto getOneWorkingSpace(Long id) {
        Optional<WorkingSpace> optionalWorkingSpace = workingSpaceRepository.findById(id);
        if (optionalWorkingSpace.isPresent()) {
            WorkingSpaceDto workingSpace = transferWorkingSpaceToWorkingSpaceDto(optionalWorkingSpace.get());
            return workingSpace;
        } else {
            throw new RecordNotFoundException("Item of type WorkingSpace with id: " + id + " could not be found.");
        }
    }

    // FUNCTION FOR CREATING ONE COMPANY //

    public WorkingSpaceDto createWorkingSpace(WorkingSpaceDto workingSpaceDto) {
        WorkingSpace newWorkingSpace = transferWorkingSpaceDtoToWorkingSpace(workingSpaceDto);
        workingSpaceRepository.save(newWorkingSpace);
        return transferWorkingSpaceToWorkingSpaceDto(newWorkingSpace);
    }

    // TODO: has to be corrected! //
    // FUNCTION TO UPDATE COMPANY //
    public WorkingSpaceDto updateWorkingSpace(Long id, WorkingSpaceDto workingSpaceDto) {
        if (workingSpaceRepository.findById(id).isPresent()) {

            WorkingSpace workingSpace = workingSpaceRepository.findById(id).get();

            WorkingSpace workingSpace1 = transferWorkingSpaceDtoToWorkingSpace(workingSpaceDto);

            workingSpace1.setId(workingSpace.getId());

            workingSpaceRepository.save(workingSpace1);

            return transferWorkingSpaceToWorkingSpaceDto(workingSpace1);
        } else {
            throw new RecordNotFoundException("Item of type WorkingSpace with id: " + id + " could not be found.");
        }
    }

    // FUNCTION TO DELETE COMPANY //
    public void deleteWorkingSpace(Long id) {
        if (workingSpaceRepository.existsById(id)) {
            Optional<WorkingSpace> optionalWorkingSpace = workingSpaceRepository.findById(id);
            WorkingSpace obsoleteWorkingSpace = optionalWorkingSpace.get();

            workingSpaceRepository.delete(obsoleteWorkingSpace);
        } else {
            throw new RecordNotFoundException("Item of type WorkingSpace with id: " + id + " could not be found.");
        }
    }

    // ASSIGN SUBSCRIPTION TO WORKING SPACE //

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

    // ******* TRANSFER HELPER METHODS HERE!!!  ******* //

    public WorkingSpaceDto transferWorkingSpaceToWorkingSpaceDto(WorkingSpace workingSpace) {

        WorkingSpaceDto workingSpaceDto = new WorkingSpaceDto();

        workingSpaceDto.setId(workingSpace.getId());
        workingSpaceDto.setName(workingSpace.getName());
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

    public WorkingSpace transferWorkingSpaceDtoToWorkingSpace(WorkingSpaceDto workingSpaceDto) {

        WorkingSpace workingSpace = new WorkingSpace();

        workingSpace.setId(workingSpaceDto.getId());
        workingSpace.setName(workingSpaceDto.getName());
        workingSpace.setType(workingSpaceDto.getType());
        workingSpace.setCapacity(workingSpaceDto.getCapacity());
        workingSpace.setDuration(workingSpaceDto.getDuration());
        workingSpace.setStartDate(workingSpaceDto.getStartDate());
        workingSpace.setEndDate(workingSpace.getEndDate());
        workingSpace.setRentalPrice(workingSpace.getRentalPrice());
        workingSpace.setCompanyName(workingSpace.getCompanyName());

        workingSpace.setFile(workingSpaceDto.getFile());

        return workingSpace;
    }

    // TRANSFER WORKING SPACE LIST TO WORKING SPACE DTO LIST //
    public List<WorkingSpaceDto> transferWorkingSpaceListToWorkingSpaceDtoList(List<WorkingSpace> workingSpaceList) {
        List<WorkingSpaceDto> workingSpaceDtoList = new ArrayList<>();
        for (WorkingSpace workingSpaces : workingSpaceList) {
            workingSpaceDtoList.add(transferWorkingSpaceToWorkingSpaceDto(workingSpaces));
        }
        return workingSpaceDtoList;
    }

    // ASSIGN image to WorkingSpace //
// has to be reviewed! //
    public void assignImageToWorkingSpace(String fileName, Long id) {
        Optional<WorkingSpace> optionalWorkingSpace = workingSpaceRepository.findById(id);
        Optional<FileDocument> fileUploadResponse = Optional.ofNullable(docFileRepository.findByFileName(fileName));
// 1. TODO: check, if this ofNullable will work out accordingly //
        if (optionalWorkingSpace.isPresent() && fileUploadResponse.isPresent()) {
            FileDocument image = fileUploadResponse.get();
            WorkingSpace workingSpace = optionalWorkingSpace.get();
            workingSpace.setFile(image);
            workingSpaceRepository.save(workingSpace);
        }
// 2. TODO: maybe also add and addImageToWorkingSpace method here //
    }
}

