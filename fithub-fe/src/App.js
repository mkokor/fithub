import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import LoginForm from "./components/login-components/LoginForm";
import NavbarClient from "./components/client-components/NavbarClient";
import NavbarCoach from "./components/coach-components/NavbarCoach";
import Home from "./components/Home";
import Nutrition from "./components/client-components/Nutrition";
import Training from "./components/client-components/Training";
import Chatroom from "./components/chat-components/Chatroom";
import Schedule from "./components/coach-components/Schedule";
import Clients from "./components/coach-components/Clients";
import HamburgerMenu from "./components/HamburgerMenu";
import ClientProgression from "./components/client-components/ClientProgression";
import CoachProgression from "./components/coach-components/CoachProgression";
import NotFound from "./components/NotFound";
import { useState, useEffect } from "react";
import { useAuth } from './context/AuthContext';
import { useRole } from './context/RoleContext';

function App() {
  const { isAuthenticated, login } = useAuth();
  const { role, updateRole } = useRole();
  const [navbarIsVisible, setNavbarIsVisible] = useState(true);

  useEffect(() => {
    const navigationRequired = ["/", "/nutrition", "/training", "/progression", "/chatroom", "/coach-nutrition", "/membership"].includes(window.location.pathname);
    setNavbarIsVisible(navigationRequired);
  }, []);

  const handleLogin = (loginData) => {
    login(loginData);
    if (loginData.role) {
      updateRole(loginData.role);
    }
  };

  if (!isAuthenticated) {
    return <LoginForm onLogin={handleLogin} />;
  }

  return (
    <div className="App">
      <BrowserRouter>
        {navbarIsVisible && (role === 'CLIENT' ? <NavbarClient /> : <NavbarCoach />)}
        {navbarIsVisible && <HamburgerMenu role={role} />}
        <Routes>
          {role === 'CLIENT' ? (
            <>
              <Route path="/" element={<Home />} />
              <Route path="/nutrition" element={<Nutrition />} />
              <Route path="/training" element={<Training />} />
              <Route path="/progression" element={<ClientProgression />} />
              <Route path="/chatroom" element={<Chatroom />} />
              <Route path="/*" element={<NotFound />} />
            </>
          ) : (
            <>
              <Route path="/" element={<Schedule />} />
              <Route path="/clients" element={<Clients />} />
              <Route path="/progression" element={<CoachProgression />} />
              <Route path="/chatroom" element={<Chatroom />} />
              <Route path="/*" element={<NotFound />} />
            </>
          )}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;