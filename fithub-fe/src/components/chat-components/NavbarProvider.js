import React, { useState, useEffect } from 'react';
import StompClientContext from './StompClientContext';
import { over } from 'stompjs';
import SockJS from 'sockjs-client';

const NavbarProvider = ({ children }) => {
  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    const socket = new SockJS('http://localhost:8002/ws');
    const client = over(socket);

    client.connect({}, () => {
      setStompClient(client);
    });

    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, []);

  return (
    <StompClientContext.Provider value={stompClient}>
      {children}
    </StompClientContext.Provider>
  );
};

export default NavbarProvider;
