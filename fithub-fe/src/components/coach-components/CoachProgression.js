import "../../css/CoachProgression.css";

import LoadingSpinner from "../LoadingSpinner";

import { useState, useEffect } from "react";


const CoachProgression = () => {
  const [isLoading, setIsLoading] = useState(true);

  const renderEvents = (
    <div id="progression-page-div" className="page-div">
      <h1 id="progression-page-title">Progression</h1>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default CoachProgression;