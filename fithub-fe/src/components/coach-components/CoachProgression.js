import React, { useState, useEffect } from "react";
import "../../css/CoachProgression.css";
import LoadingSpinner from "../LoadingSpinner";
import ScoreBoard from "../ScoreBoard";
import AddStatsModal from "../modals/AddStatsModal";
import { getScoreBoard } from "../../api/ClientApi";
import { getClients, getStatsForClient } from "../../api/CoachApi";

const CoachProgression = () => {
  const [isLoading, setIsLoading] = useState(true);
  const [clients, setClients] = useState([]);
  const [client, setClient] = useState([]);
  const [uuid, setUuid] = useState(null)
  const [clientStatsHistory, setClientStatsHistory] = useState([]);
  const [scoreBoard, setScoreBoard] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false); 

  useEffect(() => {
    const fetchData = async () => {
      try {
        const scoreBoardData = await getScoreBoard();
        setScoreBoard(scoreBoardData);

        const clientsData = await getClients();

        if (clientsData.length > 0) {
          setClients(clientsData);
          setClient(clientsData[0]);
          setUuid(clientsData[0].uuid);
          const statsData = await getStatsForClient(clientsData[0].uuid);
          setClientStatsHistory(statsData);
        }

        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching data:", error);
        setIsLoading(false);
      }
    };
    fetchData();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const scoreBoardData = await getScoreBoard();
        setScoreBoard(scoreBoardData);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, [clientStatsHistory])

  const handleClientChange = async (event) => {
    setUuid(event.target.value)
    const selectedClientUuid = event.target.value;
    try {
      const statsData = await getStatsForClient(selectedClientUuid);
      setClientStatsHistory(statsData);
    } catch (error) {
      console.error("Error fetching client stats history:", error);
    }
  };

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleAddStatsToHistory = (newStats) => {
    setClientStatsHistory(prevHistory => [...prevHistory, newStats]);
  };

  const renderEvents = (
    <div id="progression-page-div" className="page-div">
      <div id="prog-content-coach">
        <div id="client-stats-div">
          <p className="client-progression-tp">Your clients stats history:</p>
          <select id="select-client" onChange={handleClientChange}>
            {clients.map((client) => (
              <option key={client.uuid} value={client.uuid}>
                {`${client.firstName} ${client.lastName}`}
              </option>
            ))}
          </select>
          <table className="stats-table">
            <thead>
              <tr>
                <th className="cell-title-head">BMI</th>
                <th className="cell-title-head">Bench PR</th>
                <th className="cell-title-head">Squat PR</th>
                <th className="cell-title-head">Deadlift PR</th>
                <th className="cell-title-head">Treadmill PR</th>
              </tr>
            </thead>
            <tbody>
              {clientStatsHistory.map((stats, index) => (
                <tr key={index}>
                  <td className='stats-value'>{stats.bmi.toFixed(2)}</td>
                  <td className='stats-value'>{stats.benchPr.toFixed(2)}</td>
                  <td className='stats-value'>{stats.squatPr.toFixed(2)}</td>
                  <td className='stats-value'>{stats.deadliftPr.toFixed(2)}</td>
                  <td className='stats-value'>{stats.treadmillPr.toFixed(2)}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <div id="add-stats-button-div">
            <button className="button-element" onClick={handleOpenModal}>Add stats</button>
          </div>
        </div>
        <div id="score-half-div">
          <p className="client-progression-tp">Current score board:</p>
          <ScoreBoard scoreboardStats={scoreBoard} />
        </div>
      </div>
    </div>
  );

  return (
    <div className="App">
      {isLoading ? <LoadingSpinner /> : renderEvents}
      {isModalOpen && (
        <AddStatsModal 
          clientId={uuid} 
          onClose={handleCloseModal} 
          onAddStats={handleAddStatsToHistory} 
        />
      )}
    </div>
  );
};

export default CoachProgression;
