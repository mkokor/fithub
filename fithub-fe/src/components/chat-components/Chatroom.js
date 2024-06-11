import React, { useState, useEffect, useRef } from "react";
import "../../css/Chatroom.css";
import LoadingSpinner from "../LoadingSpinner";
import ScrollToBottom from "react-scroll-to-bottom";
import { getChatroomData, getChatroomMessages } from "../../api/ChatApi";
import { over } from 'stompjs';
import SockJS from 'sockjs-client';

const UserProfileImage = ({ user }) => {
  function getColor(username) {
    function hashCode(str) {
      if (!str) return 0;
      let hash = 0;
      for (let i = 0; i < str.length; i++) {
        hash = str.charCodeAt(i) + ((hash << 5) - hash);
      }
      return hash;
    }

    function intToRGB(i) {
      const c = (i & 0x00FFFFFF).toString(16).toUpperCase();
      return '#' + '00000'.substring(0, 6 - c.length) + c;
    }

    const colorHash = hashCode(username);
    return intToRGB(colorHash);
  }

  const initials = user ? user.charAt(0).toUpperCase() : '';

  const circleStyle = {
    width: '50px',
    height: '50px',
    borderRadius: '50%',
    backgroundColor: getColor(user),
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    color: 'white',
    fontSize: '24px',
    marginTop: '13px',
  };

  return (
    <div style={circleStyle}>
      {initials}
    </div>
  );
};

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

  const extractTime = (created) => {
    const dateTime = new Date(created);
    const hours = dateTime.getHours();
    const minutes = dateTime.getMinutes();
    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
  };

  return (
    <div className="App">
      {isLoading || chatroomData === null ? (
        <LoadingSpinner />
      ) : (
        <div className="page-div" id="chat">
          <div id="participants-div">
            <p id="participants-title">Participants</p>
            <hr id="divider" />
            <div id="participants">
              {chatroomData.participants?.map((participant, index) => (
                <div className="participant-div" key={index}>
                  <UserProfileImage user={participant.username} />
                  <p className="participant-nickname">{participant.username}</p>
                </div>
              ))}
            </div>
          </div>
          <div id="message-section">
            <div id="chat-body">
              <ScrollToBottom className="message-container">
                {messages.map((messageData, index) => (
                  <div className={messageData.username === userData.username ? "message-you" : "message"} key={index}>
                    {messageData.username !== userData.username && <UserProfileImage user={messageData.username} />}
                    <div className="message-div">
                      <div className="message-author-info">
                        <p className="message-author">{messageData.username}</p>
                      </div>
                      <div className="text-time-div">
                        <div className="message-content">
                          <p className="message-text">{messageData.content}</p>
                        </div>
                        <div className="message-time-info">
                          <p className="message-time">{extractTime(messageData.created)}</p>
                        </div>
                      </div>
                    </div>
                    {messageData.username === userData.username && <UserProfileImage user={messageData.username} />}
                  </div>
                ))}
              </ScrollToBottom>
            </div>
            <div id="chat-footer">
              <input
                type="text"
                placeholder="Message..."
                id="message-input"
                value={newMessage}
                onChange={(event) => setNewMessage(event.target.value)}
                onKeyDown={(event) => {
                  if (event.key === "Enter") {
                    sendMessage();
                  }
                }}
              />
              <img
                id="send-message-button"
                src="/images/send-button.png"
                alt="SEND"
                onClick={sendMessage}
              />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Chatroom;
