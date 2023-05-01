import React from 'react';
import axios from "axios";
import { useEffect, useState } from 'react';
import '../../styles/GetAdmins.css';
import Cookies from 'js-cookie';


function GetAdmins() {

    const [listOfAdmins, setListOfAdmins] = useState([]);
    const [flag, setflag] = useState(false);
    const sessionId = Cookies.get('JSESSIONID');

    const axiosInstance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        Cookie: `JSESSIONID=${sessionId}`

          },

      withCredentials: true
   })


  useEffect(()=>{
    axiosInstance.get("http://localhost:18072/Company-1.0-SNAPSHOT/api/product/getAllProducts").then((response)=>{
        setListOfAdmins(response.data);
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
      
      return <p className='retMes'>Added To Cart</p>
        
      
      
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
      <th>Product Quantity</th>
      
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
             <td>{value.productQuantity}</td>
             <td><button className='bt3' onClick={()=>{
                

                axiosInstance.put(`http://localhost:18072/Company-1.0-SNAPSHOT/api/customer/addToCart/${value.productId}`).then((response)=>{
                    console.log(response.data)
                    if(response.data === "Product added to cart successfully!"){

                        setflag(true);
                        

                    }
                    else{
                        setflag(false);
                    }
                }).catch(err =>{
                    setflag(false);
                })
                
                
               

             }}>Add to Cart</button></td>
             
             </tr>
             
          
        })
      }
      {Navigate()}

      </tbody>
      </table>
      <button onClick={click} className='bt2'>Go to Home Page</button>
      <button onClick={()=>{window.location='/getCart';}} className='bt2'>Go to Cart</button>
      </div>

     
    </div>
  );
}

export default GetAdmins;