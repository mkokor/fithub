import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/Options.css';

const Options = () => {
  const navigate = useNavigate();

  return (
    <div className="workout-styles-container">
      <div className="image-wrapper" onClick={() => navigate('/training')}>
        <img src="/images/training.jpg" alt="WEIGHT TRAINING" className="workout-image" />
        <p className="workout-style-p">TRAINING</p>
      </div>
      <div className="image-wrapper" onClick={() => navigate('/nutrition')}>
        <img src="/images/nutrition.jpg" alt="NUTRITION" className="workout-image" />
        <p className="workout-style-p">NUTRITION</p>
      </div>
      <div className="image-wrapper" onClick={() => navigate('/chatroom')}>
        <img src="/images/chat.jpg" alt="CHAT" className="workout-image" />
        <p className="workout-style-p">CHAT</p>
      </div>
    </div>
  );
};

export default Options;
