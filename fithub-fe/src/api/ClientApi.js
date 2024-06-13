import { sendRequest } from "./GenericApi";

export const getMealPlan = async () => {
  try {
    const request = {
      url: '/mealplan-service/mealplan',
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching mealplan:', error);
    return [];
  }
};


