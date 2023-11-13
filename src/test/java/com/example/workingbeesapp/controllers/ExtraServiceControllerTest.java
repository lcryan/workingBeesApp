package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.ExtraServiceDto;
import com.example.workingbeesapp.models.ExtraService;
import com.example.workingbeesapp.repositories.ExtraServiceRepository;
import com.example.workingbeesapp.security.JwtService;
import com.example.workingbeesapp.services.ExtraServiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ExtraServiceController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class ExtraServiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ExtraServiceService extraServiceService;

    @MockBean
    ExtraServiceRepository extraServiceRepository;

    @MockBean
    JwtService jwtService;


    @Test
    void getExtraService() throws Exception {

        ExtraServiceDto extraServiceDto = new ExtraServiceDto();
        extraServiceDto.setId(1L);
        extraServiceDto.setServiceName("Fancy Dinner Gala");
        extraServiceDto.setCompanyName("Pixar Inc.");
        extraServiceDto.setServiceType("Dinner");
        extraServiceDto.setServiceDuration("2 hours");
        extraServiceDto.setServicePrice(1000);

        Mockito.when(extraServiceService.getOneExtraService(1L)).thenReturn(extraServiceDto);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/extraservices/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceName").value("Fancy Dinner Gala"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Pixar Inc."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceType").value("Dinner"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.serviceDuration").value("2 hours"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servicePrice").value(1000));
    }

    @Test
    void createNewExtraService() throws Exception {

        ExtraService extraService = new ExtraService();
        extraService.setId(1L);
        extraService.setServiceName("Fancy Dinner Gala");
        extraService.setCompanyName("Pixar Inc.");
        extraService.setServiceType("Dinner");
        extraService.setServiceDuration("2 hours");
        extraService.setServicePrice(1000);

        extraServiceRepository.save(extraService);

        ExtraServiceDto extraServiceDto = new ExtraServiceDto();

        extraServiceDto.setServiceName(extraService.getServiceName());
        extraServiceDto.setCompanyName(extraService.getCompanyName());
        extraServiceDto.setServiceType(extraService.getServiceType());
        extraServiceDto.setServiceDuration(extraService.getServiceDuration());
        extraServiceDto.setServicePrice(extraService.getServicePrice());

        Mockito.when(extraServiceService.createExtraService(Mockito.any(ExtraServiceDto.class))).thenReturn(extraServiceDto);

        this.mockMvc
                .perform(post("/extraservices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(extraServiceDto)));
    }
}
