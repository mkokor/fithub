import React, { useEffect, useState } from 'react';
import '../../css/ClientsMembershipModal.css';

const months = [
    'January', 'February', 'March', 'April', 'May', 'Juny',
    'July', 'August', 'September', 'Oktobar', 'November', 'December'
];

const ClientsMembershipModal = ({ client, onClose }) => {
    const [checkedMonths, setCheckedMonths] = useState([]);

    // Funkcija za čuvanje stanja čekiranih mjeseci u lokalnom skladištu
    const saveCheckedMonthsToLocalStorage = (months) => {
        localStorage.setItem('checkedMonths', JSON.stringify(months));
    };

    // Funkcija za učitavanje stanja čekiranih mjeseci iz lokalnog skladišta
    const loadCheckedMonthsFromLocalStorage = () => {
        const storedCheckedMonths = localStorage.getItem('checkedMonths');
        return storedCheckedMonths ? JSON.parse(storedCheckedMonths) : [];
    };

    // Prvo učitavanje stanja čekiranih mjeseci kada se komponenta montira
    useEffect(() => {
        const storedCheckedMonths = loadCheckedMonthsFromLocalStorage();
        setCheckedMonths(storedCheckedMonths);
    }, []);

    // Funkcija za obradu promjene čekiranih mjeseci
    const handleCheckboxChange = (index) => {
        const updatedCheckedMonths = [...checkedMonths];
        updatedCheckedMonths[index] = !updatedCheckedMonths[index];
        setCheckedMonths(updatedCheckedMonths);
    };

    // Funkcija za čuvanje stanja čekiranih mjeseci i zatvaranje modala
    const handleSaveAndClose = () => {
        saveCheckedMonthsToLocalStorage(checkedMonths);
        onClose();
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content-membership">
                <h2>Membership for {client.firstName} {client.lastName}</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Month</th>
                            <th>Paid Membership</th>
                        </tr>
                    </thead>
                    <tbody>
                        {months.map((month, index) => (
                            <tr key={index}>
                                <td className='months'>{month}</td>
                                <td >
                                    <input className="custom-checkbox"
                                        type="checkbox"
                                        checked={checkedMonths[index]}
                                        onChange={() => handleCheckboxChange(index)}
                                    />
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <button onClick={onClose}>Close</button>
                <button onClick={handleSaveAndClose}>Save</button>
            </div>
        </div>
    );
};

export default ClientsMembershipModal;
