import Vue from 'vue'

/** Setup Vuelidate **/
import Vuelidate from 'vuelidate'
Vue.use(Vuelidate)

/** Add jQuery to window-variable **/
window.$ = window.jQuery = require('jquery')

/** Import bootstrap and Icons **/
import 'bootstrap'
import {IconsPlugin} from 'bootstrap-vue'
Vue.use(IconsPlugin)

/** Setup Moment.js for handling of dates **/
const moment = require('moment');
// Add norwegian-bokmål locale
require('moment/locale/nb');

/**  Add stylesheet **/
import './assets/sass/style.scss';

import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

/** Filters **/

// Capitalize first letter
Vue.filter('capitalize', function(text) {
    return text.charAt(0).toUpperCase() + text.substr(1).toLowerCase();
})

// Datetime
Vue.filter('datetime', function(date) {
  if(date != null){
    return moment(date).format('LLL')
  }
  return null;
});

/** Event handling **/
window.Fire = new Vue();

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
