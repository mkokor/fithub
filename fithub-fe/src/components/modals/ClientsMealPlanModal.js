import React, { useState } from 'react';
import '../../css/ClientsMealPlanModal.css';

const ClientsMealPlanModal = ({ client, onClose }) => {
    // Sample meal plan data
    const mealPlanData = [
        {
            day: 'Monday',
            amSnack: 'Apple',
            breakfast: 'Oatmeal',
            lunch: 'Chicken Salad',
            dinner: 'Grilled Fish',
            pmSnack: 'Yogurt'
        },
        {
            day: 'Tuesday',
            amSnack: 'Banana',
            breakfast: 'Scrambled Eggs',
            lunch: 'Turkey Sandwich',
            dinner: 'Spaghetti',
            pmSnack: 'Nuts'
        },
        {
            day: 'Wednesday',
            amSnack: 'Orange',
            breakfast: 'Smoothie',
            lunch: 'Caesar Salad',
            dinner: 'Beef Stir Fry',
            pmSnack: 'Cheese'
        },
        {
            day: 'Thursday',
            amSnack: 'Grapes',
            breakfast: 'Pancakes',
            lunch: 'Grilled Chicken Wrap',
            dinner: 'Salmon',
            pmSnack: 'Fruit Salad'
        },
        {
            day: 'Friday',
            amSnack: 'Pear',
            breakfast: 'French Toast',
            lunch: 'BLT Sandwich',
            dinner: 'Pizza',
            pmSnack: 'Dark Chocolate'
        },
        {
            day: 'Saturday',
            amSnack: 'Berries',
            breakfast: 'Yogurt Parfait',
            lunch: 'Veggie Burger',
            dinner: 'Tacos',
            pmSnack: 'Protein Bar'
        },
        {
            day: 'Sunday',
            amSnack: 'Nuts',
            breakfast: 'Avocado Toast',
            lunch: 'Chicken Soup',
            dinner: 'Pasta',
            pmSnack: 'Popcorn'
        }
    ];

    // Stanje za praćenje da li je ćelija u režimu uređivanja
    const [editableCell, setEditableCell] = useState(null);

    // Funkcija za omogućavanje uređivanja ćelije
    const handleEdit = (cell) => {
        setEditableCell(cell);
    };

    // Funkcija za zatvaranje režima uređivanja
    const handleCloseEdit = () => {
        setEditableCell(null);
    };

    

    return (
        <div className="modal-overlay">
            <div className="modal-content-mealplan">
                <h2>Meal Plan for {client.firstName} {client.lastName}</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Day</th>
                            <th>AM Snack</th>
                            <th>Breakfast</th>
                            <th>Lunch</th>
                            <th>Dinner</th>
                            <th>PM Snack</th>
                        </tr>
                    </thead>
                    <tbody>
                        {mealPlanData.map((meal, index) => (
                            <tr key={index}>
                                <td className="day">{meal.day}</td>
                                <td
                                    className={editableCell === `amSnack${index}` ? 'editable' : ''}
                                    onClick={() => handleEdit(`amSnack${index}`)}
                                    onBlur={handleCloseEdit}
                                    contentEditable={editableCell === `amSnack${index}`}
                                >
                                    {meal.amSnack}
                                    <img
                                        src="/images/edit-icon.png"
                                        alt="Edit"
                                        className="edit-icon"
                                    />
                                </td>
                                <td
                                    className={editableCell === `breakfast${index}` ? 'editable' : ''}
                                    onClick={() => handleEdit(`breakfast${index}`)}
                                    onBlur={handleCloseEdit}
                                    contentEditable={editableCell === `breakfast${index}`}
                                >
                                    {meal.breakfast}
                                    <img
                                        src="/images/edit-icon.png"
                                        alt="Edit"
                                        className="edit-icon"
                                    />
                                </td>
                                <td
                                    className={editableCell === `lunch${index}` ? 'editable' : ''}
                                    onClick={() => handleEdit(`lunch${index}`)}
                                    onBlur={handleCloseEdit}
                                    contentEditable={editableCell === `lunch${index}`}
                                >
                                    {meal.lunch}
                                    <img
                                        src="/images/edit-icon.png"
                                        alt="Edit"
                                        className="edit-icon"
                                    />
                                </td>
                                <td
                                    className={editableCell === `dinner${index}` ? 'editable' : ''}
                                    onClick={() => handleEdit(`dinner${index}`)}
                                    onBlur={handleCloseEdit}
                                    contentEditable={editableCell === `dinner${index}`}
                                >
                                    {meal.dinner}
                                    <img
                                        src="/images/edit-icon.png"
                                        alt="Edit"
                                        className="edit-icon"
                                    />
                                </td>
                                <td
                                    className={editableCell === `pmSnack${index}` ? 'editable' : ''}
                                    onClick={() => handleEdit(`pmSnack${index}`)}
                                    onBlur={handleCloseEdit}
                                    contentEditable={editableCell === `pmSnack${index}`}
                                >
                                    {meal.pmSnack}
                                    <img
                                        src="/images/edit-icon.png"
                                        alt="Edit"
                                        className="edit-icon"
                                    />
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <button onClick={onClose}>Close</button>
                <button>Update</button>
            </div>
        </div>
    );
};

export default ClientsMealPlanModal;
