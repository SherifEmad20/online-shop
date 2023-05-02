import React from 'react'
import { useRef, useState,useEffect } from "react";
import axios from "axios";
import HomePage from '../HomePage';
import '../styles/Login.css';
import Cookies from 'js-cookie';





function Login() {
    const [email, setEmail] = useState("");
    const [password, setPass] = useState("");
  
    const [LogOut, setLogOut] = useState(false);
    const [Registered, setReg] = useState(false);
    const sessionId = Cookies.get('JSESSIONID');

    
    const [data, setData] = useState({
      email:"",
      password:"",
    });


  

    
    function textChange () {
        setEmail(document.getElementById('email').value);
        setPass(document.getElementById('password').value);
       
    
      
      }
      function dataChange () {
        setData({
          email:email,
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
        window.location='/homePage';
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
    
    console.log(email)
    console.log(password)
      console.log(data);

      axiosInstance.post("http://localhost:18072/Company-1.0-SNAPSHOT/api/admin/login", data).then((response)=>{
        console.log(response.data);
         
      
          if(response.data === "Admin logged in successfully!"){
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
      <div className="title">Admin Login</div>
      
      <div className="input-container ic1">
        <input onChange={textChange}  autoComplete='off' id="email" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="email" className="placeholder">Email</label>
      </div>
    
      <div className="input-container ic2">
        <input onChange={textChange} id="password" className="input" type="password" placeholder=" " />
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