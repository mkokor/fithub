import React from "react";
import UserProfileImage from "./UserProfileImage";

const Participants = ({ participants }) => {
  return (
    <div id="participants-div">
      <p id="participants-title">Participants</p>
      <hr id="divider" />
      <div id="participants">
        {participants.map((participant, index) => (
          <div className="participant-div" key={index}>
            <UserProfileImage user={participant.username} />
            <p className="participant-nickname">{participant.username}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Participants;
