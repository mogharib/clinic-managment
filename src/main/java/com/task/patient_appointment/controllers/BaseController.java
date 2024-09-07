package com.task.patient_appointment.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class BaseController {
    @Qualifier("modelMapper")
    @Autowired
    protected ModelMapper modelMapper;
}

