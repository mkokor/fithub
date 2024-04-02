package com.fithub.services.training.dao.seed;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.ReservationEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.dao.repository.ClientRepository;
import com.fithub.services.training.dao.repository.CoachRepository;
import com.fithub.services.training.dao.repository.ReservationRepository;
import com.fithub.services.training.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final AppointmentRepository appointmentRepository;
    private final CoachRepository coachRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

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

            UserEntity clientUser = new UserEntity();
            clientUser.setUuid("mary-ann");
            clientUser.setFirstName("Mary");
            clientUser.setLastName("Ann");
            userRepository.save(clientUser);

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setUser(clientUser);
            clientEntity.setCoach(coachEntity);
            clientRepository.save(clientEntity);

            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setAppointment(appointmentEntity);
            reservationEntity.setClient(clientEntity);
            reservationRepository.save(reservationEntity);
        }
    }
}