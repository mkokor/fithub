import React from 'react';
import "../../css/ClientPersonalStats.css";

const ClientPersonalStats = ({ stats }) => {
  return (
    <table id="my-stats-table">
      <tbody>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>BMI</td>
          <td className='my-stats-value'>{stats.bmi ? stats.bmi.toFixed(2) : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Bench PR</td>
          <td className='my-stats-value'>{stats.benchPr ? stats.benchPr.toFixed(2) : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Squat PR</td>
          <td className='my-stats-value'>{stats.squatPr ? stats.squatPr.toFixed(2) : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Deadlift PR</td>
          <td className='my-stats-value'>{stats.deadliftPr ? stats.deadliftPr.toFixed(2) : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Treadmill PR</td>
          <td className='my-stats-value'>{stats.treadmillPr ? stats.treadmillPr.toFixed(2) : 'Not available'}</td>
        </tr>
      </tbody>
    </table>
  );
};

export default ClientPersonalStats;
