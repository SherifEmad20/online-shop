import React from 'react';
import axios from "axios";
import { useEffect, useState } from 'react';
import '../styles/GetAdmins.css';

function GetAdmins() {

    const [listOfAdmins, setListOfAdmins] = useState([]);

  useEffect(()=>{
    axios.get("http://localhost:18072/Admin-1.0-SNAPSHOT/api/admin/getAllAdmins").then((response)=>{
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