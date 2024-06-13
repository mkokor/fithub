import { sendRequest } from "./GenericApi";

export const update = async (id, meal) => {
  try {
      const request = {
          url: `/mealplan-service/daily-mealplan/${id}`,
          method: 'PUT',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(meal)
      };

      

      const response = await sendRequest(request);

      

      if (!response.ok) {
          console.error('Response not OK:', response);
          throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      console.log('Update response data JSON:', data); // Dodato za debug
      return data;
  } catch (error) {
      console.error('Error fetching update data:', error);
      return {};
  }
};

export const pay = async (payment) => {
  
  try {
    const request = {
      url: '/membership-service/payment-record/',
      method: 'PUT',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify(payment)
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error processing payment:', error);
    throw error;
  }
}


export const getClients = async () => {
  try {
    const request = {
      url: `/training-service/client`,
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

export const getMealPlanByClientId = async (id) => {
  try {
    const request = {
      url: `/mealplan-service/mealplan/${id}`,
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
}

export const getStatsForClient = async (id) => {
  try {
    const request = {
      url: `/training-service/progression-stats/client/${id}`,
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching client stats history:', error);
    return [] ;
  }
};

export const addNewStatsForClient = async (data) => {
  try {
    const request = {
      url: '/training-service/progression-stats',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data)
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error adding stats:', error);
    return {
    };
  }
};
