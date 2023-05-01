import React from 'react'
import { useRef, useState,useEffect } from "react";
import axios from "axios";
import HomePage from '../HomePage';
import '../styles/Login.css';
import axiosCook from '../Cookies/Cookies';
import Cookies from 'js-cookie';





function Login() {
    const [email, setEmail] = useState("");
    const [password, setPass] = useState("");
    const [name, setName] = useState("");
    const [LogOut, setLogOut] = useState(false);
    const [Registered, setReg] = useState(false);
    const sessionId = Cookies.get('JSESSIONID');

    
    const [data, setData] = useState({
        email:"",
        name:"",
        password:""
    });


    const axiosInstance = axios.create({
        headers: {
          'Content-Type': 'application/json',
          Cookie: `JSESSIONID=${sessionId}`
  
            },
  
        withCredentials: true
     })
  

  

        const onSubmit = (e)=>{
            e.preventDefault();
            console.log(data);

            axios.post("http://localhost:18072/Company-1.0-SNAPSHOT/api/admin/register", data).then((response)=>{
               
            
                if(response.data == "Admin registered successfully!"){
                    setReg(true);
                    setLogOut(false)
                    const sessionId = response.headers['set-cookie'][0].split(';')[0].split('=')[1];
                    localStorage.setItem('sessionId', sessionId);
      
                    }
               
                }).catch(err =>{
                    setReg(false)
                    setLogOut(true);
                })
                //setflag(true);
          }

    function textChange () {
        setEmail(document.getElementById('email').value);
        
        setName(document.getElementById('name').value);
        
        setPass(document.getElementById('password').value);
        setData({
            email:email,
            name:name,
            password:password
        })
    }

    function dataChange () {
        setData({
            email:email,
            name:name,
            password:password
      })
      }
      
    


    function RegisterHandle(){
    if(LogOut){
        return <p className='loginFail'>Email Already Exist try another email</p>
    }
    else if(Registered){
        return <p className='loginFail'>Registered Successfuly</p>

        
    }
}
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
 }

    function Navigate(){
    if(Registered){
        sleep(1000).then(() => {
            window.location='/login';
            })

    }
}



    
    return (
        <div>
        
    <form onSubmit={onSubmit} className="form">
      <div className="title">Admin Register</div>
      <div className="subtitle">Already Registered</div>
      <a className='CustLink' href='/login'>Login As Admin</a>
      <div className="input-container ic1">
        <input onChange={textChange} autoComplete='off' id="email" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="email" className="placeholder">Email</label>
      </div>
      <div className="input-container ic2">
        <input onChange={textChange} id="name" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="name" className="placeholder">Name</label>
      </div>
      <div className="input-container ic2">
        <input onChange={textChange} id="password" className="input" type="password" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="password" className="placeholder">Password</label>
      </div>
      
   
      <button type='submit'onClick={dataChange} className="submit">submit</button>
    
     {RegisterHandle()}
     {Navigate()}

    </form>
    
   
    </div>
  )
  
 
}

export default Login