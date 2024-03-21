package com.fithub.services.mealplan.dao.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "daily_meal_plans")
public class DailyMealPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    
    @Column(name = "day")
    private String day;
    
    @Column(name = "beakfast")
    private String breakfast;
    
    @Column(name = "am_snack")
    private String amSnack;
    
    @Column(name = "lunch")
    private String lunch;
    
    @Column(name = "dinner")
    private String dinner;
    
    @Column(name = "pm_snack")
    private String pmSnack;
    
    @ManyToOne
    @JoinColumn(name = "meal_plan_id", nullable = false)
    private MealPlanEntity mealPlan;
    
    
}