export const getMealPlan = async () => {
  try {
    const response = await fetch('/mealplan-service/mealplan');
    
    if (!response.ok) {
      throw new Error('Failed to fetch mealplan data');
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching mealplan data:', error);
    return [] ;
  }
};

