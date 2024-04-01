package com.fithub.services.training.test.suites;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.model.reservation.ReservationResponse;
import com.fithub.services.training.core.impl.AppointmentServiceImpl;
import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.ReservationEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.mapper.ReservationMapper;
import com.fithub.services.training.test.configuration.BasicTestConfiguration;

public class AppointmentServiceTest extends BasicTestConfiguration {

    @Value("${message}")
    private String message;

    @Autowired
    private ReservationMapper reservationMapper;

    private AppointmentRepository appointmentRepository;

    private AppointmentService appointmentService;

    @BeforeMethod
    public void beforeMethod() {
        appointmentRepository = Mockito.mock(AppointmentRepository.class);

        appointmentService = new AppointmentServiceImpl(appointmentRepository, reservationMapper);
    }

    @Test
    public void testGetReservations_ValidAppointmentIdIsProvided_ReturnsReservations() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("user-id");
            userEntity.setFirstName("John");
            userEntity.setLastName("Doe");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(userEntity);
            userEntity.setCoach(coachEntity);

            UserEntity clientUser = new UserEntity();
            clientUser.setUuid("client-id");
            clientUser.setFirstName("Mary");
            clientUser.setLastName("Ann");

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(1L);
            clientEntity.setUser(clientUser);
            clientEntity.setCoach(coachEntity);
            clientUser.setClient(clientEntity);

            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setId(1L);
            appointmentEntity.setCapacity(5);
            appointmentEntity.setDay(DayOfWeek.MONDAY.toString());
            appointmentEntity.setCoach(coachEntity);
            appointmentEntity.setStartTime(LocalTime.now());
            appointmentEntity.setEndTime(LocalTime.now());

            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setId(1L);
            reservationEntity.setClient(clientEntity);
            reservationEntity.setAppointment(appointmentEntity);

            List<ReservationEntity> reservationEntities = new ArrayList<>();
            reservationEntities.add(reservationEntity);
            appointmentEntity.setReservations(reservationEntities);

            List<ReservationResponse> expectedResponse = new ArrayList<>();
            ReservationResponse reservationResponse = new ReservationResponse();
            reservationResponse.setId(1L);
            reservationResponse.setClientId(1L);
            reservationResponse.setAppointmentId(1L);
            expectedResponse.add(reservationResponse);

            Mockito.when(appointmentRepository.findById(appointmentEntity.getId())).thenReturn(Optional.of(appointmentEntity));

            List<ReservationResponse> actualResponse = appointmentService.getReservations(appointmentEntity.getId());

            Assertions.assertThat(actualResponse).hasSameElementsAs(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}
