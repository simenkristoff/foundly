/**
 * Configure a new Router for the app.
 * The app has only one route, so the use of
 * vue-router is currently not necessary.
 * However, it lays the foundation for further expansion
 * with more views.
 *
 */

import Vue from 'vue';
import Router from 'vue-router';
import ItemsList from './components/ItemsList.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [{
    path: '/',
    name: 'items',
    alias: '/items',
    component: ItemsList,
  }],
});
