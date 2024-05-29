import "../css/Home.css";

import LoadingSpinner from "./LoadingSpinner";

import { useState, useEffect } from "react";


const Home = () => {
  const [isLoading, setIsLoading] = useState(true);

  const renderEvents = (
    <div id="home-page-div">
      <h1 id="home-page-title">Home</h1>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default Home;