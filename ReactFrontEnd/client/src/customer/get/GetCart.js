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

      withCredentials: true
   })




    
  useEffect(()=>{
    axiosInstance.get(`http://localhost:18072/Company-1.0-SNAPSHOT/api/customer/getCart`).then((response)=>{
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
      <th>Product ID</th>
      <th>Product Name</th>
      <th>Product Description</th>
      <th>Product Price</th>
      
      
    </tr>
  </thead>
  <tbody>
 
    
      {
        listOfAdmins.map((value, key)=>{
          return <tr>
             <td>{value.productId}</td>
             <td>{value.productName}</td>
             <td>{value.productDescription}</td>
             <td>{value.productPrice}</td>
           
             </tr>
             
          
        })
      }
      {Navigate()}

      </tbody>
      </table>
      <button onClick={click} className='bt2'>Go to Home Page</button>
      <button onClick={()=>{
        axios.post(`http://localhost:18072/Company-1.0-SNAPSHOT/api/order/makeOrder/${localStorage.getItem('loggedIn')}`).then((response)=>{
            console.log(response.data)
            if(response.data === "Order made successfully!"){

                setflag(true);
                

            }
            else{
                setflag(false);
            }
        }).catch(err =>{
            setflag(false);
        })
        
      }} className='bt2'>Confirm Order</button>
      </div>

     
    </div>
  );
}

export default GetAdmins;