/* eslint-disable import/no-extraneous-dependencies */
const webpack = require('webpack');

module.exports = {
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        $: 'jquery',
        jQuery: 'jquery',
        'window.jQuery': 'jquery',
        Popper: ['popper.js', 'default'],
        Util: 'exports-loader?Util!bootstrap/js/dist/util',
        Dropdown: 'exports-loader?Dropdown!bootstrap/js/dist/dropdown',
      }),
    ],
  },

  /** Add proxy to rest-api for development * */
  devServer: {
    disableHostCheck: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8098',
        ws: true,
        changeOrigin: true,
      },
      '/img': {
        target: 'http://localhost:8098',
        ws: true,
        changeOrigin: true,
      },
    },
  },
  outputDir: 'target/dist',
  assetsDir: 'static',
};
