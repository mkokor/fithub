package com.fithub.services.training.test.suites;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.core.client.MembershipServiceClient;
import com.fithub.services.training.core.context.UserContext;
import com.fithub.services.training.core.impl.AppointmentServiceImpl;
import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.dao.repository.ReservationRepository;
import com.fithub.services.training.mapper.AppointmentMapper;
import com.fithub.services.training.mapper.ReservationMapper;
import com.fithub.services.training.test.configuration.BasicTestConfiguration;

public class AppointmentServiceTest extends BasicTestConfiguration {

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
    public void testGetReservations_ValidAppointmentIdIsProvided_ReturnReservations() throws Exception {
        authenticateCoach();

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.se
        appointmentService.getReservations(1L);
    }

}