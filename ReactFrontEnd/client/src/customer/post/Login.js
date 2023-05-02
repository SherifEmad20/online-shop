import React from 'react'
import { useRef, useState,useEffect } from "react";
import axios from "axios";
import CustHomePage from '../../CustHomePage';
import '../../styles/Login.css';
import Cookies from 'js-cookie';



function Login() {
    const [username, setEmail] = useState("");
    const [password, setPass] = useState("");
  
    const [LogOut, setLogOut] = useState(false);
    const [Registered, setReg] = useState(false);
    const sessionId = Cookies.get('JSESSIONID');
  
    const [data, setData] = useState({
      username:"",
      password:"",
    });


  

    
    function textChange () {
        setEmail(document.getElementById('username').value);
        setPass(document.getElementById('password').value);
       
    
      
      }
      function dataChange () {
        setData({
          username:username,
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
        window.location='/custHomePage';
      })
      
    }
  }
  
  
  
  
  const onSubmit = (e)=>{
      e.preventDefault();
    
    console.log(username)
    console.log(password)
    console.log(data);


    const axiosInstance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        Cookie: `JSESSIONID=${sessionId}`
          },

      withCredentials: true
   })

   axiosInstance.post("http://localhost:18072/Company-1.0-SNAPSHOT/api/customer/login",data).then((response)=>{

     console.log(response.data);
         
      
          if(response.data === "Customer logged in successfully!"){
            localStorage.clear();
            localStorage.setItem('loggedIn',username);
            console.log(response.headers);
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
      <div className="title">Customer Login</div>
      <div className="subtitle">Don't have an Account?</div>
      <a className='CustLink' href='/custRegister'>Register as Customer</a>
      <div className="input-container ic1">
        <input onChange={textChange}  autoComplete='off' id="username" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="email" className="placeholder">Username</label>
      </div>
    
      <div className="input-container ic2">
        <input onChange={textChange} id="password" className="input" type="password" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="password" type="password" className="placeholder">Password</label>
      </div>
    
   
      <button type='submit' onClick={dataChange} className="submit">submit</button>
    
     {RegisterHandle()}
     {Navigate()}

    </form>
    
   
    </div>
  )
  
 
}

export default Login