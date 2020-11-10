<template>
<!-- #app -->
<div id="app">

  <!-- .site -->
  <div class="site mx-auto">
    <!-- .site-header -->
    <div class="site-header">
      <img class="logo" src="./assets/img/logo.png" />
    </div><!-- /.site-header -->
    <!-- .site-content -->
    <div class="site-content">
      <router-view />
    </div><!-- /.site-content -->
    <!-- .site-footer -->
    <div class="site-footer text-center py-3">
      <button @click="addItem('FOUND')" class="btn btn-primary mx-2">Har du funnet noe?</button>
      <button @click="addItem('LOST')" class="btn btn-secondary mx-2">Har du mistet noe?</button>
    </div><!-- /.site-footer -->
  </div><!-- /.site -->
  <modal @close="closeModal" :state="state" v-show="state != null"></modal>
</div><!-- /#app -->
</template>

<style lang="scss">
@import './assets/sass/style.scss';
</style>

<script>
import http from './http-common';
import Modal from './components/ItemModal.vue';

/**
 * The main wrapper for all components.
 * @displayName App
 */
export default {
  name: 'App',
  components: {
    Modal,
  },
  data() {
    return {
      state: '',
      form: {},
    };
  },
  methods: {

    /**
     * Opens the model for adding a new
     * lost or found item.
     *
     * @param {string} state [lost|found]
     */
    addItem(state) {
      this.state = state;
      window.jQuery('#item-modal').modal('show');
    },

    /**
     * Closes the modal and attempts to send
     * a post-request for the new item.
     * Will emit 'update' if successful.
     *
     * @param {object} form the new item
     */
    closeModal(form) {
      http.post('/items', form)
        .then(() => {
          window.jQuery('#item-modal').modal('hide');

          /**
           * Update event.
           *
           * @event update
           */
          window.Fire.$emit('update');
        })
        .catch((e) => {
          console.error(e);
        });
    },
  },
};
</script>
