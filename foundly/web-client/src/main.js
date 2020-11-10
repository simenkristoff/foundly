/**
 * Initialise the App.
 * Load all required plugins.
 *
 */

import Vue from 'vue';

// Setup Vuelidate
import Vuelidate from 'vuelidate';

// Import bootstrap and Icons
import 'bootstrap';
import {
  IconsPlugin,
} from 'bootstrap-vue';

import App from './App.vue';
import router from './router';

Vue.use(Vuelidate);

// Add jQuery to window-variable
window.jQuery = require('jquery');

window.$ = window.jQuery;

Vue.use(IconsPlugin);

// Filters
require('./assets/js/filters.js');

// Event handling
window.Fire = new Vue();

Vue.config.productionTip = false;

// Mount the app
new Vue({
  router,
  render: (h) => h(App),
}).$mount('#app');
