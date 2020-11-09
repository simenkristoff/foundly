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
