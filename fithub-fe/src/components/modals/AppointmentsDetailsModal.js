import React from "react";
import "../../css/AppointmentsDetailsModal.css";

const AppointmentsDetailsModal = ({ closeModal, selectedTermin }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <span className="close" onClick={closeModal}>&times;</span>
        <h2>Details about Appointments</h2>
        <p><strong>Day:</strong> {selectedTermin.day}</p>
        <p><strong>Time:</strong> {selectedTermin.startTime} - {selectedTermin.endTime}</p>
        <p>
          <strong>Capacity:</strong> {selectedTermin.capacity}{" "}
          {selectedTermin.capacity === 10 && (
            <span className="full-termin">(full appointment)</span>
          )}
        </p>
        <div className="button-container">
          <button onClick={closeModal}>Close</button>
        </div>
      </div>
    </div>
  );
}

export default AppointmentsDetailsModal;
