import React from 'react'
import { Formik, Form, Field, ErrorMessage} from 'formik' 
import * as Yup from 'yup';
import axios from "axios";
import '../styles/CreateComp.css';
import { useEffect, useState } from 'react';

function CreateSellingComp() {

    const [flag, setflag] = useState(false);
    
    const initialValues = {
        productName:"pc",
        productDescription:"personal computer",
        productPrice:1500,
        productQuantity:5000
    }

    let currentComp = localStorage.getItem('loggedIn');

    
   

    const onSubmit = (data)=>{
        axios.put(`http://localhost:18072/Company-1.0-SNAPSHOT/api/sellingCompany/addProduct/${currentComp}`,data).then((response)=>{
           console.log(response)
            console.log("Added Successfuly");
            console.log(data);
            })
            setflag(true);
      }


      function subFun(){
        if(flag){
            return <p>Product Added</p>
              }
    }

    function sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    }

    function Navigate(){
      if(flag){
        sleep(500).then(() => {
          window.location='/compHomePage';
        })
        
      }
    }
    function click(){
      window.location='/compHomePage';

    }
  return (
    <div className='createPostPage'> 
    
        <Formik initialValues={initialValues} 
        onSubmit={onSubmit} 
        > 
            <Form className='formContainer'>
                <label>Product Name</label>
                <Field 
                id="inputCreatePost" 
                name="productName" 
                placeholder="(example)"/>
                 <label>Product Description</label>
                <Field 
                id="inputCreatePost" 
                name="productDescription" 
                placeholder="(example)"/>
                 <label>Product Price</label>
                <Field 
                id="inputCreatePost" 
                name="productPrice" 
                placeholder="(5000)"/>
                 <label>Product Quantatity</label>
                <Field 
                id="inputCreatePost" 
                name="productQuantity" 
                placeholder="(2000)"/>
                

                <button type='submit'>Add Product</button>
                {subFun()}
                {
                //Navigate()
            }
                <button onClick={click} className='button2'>Go to Home Page</button>
            </Form>

        </Formik>
        
    </div>
  )
}

export default CreateSellingComp