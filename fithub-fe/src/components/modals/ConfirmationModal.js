import React, { useState, useRef } from 'react';
import '../../css/Modal.css';
import '../../App.css';
import { confirmCode, resendConfirmationCode } from '../../api/UserApi';
import StatusMessageModal from './StatusMessageModal';

const ConfirmationModal = ({ isOpen, onClose, email, onConfirm }) => {
  const [code, setCode] = useState(Array(6).fill(''));
  const [errorMessage, setErrorMessage] = useState('');
  const [resendMessage, setResendMessage] = useState('');
  const [resendSuccess, setResendSuccess] = useState(false);
  const [showStatusModal, setShowStatusModal] = useState(false);
  const inputRefs = useRef([]);

  const handleChange = (e, index) => {
    const value = e.target.value;
    if (/^\d?$/.test(value)) {
      const newCode = [...code];
      newCode[index] = value;
      setCode(newCode);
      if (value && index < 5) {
        inputRefs.current[index + 1].focus();
      }
    }
  };

  const handleConfirm = async () => {
    const confirmationCode = code.join('');
    if (code.includes('') || code.some(char => !/^\d$/.test(char))) {
      setErrorMessage('Please input valid data.');
      return;
    }

    try {
      const response = await confirmCode({ email, confirmationCode });
      if (response.message) {
        setShowStatusModal(true);
        setErrorMessage('');
        onClose();
      } else {
        setShowStatusModal(true);
        setErrorMessage('Confirmation was unsuccessful.');
      }
    } catch (error) {
      setShowStatusModal(true);
      setErrorMessage('Confirmation failed.');
    }
  };

  const handleResendCode = async () => {
    try {
      const response = await resendConfirmationCode(email);
      if (response.message) {
        setShowStatusModal(true);
        setResendMessage('A new code has been sent to your email.');
        setResendSuccess(true);
      } else {
        setShowStatusModal(true);
        setResendMessage('Failed to send a new code.');
        setResendSuccess(false);
      }
    } catch (error) {
      setShowStatusModal(true);
      setResendMessage('Failed to send a new code.');
      setResendSuccess(false);
    }
  };

  const handleCloseStatusModal = () => {
    setShowStatusModal(false);
    if (!resendSuccess && errorMessage !== 'Confirmation was unsuccessful.') {
      onClose();
    }
  };

  if (!isOpen) {
    return null;
  }

  return (
    <div>
      <div className="modal-overlay">
        <div className="modal-content">
          <img src="/images/close.png" alt="X" className="close-button" onClick={onClose} />
          <p className="modal-title">Please enter the confirmation code</p>
          <p className='modal-message'>(Check your email for the code)</p>
          {errorMessage && <p className="error-message">{errorMessage}</p>}
          {resendMessage && <p className="resend-message">{resendMessage}</p>}
          <div className="code-inputs">
            {code.map((value, index) => (
              <input
                key={index}
                id={`code-${index}`}
                type="text"
                value={value}
                maxLength="1"
                ref={el => inputRefs.current[index] = el}
                onChange={(e) => handleChange(e, index)}
              />
            ))}
          </div>
          <div className="modal-actions">
            <button className="button-element light-button left-button" onClick={handleResendCode}>Resend Code</button>
            <button className="button-element dark-button right-button" onClick={handleConfirm}>Confirm</button>
          </div>
        </div>
      </div>
      {showStatusModal && (
        <StatusMessageModal
          isOpen={showStatusModal}
          onClose={handleCloseStatusModal}
          message={resendSuccess ? 'A new code has been sent to your email' : errorMessage}
        />
      )}
    </div>
  );
};

export default ConfirmationModal;
