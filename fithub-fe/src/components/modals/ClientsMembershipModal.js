import React, { useEffect, useState } from 'react';
import '../../css/ClientsMembershipModal.css';
import { postMembership, getMembership } from "../../api/CoachApi"; 

const months = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
];

const ClientsMembershipModal = ({ client, onClose }) => {
    const [checkedMonths, setCheckedMonths] = useState([]);
    const [savingMembership, setSavingMembership] = useState(false);

    const saveCheckedMonthsToLocalStorage = (months) => {
        localStorage.setItem('checkedMonths', JSON.stringify(months));
    };

    const loadCheckedMonthsFromLocalStorage = () => {
        const storedCheckedMonths = localStorage.getItem('checkedMonths');
        return storedCheckedMonths ? JSON.parse(storedCheckedMonths) : [];
    };


    useEffect(() => {
        const fetchMembership = async () => {
            try {
                const membershipData = await getMembership();
                const initialCheckedMonths = months.map(month => membershipData.includes(month));
                setCheckedMonths(initialCheckedMonths);
                saveCheckedMonthsToLocalStorage(initialCheckedMonths);
            } catch (error) {
                console.error('Error fetching membership:', error);
            }
        };
        fetchMembership();
    }, []);

 
    const handleCheckboxChange = (index) => {
        const updatedCheckedMonths = [...checkedMonths];
        updatedCheckedMonths[index] = !updatedCheckedMonths[index];
        setCheckedMonths(updatedCheckedMonths);
    };

    const handleSaveAndClose = async () => {
        setSavingMembership(true);
        try {
            const selectedMonths = months.filter((month, index) => checkedMonths[index]);
            const promises = selectedMonths.map(month => postMembership(client.id, month));
            await Promise.all(promises);
            saveCheckedMonthsToLocalStorage(checkedMonths);
            onClose();
        } catch (error) {
            console.error('Error saving membership:', error);
        } finally {
            setSavingMembership(false);
        }
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
                <button onClick={handleSaveAndClose} disabled={savingMembership}>
                    {savingMembership ? 'Saving...' : 'Save'}
                </button>
            </div>
        </div>
    );
};

export default ClientsMembershipModal;
