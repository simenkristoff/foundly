/**
 * Module for handling requests made to the rest-api.
 * Configures axios to send requests to the correct url.
 *
 */

import axios from 'axios';

let apiurl = 'http://localhost:8080/';
const host = window.location.href;

// Fix for sending requests in GitPod.
// Changes the base url to GitPod's dynamic URL.
// The proxy in vue.config.js will take care of the port-isssue.
apiurl = (apiurl !== host) ? host : apiurl;

export default axios.create({
  baseURL: `${apiurl}api`,
  headers: {
    'Content-type': 'application/json',
  },
});
