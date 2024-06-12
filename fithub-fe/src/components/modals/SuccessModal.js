import React from 'react';
import '../../css/SuccessModal.css'; // Import the CSS file for styling

const SuccessModal = ({ message, onClose }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <img src="/images/close.png" alt="Close" className="close-button" onClick={onClose} />
        <p className="modal-title">{message}</p>
        <button className="ok-button" onClick={onClose}>OK</button>
      </div>
    </div>
  );
};

export default SuccessModal;