import Vue from 'vue'

const moment = require('moment');
require('moment/locale/nb');

// Capitalize first letter
Vue.filter('capitalize', function(text) {
    return text.charAt(0).toUpperCase() + text.substr(1);
})

// Datetime
Vue.filter('datetime', function(date) {
  if(date != null){
    return moment(date).format('LLL')
  }
  return null;
});
