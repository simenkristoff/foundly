/**
 * Unit test for ItemsList.vue.
 *
 * This tests purpose is to verify that components are loaded properly,
 * and functions as expected.
 *
 * Functionality to test:
 * - Test if setup is correct.
 * - Test if items are rendered properly.
 * - Test if filtering by state works.
 * - Test if filtering by text works.
 * - Test if the list of items are refreshed on update.
 * - Test if refresh-function fetches items from rest-api.
 */

import Vue from 'vue';
import ItemsList from '@/components/ItemsList.vue';
import http from '@/http-common';
import {
  mount,
  createLocalVue,
} from '@vue/test-utils';

// Import bootstrap and Icons
import 'bootstrap';
import {
  IconsPlugin,
} from 'bootstrap-vue';

// Import dummy items from test resources
import dummyItems from '../../resources/itemsData.json';

const localVue = createLocalVue();

// Create mocks
const mockRetrieveItems = jest.spyOn(ItemsList.methods, 'retrieveItems');

// Add global event bus
window.Fire = new Vue();
localVue.use(window);
localVue.use(IconsPlugin);

// Import filters
const filters = require('@/assets/js/filters.js');

const wrapper = mount(ItemsList, {
  filters,
  localVue,
  data() {
    return {
      items: dummyItems,
    };
  },
});

describe('ItemsList.vue', () => {
  // Checks if necessary components are loaded
  test('Check if setup is correct', () => {
    expect(wrapper.find('#filter-all').exists()).toBeTruthy();
    expect(wrapper.find('#filter-lost').exists()).toBeTruthy();
    expect(wrapper.find('#filter-found').exists()).toBeTruthy();
    expect(wrapper.find('div.action-wrapper > #filter-search').exists()).toBeTruthy();
    expect(wrapper.find('div.action-wrapper > .tgl-refresh').exists()).toBeTruthy();
    expect(wrapper.find('ul.items').exists()).toBeTruthy();
  });

  // Test items are loaded, and rendered properly
  test('Test dummy-items', () => {
    expect(wrapper.vm.items.length).toBe(4);
    const item = wrapper.find('li > .item:first-of-type');

    const title = item.find('.item-title');
    expect(title.text()).toContain('Funnet mobil');

    const description = item.find('.description');
    expect(description.text()).toContain('Fant en iPhone 9 på r2 i dag tidlig');

    const state = item.find('.state');
    expect(state.text()).toContain('Funnet');

    const email = item.find('.email');
    expect(email.text()).toContain('simen.kristoffersen98@gmail.com');

    const phone = item.find('.phone');
    expect(phone.text()).toContain('90360922');

    const datetime = item.find('.date');
    expect(datetime.text()).toContain(wrapper.vm.$options.filters.datetime('2020-10-31T09:18:47.947168'));
  });

  // Test if state filters are working properly
  test('Test filtering of items by state', () => {
    // No filter applied
    const filterAll = wrapper.find('#filter-all');
    filterAll.trigger('click');
    expect(wrapper.vm.itemFilterKey).toBe('all');
    expect(wrapper.vm.filteredList.length).toBe(4);

    // Filter lost items
    const filterLost = wrapper.find('#filter-lost');
    filterLost.trigger('click');
    expect(wrapper.vm.itemFilterKey).toBe('lost');
    expect(wrapper.vm.filteredList.length).toBe(2);
    for (let i = 0; i < 2; i += 1) {
      expect(wrapper.vm.filteredList[i].state).toBe('LOST');
    }

    // Filter found items
    const filterFound = wrapper.find('#filter-found');
    filterFound.trigger('click');
    expect(wrapper.vm.itemFilterKey).toBe('found');
    expect(wrapper.vm.filteredList.length).toBe(2);
    for (let k = 0; k < 2; k += 1) {
      expect(wrapper.vm.filteredList[k].state).toBe('FOUND');
    }
  });

  // Test if the search filter is working properly
  test('Test filtering of items by text-input', () => {
    // Reset state filter
    const filterAll = wrapper.find('#filter-all');
    filterAll.trigger('click');
    expect(wrapper.vm.filteredList.length).toBe(4);

    // Filter lost items
    const filterSearch = wrapper.find('#filter-search');
    filterSearch.element.value = 'MOBIL'; // uppercase to test case-insensitivity
    filterSearch.trigger('input');
    expect(wrapper.vm.filteredList.length).toBe(1);
    expect(wrapper.vm.filteredList[0].title).toContain('Funnet mobil');
  });

  // Should refresh list of items on event 'update'. In this case we will reject the response
  it('Test refresh list', (done) => {
    // Mock get-function
    http.get = jest.fn(() => Promise.reject(new Error('Request failed')));
    setTimeout(() => {
      done();
    });
    window.Fire.$emit('update');
    expect(http.get).toHaveBeenCalled();
    expect(mockRetrieveItems).toHaveBeenCalled();
  });

  // The function retrieveItems() should update our items
  it('Test retrieve and update Items', async () => {
    // Mock get-function
    http.get = jest.fn(() => Promise.resolve({
      status: 200,
      data: [{
        title: 'Mistet noe as',
        description: 'Nøkkelen min',
        state: 'LOST',
        email: 'simen.kristoffersen98@gmail.com',
        phone: '90360922',
        image: 'default.png',
        date: '2020-11-02T13:41:34.422585',
      }],
    }));
    await wrapper.vm.retrieveItems();
    expect(http.get).toHaveBeenCalled();
    expect(mockRetrieveItems).toHaveBeenCalled();

    // Should be 1 as the mocked get-function only returns 1 item
    expect(wrapper.vm.items.length).toBe(1);
  });
});
