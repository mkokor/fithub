package com.fithub.services.training.test.suites;

import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.exception.UnauthorizedException;
import com.fithub.services.training.api.model.reservation.ReservationResponse;
import com.fithub.services.training.core.client.MembershipServiceClient;
import com.fithub.services.training.core.context.UserContext;
import com.fithub.services.training.core.impl.AppointmentServiceImpl;
import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.ReservationEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.dao.repository.ReservationRepository;
import com.fithub.services.training.mapper.AppointmentMapper;
import com.fithub.services.training.mapper.ReservationMapper;
import com.fithub.services.training.test.configuration.BasicTestConfiguration;

public class AppointmentServiceTest extends BasicTestConfiguration {
/*
    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    private AppointmentRepository appointmentRepository;
    private ReservationRepository reservationRepository;
    private MembershipServiceClient membershipServiceClient;
    private AppointmentService appointmentService;

    @BeforeMethod
    public void beforeMethod() {
        appointmentRepository = Mockito.mock(AppointmentRepository.class);
        reservationRepository = Mockito.mock(ReservationRepository.class);
        membershipServiceClient = Mockito.mock(MembershipServiceClient.class);

        appointmentService = new AppointmentServiceImpl(appointmentRepository, reservationRepository, reservationMapper, appointmentMapper,
                membershipServiceClient);
    }

    private UserEntity constructCoachUser() {
        UserEntity authenticatedCoach = new UserEntity();
        authenticatedCoach.setUuid("john-doe-coach");
        authenticatedCoach.setFirstName("John");
        authenticatedCoach.setLastName("Doe");

        CoachEntity coachEntity = new CoachEntity();
        coachEntity.setId(1L);
        coachEntity.setClientCapacity(10);
        coachEntity.setUser(authenticatedCoach);

        authenticatedCoach.setCoach(coachEntity);

        return authenticatedCoach;
    }

    private void authenticateCoach() {
        UserEntity authenticatedCoach = constructCoachUser();

        UserContext userContext = new UserContext();
        userContext.setUser(authenticatedCoach);

        UserContext.setCurrentContext(userContext);
    }

    @Test
    public void testGetReservations_ValidAppointmentIdIsProvided_ReturnReservations() {
        try {
            authenticateCoach();

            UserEntity coachUser = constructCoachUser();

            UserEntity clientUser = new UserEntity();
            clientUser.setUuid("michael-jordan-client");
            clientUser.setFirstName("Michael");
            clientUser.setLastName("Jordan");

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setUser(clientUser);
            clientEntity.setId(1L);

            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setClient(clientEntity);
            reservationEntity.setId(1L);

            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setId(1L);
            appointmentEntity.setCoach(coachUser.getCoach());
            appointmentEntity.setReservations(List.of(reservationEntity));

            reservationEntity.setAppointment(appointmentEntity);

            ReservationResponse reservationResponse = new ReservationResponse();
            reservationResponse.setAppointmentId(appointmentEntity.getId());
            reservationResponse.setClientId(clientEntity.getId());
            reservationResponse.setId(reservationEntity.getId());

            Mockito.when(appointmentRepository.findById(appointmentEntity.getId())).thenReturn(Optional.of(appointmentEntity));

            List<ReservationResponse> reservations = appointmentService.getReservations(1L);

            Assert.assertEquals(reservations, reservations);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testGetReservations_AppointmentOfAnotherCoachIsRequested_ThrowsUnauthorizedException() {
        try {
            authenticateCoach();

            UserEntity coachUser = new UserEntity();
            coachUser.setUuid("mary-ann");
            coachUser.setFirstName("Mary");
            coachUser.setLastName("Ann");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(2L);
            coachEntity.setUser(coachUser);
            coachUser.setCoach(coachEntity);

            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setId(1L);
            appointmentEntity.setCoach(coachUser.getCoach());

            Mockito.when(appointmentRepository.findById(appointmentEntity.getId())).thenReturn(Optional.of(appointmentEntity));

            Assert.assertThrows(UnauthorizedException.class, () -> appointmentService.getReservations(appointmentEntity.getId()));
        } catch (Exception exception) {
            Assert.fail();
        }
    }
*/
}