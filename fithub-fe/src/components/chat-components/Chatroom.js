import '../../css/Chatroom.css';

import LoadingSpinner from "../LoadingSpinner";
import Messages from "./Messages";
import Participants from "./Participants";
import ChatFooter from "./ChatFooter";

import { useState, useEffect } from "react";
import { getChatroomData } from "../../api/ClientApi";

const Chatroom = () => {
  const [chatroomData, setChatroomData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getChatroomData();
        setChatroomData(data);
        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching chatroom data:", error);
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  function handleChatInfo() {
    const chatInfo = document.getElementById("chat-info-div");
    if (chatInfo) {
      chatInfo.classList.toggle("chat-info-closed");
      chatInfo.classList.toggle("chat-info-opened");
    }
  }

  return (
    <div className="App">
      {isLoading ? (
        <LoadingSpinner />
      ) :  (
          <div className="page-div" id="chat">
              <Participants participants={chatroomData.participants} />
              <div id="message-section">
                <Messages messages={chatroomData.messages} />
                <ChatFooter />
              </div>
          </div>
      )}
    </div>
  );
};

export default Chatroom;
