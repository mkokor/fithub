import React from "react";
import ScrollToBottom from "react-scroll-to-bottom";
import UserProfileImage from "./UserProfileImage";
import MessageInput from "./MessageInput";

const Messages = ({ messages, userData, sendMessage, newMessage, setNewMessage }) => {
  const extractTime = (created) => {
    const dateTime = new Date(created);
    const hours = dateTime.getHours();
    const minutes = dateTime.getMinutes();
    return `${hours.toString().padStart(2, "0")}:${minutes.toString().padStart(2, "0")}`;
  };

  return (
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
      <MessageInput
        newMessage={newMessage}
        setNewMessage={setNewMessage}
        sendMessage={sendMessage}
      />
    </div>
  );
};

export default Messages;
