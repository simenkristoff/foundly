import axios from 'axios';

let api_url = 'http://localhost:8080/';

/** 
 * Fix for sending requests in GitPod.
 * Changes the base url to GitPod's dynamic URL. 
 * The proxy in vue.config.js will take care of the port-isssue.
 */
api_url === location.href ? api_url : location.href;

export default axios.create({
  baseURL: api_url + '/api',
  headers: {
    "Content-type": "application/json",
  }
});
