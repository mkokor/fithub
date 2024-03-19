package com.fithub.services.training.api;

import java.util.List;

import com.fithub.services.training.api.model.appointment.AppointmentResponse;

public interface AppointmentService {

    public List<AppointmentResponse> getAll();

}