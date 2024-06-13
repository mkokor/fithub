import React, { useState, useEffect } from "react";
import "../../css/Schedule.css";
import LoadingSpinner from "../LoadingSpinner";
import AppointmentsDetailsModal from "../modals/AppointmentsDetailsModal";
import { getAllAppointments } from "../../api/CoachApi"; 

const Schedule = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [reservations, setReservations] = useState([]);
  const [selectedTermin, setSelectedTermin] = useState(null);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const appointments = await getAllAppointments();
        setReservations(appointments);
        setIsLoading(false);
      } catch (error) {
        console.error('Failed to fetch appointments:', error);
        setIsLoading(false);
      }
    };

    fetchReservations();
  }, []);

  const handleTerminClick = (termin) => {
    setSelectedTermin(termin);
  };

  const renderEvents = (
    <div id="schedule-page-div" className="schedule-page-div">
      <h1 id="schedule-page-title">SCHEDULE</h1>
      <table className="schedule-table">
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
                {['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'].map(day => {
                  const reservation = reservations.find(res => res.day === day && res.startTime === timeString);
                  return (
                    <td key={day} className="termin" onClick={() => handleTerminClick(reservation)}>
                      {reservation ? `${reservation.startTime} - ${reservation.endTime}` : ''}
                    </td>
                  );
                })}
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );

  return (
    <div className="App">
      {isLoading ? <LoadingSpinner /> : renderEvents}
      {selectedTermin && (
        <AppointmentsDetailsModal closeModal={() => setSelectedTermin(null)} selectedTermin={selectedTermin} />
      )}
    </div>
  );
};

export default Schedule;