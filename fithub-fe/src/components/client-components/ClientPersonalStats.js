import React from 'react';
import "../../css/ClientPersonalStats.css";

const ClientPersonalStats = ({ stats }) => {
  return (
    <table id="my-stats-table">
      <tbody>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>BMI</td>
          <td className='my-stats-value'>{stats ? stats.bmi.toFixed(2) : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Bench PR</td>
          <td className='my-stats-value'>{stats ? stats.benchPr.toFixed(2) : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Squat PR</td>
          <td className='my-stats-value'>{stats ? stats.squatPr.toFixed(2) : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Deadlift PR</td>
          <td className='my-stats-value'>{stats ? stats.deadliftPr.toFixed(2) : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Treadmill PR</td>
          <td className='my-stats-value'>{stats ? stats.treadmillPr.toFixed(2) : 'Not available'}</td>
        </tr>
      </tbody>
    </table>
  );
};

export default ClientPersonalStats;
