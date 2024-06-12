import "../../css/ClientProgression.css";

import LoadingSpinner from "../LoadingSpinner";

import { useState, useEffect } from "react";
import ClientPersonalStats from "./ClientPersonalStats";
import { getMyStats, getScoreBoard } from "../../api/ClientApi";
import ScoreBoard from "../ScoreBoard";


const ClientProgression = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [stats, setStats] = useState({});
  const [scoreBoardStats, setScoreBoardStats] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getMyStats(); 
        setStats(data);
        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching news:", error);
        setIsLoading(false);
      }
    };
    fetchData();
  }, []); 

  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getScoreBoard(); 
        setScoreBoardStats(data);
        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching score board data:", error);
        setIsLoading(false);
      }
    };
    fetchData();
  }, []); 

  const generateReport = () => {

  }


  const renderEvents = (
    <div id="progression-page-div" className="page-div">
      <div id="prog-content">
        <div id="client-stats-div">
          <p className="client-progression-tp">Your latest stats:</p>
          <div>
            <ClientPersonalStats stats={stats}/>
            <div id="stats-b-div">
              <img id="generate-stats-rep" src="/images/icons/icons8-report-50.png" alt="Generate stats history report" onClick={() => {
                generateReport()
              }}></img>
            </div>
          </div>
        </div>
        <div id="score-half-div">
          <p className="client-progression-tp">Current score board:</p>
          <ScoreBoard scoreboardStats={scoreBoardStats}/>
        </div>
      </div>
    </div>
  );

  return (
    <div className="App">
    {isLoading ? <LoadingSpinner /> : renderEvents}
    </div>
  );
}

export default ClientProgression;