export const userLogIn = async (loginData) => {
  try {
    const response = await fetch('/auth-service/user/sign-in', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(loginData)
    });

    if (response.status === 200) {
      const responseData = await response.json();
      return responseData;
    } else {
      throw new Error('Log in failed');
    }
  } catch (error) {
    console.error('Log in error:', error);
    return {
      "accessToken": "",
      "role": "",
      "username": ""
    };
  }
};

export const userRegister = async (registerData) => {
  try {
    const response = await fetch('/auth-service/client/sign-up', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(registerData)
    });

    if (response.status === 200) {
      const responseData = await response.json();
      return responseData;
    } else {
      const responseData = await response.json();
      throw new Error(responseData.errors[0].message);
    }
  } catch (error) {
    console.error('Register error:', error.message);
    return {
      "message": "ERROR" + error.message,
    };
  }
};

export const userAccessRefresh = async () => {
  try {
    const user = JSON.parse(localStorage.getItem('user'));
    const accessToken = user?.accessToken;
    const response = await fetch('/auth-service/user/access-refresh', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`
      }
    });

    if (response.status === 200) {
      const responseData = await response.json();
      localStorage.setItem('user', JSON.stringify(responseData));
      return responseData;
    } else {
      throw new Error('Access refresh failed');
    }
  } catch (error) {
    console.error('Access refresh error:', error);
    return {
      "acessToken": "",
      "role": "",
      "username": ""
    };
  }
};

export const getAvailableCoaches = async () => {
  try {
    const response = await fetch('/auth-service/coach');
    
    if (!response.ok) {
      throw new Error('Failed to fetch coach data');
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching coach data:', error);
    return [] ;
  }
};

export const getCoachDetails = async (coachId) => {
  try {
    const response = await fetch(`/auth-service/coach/${coachId}`);
    
    if (!response.ok) {
      throw new Error('Failed to fetch coach data');
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error('Error fetching coach data:', error);
    return {};
  }
};

export const confirmCode = async (confirmationData) => {
  try {
    console.log(confirmationData)
    const response = await fetch(`/auth-service/client/${confirmationData.email}/email-confirmation`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(confirmationData)
    });

    if (response.status === 200) {
      const responseData = await response.json();
      return responseData;
    } else {
      throw new Error('Confirmation failed');
    }
  } catch (error) {
    console.error('Confirmation error:', error);
    return {
      "message": "" 
    };
  }
};

export const resendConfirmationCode = async (emailData) => {
  try {
    const response = await fetch(`/auth-service/client/${emailData}/email-confirmation-code/resend`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(emailData)
    });

    if (response.status === 200) {
      const responseData = await response.json();
      return responseData;
    } else {
      throw new Error('Resend failed');
    }
  } catch (error) {
    console.error('Resend code error:', error);
    return {
      "message": "",
    };
  }
};

export const getPasswordResetCode = async (email) => {
  try {
    const response = await fetch(`/auth-service/user/${email}/password-reset-code`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      }
    });

    if (response.status === 200) {
      const responseData = await response.json();
      return responseData;
    } else {
      throw new Error('Code send failed');
    }
  } catch (error) {
    console.error('Code send error:', error);
    return {
      "message": ""
    };
  }
};

export const resetPassword = async (resetData) => {
  try {
    const response = await fetch(`/auth-service/user/${resetData.email}/password-reset`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(resetData)
    });

    if (response.status === 200) {
      const responseData = await response.json();
      return responseData;
    } else {
      throw new Error('Password refresh failed');
    }
  } catch (error) {
    console.error('Password refresh error:', error);
    return {
      "message": ""
    };
  }
};
export const signOut = async () => {
  try {
    const user = JSON.parse(localStorage.getItem('user'));
    const refreshToken = user?.accessToken;
    const response = await fetch(`/auth-service/user/sign-out`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${refreshToken}`
      }
    });

    if (response.status === 200) {
      const responseData = await response.json();
      return responseData;
    } else {
      throw new Error('Sign out failed');
    }
  } catch (error) {
    console.error('Sign out error:', error);
    return {
      "message": ""
    };
  }
};





