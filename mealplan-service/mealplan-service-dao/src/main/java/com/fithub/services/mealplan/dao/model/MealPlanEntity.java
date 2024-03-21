package com.fithub.services.mealplan.dao.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "meal_plans")
public class MealPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;
    
    @Column(name = "modified")
    private LocalDateTime modifiedDate;
    
    @ManyToOne
    @JoinColumn(name = "modified_by", nullable = false)
    private CoachEntity modifiedBy;
    
    @OneToMany(mappedBy = "mealPlan", cascade = CascadeType.ALL)
    private List<DailyMealPlanEntity> mealPlans;
    
}