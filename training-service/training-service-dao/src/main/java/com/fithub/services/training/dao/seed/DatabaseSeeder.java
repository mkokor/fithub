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
        UserEntity coachJohnUserEntity = new UserEntity();
        UserEntity coachAlbertUserEntity = new UserEntity();
        if (userRepository.findAll().isEmpty()) {
            coachJohnUserEntity.setUuid("john-doe-coach");
            coachJohnUserEntity.setFirstName("John");
            coachJohnUserEntity.setLastName("Doe");
            userRepository.save(coachJohnUserEntity);

            coachAlbertUserEntity.setUuid("albert-johnson-coach");
            coachAlbertUserEntity.setFirstName("Albert");
            coachAlbertUserEntity.setLastName("Johnson");
            userRepository.save(coachAlbertUserEntity);
        }

        CoachEntity coachJohnEntity = new CoachEntity();
        CoachEntity coachAlbertEntity = new CoachEntity();
        if (coachRepository.findAll().isEmpty()) {
            coachAlbertEntity.setUser(coachAlbertUserEntity);
            coachRepository.save(coachAlbertEntity);

            coachJohnEntity.setUser(coachJohnUserEntity);
            coachRepository.save(coachJohnEntity);
        }

        if (appointmentRepository.findAll().isEmpty()) {
            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setCapacity(2);
            appointmentEntity.setDay(DayOfWeek.MONDAY.toString());
            appointmentEntity.setStartTime(LocalTime.parse("09:00:00"));
            appointmentEntity.setEndTime(LocalTime.parse("10:30:00"));
            appointmentEntity.setCoach(coachJohnEntity);
            appointmentRepository.save(appointmentEntity);

            UserEntity clientUserEntity = new UserEntity();
            clientUserEntity.setUuid("mary-ann-client");
            clientUserEntity.setFirstName("Mary");
            clientUserEntity.setLastName("Ann");
            userRepository.save(clientUserEntity);

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setUser(clientUserEntity);
            clientEntity.setCoach(coachJohnEntity);
            clientRepository.save(clientEntity);

            UserEntity clientUser2 = new UserEntity();
            clientUser2.setUuid(UUID.randomUUID().toString());
            clientUser2.setFirstName("John");
            clientUser2.setLastName("Roberts");
            userRepository.save(clientUser2);

            ClientEntity clientEntity2 = new ClientEntity();
            clientEntity2.setUser(clientUser2);
            clientEntity2.setCoach(coachJohnEntity);
            clientRepository.save(clientEntity2);

            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setAppointment(appointmentEntity);
            reservationEntity.setClient(clientEntity);
            reservationRepository.save(reservationEntity);
        }
    }

}