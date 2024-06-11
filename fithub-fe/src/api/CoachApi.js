import { sendRequest } from "./GenericApi";

export const getAvailableAppointments = async () => {
  try {
    const request = {
      url: '/appointment-service/appointments',
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
