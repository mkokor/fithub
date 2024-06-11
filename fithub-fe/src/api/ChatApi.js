import { sendRequest } from './GenericApi.js'; 

export const getChatroomData = async () => {
  try {
    const request = {
      url: `/chat-service/chatroom/data`,
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching chatroom data:', error);
    return { "chatroomDetails": "", "participants": [] };
  }
};

export const getChatroomMessages = async () => {
  try {
    const request = {
      url: '/chat-service/message',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error fetching chatroom messages:', error);
    return { "messages": [] };
  }
};

export const sendNewMessage = async () => {
  try {
    var token = JSON.parse(localStorage.getItem("user")).accessToken;
    const request = {
      url: '/chat-service/message',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error sending message:', error);
    return {
      "id": null,
      "chatroomId": null,
      "username": "",
      "content": "",
      "created": ""
    };
  }
};
