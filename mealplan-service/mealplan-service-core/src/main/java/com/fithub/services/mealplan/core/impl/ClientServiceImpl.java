package com.fithub.services.mealplan.core.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.mealplan.api.ClientService;
import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.exception.BadRequestException;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.api.model.user.NewUserRequest;
import com.fithub.services.mealplan.api.model.user.UserResponse;
import com.fithub.services.mealplan.api.rabbitmq.ClientRegistrationMessage;
import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.ClientRepository;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.dao.repository.UserRepository;
import com.fithub.services.mealplan.mapper.MealPlanMapper;
import com.fithub.services.mealplan.mapper.UserMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final MealPlanRepository mealPlanRepository;
    private final MealPlanMapper mealPlanMapper;
    private final UserMapper userMapper;
    private final MealPlanService mealPlanService;
    private final Validator validator;

    @Override
    public MealPlanResponse getMealPlan(Long clientId) throws Exception {
        Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);

        if (clientEntity.isEmpty()) {
            throw new NotFoundException("The client with provided ID could not be found");
        }

        return mealPlanMapper.entityToDto(clientEntity.get().getMealPlan());
    }

    @Override
    public UserResponse getClientNameAndLastName(String userId) throws NotFoundException {
        Optional<ClientEntity> clientEntity = clientRepository.findByUserUuid(userId);

        if (clientEntity.isEmpty()) {
            throw new NotFoundException("The user associated with the client ID could not be found");
        }

        return userMapper.entityToDto(clientEntity.get().getUser());

    }
/*
    @Override
    public ClientResponse getClientResponse(String userId) throws NotFoundException {
        Optional<ClientEntity> clientEntity = clientRepository.findByUserUuid(userId);

        if (clientEntity.isEmpty()) {
            throw new NotFoundException("The user associated with the client ID could not be found");
        }

        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(clientEntity.get().getId());
        clientResponse.setUserId(userId);
        clientResponse.setCoachId(clientEntity.get().getCoach().getId());

        // Fetching the client name and last name
        UserResponse userResponse = getClientNameAndLastName(userId);
        clientResponse.setFirstName(userResponse.getFirstName());
        clientResponse.setLastName(userResponse.getLastName());

        return clientResponse;
    }*/
    @Override
    public List<DailyMealPlanResponse> getDailyMealPlanByClientId(Long clientId) throws Exception {
        MealPlanResponse mealPlanResponse = getMealPlan(clientId);

        if (mealPlanResponse != null) {
            return mealPlanService.getDailyMealByDay(mealPlanResponse.getId());
        } else {
            throw new NotFoundException("Meal plan for the client could not be found");
        }
    }

    @Override
    public MealPlanResponse makeMealPlanForClient(String userId) throws Exception {

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (!userEntity.isPresent()) {
            throw new NotFoundException("The user with provided ID could not be found.");
        }
        if (userEntity.get().getClient().getCoach() == null) {
            // System.out.println("coach: " +
            // userEntity.get().getClient().getCoach());
            throw new BadRequestException("Only coach users can make meal plan for client.");
        }

        Optional<ClientEntity> client = clientRepository.findById(userEntity.get().getClient().getId());

        if (client.isEmpty()) {
            throw new NotFoundException("The client with provided ID could not be found.");

        }

        MealPlanEntity existingMealPlan = mealPlanRepository.findMealPlanByClientId(client.get().getId());
        if (existingMealPlan != null) {
            throw new BadRequestException("Client already has a meal plan..");
        }

        MealPlanEntity mealPlanEntity = new MealPlanEntity();
        mealPlanEntity.setModified(LocalDateTime.now());
        mealPlanEntity.setClient(client.get());
        mealPlanEntity.setModifiedBy(client.get().getCoach());

        mealPlanRepository.save(mealPlanEntity);
        return mealPlanMapper.entityToDto(mealPlanEntity);

    }

    @Override
    public CoachResponse postCoachForClient(String userId, NewUserRequest newUserRequest) throws Exception {

        Set<ConstraintViolation<NewUserRequest>> violations = validator.validate(newUserRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new NotFoundException("The user with provided ID could not be found.");
        }

        UserEntity user = userOptional.get();
        ClientEntity client = user.getClient();

        Optional<UserEntity> coachOptional = userRepository.findByFirstNameAndLastName(newUserRequest.getFirstName(),
                newUserRequest.getLastName());
        if (coachOptional.isEmpty() || coachOptional.get().getCoach() == null) {
            throw new NotFoundException("Coach with specified first name and last name not found.");
        }

        CoachEntity existingCoach = client.getCoach();
        if (existingCoach != null) {
            client.setCoach(null); // Uklanjanje postojećeg coacha i dodaje
                                   // novoizabranog
        }
        CoachEntity coach = coachOptional.get().getCoach();
        client.setCoach(coach);
        clientRepository.save(client);

        CoachResponse response = new CoachResponse();
        response.setCoachId(coach.getId());
        response.setCoachName(coach.getUser().getFirstName());
        response.setCoachSurname(coach.getUser().getLastName());
        return response;
    }

    @Override
    public void addClient(ClientRegistrationMessage clientRegistrationMessage) throws ApiException {
        Optional<UserEntity> coachUserEntity = userRepository.findById(clientRegistrationMessage.getCoachUuid());
        if (coachUserEntity.isEmpty() || coachUserEntity.get().getCoach() == null) {
            throw new NotFoundException("The coach with provided ID could not be found.");
        }
        final CoachEntity coachEntity = coachUserEntity.get().getCoach();

        UserEntity newClientUserEntity = new UserEntity();
        newClientUserEntity.setUuid(clientRegistrationMessage.getUuid());
        newClientUserEntity.setFirstName(clientRegistrationMessage.getFirstName());
        newClientUserEntity.setLastName(clientRegistrationMessage.getLastName());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setUser(newClientUserEntity);
        clientEntity.setCoach(coachEntity);

        userRepository.save(newClientUserEntity);
        clientRepository.save(clientEntity);

        newClientUserEntity.setClient(clientEntity);
        userRepository.save(newClientUserEntity);
    }

}