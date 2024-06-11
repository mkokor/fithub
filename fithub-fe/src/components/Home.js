import "../css/Home.css";

import LoadingSpinner from "./LoadingSpinner";
import HomeNewsSlider from "./HomeNewsSlider";
import Options from "./Options";
import Footer from "./Footer";
import { useAuth } from '../context/AuthContext';
import { useState, useEffect } from "react";
import { getHomeNews } from "../api/ClientApi";



const Home = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [news, setNews] = useState([]);
  const { user } = useAuth(); 

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (user) {
          const data = await getHomeNews(); 
          setNews(data);
        }
        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching news:", error);
        setIsLoading(false);
      }
    };

    fetchData();
  }, [user]);

  const renderEvents = (
    <div id="home-page-div">
      <HomeNewsSlider news={news}></HomeNewsSlider>
      <p id='home-text' className="text-p">Welcome to FitHub - your personal path to better health</p>
      <p className="text-p">Here at FitHub our goal is to give you all the tools and support you need in order to reach your goals.</p>      
      <p className="text-p">Our app is designed to save you time and make your organization process as easy as possible.</p>
      <p className="text-p"> Use our inutitive interface to book training appointments that fit your personal schedule, catch up on your latest meal plan or use our real-time chat platform to connect to your coach and teammates.</p> 
      <Options></Options>
      <Footer></Footer>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default Home;
