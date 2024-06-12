import "../../css/Schedule.css";
import LoadingSpinner from "../LoadingSpinner";
import { useState, useEffect } from "react";

const availableTermins = [
  {
    "id": 1,
    "coachId": 101,
    "startTime": "08:00",
    "endTime": "09:00",
    "capacity": 2,
    "day": "Monday"
  },
  {
    "id": 2,
    "coachId": 101,
    "startTime": "10:00",
    "endTime": "11:00",
    "capacity": 0,
    "day": "Monday"
  },
  {
    "id": 3,
    "coachId": 101,
    "startTime": "11:00",
    "endTime": "12:00",
    "capacity": 10,
    "day": "Monday"
  },
  {
    "id": 4,
    "coachId": 101,
    "startTime": "12:00",
    "endTime": "13:00",
    "capacity": 10,
    "day": "Monday"
  },
  {
    "id": 5,
    "coachId": 102,
    "startTime": "08:00",
    "endTime": "09:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "id": 6,
    "coachId": 102,
    "startTime": "09:00",
    "endTime": "10:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "id": 7,
    "coachId": 102,
    "startTime": "10:00",
    "endTime": "11:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "id": 8,
    "coachId": 102,
    "startTime": "11:00",
    "endTime": "12:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "id": 9,
    "coachId": 102,
    "startTime": "12:00",
    "endTime": "13:00",
    "capacity": 12,
    "day": "Wednesday"
  },
  {
    "id": 10,
    "coachId": 103,
    "startTime": "08:00",
    "endTime": "09:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "id": 11,
    "coachId": 103,
    "startTime": "09:00",
    "endTime": "10:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "id": 12,
    "coachId": 103,
    "startTime": "10:00",
    "endTime": "11:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "id": 13,
    "coachId": 103,
    "startTime": "11:00",
    "endTime": "12:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "id": 14,
    "coachId": 103,
    "startTime": "12:00",
    "endTime": "13:00",
    "capacity": 15,
    "day": "Friday"
  },
  {
    "id": 15,
    "coachId": 104,
    "startTime": "13:00",
    "endTime": "14:00",
    "capacity": 8,
    "day": "Tuesday"
  },
  {
    "id": 16,
    "coachId": 104,
    "startTime": "16:00",
    "endTime": "17:00",
    "capacity": 8,
    "day": "Tuesday"
  },
  {
    "id": 17,
    "coachId": 104,
    "startTime": "17:00",
    "endTime": "18:00",
    "capacity": 8,
    "day": "Tuesday"
  },
  {
    "id": 18,
    "coachId": 105,
    "startTime": "13:00",
    "endTime": "14:00",
    "capacity": 20,
    "day": "Thursday"
  },
  {
    "id": 19,
    "coachId": 105,
    "startTime": "14:00",
    "endTime": "15:00",
    "capacity": 20,
    "day": "Thursday"
  },
  {
    "id": 20,
    "coachId": 105,
    "startTime": "15:00",
    "endTime": "16:00",
    "capacity": 20,
    "day": "Thursday"
  },
  {
    "id": 21,
    "coachId": 105,
    "startTime": "16:00",
    "endTime": "17:00",
    "capacity": 20,
    "day": "Thursday"
  },
  {
    "id": 22,
    "coachId": 105,
    "startTime": "17:00",
    "endTime": "18:00",
    "capacity": 20,
    "day": "Thursday"
  }
];

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
        // Simulate an API call
        const data = availableTermins;
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
                  return (
                    <td key={day} className="termin">
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
