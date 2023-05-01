import React from 'react';
import axios from "axios";
import { useEffect, useState } from 'react';
import '../styles/GetAdmins.css';
import axiosCook from '../Cookies/Cookies';
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
    axiosInstance.get("http://localhost:18072/Company-1.0-SNAPSHOT/api/admin/getAllCustomers").then((response)=>{
        setListOfAdmins(response.data);
        console.log(response.data)
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
      <th>Username</th>
      <th>E-mail</th>
      <th>Adress</th>
      <th>Balance</th>
    </tr>
  </thead>
  <tbody>
    
      {
        listOfAdmins.map((value, key)=>{
          return <tr>
             <td>{value.username}</td>
             <td>{value.email}</td>
             <td>{value.address}</td>
             <td>{value.balance}</td>
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