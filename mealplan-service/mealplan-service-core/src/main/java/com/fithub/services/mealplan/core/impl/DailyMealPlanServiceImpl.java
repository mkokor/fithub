package com.fithub.services.mealplan.core.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.mealplan.api.DailyMealPlanService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanUpdateRequest;
import com.fithub.services.mealplan.core.context.UserContext;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.DailyMealPlanRepository;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.mapper.DailyMealPlanMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyMealPlanServiceImpl implements DailyMealPlanService {

    private final DailyMealPlanRepository dailyMealPlanRepository;
    private final MealPlanRepository mealPlanRepository;
    private final DailyMealPlanMapper dailyMealPlanMapper;
    private final Validator validator;

    @Override
    public DailyMealPlanResponse update(DailyMealPlanUpdateRequest dailyMealPlanUpdateRequest) throws Exception {
        Set<ConstraintViolation<DailyMealPlanUpdateRequest>> violations = validator.validate(dailyMealPlanUpdateRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final UserEntity userEntity = UserContext.getCurrentContext().getUser();
        final CoachEntity coachEntity = userEntity.getCoach();

        Optional<DailyMealPlanEntity> dailyMealPlanEntity = dailyMealPlanRepository.findById(dailyMealPlanUpdateRequest.getId());
        if (dailyMealPlanEntity.isEmpty()) {
            throw new NotFoundException("The daily meal plan with provided ID could not be found.");
        }

        DailyMealPlanEntity dailyMealPlan = dailyMealPlanEntity.get();
        dailyMealPlan.setAmSnack(dailyMealPlanUpdateRequest.getAmSnack());
        dailyMealPlan.setPmSnack(dailyMealPlanUpdateRequest.getPmSnack());
        dailyMealPlan.setDinner(dailyMealPlanUpdateRequest.getDinner());
        dailyMealPlan.setLunch(dailyMealPlanUpdateRequest.getLunch());
        dailyMealPlan.setBreakfast(dailyMealPlanUpdateRequest.getBreakfast());
        dailyMealPlanRepository.save(dailyMealPlan);

        MealPlanEntity mealPlanEntity = dailyMealPlan.getMealPlan();
        mealPlanEntity.setLastModified(LocalDateTime.now());
        mealPlanEntity.setLastModifiedBy(coachEntity);
        mealPlanRepository.save(mealPlanEntity);

        return dailyMealPlanMapper.entityToDto(dailyMealPlan);
    }

}