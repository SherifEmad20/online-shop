import React from 'react'
import { useRef, useState,useEffect } from "react";
import axios from "axios";
import CustHomePage from '../../CustHomePage';
import '../../styles/CustLogin.css';
import Cookies from 'js-cookie';





function Login() {
    const [email, setEmail] = useState("");
    const [password, setPass] = useState("");
    const [username, setUsername] = useState("");
    const [adress, setAdress] = useState("");
    const [balance, setBalance] = useState(0);
    const [LogOut, setLogOut] = useState(false);
    const [Registered, setReg] = useState(false);
    const sessionId = Cookies.get('JSESSIONID');

    
    const [data, setData] = useState({
        username:"",
        email:"",
        password:"",
        address:"",
        balance:0
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

            axiosInstance.post("http://localhost:18072/Company-1.0-SNAPSHOT/api/customer/register", data).then((response)=>{
                console.log(response.data);
               
            
                if(response.data.includes("Customer registered successfully!")){
                    setReg(true);
                    setLogOut(false)
                    }
               
                }).catch(err =>{
                    setReg(false)
                    setLogOut(true);
                })
                //setflag(true);
          }

    function textChange () {
        setEmail(document.getElementById('email').value);
        
        setUsername(document.getElementById('name').value);
        
        setPass(document.getElementById('password').value);
       setAdress(document.getElementById('adress').value);
       setBalance(document.getElementById('balance').value);
        setData({
            email:email,
            username:username,
            password:password,
            address:adress,
            balance:balance
        })
    }

    function dataChange () {
        setData({
            email:email,
            username:username,
            password:password,
            address:adress,
            balance:balance
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
            window.location='/custLogin';
            })

    }
}



    
    return (
        <div>
        
    <form onSubmit={onSubmit} className="form">
      <div className="title">Customer Register</div>
      <div className="subtitle">Already Registered</div>
      <a className='CustLink' href='/CustLogin'>Login As Customer</a>
      <div className="input-container ic1">
        <input onChange={textChange} autoComplete='off' id="email" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="email" className="placeholder">Email</label>
      </div>
      <div className="input-container ic2">
        <input onChange={textChange} id="name" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="name" className="placeholder">Username</label>
      </div>
      <div className="input-container ic2">
        <input onChange={textChange} id="password" className="input" type="password" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="password" className="placeholder">Password</label>
      </div>
      <div className="input-container ic2">
        <input onChange={textChange} id="adress" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="password" className="placeholder">Adress</label>
      </div>
      <div className="input-container ic2">
        <input onChange={textChange} id="balance" className="input" type="text" placeholder=" " />
        <div className="cut"></div>
        <label htmlFor="password" className="placeholder">Balance</label>
      </div>
      
   
      <button type='submit'onClick={dataChange} className="submit">submit</button>
    
     {RegisterHandle()}
     {
     Navigate()
    }

    </form>
    
   
    </div>
  )
  
 
}

export default Login