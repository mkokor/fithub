package com.fithub.services.training.dao.seed;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.dao.repository.CoachRepository;
import com.fithub.services.training.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final AppointmentRepository appointmentRepository;
    private final CoachRepository coachRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seed();
    }

    private void seed() {
        UserEntity userEntity = new UserEntity();
        if (userRepository.findAll().isEmpty()) {
            userEntity.setUuid(UUID.randomUUID().toString());
            userEntity.setFirstName("John");
            userEntity.setLastName("Doe");
            userRepository.save(userEntity);
        }

        CoachEntity coachEntity = new CoachEntity();
        if (coachRepository.findAll().isEmpty()) {
            coachEntity.setUser(userEntity);
            coachRepository.save(coachEntity);
        }

        if (appointmentRepository.findAll().isEmpty()) {
            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setCapacity(5);
            appointmentEntity.setDay(DayOfWeek.MONDAY.toString());
            appointmentEntity.setStartTime(LocalTime.parse("09:00:00"));
            appointmentEntity.setEndTime(LocalTime.parse("10:30:00"));
            appointmentEntity.setCoach(coachEntity);
            appointmentRepository.save(appointmentEntity);
        }
    }
}