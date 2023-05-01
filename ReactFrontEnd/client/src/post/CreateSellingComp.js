import React from 'react'
import { Formik, Form, Field, ErrorMessage} from 'formik' 
import * as Yup from 'yup';
import axios from "axios";
import '../styles/CreateComp.css';
import { useEffect, useState } from 'react';
import Cookies from 'js-cookie';


function CreateSellingComp() {

    const [flag, setflag] = useState(false);
    const sessionId = Cookies.get('JSESSIONID');

    
    const initialValues = {
      companyName:"",
      username:"",
      password:""
    }

    const axiosInstance = axios.create({
      headers: {
        'Content-Type': 'application/json',
        Cookie: `JSESSIONID=${sessionId}`

          },

      withCredentials: true
   })

   

    const onSubmit = (data)=>{
      axios.post("http://localhost:18072/Company-1.0-SNAPSHOT/api/admin/createSellingCompany",data).then((response)=>{
           console.log(response)
            console.log("Added Successfuly");
            console.log(data);
            })
            setflag(true);
      }


      function subFun(){
        if(flag){
            return <p>Data Added</p>
              }
    }

    function sleep(ms) {
      return new Promise(resolve => setTimeout(resolve, ms));
    }

    function Navigate(){
      if(flag){
        sleep(500).then(() => {
          window.location='/homePage';
        })
        
      }
    }
    function click(){
      window.location='/homePage';

    }
  return (
    <div className='createPostPage'> 
    
        <Formik initialValues={initialValues} 
        onSubmit={onSubmit} 
        > 
            <Form className='formContainer'>
                <label>Company Name</label>
                <Field 
                id="inputCreatePost" 
                name="companyName" 
                placeholder="(example)"/>
                 <label>Username</label>
                <Field 
                id="inputCreatePost" 
                name="username" 
                placeholder="(example)"/>
                

                <button type='submit'>Create Company</button>
                {subFun()}
                {Navigate()}
                <button onClick={click} className='button2'>Go to Home Page</button>
            </Form>

        </Formik>
        
    </div>
  )
}

export default CreateSellingComp