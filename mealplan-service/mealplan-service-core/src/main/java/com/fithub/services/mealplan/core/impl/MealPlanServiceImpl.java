package com.fithub.services.mealplan.core.impl;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.exception.BadRequestException;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.core.context.UserContext;
import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.model.UserEntity;
import com.fithub.services.mealplan.dao.repository.DailyMealPlanRepository;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.dao.repository.UserRepository;
import com.fithub.services.mealplan.mapper.DailyMealPlanMapper;
import com.fithub.services.mealplan.mapper.MealPlanMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MealPlanServiceImpl implements MealPlanService {

    private final UserRepository userRepository;
    private final MealPlanRepository mealPlanRepository;
    private final DailyMealPlanRepository dailyMealPlanRepository;
    private final MealPlanMapper mealPlanMapper;
    private final DailyMealPlanMapper dailyMealPlanMapper;

    @Override
    public MealPlanResponse createMealPlan(ClientEntity clientEntity) {
        MealPlanEntity mealPlanEntity = new MealPlanEntity();
        mealPlanEntity.setClient(clientEntity);
        mealPlanEntity.setLastModified(LocalDateTime.now());
        mealPlanEntity.setLastModifiedBy(clientEntity.getCoach());
        mealPlanRepository.save(mealPlanEntity);

        final List<DayOfWeek> days = Arrays.asList(DayOfWeek.values());
        for (DayOfWeek day : days) {
            DailyMealPlanEntity dailyMealPlanEntity = new DailyMealPlanEntity();
            dailyMealPlanEntity.setDay(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            dailyMealPlanEntity.setAmSnack("/");
            dailyMealPlanEntity.setBreakfast("/");
            dailyMealPlanEntity.setDinner("/");
            dailyMealPlanEntity.setLunch("/");
            dailyMealPlanEntity.setMealPlan(mealPlanEntity);
            dailyMealPlanEntity.setPmSnack("/");

            dailyMealPlanRepository.save(dailyMealPlanEntity);
        }

        return createMealPlanResponse(mealPlanEntity);
    }

    private MealPlanResponse createMealPlanResponse(final MealPlanEntity mealPlanEntity) {
        MealPlanResponse mealPlanResponse = mealPlanMapper.entityToDto(mealPlanEntity);

        mealPlanResponse
                .setDailyMealPlans(dailyMealPlanMapper.entitiesToDtos(dailyMealPlanRepository.findByMealPlanId(mealPlanEntity.getId())));

        return mealPlanResponse;
    }

    @Override
    public MealPlanResponse getMealPlanByClientUuid(final String clientUuid) throws ApiException {
        final UserEntity userEntity = UserContext.getCurrentContext().getUser();
        final CoachEntity coachEntity = userEntity.getCoach();

        Optional<UserEntity> clientUser = userRepository.findById(clientUuid);
        if (clientUser.isEmpty()) {
            throw new NotFoundException("The client with the provided UUID could not be found.");
        }
        if (Objects.nonNull(clientUser.get().getCoach())) {
            throw new BadRequestException("The provided UUID is related to the coach user.");
        }
        if (!clientUser.get().getClient().getCoach().getId().equals(coachEntity.getId())) {
            throw new BadRequestException("The coach and the client are not related.");
        }

        Optional<MealPlanEntity> mealPlan = mealPlanRepository.findByClientUuid(clientUuid);
        return createMealPlanResponse(mealPlan.get());
    }

    @Override
    public MealPlanResponse getMealPlanClient() {
        final UserEntity userEntity = UserContext.getCurrentContext().getUser();
        final ClientEntity clientEntity = userEntity.getClient();

        return createMealPlanResponse(mealPlanRepository.findByClientUuid(clientEntity.getUser().getUuid()).get());
    }

}