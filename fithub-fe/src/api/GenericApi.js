import { userAccessRefresh } from "./UserApi";
import { useAuth } from "../context/AuthContext";

export const sendRequest = async (request) => {
  const { logout } = useAuth();
  try {
    const user = JSON.parse(localStorage.getItem('user'));
    let accessToken = user?.accessToken;

    const response = await fetch(request.url, {
      ...request,
      headers: {
        ...request.headers,
        'Authorization': `Bearer ${accessToken}`
      }
    });

    if (response.status === 401) {
      const refreshedUser = await userAccessRefresh();
      accessToken = refreshedUser.accessToken;

      const refreshedResponse = await fetch(request.url, {
        ...request,
        headers: {
          ...request.headers,
          'Authorization': `Bearer ${accessToken}`
        }
      });
      if (!refreshedResponse.ok) {
        throw new Error('Request failed after access refresh');
      }

      return refreshedResponse;
    }

    if (!response.ok) {
      throw new Error('Request failed');
    }

    return response;
  } catch (error) {
    console.error('Error sending request:', error);
    throw error;
  }
};
