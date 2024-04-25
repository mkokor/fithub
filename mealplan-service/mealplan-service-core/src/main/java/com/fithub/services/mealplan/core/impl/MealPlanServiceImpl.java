package com.fithub.services.mealplan.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.mapper.DailyMealPlanMapper;
import com.fithub.services.systemevents.ActionLogRequest;
import com.fithub.services.systemevents.ActionLoggerServiceGrpc;
import com.fithub.services.systemevents.VoidResponse;
import com.fithub.services.mealplan.api.enums.ActionType;
import com.fithub.services.mealplan.api.enums.ResourceType;
import com.fithub.services.mealplan.api.enums.ResponseType;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class MealPlanServiceImpl implements MealPlanService{

	private final MealPlanRepository mealPlanRepository;
	private final DailyMealPlanMapper dailyMealPlanMapper;
	
    @Value("${spring.application.name}")
    private String microserviceName;

    @GrpcClient("system-events")
    private ActionLoggerServiceGrpc.ActionLoggerServiceStub systemEventsClient;
    
    public MealPlanServiceImpl (MealPlanRepository mealPlanRepository, DailyMealPlanMapper dailyMealPlanMapper) {
    	this.mealPlanRepository = mealPlanRepository;
    	this.dailyMealPlanMapper = dailyMealPlanMapper;
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
    }
    
	@Override
	public List<DailyMealPlanResponse> getDailyMealByDay(Long mealPlanId) throws Exception{
		Optional<MealPlanEntity> mealPlanEntity = mealPlanRepository.findById(mealPlanId);
		
		if (mealPlanEntity.isEmpty()){
            sendActionLogRequest(ActionType.GET, ResponseType.ERROR);
			throw new NotFoundException("The meal plan with provided ID could not be found");
		}
        sendActionLogRequest(ActionType.GET, ResponseType.SUCCESS);
		return dailyMealPlanMapper.entitiesToDtos(mealPlanEntity.get().getMealPlans());
	}
	
	
		
}
