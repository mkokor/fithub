package com.fithub.services.mealplan.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.MealPlanUpdateRequest;
import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.repository.ClientRepository;
import com.fithub.services.mealplan.dao.repository.DailyMealPlanRepository;
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
	private final DailyMealPlanRepository dailyMealPlanRepository;
	private final ClientRepository clientRepository;
	
    @Value("${spring.application.name}")
    private String microserviceName;

    @GrpcClient("system-events")
    private ActionLoggerServiceGrpc.ActionLoggerServiceStub systemEventsClient;
    
    public MealPlanServiceImpl() {
		this.mealPlanRepository = null;
		this.dailyMealPlanMapper = null;
		this.dailyMealPlanRepository = null;
		this.clientRepository = null;
    	
    }
    
    public MealPlanServiceImpl (MealPlanRepository mealPlanRepository, DailyMealPlanMapper dailyMealPlanMapper) {
    	
    	this.mealPlanRepository = mealPlanRepository;
    	this.dailyMealPlanMapper = dailyMealPlanMapper;
		this.dailyMealPlanRepository = null;
		this.clientRepository = null;
    
    }
    
    public MealPlanServiceImpl (MealPlanRepository mealPlanRepository, DailyMealPlanMapper dailyMealPlanMapper, DailyMealPlanRepository dailyMealPlanRepository,
    		ClientRepository clientRepository) {
    	this.mealPlanRepository = mealPlanRepository;
    	this.dailyMealPlanMapper = dailyMealPlanMapper;
    	this.dailyMealPlanRepository = dailyMealPlanRepository;
		this.clientRepository = clientRepository;
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
	
	@Override
	public List<DailyMealPlanResponse> updateMealPlan(Long clientId, MealPlanUpdateRequest mealPlanUpdateRequest) throws Exception{
		
		Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);
		if(clientEntity.isEmpty()) {
			sendActionLogRequest(ActionType.GET, ResponseType.ERROR);
			throw new NotFoundException("The client with the provided ID could not be found");
		}
		
		
		MealPlanEntity mealPlan = mealPlanRepository.findMealPlanByClientId(clientEntity.get().getId());
		if(mealPlan.getMealPlans().isEmpty()) {
			sendActionLogRequest(ActionType.GET, ResponseType.ERROR);
			throw new NotFoundException("The meal plan the with provided ID could not be found");
		}
		
		List<DailyMealPlanEntity> dailyPlans = mealPlan.getMealPlans();
		
		for (DailyMealPlanEntity dailyPlan : dailyPlans) {
			
	        dailyPlan.setAmSnack(mealPlanUpdateRequest.getAmSnack());
	        dailyPlan.setPmSnack(mealPlanUpdateRequest.getPmSnack());
	        dailyPlan.setBreakfast(mealPlanUpdateRequest.getBreakfast());
	        dailyPlan.setDinner(mealPlanUpdateRequest.getDinner());
	        dailyPlan.setLunch(mealPlanUpdateRequest.getLunch());
	        dailyMealPlanRepository.save(dailyPlan);
	    }
		
		mealPlan.setMealPlans(dailyPlans);
		mealPlanRepository.save(mealPlan);
		
		return dailyMealPlanMapper.entitiesToDtos(dailyPlans);
	}
	
	
		
}
