package com.fithub.services.training.test.suites;

import static org.assertj.core.api.Assertions.assertThat;

import static org.testng.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.model.appointment.AppointmentResponse;
import com.fithub.services.training.api.model.reservation.NewReservationRequest;
import com.fithub.services.training.api.model.reservation.ReservationResponse;
import com.fithub.services.training.core.impl.AppointmentServiceImpl;
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
import com.fithub.services.training.mapper.AppointmentMapper;
import com.fithub.services.training.mapper.ClientAppointmentMapper;
import com.fithub.services.training.mapper.CoachAppointmentMapper;
import com.fithub.services.training.mapper.ReservationMapper;
import com.fithub.services.training.test.configuration.BasicTestConfiguration;

import jakarta.validation.Validator;

public class AppointmentServiceTest extends BasicTestConfiguration {

    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private ClientAppointmentMapper clientAppointmentMapper;
    @Autowired
    private CoachAppointmentMapper coachAppointmentMapper;

    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private CoachRepository coachRepository;
    private ClientRepository clientRepository;
    private ReservationRepository reservationRepository;

    private AppointmentService appointmentService;
    
    private Validator validator;

    @BeforeMethod
    public void beforeMethod() {
        appointmentRepository = Mockito.mock(AppointmentRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        coachRepository = Mockito.mock(CoachRepository.class);
        reservationRepository = Mockito.mock(ReservationRepository.class);
        
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
        
        appointmentService = new AppointmentServiceImpl(appointmentRepository, userRepository, reservationRepository, reservationMapper, appointmentMapper, clientAppointmentMapper, coachAppointmentMapper, validator);
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
    
    @Test
    public void testGetAvailableAppointments_ValidUserIdIsProvided_ReturnsAvailableAppointments() {
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("user-id");
            userEntity.setFirstName("John");
            userEntity.setLastName("Doe");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(userEntity);
            userEntity.setCoach(coachEntity);
            
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
            
            clientUser.setClient(clientEntity);
            
            LocalTime time1 = LocalTime.now();
            LocalTime time2 = LocalTime.now();

            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setId(1L);
            appointmentEntity.setCapacity(5);
            appointmentEntity.setDay(DayOfWeek.MONDAY.toString());
            appointmentEntity.setCoach(coachEntity);
            appointmentEntity.setStartTime(time1);
            appointmentEntity.setEndTime(time2);

            List<AppointmentResponse> expectedResponse = new ArrayList<>();
            AppointmentResponse availableAppointment = new AppointmentResponse();
            availableAppointment.setId(1L);
            availableAppointment.setCapacity(5);
            availableAppointment.setCoachId(1L);
            availableAppointment.setStartTime(time1);
            availableAppointment.setEndTime(time2);
            availableAppointment.setDay(DayOfWeek.MONDAY.toString());
            expectedResponse.add(availableAppointment);
            
            List<AppointmentEntity> availableAppointments = new ArrayList<>();
            availableAppointments.add(appointmentEntity);

            Mockito.when(appointmentRepository.findById(appointmentEntity.getId())).thenReturn(Optional.of(appointmentEntity));
            Mockito.when(appointmentRepository.findAvailableAppointmentsByCoachId(coachEntity.getId())).thenReturn(availableAppointments);
            Mockito.when(userRepository.findById(clientUser.getUuid())).thenReturn(Optional.of(clientUser));
            Mockito.when(clientRepository.findById(clientEntity.getId())).thenReturn(Optional.of(clientEntity));

            List<AppointmentResponse> actualResponse = appointmentService.getAvailableAppointments(clientUser.getUuid());

            Assertions.assertThat(actualResponse).hasSameElementsAs(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    } 
    
    @Test
    public void testMakeReservationForAppointment_ValidDataIsProvided_ReturnsNewReservation() {
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
            
            LocalTime time1 = LocalTime.now();
            LocalTime time2 = LocalTime.now();

            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setId(1L);
            appointmentEntity.setCapacity(5);
            appointmentEntity.setDay(DayOfWeek.MONDAY.toString());
            appointmentEntity.setCoach(coachEntity);
            appointmentEntity.setStartTime(time1);
            appointmentEntity.setEndTime(time2);
            
            List<AppointmentEntity> availableAppointments = new ArrayList<>();
            availableAppointments.add(appointmentEntity);
            
            NewReservationRequest reservationRequest = new NewReservationRequest();
            reservationRequest.setAppointmentId(1L);
            
            String userId = clientUser.getUuid();
            
            ReservationResponse expectedResponse = new ReservationResponse();
            expectedResponse.setClientId(1L);
            expectedResponse.setAppointmentId(1L);      

            Mockito.when(userRepository.findById(clientUser.getUuid())).thenReturn(Optional.of(clientUser));
            Mockito.when(coachRepository.findById(coachEntity.getId())).thenReturn(Optional.of(coachEntity));
            Mockito.when(clientRepository.findById(clientEntity.getId())).thenReturn(Optional.of(clientEntity));
            Mockito.when(appointmentRepository.findAvailableAppointmentsByCoachId(coachEntity.getId())).thenReturn(availableAppointments);
            Mockito.when(appointmentRepository.findById(appointmentEntity.getId())).thenReturn(Optional.of(appointmentEntity));
            Mockito.when(reservationRepository.findReservationByClientId(appointmentEntity.getId(), clientEntity.getId())).thenReturn(null);
            
            ReservationResponse actualResponse = appointmentService.makeReservationForAppointment(userId, reservationRequest);

            assertThat(actualResponse).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }
    
    @Test
    public void testMakeReservationForAppointment_ClientAlreadyHasAReservationForAppointment_ThrowsBadRequestException() {
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
            
            LocalTime time1 = LocalTime.now();
            LocalTime time2 = LocalTime.now();

            AppointmentEntity appointmentEntity = new AppointmentEntity();
            appointmentEntity.setId(1L);
            appointmentEntity.setCapacity(5);
            appointmentEntity.setDay(DayOfWeek.MONDAY.toString());
            appointmentEntity.setCoach(coachEntity);
            appointmentEntity.setStartTime(time1);
            appointmentEntity.setEndTime(time2);
            
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setId(1L);
            reservationEntity.setAppointment(appointmentEntity);
            reservationEntity.setClient(clientEntity);
            
            List<AppointmentEntity> availableAppointments = new ArrayList<>();
            availableAppointments.add(appointmentEntity);
            
            NewReservationRequest reservationRequest = new NewReservationRequest();
            reservationRequest.setAppointmentId(1L);
            
            ReservationResponse expectedResponse = new ReservationResponse();
            expectedResponse.setClientId(1L);
            expectedResponse.setAppointmentId(1L);      

            Mockito.when(userRepository.findById(clientUser.getUuid())).thenReturn(Optional.of(clientUser));
            Mockito.when(coachRepository.findById(coachEntity.getId())).thenReturn(Optional.of(coachEntity));
            Mockito.when(clientRepository.findById(clientEntity.getId())).thenReturn(Optional.of(clientEntity));
            Mockito.when(appointmentRepository.findAvailableAppointmentsByCoachId(coachEntity.getId())).thenReturn(availableAppointments);
            Mockito.when(appointmentRepository.findById(appointmentEntity.getId())).thenReturn(Optional.of(appointmentEntity));
            Mockito.when(reservationRepository.findReservationByClientId(appointmentEntity.getId(), clientEntity.getId())).thenReturn(reservationEntity);
            
            assertThrows(BadRequestException.class, () -> appointmentService.makeReservationForAppointment(clientUser.getUuid(), reservationRequest));
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}
