import React from 'react';
import '../../css/ConfirmDeleteModal.css'; // Import the CSS file for styling

const ConfirmDeleteModal = ({ onConfirm, onCancel }) => {
    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <img src="/images/close.png" alt="Close" className="close-button" onClick={onCancel} />
                <p className="modal-title">Are you sure you want to delete this client?</p>
                <div className="modal-buttons">
                    <button className="cancel-button" onClick={onCancel}>No</button>
                    <button className="confirm-button" onClick={onConfirm}>Yes</button>
                </div>
            </div>
        </div>
    );
};

export default ConfirmDeleteModal;
