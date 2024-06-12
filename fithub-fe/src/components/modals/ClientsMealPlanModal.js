import React, { useState, useEffect } from 'react';
import '../../css/ClientsMealPlanModal.css';
import { getMealPlan } from "../../api/CoachApi"; 

const ClientsMealPlanModal = ({ client, onClose }) => {
    const [mealPlanData, setMealPlanData] = useState([]); 
    const [editableCell, setEditableCell] = useState(null); 

    useEffect(() => {
        const fetchMealPlan = async () => {
            try {
                const data = await getMealPlan(client.id); 
                setMealPlanData(data); 
            } catch (error) {
                console.error('Error fetching meal plan:', error);
                setMealPlanData([]);
            }
        };

        fetchMealPlan(); 
    }, [client.id]); 

    const handleEdit = (cell) => {
        setEditableCell(cell);
    };

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
