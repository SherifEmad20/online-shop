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
   
   let currentComp = localStorage.getItem('loggedIn');


  useEffect(()=>{
    axiosInstance.get(`http://localhost:18072/Company-1.0-SNAPSHOT/api/sellingCompany/getSellingCompanyById/${currentComp}`).then((response)=>{
        setListOfAdmins(response.data['products']);
        console.log(response.data['products'])
    })


  },[])

  function click(){
    window.location='CompHomePage';

  }
  return (
    <div className="App">
      <div className="container">
<table>
  <thead>
    <tr>
      <th>Product Name</th>
      <th>Product Description</th>
      <th>Product Price</th>
      <th>Product Quantity</th>
      
    </tr>
  </thead>
  <tbody>
    
      {
        listOfAdmins.map((value, key)=>{
          return <tr>
             <td>{value.productName}</td>
             <td>{value.productDescription}</td>
             <td>{value.productPrice}</td>
             <td>{value.productQuantity}</td>
            
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