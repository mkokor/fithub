import "../../css/Schedule.css";
import LoadingSpinner from "../LoadingSpinner";
import { useState, useEffect } from "react";

const reservations = [
  {
    "startTime": "08:00",
    "endTime": "09:00",
    "capacity": 10,
    "day": "Monday"
  },
  {
    "startTime": "10:00",
    "endTime": "11:00",
    "capacity": 10,
    "day": "Monday"
  },
  {
    "startTime": "11:00",
    "endTime": "12:00",
    "capacity": 10,
    "day": "Monday"
  },
  {
    "startTime": "12:00",
    "endTime": "13:00",
    "capacity": 10,
    "day": "Monday"
  },
  {
    "startTime": "08:00",
    "endTime": "09:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "startTime": "09:00",
    "endTime": "10:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "startTime": "10:00",
    "endTime": "11:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "startTime": "11:00",
    "endTime": "12:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "startTime": "12:00",
    "endTime": "13:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "startTime": "08:00",
    "endTime": "09:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "startTime": "09:00",
    "endTime": "10:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "startTime": "10:00",
    "endTime": "11:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "startTime": "11:00",
    "endTime": "12:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "startTime": "12:00",
    "endTime": "13:00",
    "capacity": 15,
    "day": "Friday"
  },
  
  {
    "startTime": "13:00",
    "endTime": "14:00",
    "capacity": 8,
    "day": "Tuesday"
  },
  {
    "startTime": "16:00",
    "endTime": "17:00",
    "capacity": 8,
    "day": "Tuesday"
  },
  {
    "startTime": "17:00",
    "endTime": "18:00",
    "capacity": 8,
    "day": "Tuesday"
  },
  {
    "startTime": "13:00",
    "endTime": "14:00",
    "capacity": 20,
    "day": "Thursday"
  },
  {
    "startTime": "14:00",
    "endTime": "15:00",
    "capacity": 20,
    "day": "Thursday"
  },
  {
    "startTime": "15:00",
    "endTime": "16:00",
    "capacity": 20,
    "day": "Thursday"
  },
  {
    "startTime": "16:00",
    "endTime": "17:00",
    "capacity": 20,
    "day": "Thursday"
  },
  {
    "startTime": "17:00",
    "endTime": "18:00",
    "capacity": 20,
    "day": "Thursday"
  }
];

// Funkcija za provjeru rezervacija
const getReservation = (day, time) => {
  const reservation = reservations.find(
    res => res.day === day && res.startTime === time
  );
  return reservation ? `${reservation.startTime} - ${reservation.endTime}` : '';
};

const Schedule = () => {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setTimeout(() => {
      setIsLoading(false);
    }, 500);
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
                <td className="termin">{getReservation("Monday", timeString)}</td>
                <td className="termin">{getReservation("Tuesday", timeString)}</td>
                <td className="termin">{getReservation("Wednesday", timeString)}</td>
                <td className="termin">{getReservation("Thursday", timeString)}</td>
                <td className="termin">{getReservation("Friday", timeString)}</td>
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
