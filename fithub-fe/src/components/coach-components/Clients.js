import LoadingSpinner from "../LoadingSpinner";

import { useState, useEffect } from "react";


const Clients = () => {
  const [isLoading, setIsLoading] = useState(true);

  const renderEvents = (
    <div id="clients-coach-page-div" className="page-div">
      <h1 id="clients-coach-page-title">Clients</h1>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default Clients;
