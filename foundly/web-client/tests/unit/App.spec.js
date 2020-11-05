import App from "@/App";
import Modal from '@/components/ItemModal'
import http from '@/http-common';
import { shallowMount, createLocalVue } from '@vue/test-utils'
import Router from 'vue-router'

/** Register jQuery for use in the test **/
window.$ = window.jQuery = require('jquery');

/** Create mocks **/
const mockAddItem = jest.spyOn(App.methods, 'addItem')
const mockCloseModal = jest.spyOn(App.methods, 'closeModal')

const localVue = createLocalVue()

/** Register vue-router **/
localVue.use(Router);

const wrapper = shallowMount(App, {
  localVue,
})

/** Register global event bus to root **/
window.Fire = wrapper.vm

describe('App.vue', () => {

  // Checks if necessary components are loaded
  test('Check if setup is correct', () => {
    expect(wrapper.find('.site-header > img.logo').exists()).toBeTruthy()
    expect(wrapper.find('router-view-stub').exists()).toBeTruthy()
    expect(wrapper.find('.site-footer > button.btn-primary').exists()).toBeTruthy()
    expect(wrapper.find('.site-footer > button.btn-secondary').exists()).toBeTruthy()
  });

  // Test if the button for adding found items passes the correct paramater
  test('Test "Add found item"-button', () => {
    wrapper.find('.site-footer > button.btn-primary').trigger('click')
    expect(mockAddItem).toHaveBeenCalledWith("FOUND")
  });

  // Test if the button for adding lost items passes the correct paramater
  test('Test "Add found item"-button', () => {
    wrapper.find('.site-footer > button.btn-secondary').trigger('click')
    expect(mockAddItem).toHaveBeenCalledWith("LOST")
  });

  // Test if the button for adding lost items passes the correct paramater
  test('Test "Add lost item"-button', () => {
    const modal = wrapper.findComponent(Modal)
    modal.vm.$emit('close')
    expect(mockCloseModal).toHaveBeenCalled()
  });

  // Test call to post item after modal is closed. Should emit 'update' if successful
  test('Test 1', async () => {
    http.post = jest.fn(()=>{
      return Promise.resolve({
        status: 200,
        data: {
          form: {
            title: "New item"
          }
        }
      })
    });
    await wrapper.vm.closeModal()
    expect(http.post).toHaveBeenCalled()
    expect(wrapper.emitted('update')).toBeTruthy()
  })

});
