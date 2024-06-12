import { json } from "react-router-dom";
import { sendRequest } from "./GenericApi";

export const getClients = async () => {
  try {
    const request = {
      url: '/training-service/client',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching clients:', error);
    return [] ;
  }
};

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