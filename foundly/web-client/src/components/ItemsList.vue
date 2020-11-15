<template>
<!-- .wrapper -->
<div class="wrapper">
  <!-- .tab-nav -->
  <ul class="tab-nav">
    <li class="tab-item">
      <button class="nav-link" id="filter-all" v-on:click="itemFilterKey = 'all'"
        :class="{ active: itemFilterKey == 'all' }">Alle</button>
    </li>
    <li class="tab-item">
      <button class="nav-link" id="filter-lost" v-on:click="itemFilterKey = 'lost'"
        :class="{ active: itemFilterKey == 'lost' }">Mistet</button>
    </li>
    <li class="tab-item">
      <button class="nav-link" id="filter-found" v-on:click="itemFilterKey = 'found'"
        :class="{ active: itemFilterKey == 'found' }">Funnet</button>
    </li>
  </ul><!-- /.tab-nav -->
  <!-- .search-wrapper -->
  <div class="search-wrapper">
    <input type="text" class="form-control" id="filter-search" v-model="search"
      placeholder="Søk.." />
  </div><!-- /.search-wrapper -->
  <!-- .items -->
  <ul class="items">
    <li v-for="(item, index) in filteredList" :key="index">
      <!-- .item -->
      <div class="item">
        <!-- .item-image -->
        <div class="item-image">
          <img v-if="item.image" :src="'img/' + item.image" :alt="item.title" />
          <img v-else src="../assets/img/default.png" :alt="item.title" />
        </div><!-- /.item-image -->
        <!-- .item-content -->
        <div class="item-content">
          <div class="item-header">
            <h4 class="item-title">{{item.title | capitalize}}</h4>
            <div class="state" :class="item.state === 'FOUND' ? 'state-found' : 'state-lost'"
              v-html="item.state === 'FOUND' ? 'Funnet' : 'Mistet' ">
            </div>
          </div>
          <!-- .item-body -->
          <div class="item-body">
            <div class="description">
              {{item.description | capitalize}}
            </div>
          </div><!-- /.item-body -->
          <!-- .item-footer -->
          <div class="item-footer">
            <span><b>Kontakt</b></span>
            <ul class="item-details">
              <li class="email">
                <a :href="'mailto:' + item.email">
                  <b-icon icon="envelope" font-scale="1.25"></b-icon>
                  <span>{{ item.email }}</span>
                </a>
              </li>
              <li class="phone">
                <a :href="'tel:' + item.phone ">
                  <b-icon icon="phone" font-scale="1.25"></b-icon>
                  <span>{{ item.phone }}</span>
                </a>
              </li>
              <li class="date">
                <span>{{item.date | datetime}}</span>
              </li>
            </ul>
          </div><!-- /.item-footer -->
        </div><!-- /.item-content -->
      </div><!-- /.item -->
    </li>
  </ul><!-- /.items -->
</div><!-- /.wrapper -->
</template>

<script>
import http from '../http-common';

/**
 * Displays item-postings, either lost or found
 * @displayName Items list
 */
export default {
  name: 'items-list',
  data() {
    return {
      itemFilterKey: 'all',
      search: '',
      items: [],
    };
  },
  computed: {

    /**
    * List filtered by a state
    *
    * @return {array} filtered list
    */
    stateFilter() {
      return this[this.itemFilterKey];
    },

    /**
     * Filter no items
     *
     * @return {array} all items
     */
    all() {
      return this.items;
    },

    /**
     * Filter items with state = LOST
     *
     * @return {array} items with state LOST
     */
    lost() {
      return this.items.filter((item) => item.state === 'LOST');
    },

    /**
     * Filter items with state = FOUND
     *
     * @return {array} items with state FOUND
     */
    found() {
      return this.items.filter((item) => item.state === 'FOUND');
    },

    /**
     * Adds text-filter upopn the state filter.
     *
     * @return {array} items filtered by text and state
     */
    filteredList() {
      return this.stateFilter.filter((item) => item.description.toLowerCase().includes(this.search
        .toLowerCase()) || item.title.toLowerCase().includes(this.search.toLowerCase()));
    },
  },
  methods: {

    /**
     * Retrieves stored items from the rest-api
     * and updates the items-list.
     *
     */
    retrieveItems() {
      http
        .get('/items')
        .then((response) => {
          this.items = response.data;
        })
        .catch((e) => {
          console.error(e);
        });
    },
  },
  mounted() {
    this.retrieveItems();
    window.Fire.$on('update', () => {
      this.retrieveItems();
    });
  },
};
</script>
