import React, { useState, useEffect } from 'react';
import '../../css/LoginForm.css';
import '../../App.css';
import '../../css/CoachDetailsModal.css';
import { userRegister, userLogIn, getAvailableCoaches, getCoachDetails } from "../../api/UserApi";
import InputField from './InputField';
import ConfirmationModal from '../modals/ConfirmationModal';
import CoachDetailsModal from '../modals/CoachDetailsModal';
import InputEmailModal from '../modals/InputEmailModal';
import ResetPasswordModal from '../modals/ResetPasswordModal';
import StatusMessageModal from '../modals/StatusMessageModal';
import CoachDropdown from './CoachDropdown';
import { useAuth } from '../../context/AuthContext';
import { useRole } from '../../context/RoleContext';

const LoginForm = () => {
  const [isRegistering, setIsRegistering] = useState(true);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    password: '',
    coachId: ''
  });

  const [hasError, setHasError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [isConfirmationModalOpen, setIsConfirmationModalOpen] = useState(false);
  const [isCoachDetailsModalOpen, setIsCoachDetailsModalOpen] = useState(false);
  const [coaches, setCoaches] = useState([]);
  const [coachDetails, setCoachDetails] = useState({
    "id": 0,
    "firstName": "",
    "lastName": "",
    "biography": "",
    "imagePath": ""
  });
  const [isInputEmailModalOpen, setIsInputEmailModalOpen] = useState(false);
  const [isResetPasswordModalOpen, setIsResetPasswordModalOpen] = useState(false);
  const [isStatusModalOpen, setIsStatusModalOpen] = useState(false);
  const { login } = useAuth();
  const { updateRole } = useRole();

  useEffect(() => {
    const fetchCoaches = async () => {
      const coachData = await getAvailableCoaches();
      setCoaches(coachData);
      if (coachData.length > 0) {
        setFormData(prevData => ({ ...prevData, coachId: coachData[0].id }));
        const coachDetailsData = await getCoachDetails(coachData[0].id);
        setCoachDetails(coachDetailsData);
      }
    };

    fetchCoaches();
  }, []);

  const toggleForm = () => {
    setIsRegistering(!isRegistering);
    setHasError(false);
    setErrorMessage('');
  };

  const handleChange = ({ target: { name, value } }) => {
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleCoachSelect = (coachId) => {
    setFormData((prevData) => ({ ...prevData, coachId }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setHasError(false);
    setErrorMessage('');

    const { username, password, firstName, lastName, email, coachId } = formData;

    if (!username || !password || (isRegistering && (!firstName || !lastName || !email || !coachId))) {
      setIsStatusModalOpen(true);
      return;
    }

    if (isRegistering) {
      await registerUser();
    } else {
      await logIn();
    }
  };

  const logIn = async () => {
    try {
      const loginResponse = await userLogIn({ username: formData.username, password: formData.password });
      if (loginResponse.accessToken) {
        login(loginResponse);
        if (loginResponse.role) {
          updateRole(loginResponse.role);
        }
      } else {
        throw new Error('Invalid credentials');
      }
    } catch (error) {
      setHasError(true);
      setErrorMessage(`Log in failed: ${error.message}`);
      setIsStatusModalOpen(true);
    }
  };

  const registerUser = async () => {
    try {
      const registerResponse = await userRegister(formData);
      if (registerResponse.message && registerResponse.message.startsWith("ERROR")) {
        setHasError(true);
        setErrorMessage(registerResponse.message.substring(5));
        setIsStatusModalOpen(true);
      } else {
        console.log("Registration successful");
        setIsConfirmationModalOpen(true);
      }
    } catch (error) {
      setHasError(true);
      const errorMessageWithoutPrefix = error.message.replace("ERROR", "").trim();
      setErrorMessage(`Register error: ${errorMessageWithoutPrefix}`);
      setIsStatusModalOpen(true);
    }
  };

  const handleModalConfirm = (confirmationCode) => {
    console.log('Confirmed with code:', confirmationCode);
    setIsConfirmationModalOpen(false);
    setIsRegistering(false);
  };

  const handleConfirmationModalClose = () => {
    setIsConfirmationModalOpen(false);
  };

  const openCoachDetails = async () => {
    setIsCoachDetailsModalOpen(true);
    const coachData = await getCoachDetails(formData.coachId);
    setCoachDetails(coachData);
  };

  const handleForgotPasswordClick = () => {
    setIsInputEmailModalOpen(true);
  };

  const handleInputEmailModalClose = () => {
    setIsInputEmailModalOpen(false);
  };

  const handleInputEmailSubmit = (email) => {
    console.log("Reset password request for email:", email);
    setIsInputEmailModalOpen(false);
  };

  const handleInputEmailSuccess = (email) => {
    console.log("Password reset code sent to email:", email);
    setIsInputEmailModalOpen(false);
    setIsResetPasswordModalOpen(true);
  };

  const handleResetPasswordModalClose = () => {
    setIsResetPasswordModalOpen(false);
  };

  const handleCloseStatusModal = () => {
    setIsStatusModalOpen(false);
  };

  const { username, password, firstName, lastName, email, coachId } = formData;

  return (
    <div className="login-div">
      <div className="bg-img"></div>
      <div className="content">
        <p id="login-title">Start your journey today</p>
        <form onSubmit={handleSubmit}>
          {isRegistering && (
            <>
              <InputField type="text" name="firstName" placeholder="First name" value={firstName} onChange={handleChange} hasError={hasError} />
              <InputField type="text" name="lastName" placeholder="Last name" value={lastName} onChange={handleChange} hasError={hasError} />
              <InputField type="email" name="email" placeholder="Email" value={email} onChange={handleChange} hasError={hasError} />
            </>
          )}
          <InputField type="text" name="username" placeholder="Username" value={username} onChange={handleChange} hasError={hasError} />
          <InputField type="password" name="password" placeholder="Password" value={password} onChange={handleChange} hasError={hasError} />
          {!isRegistering && <p className="link-el forgot-password" onClick={handleForgotPasswordClick}>Forgot password?</p>}
          {isRegistering && (
            <div id="coach-selection-div">
              <p id="selection-title">Select your coach: </p>
              <CoachDropdown selectedCoachId={coachId} onCoachSelect={handleCoachSelect} coaches={coaches} />
              <img src="/images/details.png" alt="X" className="coach-details-button" onClick={openCoachDetails} />
              {
                isCoachDetailsModalOpen && (
                  <CoachDetailsModal
                    show={isCoachDetailsModalOpen}
                    onClose={() => setIsCoachDetailsModalOpen(false)}
                    coach={coachDetails}
                  />
                )
              }
            </div>
          )}
          <div className='login-b-div'>
            <button className="button-element login-button dark-button" type="submit">{isRegistering ? 'Register' : 'Log in'}</button>
          </div>
        </form>
        <div className="options">
          {isRegistering ? (
            <>
              <p>Already have an account?</p>
              <p className="link-el" onClick={toggleForm}>Log in</p>
            </>
          ) : (
            <>
              <p>Don't have an account?</p>
              <p className="link-el" onClick={toggleForm}>Register</p>
            </>
          )}
        </div>
      </div>
      <ConfirmationModal
        isOpen={isConfirmationModalOpen}
        onClose={handleConfirmationModalClose}
        onConfirm={handleModalConfirm}
        email={email}
        setIsRegistering={setIsRegistering} // Pass the setter function
      />
      {isInputEmailModalOpen && (
        <InputEmailModal
          onSubmit={handleInputEmailSubmit}
          onClose={handleInputEmailModalClose}
          onSuccess={handleInputEmailSuccess}
        />
      )}
      {isResetPasswordModalOpen && (
        <ResetPasswordModal
          onClose={handleResetPasswordModalClose}
          onSubmit={() => setIsResetPasswordModalOpen(false)}
          resetPassword={() => console.log('Reset Password function')}
        />
      )}
      {isStatusModalOpen && (
        <StatusMessageModal
          isOpen={isStatusModalOpen}
          onClose={handleCloseStatusModal}
          message={errorMessage}
        />
      )}
    </div>
  );
};

export default LoginForm;
