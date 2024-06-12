import "../../css/Navbar.css";

import { NavLink } from "react-router-dom";

import { signOut } from "../../api/UserApi";
import LogoutButton from "../login-components/LogoutButton";


const NavbarCoach = () => { 
  function handleHamburgerMenu() {
    let hamburgerMenu = document.getElementById("hamburger-menu-div")
    if(hamburgerMenu.className === "hamburger-closed-div") {
      hamburgerMenu.className = "hamburger-opened-div"
    } else if(hamburgerMenu.className === "hamburger-opened-div") {
      hamburgerMenu.className = "hamburger-closed-div"
    }
  }

  return (
    <div id="menu-div">
      <img id="logo-img" src='/images/logo.png' alt="FITHUB"></img>
      <NavLink to="/" className="menu-item-link" id="home-link">Schedule</NavLink>
      <NavLink to="/clients" className="menu-item-link" id="clients-link">Clients</NavLink>
      <NavLink to="/progression" className="menu-item-link" id="progression-link">Progression</NavLink>
      <NavLink to="/chatroom" className="menu-item-link" id="chatroom-link">Chatroom</NavLink>
      <LogoutButton tag="navbar" />
      <img id="menu-icon-img" src="/images/hamburger-menu-icon.png" alt="MENU" onClick={ () => { handleHamburgerMenu() }}></img>
    </div>
  );
}

export default NavbarCoach;