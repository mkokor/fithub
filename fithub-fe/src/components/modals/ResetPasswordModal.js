import React, { useState } from 'react';
import '../../App.css';
import StatusMessageModal from './StatusMessageModal';

const ResetPasswordModal = ({ onClose, onSubmit, resetPassword }) => {
  const [code, setCode] = useState(Array(6).fill(''));
  const [email, setEmail] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [isStatusModalOpen, setIsStatusModalOpen] = useState(false);
  const [statusMessage, setStatusMessage] = useState('');

  const handleCodeChange = (index, value) => {
    if (/^[0-9]?$/.test(value)) {
      const newCode = [...code];
      newCode[index] = value;
      setCode(newCode);
    }
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setNewPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const codeString = code.join('');
    if (codeString.length !== 6 || !email || !newPassword) {
      setErrorMessage('Please provide valid data.');
      return;
    }

    try {
      const response = await resetPassword({
        userEmail: email,
        passwordResetCode: codeString,
        newPassword: newPassword,
      });

      if (response.message) {
        setStatusMessage('Your password was successfully changed.');
        onSubmit({ code: codeString, email, newPassword });
      } else {
        setStatusMessage('Something went wrong.');
      }
    } catch (error) {
      setStatusMessage('Something went wrong.');
    }

    setIsStatusModalOpen(true);
  };

  const handleCloseStatusModal = () => {
    setIsStatusModalOpen(false);
    onClose();
  };

  return (
    <div>
      <div className="modal-overlay">
        <div className="modal-content">
          <img src="/images/close.png" alt="X" className="close-button" onClick={onClose} />
          <p className="modal-title">Enter your reset code</p>
          <p className='modal-message'>(Check your email for the code)</p>
          {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
          <div className="code-inputs">
            {code.map((digit, index) => (
              <input
                key={index}
                type="text"
                maxLength="1"
                value={digit}
                onChange={(e) => handleCodeChange(index, e.target.value)}
                className="code-input"
              />
            ))}
          </div>
          <form className="reset-form" onSubmit={handleSubmit}>
            <input
              type="email"
              placeholder="Enter your email:"
              value={email}
              onChange={handleEmailChange}
              required
            />
            <input
              type="password"
              placeholder="Enter new password:"
              value={newPassword}
              onChange={handlePasswordChange}
              required
            />
            <button className="button-element dark-button new-pass-button" type="submit">Set new password</button>
          </form>
        </div>
      </div>
      {isStatusModalOpen && (
        <StatusMessageModal onClose={handleCloseStatusModal} message={statusMessage} />
      )}
    </div>
  );
};

export default ResetPasswordModal;
