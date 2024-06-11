import "../../css/Schedule.css";
import LoadingSpinner from "../LoadingSpinner";
import { useState, useEffect } from "react";
import { getAvailableAppointments } from '../../api/CoachApi';

const getReservation = (reservations, day, time) => {
  return reservations.find(
    res => res.day === day && res.startTime === time
  );
};

const Schedule = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const data = await getAvailableAppointments();
        setReservations(data);
        setIsLoading(false);
      } catch (error) {
        console.error('Failed to fetch reservations:', error);
        setIsLoading(false);
      }
    };

    fetchReservations();
  }, []);

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
                  const reservation = getReservation(reservations, day, timeString);
                  const cellStyle = {
                    backgroundColor: reservation ? 'rgb(116, 141, 131, 0.548)' : 'rgb(233, 122, 122, 0.548)',
                    color: 'white',
                  };
                  return (
                    <td key={day} className="termin" style={cellStyle}>
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
    </div>
  );
};

export default Schedule;
