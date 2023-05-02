import React from 'react'
import { useRef, useState,useEffect } from "react";
import axios from "axios";
import CustHomePage from '../../CustHomePage';
import '../../styles/Login.css';
import Cookies from 'js-cookie';





function Login() {
   
    const [listOfAdmins, setListOfAdmins] = useState([]);
  
    const [LogOut, setLogOut] = useState(false);
    const [Resp, setResp] = useState("");
    const [Registered, setReg] = useState(false);
    const [Company, setCompany] = useState("");
    const sessionId = Cookies.get('JSESSIONID');

    
    const [data, setData] = useState({
      companyName:""
      
    });

    let currentComp = localStorage.getItem('loggedIn');
    let currentId = localStorage.getItem('orderId');

    const axiosInstance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        Cookie: `JSESSIONID=${sessionId}`

          },

      withCredentials: true
   })


    useEffect(()=>{
        axiosInstance.get("http://localhost:18072/Company-1.0-SNAPSHOT/api/shippingCompany/getAllShippingCompanies").then((response)=>{
            setListOfAdmins(response.data);
            setResp(response.data)
            console.log(response.data)
          
           
        })
    
    
      },[])


  

    
    function textChange () {
        setCompany(document.getElementById('companyName').value);
       
    }
      function dataChange () {
        setData({
          
          companyName:Company,
      })
      }
      
    

      
      
      function RegisterHandle(){
        if(LogOut){
          return <p className='loginFail'>Shipping Company can't deliver to your location</p>
    }
    else if(Registered){
        return <p className='loginFail'>Shipping Request made Successfully</p>

        
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
    
    console.log(Company)
   

      axiosInstance.post(`http://localhost:18072/Company-1.0-SNAPSHOT/api/customer/makeShippingRequest/${Company}/${currentId}`).then((response)=>{
        console.log(response.data);
        
         
      
          if(response.data === "Shipping Company can't deliver to your location"){
           
              
              setLogOut(true);
            }
            else{
                  setReg(true);
               
              }
         
          }).catch(err =>{
              setLogOut(true);
          })
          
    }
  return (
    <div>
        
    <form onSubmit={onSubmit} className="form">
      <div className="title">Shipping Request</div>
      
      <a className='CustLink' href='/custHomePage'>Go to Home Page</a>
      <div className="input-container ic1">
        <select onChange={textChange}  autoComplete='off' id="companyName" className="input" type="text" placeholder=" " >
            <option>Select Copmany</option>
            {
                 listOfAdmins.map((value, key)=>{
                    return <option>
                       {value.companyName}
                     
                       </option>
                       
                    
                  })
            }

        </select>
        <div className="cut"></div>
        <label htmlFor="companyName" className="placeholder">Company Name</label>
      </div>
    
     
   
      <button type='submit' onClick={dataChange} className="submit">submit</button>
    
     
     {RegisterHandle()}
     {Navigate()}

    </form>
    
   
    </div>
  )
  
 
}

export default Login