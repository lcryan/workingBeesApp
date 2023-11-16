package com.example.workingbeesapp.services;

import com.example.workingbeesapp.dtos.ExtraServiceDto;

import com.example.workingbeesapp.exceptions.RecordNotFoundException;
import com.example.workingbeesapp.models.ExtraService;
import com.example.workingbeesapp.repositories.ExtraServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExtraServiceServiceTest {
    @Mock
    ExtraServiceRepository extraServiceRepository;

    @InjectMocks
    ExtraServiceService extraServiceService;

    @Test
    void getAllExtraServices() {

        ExtraService extraServiceOne = new ExtraService();
        extraServiceOne.setId(1L);
        extraServiceOne.setExtraService("Breakfast Team Brunch");
        extraServiceOne.setCompanyName("Pixie Dust Inc.");
        extraServiceOne.setServiceType("breakfast");
        extraServiceOne.setServicePrice(1000);
        extraServiceOne.setServiceDuration("7 days");

        ExtraService extraServiceTwo = new ExtraService();
        extraServiceTwo.setId(2L);
        extraServiceTwo.setExtraService("Lunch Team Brunch");
        extraServiceTwo.setCompanyName("Pixie Dust Inc.");
        extraServiceTwo.setServiceType("lunch");
        extraServiceTwo.setServicePrice(1000);
        extraServiceTwo.setServiceDuration("7 days");

        List<ExtraService> testExtraServices = new ArrayList<>();
        testExtraServices.add(extraServiceOne);
        testExtraServices.add(extraServiceTwo);

        ExtraServiceRepository extraServiceRepositoryFake = mock(ExtraServiceRepository.class);
        when(extraServiceRepositoryFake.findAll()).thenReturn(testExtraServices);

        ExtraServiceService extraServiceService = new ExtraServiceService(extraServiceRepositoryFake);

        // ACT//
        List<ExtraServiceDto> result = extraServiceService.getAllExtraServices();


        // ASSERT //
        assertEquals(testExtraServices.size(), result.size());

        // verify //

        verify(extraServiceRepositoryFake, times(1)).findAll();

        assertEquals(extraServiceOne.getId(), result.get(0).getId());
        assertEquals(extraServiceOne.getExtraService(), result.get(0).getExtraService());
        assertEquals(extraServiceOne.getCompanyName(), result.get(0).getCompanyName());
        assertEquals(extraServiceOne.getServiceType(), result.get(0).getServiceType());
        assertEquals(extraServiceOne.getServicePrice(), result.get(0).getServicePrice());
        assertEquals(extraServiceOne.getServiceDuration(), result.get(0).getServiceDuration());
    }

    @Test
    void getOneExtraService() {

        Long id = 1L;
        ExtraServiceDto extraServiceDto = new ExtraServiceDto();
        extraServiceDto.setId(id);
        extraServiceDto.setExtraService("Breakfast Team Brunch");
        extraServiceDto.setCompanyName("Pixie Dust Inc.");
        extraServiceDto.setServiceType("breakfast");
        extraServiceDto.setServicePrice(1000);
        extraServiceDto.setServiceDuration("7 days");

        when(extraServiceRepository.findById(id)).thenReturn(Optional.of(extraServiceService.transferExtraServiceDtoToExtraService(extraServiceDto)));
        ExtraServiceDto result = extraServiceService.getOneExtraService(id);

        assertEquals(extraServiceDto.getId(), result.getId());
        assertEquals(extraServiceDto.getExtraService(), result.getExtraService());
        assertEquals(extraServiceDto.getCompanyName(), result.getCompanyName());
        assertEquals(extraServiceDto.getServiceType(), result.getServiceType());
        assertEquals(extraServiceDto.getServicePrice(), result.getServicePrice());
        assertEquals(extraServiceDto.getServiceDuration(), result.getServiceDuration());
    }

    @Test
    void getOneExtraServiceNotFound() {
        Long id = 1L;
        when(extraServiceRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> extraServiceService.getOneExtraService(id));
    }

    @Test
    void createExtraService() {

        ExtraServiceDto newExtraServiceDto = new ExtraServiceDto();
        newExtraServiceDto.setExtraService("Breakfast Team Brunch");
        newExtraServiceDto.setCompanyName("Pixie Dust Inc.");
        newExtraServiceDto.setServiceType("breakfast");
        newExtraServiceDto.setServicePrice(1000);
        newExtraServiceDto.setServiceDuration("7 days");

        ExtraService expectedExtraService = new ExtraService();
        expectedExtraService.setExtraService("Breakfast Team Brunch");
        expectedExtraService.setCompanyName("Pixie Dust Inc.");
        expectedExtraService.setServiceType("breakfast");
        expectedExtraService.setServicePrice(1000);
        expectedExtraService.setServiceDuration("7 days");

        when(extraServiceRepository.save(Mockito.any(ExtraService.class))).thenReturn(expectedExtraService);

        ExtraServiceDto result = extraServiceService.createExtraService(newExtraServiceDto);

        assertEquals(expectedExtraService.getExtraService(), result.getExtraService());
        assertEquals(expectedExtraService.getCompanyName(), result.getCompanyName());
        assertEquals(expectedExtraService.getServiceType(), result.getServiceType());
        assertEquals(expectedExtraService.getServicePrice(), result.getServicePrice());
        assertEquals(expectedExtraService.getServiceDuration(), result.getServiceDuration());


    }

    @Test
    void updateExtraService() {

        Long id = 1L;
        ExtraServiceDto extraServiceDto = new ExtraServiceDto();
        extraServiceDto.setId(id);
        extraServiceDto.setExtraService("Breakfast Team Brunch");
        extraServiceDto.setCompanyName("Pixie Dust Inc.");
        extraServiceDto.setServiceType("breakfast");
        extraServiceDto.setServicePrice(1000);
        extraServiceDto.setServiceDuration("7 days");

        ExtraService existingExtraService = new ExtraService();

        existingExtraService.setId(id);
        existingExtraService.setExtraService("Breakfast Team Brunch");
        existingExtraService.setCompanyName("Pixie Dust Inc.");
        existingExtraService.setServiceType("breakfast");
        existingExtraService.setServicePrice(1000);
        existingExtraService.setServiceDuration("7 days");

        when(extraServiceRepository.findById(id)).thenReturn(Optional.of(existingExtraService));

        ExtraServiceDto updatedExtraService = extraServiceService.updateExtraService(id, extraServiceDto);

        verify(extraServiceRepository, times(1)).findById(id);
        verify(extraServiceRepository, times(1)).save(any(ExtraService.class));

        assertNotNull(updatedExtraService);
        assertEquals(existingExtraService.getId(), updatedExtraService.getId());
        assertEquals(existingExtraService.getExtraService(), updatedExtraService.getExtraService());
        assertEquals(existingExtraService.getCompanyName(), updatedExtraService.getCompanyName());
        assertEquals(existingExtraService.getServiceType(), updatedExtraService.getServiceType());
        assertEquals(existingExtraService.getServicePrice(), updatedExtraService.getServicePrice());
        assertEquals(existingExtraService.getServiceDuration(), updatedExtraService.getServiceDuration());
    }

    @Test
    void getUpdatedExtraServiceNotFound() {
        Long id = 1L;
        when(extraServiceRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> extraServiceService.updateExtraService(id, new ExtraServiceDto()));
    }

    @Test
    void deleteExtraService() {
        Long id = 1L;
        ExtraService existingExtraService = new ExtraService();
        existingExtraService.setId(id);
        existingExtraService.setExtraService("Breakfast Team Brunch");
        existingExtraService.setCompanyName("Pixie Dust Inc.");
        existingExtraService.setServiceType("breakfast");
        existingExtraService.setServicePrice(1000);
        existingExtraService.setServiceDuration("7 days");

        when(extraServiceRepository.existsById(id)).thenReturn(true);
        when(extraServiceRepository.findById(id)).thenReturn(Optional.of(existingExtraService));

        extraServiceService.deleteExtraService(id);

        Mockito.verify(extraServiceRepository, times(1)).delete(existingExtraService);
    }

    @Test
    void testDeleteExtraServiceThrowsRecordNotFoundException() {
        // Arrange
        Long nonExistingExtraServiceId = 2L;
        ExtraServiceRepository extraServiceRepository = mock(ExtraServiceRepository.class);
        when(extraServiceRepository.existsById(nonExistingExtraServiceId)).thenReturn(false);

        ExtraServiceService someExtraService = new ExtraServiceService(extraServiceRepository);

        // Act and Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> someExtraService.deleteExtraService(nonExistingExtraServiceId));
        assertEquals("Item of type Extra-Service with id: " + nonExistingExtraServiceId + " could not be found.", exception.getMessage());

        // Verify that delete was not called
        verify(extraServiceRepository, never()).delete(any());
    }


    @Test
    void transferExtraServiceToExtraServiceDto() {

        ExtraServiceDto extraServiceDto = new ExtraServiceDto();
        extraServiceDto.setId(1L);
        extraServiceDto.setExtraService("Breakfast Team Brunch");
        extraServiceDto.setCompanyName("Pixie Dust Inc.");
        extraServiceDto.setServiceType("breakfast");
        extraServiceDto.setServicePrice(1000);
        extraServiceDto.setServiceDuration("7 days");

        ExtraService extraService = extraServiceService.transferExtraServiceDtoToExtraService(extraServiceDto);

        assertEquals(extraServiceDto.getId(), extraService.getId());
        assertEquals(extraServiceDto.getExtraService(), extraService.getExtraService());
        assertEquals(extraServiceDto.getCompanyName(), extraService.getCompanyName());
        assertEquals(extraServiceDto.getServiceType(), extraService.getServiceType());
        assertEquals(extraServiceDto.getServicePrice(), extraService.getServicePrice());
        assertEquals(extraServiceDto.getServiceDuration(), extraService.getServiceDuration());

    }

    @Test
    void transferExtraServiceDtoToExtraService() {

        ExtraService extraService = new ExtraService();
        extraService.setId(1L);
        extraService.setExtraService("Breakfast Team Brunch");
        extraService.setCompanyName("Pixie Dust Inc.");
        extraService.setServiceType("breakfast");
        extraService.setServicePrice(1000);
        extraService.setServiceDuration("7 days");

        ExtraServiceDto extraServiceDto = extraServiceService.transferExtraServiceToExtraServiceDto(extraService);

        assertEquals(extraService.getId(), extraServiceDto.getId());
        assertEquals(extraService.getExtraService(), extraServiceDto.getExtraService());
        assertEquals(extraService.getCompanyName(), extraServiceDto.getCompanyName());
        assertEquals(extraService.getServiceType(), extraServiceDto.getServiceType());
        assertEquals(extraService.getServicePrice(), extraServiceDto.getServicePrice());
        assertEquals(extraService.getServiceDuration(), extraServiceDto.getServiceDuration());
    }

    @Test
    void transferExtraServiceListToExtraServiceListDto() {

        ExtraService extraService1 = new ExtraService();
        extraService1.setId(1L);
        extraService1.setExtraService("Breakfast Team Brunch");
        extraService1.setCompanyName("Pixie Dust Inc.");
        extraService1.setServiceType("breakfast");
        extraService1.setServicePrice(1000);
        extraService1.setServiceDuration("7 days");

        ExtraService extraService2 = new ExtraService();
        extraService2.setId(2L);
        extraService2.setExtraService("Lunch Team Brunch");
        extraService2.setCompanyName("Pixie Dust Inc.");
        extraService2.setServiceType("lunch");
        extraService2.setServicePrice(1000);
        extraService2.setServiceDuration("7 days");


        List<ExtraService> extraServiceList = new ArrayList<>();
        extraServiceList.add(extraService1);
        extraServiceList.add(extraService2);

        List<ExtraServiceDto> extraServiceDtoList = extraServiceService.transferExtraServiceListToExtraServiceListDto(extraServiceList);

        assertEquals(extraServiceDtoList.size(), extraServiceList.size());
    }

    @Test
    void transferExtraServiceDtoListToExtraServiceList() {

        ExtraServiceDto extraServiceDtoOne = new ExtraServiceDto();
        extraServiceDtoOne.setId(1L);
        extraServiceDtoOne.setExtraService("Breakfast Team Brunch");
        extraServiceDtoOne.setCompanyName("Pixie Dust Inc.");
        extraServiceDtoOne.setServiceType("breakfast");
        extraServiceDtoOne.setServicePrice(1000);
        extraServiceDtoOne.setServiceDuration("7 days");

        ExtraServiceDto extraServiceDtoTwo = new ExtraServiceDto();
        extraServiceDtoTwo.setId(2L);
        extraServiceDtoTwo.setExtraService("Lunch Team Brunch");
        extraServiceDtoTwo.setCompanyName("Pixie Dust Inc.");
        extraServiceDtoTwo.setServiceType("lunch");
        extraServiceDtoTwo.setServicePrice(1000);
        extraServiceDtoTwo.setServiceDuration("7 days");

        List<ExtraServiceDto> extraServiceDtoList = List.of(extraServiceDtoOne, extraServiceDtoTwo);

        List<ExtraService> extraServiceList = extraServiceService.transferExtraServiceDtoListToExtraServiceList(extraServiceDtoList);

        assertEquals(extraServiceDtoList.size(), extraServiceList.size());
    }
}