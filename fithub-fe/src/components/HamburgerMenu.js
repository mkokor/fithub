import React from 'react';
import { NavLink } from "react-router-dom";
import "../css/HamburgerMenu.css";
import "../css/LogoutButton.css"
import "../css/Navbar.css";
import LogoutButton from './login-components/LogoutButton';

const HamburgerMenu = ({ role }) => {
  return (
    <div id="hamburger-menu-div" className="hamburger-closed-div">
      {role === 'CLIENT' ? (
        <>
          <NavLink to="/" className="hamburger-menu-item-link" id="hamburger-home-link">Home</NavLink>
          <NavLink to="/nutrition" className="hamburger-menu-item-link" id="hamburger-nutrition-link">Nutrition</NavLink>
          <NavLink to="/training" className="hamburger-menu-item-link" id="hamburger-training-link">Training</NavLink>
          <NavLink to="/progression" className="hamburger-menu-item-link" id="hamburger-progression-link">Progression</NavLink>
          <NavLink to="/chatroom" className="hamburger-menu-item-link" id="hamburger-chatroom-link">Chatroom</NavLink>
          <div id="logout-div">
            <hr></hr>
            <LogoutButton tag="side" />
          </div>
        </>
        
      ) : (
        <>
          <NavLink to="/" className="hamburger-menu-item-link" id="hamburger-schedule-link">Schedule</NavLink>
          <NavLink to="/clients" className="hamburger-menu-item-link" id="hamburger-clients-link">Clients</NavLink>
          <NavLink to="/progression" className="hamburger-menu-item-link" id="hamburger-progression-link">Progression</NavLink>
          <NavLink to="/chatroom" className="hamburger-menu-item-link" id="hamburger-chatroom-link">Chatroom</NavLink>
          <div id="logout-div">
            <hr></hr>
            <LogoutButton tag="side" />
          </div>
        </>
      )}
    </div>
  );
}

export default HamburgerMenu;
