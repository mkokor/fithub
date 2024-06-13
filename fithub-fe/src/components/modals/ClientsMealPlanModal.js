import React, { useState, useEffect } from 'react';
import '../../css/ClientsMealPlanModal.css';
import UpdateModal from './UpdateModal';
import { getMealPlanByClientId } from '../../api/CoachApi';
import { update } from '../../api/CoachApi'; 

const ClientsMealPlanModal = ({ client, onClose }) => {
    const [mealPlanData, setMealPlanData] = useState([]);

    useEffect(() => {
        const fetchData = async () => { 
            try {
                const data = await getMealPlanByClientId(client.uuid);
                setMealPlanData(data.dailyMealPlans);
                
            } catch (error) {
                console.log('Error fetching meal plan', error);
            }
        };

        fetchData();
    }, [client.uuid]); 

    const [updateModalOpen, setUpdateModalOpen] = useState(false);
    const [currentDayIndex, setCurrentDayIndex] = useState(null);

    const handleOpenUpdateModal = (index) => {
        setCurrentDayIndex(index);
        setUpdateModalOpen(true);
    };

    const handleCloseUpdateModal = () => {
        setUpdateModalOpen(false);
        setCurrentDayIndex(null);
    };

    const handleSaveUpdate = async (amsnack, breakfast, lunch, dinner, pmsnack) => {
        const updatedMealPlan = [...mealPlanData];
        const currentDay = updatedMealPlan[currentDayIndex];

        const updatedDay = {
            ...currentDay,
            amSnack: amsnack,
            breakfast: breakfast,
            lunch: lunch,
            dinner: dinner,
            pmSnack: pmsnack
        };

        updatedMealPlan[currentDayIndex] = updatedDay;
        setMealPlanData(updatedMealPlan);

        

        try {
            await update(currentDay.id, updatedDay);
            console.log('Meal plan updated successfully');
        } catch (error) {
            console.log('Error updating meal plan', error);
        }
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
                                <td onClick={() => handleOpenUpdateModal(index)}>
                                    {meal.amSnack}
                                    <img src="/images/edit-icon.png" alt="Edit" className="edit-icon" />
                                </td>
                                <td onClick={() => handleOpenUpdateModal(index)}>
                                    {meal.breakfast}
                                    <img src="/images/edit-icon.png" alt="Edit" className="edit-icon" />
                                </td>
                                <td onClick={() => handleOpenUpdateModal(index)}>
                                    {meal.lunch}
                                    <img src="/images/edit-icon.png" alt="Edit" className="edit-icon" />
                                </td>
                                <td onClick={() => handleOpenUpdateModal(index)}>
                                    {meal.dinner}
                                    <img src="/images/edit-icon.png" alt="Edit" className="edit-icon" />
                                </td>
                                <td onClick={() => handleOpenUpdateModal(index)}>
                                    {meal.pmSnack}
                                    <img src="/images/edit-icon.png" alt="Edit" className="edit-icon" />
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <button onClick={onClose}>Close</button>
                {updateModalOpen && (
                    <UpdateModal
                        onClose={handleCloseUpdateModal}
                        onSave={handleSaveUpdate}
                        day={mealPlanData[currentDayIndex].day}
                    />
                )}
            </div>
        </div>
    );
};

export default ClientsMealPlanModal;
