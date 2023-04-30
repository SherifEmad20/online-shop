import React from 'react'
import './styles/Home.css'
//import 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'
import Admin from './post/Login';




function HomePage() {



  return (
    <div>
      <input type="checkbox" id="active"/>
      <label htmlFor="active" className="menu-btn"><i className="fas fa-bars"></i>Press</label>
      <div className="wrapper">
         <ul>
            <li><a href="/Login">Login As Admin</a></li>
            <li><a href="/custLogin">Login as Customer</a></li>
            <li><a href="/sellLogin">Login as Sellling Company</a></li>
            <li><a href="/shipLogin">Login as Shipping Company</a></li>
           
         </ul>
      </div>
      <div className="content">
         <div className="title">
            Welcome to Our Website
         </div>
         <p>
            Press on the button Above
         </p>
      </div>
    </div>
  )
}

export default HomePage