import React from 'react';
import axios from "axios";
import { useEffect, useState } from 'react';
import '../styles/GetAdmins.css';
import Cookies from 'js-cookie';

function GetAdmins() {
    const [listOfAdmins, setListOfAdmins] = useState([]);
    const sessionId = Cookies.get('JSESSIONID')

    const axiosInstance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        Cookie: `JSESSIONID=${sessionId}`

          },
      withCredentials: true
   })


  useEffect(()=>{
    axiosInstance.get("http://localhost:18072/Company-1.0-SNAPSHOT/api/admin/getAllAdmins").then((response)=>{
        setListOfAdmins(response.data);
    })
  },[])
  return (
    <div className="App">
      <a href='/createCompany'>aloo</a>
      <div class="container">
<table>
  <thead>
    <tr>
      <th>ID</th>
      <th>Email</th>
      <th>Company Name</th>
    </tr>
  </thead>
  <tbody>
    
      {
        listOfAdmins.map((value, key)=>{
          return <tr>
             <td>{value.id}</td>
             <td>{value.email}</td>
             <td>{value.name}</td>
             </tr>
          
        })
      }

      </tbody>
      </table>
      </div>
     
    </div>
  );
}

export default GetAdmins;