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
            <li><a href="/getCustomers">List Customers</a></li>
            <li><a href="/getCompanies">List Selling Companies</a></li>
            <li><a href="/getShipping">List Shipping Companies</a></li>
            <li><a href="/createShipCompany">Create Shipping Company</a></li>
            <li><a href="/createSellCompany">Create Selling Company</a></li>
            <li><a href="/">Logout</a></li>
         </ul>
      </div>
      <div className="content">
         <div className="title">
            Welcome back Admin
         </div>
         <p>
            Press on the button Above
         </p>
      </div>
    </div>
  )
}

export default HomePage