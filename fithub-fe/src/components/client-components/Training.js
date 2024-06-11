import "../../css/Training.css";
import LoadingSpinner from "../LoadingSpinner";
import { useState, useEffect } from "react";

// Podaci o dostupnim terminima
const availableTermins = [
  // Monday
  {
    "startTime": "08:00",
    "endTime": "09:00",
    "day": "Monday"
  },
  {
    "startTime": "09:00",
    "endTime": "10:00",
    "day": "Monday"
  },
  {
    "startTime": "10:00",
    "endTime": "11:00",
    "day": "Monday"
  },
  {
    "startTime": "11:00",
    "endTime": "12:00",
    "day": "Monday"
  },
  {
    "startTime": "12:00",
    "endTime": "13:00",
    "day": "Monday"
  },
  {
    "startTime": "13:00",
    "endTime": "14:00",
    "day": "Monday"
  },
  // Wednesday
  {
    "startTime": "08:00",
    "endTime": "09:00",
    "day": "Wednesday"
  },
  {
    "startTime": "09:00",
    "endTime": "10:00",
    "day": "Wednesday"
  },
  {
    "startTime": "10:00",
    "endTime": "11:00",
    "day": "Wednesday"
  },
  {
    "startTime": "11:00",
    "endTime": "12:00",
    "day": "Wednesday"
  },
  {
    "startTime": "12:00",
    "endTime": "13:00",
    "day": "Wednesday"
  },
  {
    "startTime": "13:00",
    "endTime": "14:00",
    "day": "Wednesday"
  },
  // Friday
  {
    "startTime": "08:00",
    "endTime": "09:00",
    "day": "Friday"
  },
  {
    "startTime": "09:00",
    "endTime": "10:00",
    "day": "Friday"
  },
  {
    "startTime": "10:00",
    "endTime": "11:00",
    "day": "Friday"
  },
  {
    "startTime": "11:00",
    "endTime": "12:00",
    "day": "Friday"
  },
  {
    "startTime": "12:00",
    "endTime": "13:00",
    "day": "Friday"
  },
  {
    "startTime": "13:00",
    "endTime": "14:00",
    "day": "Friday"
  },
  // Tuesday
  {
    "startTime": "14:00",
    "endTime": "15:00",
    "day": "Tuesday"
  },
  {
    "startTime": "15:00",
    "endTime": "16:00",
    "day": "Tuesday"
  },
  {
    "startTime": "16:00",
    "endTime": "17:00",
    "day": "Tuesday"
  },
  {
    "startTime": "17:00",
    "endTime": "18:00",
    "day": "Tuesday"
  },
  {
    "startTime": "18:00",
    "endTime": "19:00",
    "day": "Tuesday"
  },
  {
    "startTime": "19:00",
    "endTime": "20:00",
    "day": "Tuesday"
  },
  // Thursday
  {
    "startTime": "14:00",
    "endTime": "15:00",
    "day": "Thursday"
  },
  {
    "startTime": "15:00",
    "endTime": "16:00",
    "day": "Thursday"
  },
  {
    "startTime": "16:00",
    "endTime": "17:00",
    "day": "Thursday"
  },
  {
    "startTime": "17:00",
    "endTime": "18:00",
    "day": "Thursday"
  },
  {
    "startTime": "18:00",
    "endTime": "19:00",
    "day": "Thursday"
  },
  {
    "startTime": "19:00",
    "endTime": "20:00",
    "day": "Thursday"
  }
];


// Funkcija za provjeru dostupnih termina
const getTermin = (day, time) => {
  const termin = availableTermins.find(
    res => res.day === day && res.startTime === time
  );
  return termin ? `${termin.startTime} - ${termin.endTime}` : '';
};

const Training = () => {
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    setTimeout(() => {
      setIsLoading(false);
    }, 500);
  }, []);

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
                <td className="availableTerm">{getTermin("Monday", timeString)}</td>
                <td className="availableTerm">{getTermin("Tuesday", timeString)}</td>
                <td className="availableTerm">{getTermin("Wednesday", timeString)}</td>
                <td className="availableTerm">{getTermin("Thursday", timeString)}</td>
                <td className="availableTerm">{getTermin("Friday", timeString)}</td>
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
}

export default Training;
