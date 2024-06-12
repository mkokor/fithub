import React, { useState } from 'react';
import '../../css/InputEmailModal.css';
import '../../App.css';
import StatusMessageModal from './StatusMessageModal';
import { addNewStatsForClient } from '../../api/CoachApi';

const AddStatsModal = ({ onClose, clientId, onAddStats }) => { 
  const [stats, setStats] = useState({
    clientUuid: clientId,
    weight: '',
    height: '',
    deadliftPr: '',
    squatPr: '',
    benchPr: '',
    treadmillPr: ''
  });

  const [isStatusModalOpen, setIsStatusModalOpen] = useState(false);
  const [statusMessage, setStatusMessage] = useState('');

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setStats({ ...stats, [name]: value });
  };

  const handleAddStats = async () => {
    const { weight, height, deadliftPr, squatPr, benchPr, treadmillPr } = stats;
    if (!weight || !height || !deadliftPr || !squatPr || !benchPr || !treadmillPr) {
      setStatusMessage("Please input all data");
      setIsStatusModalOpen(true);
    } else {
      try {
        console.log(stats)
        const response = await addNewStatsForClient(stats);
        onAddStats(response); 
        onClose(); 
      } catch (error) {
        console.error("Error adding stats:", error);
      }
    }
  };

  const handleCloseStatusModal = () => {
    setIsStatusModalOpen(false);
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <img src="/images/close.png" alt="X" className="close-button" onClick={onClose}/> 
        <p className="modal-title">Please enter stats data</p>
        <div className='stats-input-div'>
          <div>
            <p className='stats-label'>Weight:</p>
            <input 
              type="number" 
              name="weight" 
              value={stats.weight} 
              onChange={handleInputChange} 
              step="0.01" 
            />
          </div>
          <div>
            <p className='stats-label'>Height:</p>
            <input 
              type="number" 
              name="height" 
              value={stats.height} 
              onChange={handleInputChange} 
              step="0.01" 
            />
          </div>
          <div>
            <p className='stats-label'>Deadlift PR:</p>
            <input 
              type="number" 
              name="deadliftPr" 
              value={stats.deadliftPr} 
              onChange={handleInputChange} 
              step="0.01" 
            />
          </div>
          <div>
            <p className='stats-label'>Squat PR:</p>
            <input 
              type="number" 
              name="squatPr" 
              value={stats.squatPr} 
              onChange={handleInputChange} 
              step="0.01" 
            />
          </div>
          <div>
            <p className='stats-label'>Bench PR:</p>
            <input 
              type="number" 
              name="benchPr" 
              value={stats.benchPr} 
              onChange={handleInputChange} 
              step="0.01" 
            />
          </div>
          <div>
            <p className='stats-label'>Treadmill PR:</p>
            <input 
              type="number" 
              name="treadmillPr" 
              value={stats.treadmillPr} 
              onChange={handleInputChange} 
              step="0.01" 
            />
          </div>
        </div>
        <div>
          <button className='button-element add-st' onClick={handleAddStats}>Add stats</button>
        </div>
      </div>
      {isStatusModalOpen && (
        <StatusMessageModal onClose={handleCloseStatusModal} message={statusMessage} />
      )}
    </div>
  );
};

export default AddStatsModal;
