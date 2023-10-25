package com.example.workingbeesapp.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class IdInputDto {

    @NonNull
    public Long id;

}

