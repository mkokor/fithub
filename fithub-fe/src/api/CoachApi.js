export const updateMealPlan = async(clientId, mealPlan) => {
    try{

        
        const response = await fetch(`/mealplan-service/mealplan/${clientId}/updateMealPlan`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(mealPlan) 
        });

        if (!response.ok) {
            throw new Error('Failed to update meal plan');
        }
    
        const responseData = await response.json(); 
    
        return responseData; 
    } catch(error){
        console.error('Error updating meal plan:', error);
        return { 
            "day": "",
            "breakfast": "",
            "amSnack": "",
            "lunch": "",
            "dinner": "",
            "pmSnack": ""
        };
    }
}

export const updatePaymentRecord = async(clientId, payment) => {
    try{
        const response = await fetch(`/mealplan-service/membership/${clientId}/updatePaymentRecord`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(payment) 
        });

        if (!response.ok) {
            throw new Error('Failed to update payment record');
        }
    
        const responseData = await response.json(); 
    
        return responseData; 
    } catch(error){
        console.error('Error updating meal plan:', error);
        return { 
            "paid": false,
            "date": []
        };
    }
}

export const getAppointments = async(coachId) => {
    try{
        const response = await fetch(`/training-service/appointment/${coachId}/getAppointmentsForCoach`);

        if (!response.ok) {
            throw new Error('Failed to fetch coach appointments');
        }
    
        const responseData = await response.json(); 
    
        return responseData; 
    } catch(error){
        console.error('Error fetching coach appointments:', error);
        return [];
    }
}