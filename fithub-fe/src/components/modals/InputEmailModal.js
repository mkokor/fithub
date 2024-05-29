import React, { useState } from 'react';
import { getPasswordResetCode } from '../../api/UserApi';
import '../../css/InputEmailModal.css';
import '../../App.css';

const InputEmailModal = ({ onSubmit, onClose, onSuccess }) => { 
  const [email, setEmail] = useState('');
  const [isFormSubmitted, setIsFormSubmitted] = useState(false); 
  const [statusMessage, setStatusMessage] = useState(''); 

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (email.trim() === '') {
      setIsFormSubmitted(true); 
      return;
    }
    setIsFormSubmitted(false); 

    try {
      const response = await getPasswordResetCode(email); 
      if (response.message) {
        setStatusMessage('Password reset code sent successfully.');
        onSuccess(email); 
      } else {
        setStatusMessage('Failed to send password reset code.');
      }
    } catch (error) {
      setStatusMessage('Failed to send password reset code.');
    }

    onSubmit(email); 
  };

  const handleChange = (e) => {
    setEmail(e.target.value);
    setIsFormSubmitted(false); 
    setStatusMessage(''); 
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <img src="/images/close.png" alt="X" className="close-button" onClick={onClose}/> 
        <p className="modal-title">Please enter your email</p>
        {isFormSubmitted && email.trim() === '' && ( 
          <p style={{ color: 'red' }}>Please provide your email.</p>
        )}
        {statusMessage && (
          <p style={{ color: statusMessage.includes('successfully') ? 'green' : 'red' }}>{statusMessage}</p>
        )}
        <form className="email-input-modal" onSubmit={handleSubmit}>
          <input
            type="email"
            placeholder="Enter your email"
            value={email}
            onChange={handleChange}
          />
          <button className="button-element dark-button dark-button" type="submit">Reset password</button>
        </form>
      </div>
    </div>
  );
};

export default InputEmailModal;
