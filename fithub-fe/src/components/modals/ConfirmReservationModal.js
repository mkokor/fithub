import React, { useState } from "react";
import "../../css/ConfirmReservationModal.css";

const ConfirmReservationModal = ({ closeModal, selectedTermin }) => {
  const [isReserved, setIsReserved] = useState(false);

  const handleConfirmReservation = () => {
    setIsReserved(true);
    closeModal();
  };

  if (selectedTermin.capacity === 10) {
    return (
      <div className="modal-overlay">
        <div className="modal-content">
          <span className="close" onClick={closeModal}>&times;</span>
          <h2>Full Appointment</h2>
          <p className="full">The appointment slot is full! Please reserve another one!</p>
          <div className="button-container">
            <button onClick={closeModal}>Close</button>
          </div>
        </div>
      </div>
    );
  } else if (!isReserved) {
    return (
      <div className="modal-overlay">
        <div className="modal-content">
          <span className="close" onClick={closeModal}>&times;</span>
          <h2>{`${selectedTermin.day}, ${selectedTermin.startTime} - ${selectedTermin.endTime}`}</h2>
          <p className="confirmation">Your reservation has been recorded!</p>
          <div className="button-container">
            <button onClick={handleConfirmReservation}>Close</button>
          </div>
        </div>
      </div>
    );
  } else {
    return null; // Termin je rezervisan i zaleđen, pa se modal ne prikazuje
  }
};

export default ConfirmReservationModal;
