package com.fithub.services.mealplan.core.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.repository.CoachRepository;
import com.fithub.services.mealplan.dao.repository.UserRepository;
import com.fithub.services.mealplan.mapper.ClientMapper;
import com.fithub.services.mealplan.mapper.UserMapper;
import com.fithub.services.systemevents.ActionLogRequest;
import com.fithub.services.systemevents.ActionLoggerServiceGrpc;
import com.fithub.services.systemevents.VoidResponse;

import io.grpc.stub.StreamObserver;

import com.fithub.services.mealplan.api.CoachService;
import com.fithub.services.mealplan.api.enums.ActionType;
import com.fithub.services.mealplan.api.enums.ResourceType;
import com.fithub.services.mealplan.api.enums.ResponseType;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;
import com.fithub.services.mealplan.dao.model.UserEntity;


import lombok.AllArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@AllArgsConstructor
public class CoachServiceImpl implements CoachService {
	
    private final CoachRepository coachRepository;
    private final UserMapper userMapper;
    private final ClientMapper clientMapper;

    /*@Value("${spring.application.name}")
    private String microserviceName;
    
    @GrpcClient("system-events")
    private ActionLoggerServiceGrpc.ActionLoggerServiceStub systemEventsClient;
    
    public CoachServiceImpl(CoachRepository coachRepository, UserMapper userMapper, ClientMapper clientMapper) {
    	this.coachRepository = coachRepository;
    	this.userMapper = userMapper;
    	this.clientMapper = clientMapper;
    }
    
    private void sendActionLogRequest(ActionType actionType, ResponseType responseType) {
        ActionLogRequest actionLogRequest = ActionLogRequest.newBuilder().setMicroserviceName(microserviceName)
                .setActionType(actionType.toString()).setResourceTitle(ResourceType.MEAL_PLAN.toString())
                .setResponseType(responseType.toString()).build();

        systemEventsClient.logAction(actionLogRequest, new StreamObserver<VoidResponse>() {

            @Override
            public void onNext(VoidResponse response) {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }

        });
    }*/
    
    @Override
    public UserResponse getCoachNameAndLastName(String userId) throws NotFoundException {
        Optional<CoachEntity> coachEntity = coachRepository.findByUserUuid(userId);

        if (coachEntity.isEmpty()) {
        	 //sendActionLogRequest(ActionType.GET, ResponseType.ERROR);
            throw new NotFoundException("The user associated with the coach ID could not be found");
        }
        //sendActionLogRequest(ActionType.GET, ResponseType.SUCCESS);
        return userMapper.entityToDto(coachEntity.get().getUser());

    }
    
    @Override
    public List<ClientResponse> getClientsByCoach(Long coachId) throws Exception {
    	Optional<CoachEntity> coachEntity = coachRepository.findById(coachId);
    	
    	if (coachEntity.isEmpty()) {
			throw new NotFoundException("The coach with provided ID could not be found");
    	}
    	return clientMapper.entitiesToDtos(coachEntity.get().getClients());

    }
}
