import { sendRequest } from './GenericApi.js'; 

export const getChatroomData = async () => {
  try {
    const request = {
      url: '/chat-service/chatroom/data',
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

export const sendNewMessage = async (messageData) => {
  try {
    const request = {
      url: '/chat-service/message/sendMessage',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(messageData)
    };
    const response = await sendRequest(request);
    return await response.json();
  } catch (error) {
    console.error('Error sending message:', error);
    return { 
      "id": "",
      "chatroomId": "",
      "username": "",
      "content": "",
      "created": ""
    };
  }
};
