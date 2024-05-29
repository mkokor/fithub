import "../../css/Chatroom.css";
import UserProfileImage from "./UserProfileImage";


function Participants({participants}) {

  return (
    <div id="participants-div">
      <p id='participants-title'>Participants</p>
      <hr id="divider"></hr>
      <div id='participants'>
        {
          participants.map((participant) => {
            return (
              <div className="participant-div">
                <UserProfileImage user={participant.username}/>
                <p className="participant-nickname">{participant.username}</p>
              </div>
            )
          })
        }
      </div>
    </div>
  )
}

export default Participants