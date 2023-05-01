import './App.css';
import Admins from './get/GetAdmins';
import sellingComps from './get/GetSelling';
import Companies from './post/CreateSellingComp';
import Shipping from './get/GetShippingCompany';
import CreateShipping from './post/CreateShipping';
import HomePage from './HomePage';
import Login from './post/Login';
import Register from './post/RegisterAdmin';
import CustLogin from './customer/post/Login';
import CustRegister from './customer/post/Register';
import MainHomePage from './MainHomePage'
import CustHomePage from './CustHomePage';
import SellLogin from './selling_company/Login';
import ShipLogin from './shipping_company/Login';
import CompHomePage from './selling_company/HomePage';
import AddProduct from './selling_company/AddProduct';
import ShipHomePage from './shipping_company/HomePage';
import AddLocation from './shipping_company/AddLocation';
import GetAllProducts from './customer/get/GetProducts';
import GetPendingOrders from './customer/get/GetPendingOrders';
import GetCart from './customer/get/GetCart';
import ReqShipping from './customer/post/RequestShipping';
import GetShippingOrders from './customer/get/GetShippingOrders';
import GetNotifications from './customer/get/GetNotifications';
import {BrowserRouter as Router, Route, Routes, Link} from 'react-router-dom';


import Customers from './get/GetCustomers';


function App() {

  
  return (
    <div className="App">
      
      <Router>
       <Routes>
          <Route path='/' exact Component={MainHomePage}/>
          <Route path='/login' exact Component={Login}/>
          <Route path='/custRegister' exact Component={CustRegister}/>
          <Route path='/adminRegister' exact Component={Register}/>
          <Route path='/custLogin' exact Component={CustLogin}/>
          <Route path='/getCompanies' exact Component={sellingComps}/>
          <Route path='/homePage' exact Component={HomePage}/>
          <Route path='/createSellCompany' Component={Companies}/>
          <Route path='/createShipCompany' Component={CreateShipping}/>
          <Route path='/getShipping' Component={Shipping}/>
          <Route path='/createShipping' Component={CreateShipping}/>
          <Route path='/getCustomers' Component={Customers}/>
          <Route path='/custHomePage' Component={CustHomePage}/>
          <Route path='/sellLogin' Component={SellLogin}/>
          <Route path='/shipLogin' Component={ShipLogin}/>
          <Route path='/compHomePage' Component={CompHomePage}/>
          <Route path='/shipHomePage' Component={ShipHomePage}/>
          <Route path='/addProduct' Component={AddProduct}/>
          <Route path='/addLocation' Component={AddLocation}/>
          <Route path='/getAllProducts' Component={GetAllProducts}/>
          <Route path='/getPendingOrders' Component={GetPendingOrders}/>
          <Route path='/getCart' Component={GetCart}/>
          <Route path='/reqShipping' Component={ReqShipping}/>
          <Route path='/getShippingOrders' Component={GetShippingOrders}/>
          <Route path='/getNotifications' Component={GetNotifications}/>
          

        </Routes>

      </Router>
      </div>
  );
}

export default App;
