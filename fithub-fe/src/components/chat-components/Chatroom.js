import React, { useState, useEffect } from "react";
import "../../css/Chatroom.css";
import LoadingSpinner from "../LoadingSpinner";
import Participants from "./Participants";
import Messages from "./Messages"; 
import { getChatroomData, getChatroomMessages } from "../../api/ChatApi";
import { over } from "stompjs";
import SockJS from "sockjs-client";

var stompClient = null;

const Chatroom = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [messages, setMessages] = useState([]); 
  const [chatroomData, setChatroomData] = useState(null);
  const [newMessage, setNewMessage] = useState("");
  const [userData, setUserData] = useState({
    username: JSON.parse(localStorage.getItem("user")).username,
    connected: false,
    message: ''
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getChatroomData();
        setChatroomData(data);
        
        try {
          const messagesData = await getChatroomMessages();
          setMessages(messagesData);
        } catch(error) {
          console.error("Error fetching messages:", error);
        }
      } catch (error) {
        console.error("Error fetching chatroom data:", error);
      } finally {
        setIsLoading(false);
      }
    };

    if (chatroomData === null) {
      fetchData();
    } else {
      connect(chatroomData.chatroomDetails.id);
    }
  }, [chatroomData]);

  const connect = (chatroomId) => {
    let socket = new SockJS('http://localhost:8002/ws');
    stompClient = over(socket);
    stompClient.connect({}, () => onConnected(chatroomId), onError);
  };

  const onConnected = (chatroomId) => {
    setUserData(prevState => ({ ...prevState, connected: true }));
    stompClient.subscribe(`/chatroom/${chatroomId}`, onMessageReceived);
    userJoin();
  };

  const userJoin = () => {
    const chatMessage = {
      senderUsername: userData.username,
      type: "JOIN"
    };
    stompClient.send("/chat/message", {}, JSON.stringify(chatMessage));
  };

  const onMessageReceived = (payload) => {
    const payloadData = JSON.parse(payload.body);
    switch(payloadData.type) {
      case "JOIN":
        console.log("User joined:", payloadData.senderUsername);
        console.log('fdgteg', messages)
        break;
      case "MESSAGE":
        const newMessage = {
          content: payloadData.content,
          created: payloadData.created,
          username: payloadData.senderUsername
        }
        console.log(payloadData)
        setMessages(prevMessages => [...prevMessages, newMessage]);
        console.log('gewre' , messages)
        break;
      default:
        break;
    }   
  };

  const onError = (err) => {
    console.error("WebSocket error:", err);
  };

  const sendMessage = () => {
    if (stompClient && newMessage.trim() !== "") {
      const chatMessage = {
        senderUsername: userData.username,
        content: newMessage,
        type: "MESSAGE"
      };
      stompClient.send("/chat/message", {}, JSON.stringify(chatMessage));
      setNewMessage("");
    }
  };

  return (
    <div className="App">
      {isLoading || chatroomData === null ? (
        <LoadingSpinner />
      ) : (
        <div className="page-div" id="chat">
          <Participants participants={chatroomData.participants} />
          <Messages messages={messages} userData={userData} sendMessage={sendMessage} newMessage={newMessage} setNewMessage={setNewMessage} />
        </div>
        
      )}
    </div>
  );
};

export default Chatroom;
