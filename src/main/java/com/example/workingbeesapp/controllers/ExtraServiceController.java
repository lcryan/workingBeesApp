package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.ExtraServiceDto;
import com.example.workingbeesapp.services.ExtraServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/extraservices")
public class ExtraServiceController {
    private final ExtraServiceService extraServiceService;

    public ExtraServiceController(ExtraServiceService extraServiceService) {
        this.extraServiceService = extraServiceService;
    }


    //--- get all extra-services ---//
    @GetMapping("")
    public ResponseEntity<List<ExtraServiceDto>> getAllExtraServices() {
        List<ExtraServiceDto> extraServiceDtoList = extraServiceService.getAllExtraServices();
        return ResponseEntity.ok(extraServiceDtoList);
    }

    //--- get one extra-service ---//
    @GetMapping("/{id}")
    public ResponseEntity<ExtraServiceDto> getExtraService(@PathVariable Long id) {
        ExtraServiceDto extraServiceDto = extraServiceService.getOneExtraService(id);
        return ResponseEntity.ok(extraServiceDto);
    }

    //--- create extra-service ---//
    @PostMapping("")
    public ResponseEntity<Object> createExtraService(@Validated @RequestBody ExtraServiceDto extraServiceDto, BindingResult bindingResult) {
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
            ExtraServiceDto extraServiceDto1 = extraServiceService.createExtraService(extraServiceDto);
            return ResponseEntity.created(null).body(extraServiceDto1);
        }
    }

    //--- update extra-service ---//
    @PutMapping("/{id}")
    public ResponseEntity<ExtraServiceDto> updateExtraService(@PathVariable Long id, @Validated @RequestBody ExtraServiceDto newExtraService) {
        ExtraServiceDto extraServiceDto1 = extraServiceService.updateExtraService(id, newExtraService);
        return ResponseEntity.ok().body(extraServiceDto1);
    }

    //--- delete extra-service ---//
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteExtraService(@PathVariable Long id) {
        extraServiceService.deleteExtraService(id);
        return ResponseEntity.noContent().build();
    }
}
