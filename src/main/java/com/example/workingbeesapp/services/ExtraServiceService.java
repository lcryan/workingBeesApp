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
    private final ExtraServiceRepository extraServiceRepository;

    public ExtraServiceService(ExtraServiceRepository extraServiceRepository) {
        this.extraServiceRepository = extraServiceRepository;
    }

    //--- get all extra services ---//
    public List<ExtraServiceDto> getAllExtraServices() {
        List<ExtraService> extraServices = extraServiceRepository.findAll();
        List<ExtraServiceDto> extraServiceDtoList = new ArrayList<>();
        for (ExtraService extraService : extraServices) {
            ExtraServiceDto extraServiceDto = transferExtraServiceToExtraServiceDto(extraService);
            extraServiceDtoList.add(extraServiceDto);
        }
        return extraServiceDtoList;
    }

    //--- get extra service ---//
    public ExtraServiceDto getOneExtraService(Long id) {
        Optional<ExtraService> optionalExtraService = extraServiceRepository.findById(id);
        if (optionalExtraService.isPresent()) {
            ExtraServiceDto extraService = transferExtraServiceToExtraServiceDto(optionalExtraService.get());
            return extraService;
        } else {
            throw new RecordNotFoundException("Item of type Extra-Service with id: " + id + " could not be found.");
        }
    }

    //--- create extra service ---//
    public ExtraServiceDto createExtraService(ExtraServiceDto extraServiceDto) {
        ExtraService newExtraService = transferExtraServiceDtoToExtraService(extraServiceDto);
        extraServiceRepository.save(newExtraService);
        return transferExtraServiceToExtraServiceDto(newExtraService);
    }

    //--- update extra service ---//
    public ExtraServiceDto updateExtraService(Long id, ExtraServiceDto extraServiceDto) {

        Optional<ExtraService> optionalExtraService = extraServiceRepository.findById(id);

        if (optionalExtraService.isPresent()) {
            ExtraService extraService = optionalExtraService.get();

            ExtraService updatedExtraService = transferExtraServiceDtoToExtraService(extraServiceDto);
            updatedExtraService.setId(extraService.getId());
            extraServiceRepository.save(updatedExtraService);

            return transferExtraServiceToExtraServiceDto(updatedExtraService);
        } else {
            throw new RecordNotFoundException("Item of type Company with id: " + id + " could not be found.");
        }
    }

    //--- delete extra service ---//
    public void deleteExtraService(Long id) {
        if (extraServiceRepository.existsById(id)) {
            Optional<ExtraService> optionalExtraService = extraServiceRepository.findById(id);
            ExtraService obsoleteExtraService = optionalExtraService.get();

            extraServiceRepository.delete(obsoleteExtraService);
        } else {
            throw new RecordNotFoundException("Item of type Extra-Service with id: " + id + " could not be found.");
        }
    }

    //--- transfer extra service to extra service dto ---//
    public ExtraServiceDto transferExtraServiceToExtraServiceDto(ExtraService extraService) {

        ExtraServiceDto extraServiceDto = new ExtraServiceDto();

        extraServiceDto.setId(extraService.getId());
        extraServiceDto.setExtraService(extraService.getExtraService());
        extraServiceDto.setCompanyName(extraService.getCompanyName());
        extraServiceDto.setServiceType(extraService.getServiceType());
        extraServiceDto.setServiceDuration(extraService.getServiceDuration());
        extraServiceDto.setServicePrice(extraService.getServicePrice());

        return extraServiceDto;
    }

    //--- transfer extra service dto to extra service ---//
    public ExtraService transferExtraServiceDtoToExtraService(ExtraServiceDto extraServiceDto) {

        ExtraService extraService = new ExtraService();

        extraService.setId(extraServiceDto.getId());
        extraService.setExtraService(extraServiceDto.getExtraService());
        extraService.setCompanyName(extraServiceDto.getCompanyName());
        extraService.setServiceType(extraServiceDto.getServiceType());
        extraService.setServiceDuration(extraServiceDto.getServiceDuration());
        extraService.setServicePrice(extraServiceDto.getServicePrice());

        return extraService;
    }

    //--- transfer extra service list to extra service list dto ---//
    public List<ExtraServiceDto> transferExtraServiceListToExtraServiceListDto(List<ExtraService> extraServiceList) {
        List<ExtraServiceDto> extraServiceDtoList = new ArrayList<>();
        for (ExtraService extraServices : extraServiceList) {
            extraServiceDtoList.add(transferExtraServiceToExtraServiceDto(extraServices));
        }
        return extraServiceDtoList;
    }

    //--- transfer extra service dto list to extra service list ---//
    public List<ExtraService> transferExtraServiceDtoListToExtraServiceList(List<ExtraServiceDto> extraServiceDtoList) {
        List<ExtraService> extraServiceList1 = new ArrayList<>();
        for (ExtraServiceDto extraServiceDtos : extraServiceDtoList) {
            extraServiceList1.add(transferExtraServiceDtoToExtraService(extraServiceDtos));
        }
        return extraServiceList1;
    }
}
