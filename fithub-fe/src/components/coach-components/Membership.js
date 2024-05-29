import "../../css/Training.css";

import LoadingSpinner from "../LoadingSpinner";

import { useState, useEffect } from "react";


const Membership = () => {
  const [isLoading, setIsLoading] = useState(true);

  const renderEvents = (
    <div id="membership-page-div" className="page-div">
      <h1 id="membership-page-title">Membership</h1>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default Membership;