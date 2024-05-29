import React, { useState, useEffect } from 'react';
import CoachDetailsModal from '../modals/CoachDetailsModal';
import '../../css/CoachDropdown.css';
import { getCoachDetails } from '../../api/UserApi';

const CoachDropdown = ({ selectedCoachId, onCoachSelect, coaches }) => {
  const [isModalOpen, setIsModalOpen] = useState(false); 
  const [coachDetails, setCoachDetails] = useState({
    "id": 0,
    "firstName": "",
    "lastName": "",
    "biography": "",
    "imagePath": ""
  });

  const handleCoachSelect = async (coachId) => {
    onCoachSelect(coachId);
    const coachData = await getCoachDetails(coachId);
    setCoachDetails(coachData);
  };

  return (
    <div id="select-coach-div">
      <select
        id="select-coach"
        value={selectedCoachId}
        onChange={(e) => handleCoachSelect(String(e.target.value))}
      >
        {coaches.map((coach) => (
          <option key={coach.id} value={coach.id}>{coach.displayName}</option>
        ))}
      </select>
      <CoachDetailsModal coach={coachDetails} show={isModalOpen} onClose={() => setIsModalOpen(false)} />
    </div>
  );
};

export default CoachDropdown;
