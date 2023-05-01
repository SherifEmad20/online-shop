import React from 'react';
import axios from "axios";
import { useEffect, useState } from 'react';
import '../../styles/GetAdmins.css';


function GetAdmins() {
    var i;

    const [listOfAdmins, setListOfAdmins] = useState([]);
    const [listOfOrders, setListOfOrders] = useState([]);
    const [flag, setflag] = useState(false);

  useEffect(()=>{
    axios.get(`http://localhost:18072/Company-1.0-SNAPSHOT/api/order/getShippingOrders/${localStorage.getItem('loggedIn')}`).then((response)=>{
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

 
  return (
    <div className="App">
      <div className="container">
<table>
  <thead>
    <tr>
      <th>Order ID</th>
      <th>Order Date</th>
      <th>Number of Products</th>
      <th>Order Price</th>
      
      
    </tr>
  </thead>
  <tbody>
 
    
      {
        listOfAdmins.map((value, key)=>{
          return <tr>
             <td>{value.orderId}</td>
             <td>{value.orderDate}</td>
             <td>{value.products.length}</td>
             <td>{value.orderTotal}</td>
             </tr>
             
          
        })
      }
      {//Navigate()
      }

      </tbody>
      </table>
      <button onClick={click} className='bt2'>Go to Home Page</button>
      
      </div>

     
    </div>
  );
}

export default GetAdmins;