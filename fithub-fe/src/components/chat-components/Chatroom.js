import React, { useState, useEffect } from "react";
import "../../css/Chatroom.css";
import LoadingSpinner from "../LoadingSpinner";
import ScrollToBottom from "react-scroll-to-bottom";
import { getChatroomData, getChatroomMessages } from "../../api/ChatApi";
import { over } from 'stompjs';
import SockJS from 'sockjs-client';

const UserProfileImage = ({ user }) => {
  function getColor(username) {
    function hashCode(str) {
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
  const [chatroomData, setChatroomData] = useState({});
  const [newMessage, setNewMessage] = useState("");
  const [userData, setUserData] = useState({
    username: '',
    connected: false,
    message: ''
  });

  useEffect(() => {
    console.log(userData);
  }, [userData]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        console.log("Fetching chatroom data...");
        const data = await getChatroomData();
        setChatroomData(data);
        console.log("Chatroom data fetched:", data);
        try {
          console.log("Fetching messages...");
          const messagesData = await getChatroomMessages();
          setMessages(messagesData);
          console.log("Chatroom messages fetched:", messagesData);
          const id = chatroomData.chatroomDetails.id;
          connect(id);
        } catch(error) {
          console.error("Error fetching messages:", error);
        }
      } catch (error) {
        console.error("Error fetching chatroom data:", error);
      } finally {
        setIsLoading(false);
      }
    };
    fetchData();
  }, []);

  const connect = () => {
    // SockJS connection
    let socket = new SockJS('http://localhost:8002/ws');
    stompClient = over(socket);

    // Configure headers
    stompClient.connect({  }, onConnected, onError);
    console.log("Connecting...");
  }

  const onConnected = (id) => {
      setUserData({...userData,"connected": true});
      stompClient.subscribe(`/chatroom/3`, onMessageReceived);
      userJoin();
  }

  const userJoin=()=>{
    var chatMessage = {
      senderUsername: JSON.parse(localStorage.getItem("user")).username,
      type:"JOIN"
    };
    stompClient.send("/chat/message", {}, JSON.stringify(chatMessage));
  }

  const onMessageReceived = (payload)=>{
  var payloadData = JSON.parse(payload.body);
  switch(payloadData.type){
      case "JOIN":
          console.log(`${payloadData.senderUsername} joined`)
          break;
      case "MESSAGE":
          messages.push(payloadData);
          setMessages([...messages]);
          break;
    }   
  }

  const onError = (err) => {
    console.log(err);
  }

  const sendMessage=()=>{
    console.log(stompClient);
      if (stompClient !== null) {
        var chatMessage = {
          senderUsername: JSON.parse(localStorage.getItem("user")).username,
          content: newMessage,
          type:"MESSAGE"
        };
        stompClient.send("/chat/message", {}, JSON.stringify(chatMessage));
        setUserData({...userData,"message": ""});
      }
  }

  const extractTime = (created) => {
    const dateTime = new Date(created);
    const hours = dateTime.getHours();
    const minutes = dateTime.getMinutes();
    const formattedTime = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
    return formattedTime;
  };

  return (
    <div className="App">
      {isLoading ? (
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
                  <div className="message" key={index}>
                    <UserProfileImage user={messageData.username} />
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
