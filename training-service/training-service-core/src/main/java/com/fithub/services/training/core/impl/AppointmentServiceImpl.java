package com.fithub.services.training.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.model.appointment.AppointmentResponse;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.mapper.AppointmentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentResponse> getAll() {
        return appointmentMapper.entitiesToDtos(appointmentRepository.findAll());
    }

}