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