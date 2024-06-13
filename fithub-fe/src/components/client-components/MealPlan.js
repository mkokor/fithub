import React from 'react';

const MealPlan = ({ mealplan }) => {
  return (
    <div id="mealplan-table-div">
      <table id="mealplan-table" border="1">
        <thead>
          <tr id="header">
            <td id="meal-cell">Meal</td>
            <td className="day-cell">Monday</td>
            <td className="day-cell">Tuesday</td>
            <td className="day-cell">Wednesday</td>
            <td className="day-cell">Thursday</td>
            <td className="day-cell">Friday</td>
            <td className="day-cell">Saturday</td>
            <td className="day-cell">Sunday</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className='meal-title'>Breakfast</td>
            {mealplan.map((dayPlan, index) => (
              <td className="meal-data" key={index}>{dayPlan.breakfast}</td>
            ))}
          </tr>
          <tr>
            <td className='meal-title'>AM Snack</td>
            {mealplan.map((dayPlan, index) => (
              <td className="meal-data" key={index}>{dayPlan.amSnack}</td>
            ))}
          </tr>
          <tr>
            <td className='meal-title'>Lunch</td>
            {mealplan.map((dayPlan, index) => (
              <td className="meal-data" key={index}>{dayPlan.lunch}</td>
            ))}
          </tr>
          <tr>
            <td className='meal-title'>Dinner</td>
            {mealplan.map((dayPlan, index) => (
              <td className="meal-data" key={index}>{dayPlan.dinner}</td>
            ))}
          </tr>
          <tr>
            <td className='meal-title'>PM Snack</td>
            {mealplan.map((dayPlan, index) => (
              <td className="meal-data" key={index}>{dayPlan.pmSnack}</td>
            ))}
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default MealPlan;