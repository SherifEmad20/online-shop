import React from 'react'
import { useRef, useState,useEffect } from "react";
import axios from "axios";

import '../styles/Login.css';
import Cookies from 'js-cookie';





function Login() {
    const [username, setEmail] = useState("");
    const [password, setPass] = useState("");
  
    const [LogOut, setLogOut] = useState(false);
    const [Registered, setReg] = useState(false);
    const sessionId = Cookies.get('JSESSIONID');

    
    const [data, setData] = useState({
      companyName:"",
      password:"",
    });


    


  

    
    function textChange () {
        setEmail(document.getElementById('username').value);
        setPass(document.getElementById('password').value);
       
    
      
      }
      function dataChange () {
        setData({
          companyName:username,
          password:password,
      })
      }
      
      
      
      function RegisterHandle(){
        if(LogOut){
          return <p className='loginFail'>Login Failed! Try Again</p>
    }
    else if(Registered){
        return <p className='loginFail'>Login Successfuly</p>

        
    }
}
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
  
  function Navigate(){
    if(Registered){
      sleep(500).then(() => {
        window.location='/compHomePage';
      })
      
    }
  }
  
  
  
  const axiosInstance = axios.create({
    headers: {
      'Content-Type': 'application/json',
      Cookie: `JSESSIONID=${sessionId}`

        },

    withCredentials: true
 })


  const onSubmit = (e)=>{
      e.preventDefault();
    
    console.log(username)
    console.log(password)
    console.log(data);

      axiosInstance.post("http://localhost:18072/Company-1.0-SNAPSHOT/api/sellingCompany/loginSellingCompany", data).then((response)=>{
        console.log(response.data);
         
      
          if(response.data === "Selling company logged in successfully!"){
              localStorage.clear();
              localStorage.setItem('loggedIn',username);
              setReg(true);
             
              }
              else{
               
                setLogOut(true);
              }
         
          }).catch(err =>{
              setLogOut(true);
          })
          
    }
  return (
    <div>
        
    <form onSubmit={onSubmit} className="form">
      <div className="title">Selling Company Login</div>
      
      <div className="input-container ic1">
        <input onChange={textChange}  autoComplete='off' id="username" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="email" className="placeholder">companyName</label>
      </div>
    
      <div className="input-container ic2">
        <input onChange={textChange} id="password" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="password" className="placeholder">Password</label>
      </div>
    
   
      <button type='submit' onClick={dataChange} className="submit">submit</button>
    
     {RegisterHandle()}
     {Navigate()}

    </form>
    
   
    </div>
  )
  
 
}

export default Login