export const getMealPlan = async () => {
  try {
    const response = await fetch('/fithub-mealplan-service/client/1/dailyMealPlan');
    
    if (!response.ok) {
      throw new Error('Failed to fetch mealplan data');
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching mealplan data:', error);
    return [] ;
  }
};


export const getChatroomData = async () => {
  try {
    const response = await fetch('/fithub-chat-service/chatroom/29262910-09fb-40e8-9db7-c05372ad6ae6/data');
    
    if (!response.ok) {
      throw new Error('Failed to fetch chatroom data');
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching chatroom data:', error);
    return { "chatroomDetails": "", "messages": [], "participants": [] };
  }
};

export const sendNewMessage = async (messageData) => {
  try {
    const response = await fetch('/fithub-chat-service/chatroom/29262910-09fb-40e8-9db7-c05372ad6ae6/send-message', {
      method: 'POST', 
      headers: {
        'Content-Type': 'application/json' 
      },
      body: JSON.stringify(messageData) 
    });

    if (!response.ok) {
      throw new Error('Failed to send message');
    }

    const responseData = await response.json(); 

    return responseData; 
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
