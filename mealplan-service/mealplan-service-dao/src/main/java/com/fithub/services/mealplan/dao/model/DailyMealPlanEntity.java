package com.fithub.services.mealplan.dao.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String day;
    
    @Column(name = "breakfast")
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String breakfast;
    
    @Column(name = "am_snack")
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String amSnack;
    
    @Column(name = "lunch")
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String lunch;
    
    @Column(name = "dinner")
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String dinner;
    
    @Column(name = "pm_snack")
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String pmSnack;
    
    @ManyToOne
    @JoinColumn(name = "meal_plan", nullable = false)
    private MealPlanEntity mealPlan;
    
    
}