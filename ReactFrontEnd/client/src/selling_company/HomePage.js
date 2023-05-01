import React from 'react'
import '../styles/Home.css'
//import 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'





function HomePage() {

    let currentUser = localStorage.getItem('loggedIn');



  return (
    <div>
      <input type="checkbox" id="active"/>
      <label htmlFor="active" className="menu-btn"><i className="fas fa-bars"></i>Press</label>
      <div className="wrapper">
         <ul>
            <li><a href="/addProduct">Add Product</a></li>
            <li><a href="#">History</a></li>
            <li><a href="/">Logout</a></li>
           
         </ul>
      </div>
      <div className="content">
         <div className="title">
            Welcome to Our Website {currentUser}
         </div>
         <p>
            Press on the button Above
         </p>
      </div>
    </div>
  )
}

export default HomePage