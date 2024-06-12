import React from "react";
import "../../css/ConfirmReservationModal.css";

const ConfirmReservationModal = ({ closeModal, selectedTermin }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <span className="close" onClick={closeModal}>&times;</span>
        <h2>Reservation Confirmed</h2>
        <p className="confirmation">Your reservation for the following slot has been successfully recorded:</p>
        <p><strong>Day:</strong> {selectedTermin.day}</p>
        <p><strong>Time:</strong> {selectedTermin.startTime} - {selectedTermin.endTime}</p>
        <p><strong>Capacity:</strong> {selectedTermin.capacity}</p>
        <div className="button-container">
          <button onClick={closeModal}>Close</button>
        </div>
      </div>
    </div>
  );
}

export default ConfirmReservationModal;