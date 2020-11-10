/**
 * Global filters for text.
 *
 * Example usage:
 *   <h1 class="title">{{title | capitalize}}</h1>
 *
 */

import Vue from 'vue';

const moment = require('moment');
require('moment/locale/nb');

/**
 * Capitalize the first letter of a text
 *
 * @param {string} text
 * @return {string} capitalized text
 */
Vue.filter('capitalize', (text) => text.charAt(0).toUpperCase() + text.substr(1));

/**
 * Formats datetime to local datetime
 *
 * @param {string} date
 * @return {string} local datetime
 */
Vue.filter('datetime', (date) => {
  if (date != null) {
    return moment(date).format('LLL');
  }
  return null;
});
