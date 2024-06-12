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

export const getMyStats = async () => {
  try {
    const request = {
      url: '/training-service/progression-stats/latest',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching client stats:', error);
    return { 
      
     };
  }
};

export const getScoreBoard = async () => {
  try {
    const request = {
      url: '/training-service/progression-stats/rang-list',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching score board data:', error);
    return [];
  }
};

export const getReport = async () => {
  var token = JSON.parse(localStorage.getItem("user")).accessToken;
  fetch('/training-service/progression-stats/report', {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
  .then(response => {
    return response.blob(); 
  })
  .then(blob => {
    const url = window.URL.createObjectURL(new Blob([blob]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'stats_history_fithub.xlsx'); 
    document.body.appendChild(link);
    link.click();
    link.parentNode.removeChild(link); 
  })
  .catch(error => {
    console.error('Greška pri preuzimanju fajla:', error);
  });
};
