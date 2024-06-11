import React from 'react';
import '../css/Options.css'; 

const Options = () => {

  return (
    <div className="workout-styles-container">
      <div className="image-wrapper">
        <img src="/images/training.jpg" alt={`WEIGHT TRAINING`} className="workout-image" />
        <p className='workout-style-p'>TRAINING</p>
      </div>
      <div className="image-wrapper">
        <img src="/images/nutrition.jpg" alt={`YOGA`} className="workout-image" />
        <p className='workout-style-p'>NUTRITION</p>
      </div>
      <div className="image-wrapper">
        <img src="/images/chat.jpg" alt={`PILATES`} className="workout-image" />
        <p className='workout-style-p'>CHAT</p>
      </div>
    </div>
  );
};

export default Options;
