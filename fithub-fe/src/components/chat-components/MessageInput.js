import React from "react";

const MessageInput = ({ newMessage, setNewMessage, sendMessage }) => {
  return (
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
  );
};

export default MessageInput;
