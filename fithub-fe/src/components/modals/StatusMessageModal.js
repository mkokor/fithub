import React from 'react';
import '../../App.css';

const StatusMessageModal = ({ onClose, message }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <img src="/images/close.png" alt="X" className="close-button" onClick={onClose} />
        <p>{message}</p>
        <button className="button-element dark-button" onClick={onClose}>OK</button>
      </div>
    </div>
  );
};

export default StatusMessageModal;
