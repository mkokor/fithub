import React, { Component } from "react";
import Slider from "react-slick";
import '../css/slick.css';
import '../css/slick-theme.css';

export default class SimpleSlider extends Component {

  render() {
    const { news } = this.props;
    
    const settings = {
      autoplay: true,
      autoplaySpeed: 3000,
      dots: false,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1
    };

    return (
      <div className="news-slider">
        <Slider {...settings}>
          {
            news.map((slide, index) => (
              <img className="banner" src={slide.image} alt={`slide-${index}`} key={index}></img>
            ))
          }
        </Slider>
      </div>
    )
  }
}
