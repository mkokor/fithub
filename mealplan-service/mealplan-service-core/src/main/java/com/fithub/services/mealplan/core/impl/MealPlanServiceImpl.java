package com.fithub.services.mealplan.core.impl;

import java.util.List;
import java.util.Optional;

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

@Service
public class MealPlanServiceImpl implements MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final DailyMealPlanMapper dailyMealPlanMapper;
    private final DailyMealPlanRepository dailyMealPlanRepository;
    private final ClientRepository clientRepository;

    public MealPlanServiceImpl() {
        this.mealPlanRepository = null;
        this.dailyMealPlanMapper = null;
        this.dailyMealPlanRepository = null;
        this.clientRepository = null;

    }

    public MealPlanServiceImpl(MealPlanRepository mealPlanRepository, DailyMealPlanMapper dailyMealPlanMapper) {

        this.mealPlanRepository = mealPlanRepository;
        this.dailyMealPlanMapper = dailyMealPlanMapper;
        this.dailyMealPlanRepository = null;
        this.clientRepository = null;

    }

    public MealPlanServiceImpl(MealPlanRepository mealPlanRepository, DailyMealPlanMapper dailyMealPlanMapper,
            DailyMealPlanRepository dailyMealPlanRepository, ClientRepository clientRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.dailyMealPlanMapper = dailyMealPlanMapper;
        this.dailyMealPlanRepository = dailyMealPlanRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<DailyMealPlanResponse> getDailyMealByDay(Long mealPlanId) throws Exception {
        Optional<MealPlanEntity> mealPlanEntity = mealPlanRepository.findById(mealPlanId);

        if (mealPlanEntity.isEmpty()) {
            throw new NotFoundException("The meal plan with provided ID could not be found");
        }

        return dailyMealPlanMapper.entitiesToDtos(mealPlanEntity.get().getMealPlans());
    }

    @Override
    public List<DailyMealPlanResponse> updateMealPlan(Long clientId, MealPlanUpdateRequest mealPlanUpdateRequest) throws Exception {

        Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);
        if (clientEntity.isEmpty()) {
            throw new NotFoundException("The client with the provided ID could not be found");
        }

        MealPlanEntity mealPlan = mealPlanRepository.findMealPlanByClientId(clientEntity.get().getId());
        if (mealPlan.getMealPlans().isEmpty()) {
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