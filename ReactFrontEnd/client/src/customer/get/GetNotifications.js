import React from 'react';
import axios from "axios";
import { useEffect, useState } from 'react';
import '../../styles/GetAdmins.css';
import Cookies from 'js-cookie';


function GetAdmins() {

    const [listOfAdmins, setListOfAdmins] = useState([]);
    const [flag, setflag] = useState(false);
    const [total, setTotal] = useState(0);
    const sessionId = Cookies.get('JSESSIONID');


    const axiosInstance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        Cookie: `JSESSIONID=${sessionId}`
          },
          params: {
            sessionId
          },


      withCredentials: true
   })


  useEffect(()=>{
    axiosInstance.get(`http://localhost:18072/Company-1.0-SNAPSHOT/api/customer/getNotifications`).then((response)=>{
        setListOfAdmins(response.data);
        console.log(response.data)
    })


  },[])

  function click(){
    window.location='/custHomePage';

  }

  function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  function Navigate(){
    if(flag){
      
      return [
      <p className='retMes'>Order is Done</p>,
      window.location='/custHomePage'
    ]
        
      
      
    }
 
  
    
   
  }
  return (
    <div className="App">
      <div className="container">
<table>
  <thead>
    <tr>
      <th>Notification</th>
      
      
    </tr>
  </thead>
  <tbody>
 
    
      {
        listOfAdmins.map((value, key)=>{
          return <tr>
             <td>{value
             }</td>
             
           
             </tr>
             
          
        })
      }
      {Navigate()}

      </tbody>
      </table>
      <button onClick={click} className='bt2'>Go to Home Page</button>
    
      </div>

     
    </div>
  );
}

export default GetAdmins;