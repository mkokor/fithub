import "../../css/Training.css";

import LoadingSpinner from "../LoadingSpinner";

import { useState, useEffect } from "react";


const Training = () => {
  const [isLoading, setIsLoading] = useState(true);

  const renderEvents = (
    <div id="training-page-div" className="page-div">
      <h1 id="training-page-title">Training</h1>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default Training;