module.exports = {
  webdriver: {
    start_process: true,
    port: 9515,
    server_path: require('chromedriver').path
  },

  test_settings: {
    default: {
      launch_url: '${VUE_DEV_SERVER_URL}',
      desiredCapabilities : {
        browserName: 'chrome',
        chromeOptions: {
          w3c: false,
          args: [
              'headless',
              '--no-sandbox',
          ]
        }
      }
    }
  }
};