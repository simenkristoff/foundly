<template>
<div class="wrapper">

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
  </ul>

  <div class="search-wrapper">
    <input type="text" class="form-control" id="filter-search" v-model="search"
      placeholder="Søk.." />
  </div>

  <ul class="items">
    <li v-for="(item, index) in filteredList" :key="index">
      <div class="item">
        <div class="item-image">
          <img :src="'img/' + item.image" :alt="item.title" />
        </div>
        <div class="item-content">
          <div class="item-header">
            <h4 class="item-title">{{item.title | capitalize}}</h4>
            <div class="state" :class="item.state === 'FOUND' ? 'state-found' : 'state-lost'"
              v-html="item.state === 'FOUND' ? 'Funnet' : 'Mistet' ">
            </div>
          </div>

          <div class="item-body">
            <div class="description">
              {{item.description | capitalize}}
            </div>
          </div>

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
          </div>
        </div>
      </div>
    </li>
  </ul>
</div>
</template>

<script>
import http from '../http-common';

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
    stateFilter() {
      return this[this.itemFilterKey];
    },
    all() {
      return this.items;
    },
    lost() {
      return this.items.filter((item) => item.state === 'LOST');
    },
    found() {
      return this.items.filter((item) => item.state === 'FOUND');
    },
    filteredList() {
      return this.stateFilter.filter((item) => item.description.toLowerCase().includes(this.search
        .toLowerCase()) || item.title.toLowerCase().includes(this.search.toLowerCase()));
    },
  },
  methods: {
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

<style>

</style>
