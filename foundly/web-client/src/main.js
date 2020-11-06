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

/** Filters **/
require('./assets/js/filters.js');

/** Event handling **/
window.Fire = new Vue();

import App from './App.vue'
import router from './router'

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
