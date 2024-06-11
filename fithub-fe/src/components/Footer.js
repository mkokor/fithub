import '../css/Footer.css';

function Footer() {

  const openMaps = () => {
    window.open("https://www.google.com/maps/place/Fit+Hub/@-6.3453894,106.7776633,15z/data=!4m6!3m5!1s0x2e69ef0078084d97:0x7c3750aa8a2cad29!8m2!3d-6.3453894!4d106.7776633!16s%2Fg%2F11vzx_cvgq?entry=ttu", "_blank");
  }
  
  const openEmail = (email) => {
    const mailtoLink = `mailto:${email}`
    window.location.href = mailtoLink
  }
  
  return (
    <div id="footer">
      <div id="footer-info">
        <div id="footer-contact">
          <div className='footer-contact-link'>
            <img src='images/icons/icons8-location-white-50.png'></img>
            <p onClick={() => {openMaps() }}>24 Cornelia Street, New York</p>
          </div>
          <div className='footer-contact-link'>
            <img src='images/icons/icons8-phone-white-50.png'></img>
            <p>+978 574 55 43</p>
          </div>
          <div className='footer-contact-link'>
            <img src='images/icons/icons8-mail-white-50.png'></img>
            <p onClick={() => {openEmail('fithub@gmail.com') }}>fithub@gmail.com</p>
          </div>
        </div>
        <div id="footer-company-info">
          <p id="footer-title">ABOUT US</p>
          <p id='company-text'>At FitHub our goal is to give you all the tools and support you need in order to reach your goals. We offer one on one coaching, as well as all sorts of group programs including zumba, yoga, pilates and weight training classes. Our programs include weekly updated mealplans and the free use of our online platform through which you can make reservations for classes, view your mealplan, chat with your coach, catch up on our latest offers and make song suggestions.</p>           
          <div id="footer-socials">
            <img className='footer-social-link' src="images/icons/icons8-instagram-white-50.png"></img> 
            <img className='footer-social-link' src="images/icons/icons8-facebook-white-50.png"></img>
            <img className='footer-social-link' src="images/icons/icons8-twitter-white-64.png"></img>
          </div>
        </div>
      </div>
      <p id="copyright">Copyright© 2024 FITHUB All rights reserved.</p>    
    </div>
  )
}

export default Footer;