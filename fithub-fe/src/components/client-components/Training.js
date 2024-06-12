import React, { useState, useEffect } from "react";
import "../../css/Training.css";
import LoadingSpinner from "../LoadingSpinner";
import ConfirmReservationModal from "../modals/ConfirmReservationModal";
import { getAvailableAppointments } from "../../api/ClientApi"; 

const getTermin = (day, time, availableTermins) => {
  const termin = availableTermins.find(
    res => res.day === day && res.startTime === time && res.capacity > 1
  );
  return termin ? `${termin.startTime} - ${termin.endTime}` : '';
};

const Training = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [availableTermins, setAvailableTermins] = useState([]);
  const [selectedTermin, setSelectedTermin] = useState(null);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [lockedTermins, setLockedTermins] = useState([]);

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        const appointments = await getAvailableAppointments();
        setAvailableTermins(appointments);
        setIsLoading(false);
      } catch (error) {
        console.error('Error fetching appointments:', error);
        setErrorMessage(error.message || 'Something went wrong.');
        setIsLoading(false);
      }
    };

    fetchAppointments();
  }, []);

  const handleTerminClick = (day, time) => {
    const termin = availableTermins.find(
      res => res.day === day && res.startTime === time && res.capacity > 1
    );
    if (termin && !lockedTermins.includes(termin.id)) {
      setSelectedTermin(termin);
      setShowConfirmation(true);
      setLockedTermins(prevLockedTermins => [...prevLockedTermins, termin.id]);
    }
  };

  const closeModal = () => {
    if (selectedTermin && selectedTermin.capacity === 1) {
      setSelectedTermin(prevTermin => {
        return availableTermins.filter(termin => termin.id !== prevTermin.id);
      });
    }
    setSelectedTermin(null);
    setShowConfirmation(false);
  };
  
  const renderEvents = (
    <div id="training-page-div" className="training-page-div">
      <h1 id="training-page-title">Training</h1>
      <table className="training-table">
        <thead>
          <tr>
            <th>Time</th>
            <th>Monday</th>
            <th>Tuesday</th>
            <th>Wednesday</th>
            <th>Thursday</th>
            <th>Friday</th>
          </tr>
        </thead>
        <tbody>
          {Array.from({ length: 12 }, (_, index) => {
            const hour = 8 + index;
            const timeString = `${hour.toString().padStart(2, '0')}:00`;
            return (
              <tr key={timeString}>
                <td className="times">{timeString}</td>
                <td className={`availableTerm${lockedTermins.includes(`${timeString}-Monday`) ? ' locked' : ''}`} onClick={() => handleTerminClick("Monday", timeString)}>{getTermin("Monday", timeString, availableTermins)}</td>
                <td className={`availableTerm${lockedTermins.includes(`${timeString}-Tuesday`) ? ' locked' : ''}`} onClick={() => handleTerminClick("Tuesday", timeString)}>{getTermin("Tuesday", timeString, availableTermins)}</td>
                <td className={`availableTerm${lockedTermins.includes(`${timeString}-Wednesday`) ? ' locked' : ''}`} onClick={() => handleTerminClick("Wednesday", timeString)}>{getTermin("Wednesday", timeString, availableTermins)}</td>
                <td className={`availableTerm${lockedTermins.includes(`${timeString}-Thursday`) ? ' locked' : ''}`} onClick={() => handleTerminClick("Thursday", timeString)}>{getTermin("Thursday", timeString, availableTermins)}</td>
                <td className={`availableTerm${lockedTermins.includes(`${timeString}-Friday`) ? ' locked' : ''}`} onClick={() => handleTerminClick("Friday", timeString)}>{getTermin("Friday", timeString, availableTermins)}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
      {errorMessage && <div className="error-message">{errorMessage}</div>}
    </div>
  );

  return (
    <div className="App">
      {isLoading ? <LoadingSpinner /> : renderEvents}
      {showConfirmation && selectedTermin && (
        <ConfirmReservationModal closeModal={closeModal} selectedTermin={selectedTermin} />
      )}
    </div>
  );
};

export default Training;