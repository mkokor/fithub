import React from 'react';
import "../../css/ClientPersonalStats.css";

const ClientPersonalStats = ({ stats }) => {
  return (
    <table id="my-stats-table">
      <tbody>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>BMI</td>
          <td className='my-stats-value'>{stats ? stats.bmi : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Bench PR</td>
          <td className='my-stats-value'>{stats ? stats.benchPr : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Squat PR</td>
          <td className='my-stats-value'>{stats ? stats.squatPr : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Deadlift PR</td>
          <td className='my-stats-value'>{stats ? stats.deadliftPr : 'Not available'}</td>
        </tr>
        <tr className='my-stats-row'>
          <td className='my-stats-cat'>Treadmill PR</td>
          <td className='my-stats-value'>{stats ? stats.treadmillPr : 'Not available'}</td>
        </tr>
      </tbody>
    </table>
  );
};

export default ClientPersonalStats;
