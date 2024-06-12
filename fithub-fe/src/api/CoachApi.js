import { sendRequest } from "./GenericApi";

export const getAllAppointments = async () => {
  try {
    const request = {
      url: '/training-service/appointment',
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching appointments:', error);
    return [];
  }
};

export const getClients = async () => {
  try {
    const request = {
      url: `training-service/clients`,
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching clients:', error);
    return [];
  }
};

export const getMealPlan = async () => {
  try {
    const request = {
      url: `mealplan-service/mealplan`,
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

export const postMembership = async () => {
  try {
    const request = {
      url: `membership-service/membership`,
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching membership:', error);
    return [];
  }
};

export const getMembership = async () => {
  try {
    const request = {
      url: `membership-service/membership/report`,
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching membership:', error);
    return [];
  }
};