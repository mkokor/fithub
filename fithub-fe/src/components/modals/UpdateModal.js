import React, { useState } from 'react';
import '../../css/UpdateModal.css';

const UpdateModal = ({ onClose, onSave, day }) => {
    const [breakfast, setBreakfast] = useState('');
    const [lunch, setLunch] = useState('');
    const [dinner, setDinner] = useState('');
    const [amsnack, setAMSnack] = useState('');
    const [pmsnack, setPMSnack] = useState('');

    const handleSubmit = () => {
        onSave(amsnack, breakfast, lunch, dinner, pmsnack);
        onClose();
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Meal plan update window</h2>
                <h4>Please enter the new meal plan for {day}:</h4>


                <div className="form-group">
                    <label htmlFor="amsnack">AM Snack:</label>
                    <textarea
                        id="amsnack"
                        value={amsnack}
                        onChange={(e) => setAMSnack(e.target.value)}
                        className="form-control"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="breakfast">Breakfast:</label>
                    <textarea
                        id="breakfast"
                        value={breakfast}
                        onChange={(e) => setBreakfast(e.target.value)}
                        className="form-control"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="lunch">Lunch:</label>
                    <textarea
                        id="lunch"
                        value={lunch}
                        onChange={(e) => setLunch(e.target.value)}
                        className="form-control"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="dinner">Dinner:</label>
                    <textarea
                        id="dinner"
                        value={dinner}
                        onChange={(e) => setDinner(e.target.value)}
                        className="form-control"
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="pmsnack">PM Snack:</label>
                    <textarea
                        id="pmsnack"
                        value={pmsnack}
                        onChange={(e) => setPMSnack(e.target.value)}
                        className="form-control"
                    />
                </div>
                <div className="button-group">
                    <button onClick={handleSubmit} className="btn btn-primary">Submit</button>
                    <button onClick={onClose} className="btn btn-secondary">Close</button>
                </div>
            </div>
        </div>
    );
};

export default UpdateModal;
