package com.task.patient_appointment.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper dtoMapper = new ModelMapper();
        dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return dtoMapper;
    }

}