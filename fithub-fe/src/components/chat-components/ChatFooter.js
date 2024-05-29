import "../../css/Chatroom.css";

import { useState } from "react";

import { sendNewMessage } from "../../api/ClientApi";

function ChatFooter({socket, data, setMessages}) {

  const [newMessage, setNewMessage] = useState("")

  const sendMessage = async () => {
    if(newMessage !== "") {
      const messageTime = new Date(Date.now())
      const messageData = {
        userId: "29262910-09fb-40e8-9db7-c05372ad6ae6",
        content: newMessage,
        created: messageTime
      }
      setNewMessage("")
      sendNewMessage(messageData)
    }
  }
  
  return (
    <div id="chat-footer">
      <input 
        type="text" 
        placeholder="Message..." 
        id="message-input"
        onChange={ 
          (event) => {
          setNewMessage(event.target.value)
        }
        }
        onKeyDown={
          (event) => {
            if(event.key === "Enter") {
              sendMessage()
            }
          }
        }
        ></input>
      <img id="send-message-button" src="/images/send-button.png" alt="SEND" onClick={ () => { sendMessage() }}></img>
    </div>
  )
}

export default ChatFooter