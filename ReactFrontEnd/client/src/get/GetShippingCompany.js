import React from 'react';
import axios from "axios";
import { useEffect, useState } from 'react';
import '../styles/GetAdmins.css';
import axiosCook from '../Cookies/Cookies';
import Cookies from 'js-cookie';


function GetAdmins() {

    const [listOfAdmins, setListOfAdmins] = useState([]);
    const sessionId = Cookies.get('JSESSIONID')

  useEffect(()=>{
    axiosCook.get("http://localhost:18072/Company-1.0-SNAPSHOT/api/admin/getShippingCompanies",{
      params: {
        sessionId  
      }
  
      }).then((response)=>{
        setListOfAdmins(response.data);
    })


  },[])

  function click(){
    window.location='/homePage';

  }
  return (
    <div className="App">
      <div className="container">
<table>
  <thead>
    <tr>
      <th>Company Name</th>
      <th>UserName</th>
      
    </tr>
  </thead>
  <tbody>
    
      {
        listOfAdmins.map((value, key)=>{
          return <tr>
             <td>{value.companyName}</td>
             <td>{value.username}</td>
             
             </tr>
          
        })
      }

      </tbody>
      </table>
      <button onClick={click} className='bt2'>Go to Home Page</button>
      </div>

     
    </div>
  );
}

export default GetAdmins;