import "../../css/Training.css";

import LoadingSpinner from "../LoadingSpinner";

import { useState, useEffect } from "react";


const Schedule = () => {
  const [isLoading, setIsLoading] = useState(true);

  const renderEvents = (
    <div id="schedule-page-div" className="page-div">
      <h1 id="schedule-page-title">Schedule</h1>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default Schedule;