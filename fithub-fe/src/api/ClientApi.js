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
    console.log("Fetched melpla")
    return await response.json();
  } catch (error) {
    console.error('Error fetching mealplan:', error);
    return [];
  }
};

export const getHomeNews = async () => {
  try {
    const response = await fetch(`/training-service/image/path`);
    if (!response.ok) {
      throw new Error('Failed to fetch news data');
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching home news: ', error);
    return [];
  }
}

