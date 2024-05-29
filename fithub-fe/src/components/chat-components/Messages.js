import "../../css/Chatroom.css";

import ScrollToBottom from "react-scroll-to-bottom";
import UserProfileImage from "./UserProfileImage";

function Messages({ messages, nickname }) {
  function extractTime(created) {
    const dateTime = new Date(created);
    const hours = dateTime.getHours();
    const minutes = dateTime.getMinutes();
    const formattedTime = `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
    return formattedTime;
  }

  return (
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
                <div className='message-time-info'>
                  <p className="message-time">{ extractTime(messageData.created) }</p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </ScrollToBottom>
    </div>
  );
}

export default Messages