import React, { useState } from 'react';
import '../../css/LogoutButton.css';
import { signOut } from '../../api/UserApi';
import { useAuth } from '../../context/AuthContext';
import StatusMessageModal from '../modals/StatusMessageModal';

const LogoutButton = ({ tag }) => {
  const { logout } = useAuth();
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState('');

  const handleLogout = async () => {
    const response = await signOut();
    if (response.message) {
      logout();
    } else {
      setModalMessage('Log out was not successful.');
      setShowModal(true);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  return (
    <>
      <img 
        src="/images/logout.png" 
        alt="Log out" 
        className={`logout-button ${tag}`} 
        onClick={handleLogout} 
      />
      {showModal && (
        <StatusMessageModal 
          onClose={handleCloseModal} 
          message={modalMessage} 
        />
      )}
    </>
  );
};

export default LogoutButton;
