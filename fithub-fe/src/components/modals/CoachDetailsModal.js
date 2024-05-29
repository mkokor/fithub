import React from 'react';
import '../../css/CoachDetailsModal.css';

const CoachDetailsModal = ({ show, onClose, coach }) => {
  if (!show) {
    return null;
  }

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <img src="/images/close.png" alt="X" className="close-button" onClick={onClose} />
        <p class="modal-title">Your coach will be:</p>
        {coach && (
          <>
            <img src={coach.imagePath} alt={`${coach.firstName} ${coach.lastName}`} className="coach-image" />
            <p id="coach-name">{coach.firstName} {coach.lastName}</p>
            <hr className='line'></hr>
            <p className="coach-biography">{coach.biography}</p>
          </>
        )}
      </div>
    </div>
  );
};

export default CoachDetailsModal;
