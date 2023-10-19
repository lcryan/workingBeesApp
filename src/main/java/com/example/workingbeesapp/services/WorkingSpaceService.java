package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.WorkingSpaceDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.WorkingSpace;
import com.example.workingbeesapp.repositories.WorkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingSpaceService {

    private final WorkingSpaceRepository workingSpaceRepository;

    public WorkingSpaceService(WorkingSpaceRepository workingSpaceRepository) {
        this.workingSpaceRepository = workingSpaceRepository;
    }

    public List<WorkingSpaceDto> getAllWorkingSpaces() {
        List<WorkingSpace> workingSpaces = workingSpaceRepository.findAll();
        List<WorkingSpaceDto> workingSpaceDtoList = new ArrayList<>();
        for (WorkingSpace workingSpace : workingSpaces) {
            WorkingSpaceDto workingSpaceDto = transferWorkingSpaceToWorkingSpaceDto(workingSpace);
            workingSpaceDtoList.add(workingSpaceDto);
        }
        return workingSpaceDtoList;
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


    // ******* TRANSFER HELPER METHODS HERE!!!  ******* //

    private WorkingSpaceDto transferWorkingSpaceToWorkingSpaceDto(WorkingSpace workingSpace) {

        WorkingSpaceDto workingSpaceDto = new WorkingSpaceDto();

        workingSpaceDto.setId(workingSpace.getId());
        workingSpaceDto.setName(workingSpace.getName());
        workingSpaceDto.setType(workingSpace.getType());
        workingSpaceDto.setSize(workingSpace.getSize());

        return workingSpaceDto;
    }

    private WorkingSpace transferWorkingSpaceDtoToWorkingSpace(WorkingSpaceDto workingSpaceDto) {

        WorkingSpace workingSpace = new WorkingSpace();

        workingSpace.setId(workingSpaceDto.getId());
        workingSpace.setName(workingSpaceDto.getName());
        workingSpace.setType(workingSpaceDto.getType());
        workingSpace.setCapacity(workingSpaceDto.getCapacity());

        return workingSpace;
    }
}

