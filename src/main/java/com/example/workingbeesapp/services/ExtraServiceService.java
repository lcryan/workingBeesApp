package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.ExtraServiceDto;
import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.ExtraService;
import com.example.workingbeesapp.repositories.ExtraServiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExtraServiceService {
    ;
    private final ExtraServiceRepository extraServiceRepository;


    public ExtraServiceService(ExtraServiceRepository extraServiceRepository) {
        this.extraServiceRepository = extraServiceRepository;
    }

    // FUNCTION FOR GETTING EXTRA-SERVICE LIST --- functional //
    public List<ExtraServiceDto> getAllExtraServices() {
        List<ExtraService> extraServices = extraServiceRepository.findAll();
        List<ExtraServiceDto> extraServiceDtoList = new ArrayList<>();
        for (ExtraService extraService : extraServices) {
            ExtraServiceDto extraServiceDto = transferExtraServiceToExtraServiceDto(extraService);
            extraServiceDtoList.add(extraServiceDto);
        }
        return extraServiceDtoList;
    }


    // FUNCTION FOR GET ONE EXTRA-SERVICE //

    public ExtraServiceDto getOneExtraService(Long id) {
        Optional<ExtraService> optionalExtraService = extraServiceRepository.findById(id);
        if (optionalExtraService.isPresent()) {
            ExtraServiceDto extraService = transferExtraServiceToExtraServiceDto(optionalExtraService.get());
            return extraService;
        } else {
            throw new RecordNotFoundException("Item of type Extra-Service with id: " + id + " could not be found.");
        }
    }


    // FUNCTION FOR CREATING ONE EXTRA-SERVICE //

    public ExtraServiceDto createExtraService(ExtraServiceDto extraServiceDto) {
        ExtraService newExtraService = transferExtraServiceDtoToExtraService(extraServiceDto);
        extraServiceRepository.save(newExtraService);
        return transferExtraServiceToExtraServiceDto(newExtraService);
    }

    // FUNCTION TO UPDATE EXTRA-SERVICE //
    public ExtraServiceDto updateExtraService(Long id, ExtraServiceDto extraServiceDto) {
        if (extraServiceRepository.findById(id).isPresent()) {

            ExtraService extraService = extraServiceRepository.findById(id).get();

            ExtraService extraService1 = transferExtraServiceDtoToExtraService(extraServiceDto);

            extraService1.setId(extraService.getId());

            extraServiceRepository.save(extraService1);

            return transferExtraServiceToExtraServiceDto(extraService1);
        } else {
            throw new RecordNotFoundException("Item of type Extra-Service with id: " + id + " could not be found.");
        }
    }

    // FUNCTION TO DELETE EXTRA-SERVICE //
    public void deleteExtraService(Long id) {
        if (extraServiceRepository.existsById(id)) {
            Optional<ExtraService> optionalExtraService = extraServiceRepository.findById(id);
            ExtraService obsoleteExtraService = optionalExtraService.get();

            extraServiceRepository.delete(obsoleteExtraService);
        } else {
            throw new RecordNotFoundException("Item of type Extra-Service with id: " + id + " could not be found.");
        }
    }


    // ******* TRANSFER HELPER METHODS HERE!!!  ******* //

    public ExtraServiceDto transferExtraServiceToExtraServiceDto(ExtraService extraService) {

        ExtraServiceDto extraServiceDto = new ExtraServiceDto();

        extraServiceDto.setId(extraService.getId());
        extraServiceDto.setServiceName(extraService.getServiceName());
        extraServiceDto.setServiceType(extraService.getServiceType());
        extraServiceDto.setServiceDuration(extraService.getServiceDuration());
        extraServiceDto.setServicePrice(extraService.getServicePrice());


        return extraServiceDto;
    }

    public ExtraService transferExtraServiceDtoToExtraService(ExtraServiceDto extraServiceDto) {

        ExtraService extraService = new ExtraService();

        extraService.setId(extraServiceDto.getId());
        extraService.setServiceName(extraServiceDto.getServiceName());
        extraService.setServiceType(extraServiceDto.getServiceType());
        extraService.setServiceDuration(extraServiceDto.getServiceDuration());
        extraService.setServicePrice(extraServiceDto.getServicePrice());

        return extraService;
    }

    // TRANSFER METHOD FOR LIST EXTRA SERVICE //
    public List<ExtraServiceDto> transferExtraServiceListToExtraServiceListDto(List<ExtraService> extraServiceList) {
        List<ExtraServiceDto> extraServiceDtoList = new ArrayList<>();
        for (ExtraService extraServices : extraServiceList) {
            extraServiceDtoList.add(transferExtraServiceToExtraServiceDto(extraServices));
        }
        return extraServiceDtoList;
    }

    public List<ExtraService> transferExtraServiceDtoListToExtraServiceList(List<ExtraServiceDto> extraServiceDtoList) {
        List<ExtraService> extraServiceList1 = new ArrayList<>();
        for (ExtraServiceDto extraServiceDtos : extraServiceDtoList) {
            extraServiceList1.add(transferExtraServiceDtoToExtraService(extraServiceDtos));
        }
        return extraServiceList1;
    }

}
