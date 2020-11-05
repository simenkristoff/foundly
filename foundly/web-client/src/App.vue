<template>
  <div id="app">
    <div class="site mx-auto">
      <div class="site-header">
        <img class="logo" src="./assets/img/logo.png" />
      </div>
      <div class="site-content">
        <router-view/>
      </div>
      <div class="site-footer text-center py-3">
        <button @click="addItem('FOUND')" class="btn btn-primary mx-2">Har du funnet noe?</button>
        <button @click="addItem('LOST')" class="btn btn-secondary mx-2">Har du mistet noe?</button>
      </div>
    </div>
    <modal @close="closeModal" :state="state" v-show="state != null"></modal>
  </div>
</template>

<style lang="scss">
@import './assets/sass/style.scss';
</style>

<script>
import http from "./http-common";
import Modal from './components/ItemModal'

export default {
  name: 'App',
  components: {Modal},
  data() {
    return {
      state: '',
      form: {},
    }
  },
  methods: {
    addItem(state){
      this.state = state;
      window.jQuery('#ItemModal').modal('show');
    },
    closeModal(form) {
      http.post("/items", form)
      .then(response => {
        console.log(response.data);
        window.jQuery('#ItemModal').modal('hide');
        window.Fire.$emit('update');
      })
      .catch(e => {
        console.log(e);
      });

    },
  }
}
</script>
