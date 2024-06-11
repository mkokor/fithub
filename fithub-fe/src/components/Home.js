import "../css/Home.css";

import LoadingSpinner from "./LoadingSpinner";
import HomeNewsSlider from "./HomeNewsSlider";
import Options from "./Options";

import { useState, useEffect } from "react";


const Home = () => {
  const [isLoading, setIsLoading] = useState(false);

  const newsImages = [

  ]

  const renderEvents = (
    <div id="home-page-div">
      <HomeNewsSlider news={newsImages}></HomeNewsSlider>
      <p id='home-text'>Welcome to FitHub - your personal path to better health.</p>
      <p>Here at FitHub our goal is to give you all the tools and support you need in order to reach your goals.</p>      
      <p>Our app is designed to save you time and make your organization process as easy as possible.</p>
      <p> Use our inutitive interface to book training appointments that fit your personal schedule, catch up on your latest meal plan or use our real-time chat platform to connect to your coach and teammates.</p> 
      <Options></Options>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default Home;
