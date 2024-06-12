import React from "react";
import "../css/ScoreBoard.css";

const ScoreBoard = ({ scoreboardStats }) => {
  return (
    <div className="scoreboard-container">
      <table className="stats-table-score">
        <thead>
          <tr>
            <th></th> 
            <th className="cell-title-head">Client</th>
            <th className="cell-title-head">Coach</th>
            <th className="cell-title-head">Bench PR</th>
            <th className="cell-title-head">Squat PR</th>
            <th className="cell-title-head">Deadlift PR</th>
            <th className="cell-title-head">Treadmill PR</th>
          </tr>
        </thead>
        <tbody>
          {scoreboardStats.length > 0 ? (
            scoreboardStats.map((stats, index) => (
              <tr key={index}>
                <td className="title-cell">{index + 1}</td> 
                <td className="title-cell">{stats.client.firstName + " " + stats.client.lastName}</td>
                <td className="title-cell">{stats.coach.firstName + " " + stats.coach.lastName}</td>
                <td className="title-cell">{stats.benchPr}</td>
                <td className="title-cell">{stats.squatPr}</td>
                <td className="title-cell">{stats.deadliftPr}</td>
                <td className="title-cell">{stats.treadmillPr}</td>
              </tr>
            ))
          ) : (
            <tr id="stats-no-data">
              <td colSpan="5">No data available.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ScoreBoard;
