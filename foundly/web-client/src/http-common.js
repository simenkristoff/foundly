import axios from 'axios';

let api_url = 'http://localhost:8080';

export default axios.create({
  baseURL: api_url + '/api',
  headers: {
    "Content-type": "application/json",
  }
});
