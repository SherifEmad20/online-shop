import axios from 'axios';

const sessionId = localStorage.getItem('sessionId');

const axiosCook = axios.create({
  baseURL: 'http://localhost:18072/Company-1.0-SNAPSHOT/api',
  headers: {
    'Content-Type': 'application/json',
    Cookie: `JSESSIONID=${sessionId}`,
  },
  withCredentials: true,
});

export default axiosCook;