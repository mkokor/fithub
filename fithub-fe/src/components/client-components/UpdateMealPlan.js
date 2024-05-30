import React, { useState } from 'react';
import { updateMealPlan } from '../api/CoachApi';

const UpdateMealPlan = ({ clientId}) => {
    const [mealPlan, setMealPlan] = useState({
        breakfast: '',
        amSnack: '',
        lunch: '',
        dinner: '',
        pmSnack: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setMealPlan({
            ...mealPlan,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const updatedMealPlan = await updateMealPlan(mealPlan);
            alert('Meal plan updated successfully!');
            console.log(updatedMealPlan);
        } catch (error) {
            alert('There was a problem with the update request.');
            console.error('There was a problem with the fetch operation:', error);
        }
    };

    return (
        <div>
            <h2>Update Meal Plan</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="breakfast">Breakfast:</label>
                    <input
                        type="text"
                        id="breakfast"
                        name="breakfast"
                        value={mealPlan.breakfast}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="amSnack">AM Snack:</label>
                    <input
                        type="text"
                        id="amSnack"
                        name="amSnack"
                        value={mealPlan.amSnack}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="lunch">Lunch:</label>
                    <input
                        type="text"
                        id="lunch"
                        name="lunch"
                        value={mealPlan.lunch}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="dinner">Dinner:</label>
                    <input
                        type="text"
                        id="dinner"
                        name="dinner"
                        value={mealPlan.dinner}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="pmSnack">PM Snack:</label>
                    <input
                        type="text"
                        id="pmSnack"
                        name="pmSnack"
                        value={mealPlan.pmSnack}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">Update Meal Plan</button>
            </form>
        </div>
    );
};

export default UpdateMealPlan;