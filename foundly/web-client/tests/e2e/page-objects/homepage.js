/**
 * A Nightwatch page object. The page object name is the filename.
 *
 * Example usage:
 *   browser.page.homepage.navigate()
 *
 * For more information on working with page objects see:
 *   https://nightwatchjs.org/guide/working-with-page-objects/
 *
 */

module.exports = {
  url: '/',
  commands: [],

  // A page object can have elements
  elements: {
    appContainer: '#app',
  },

  // Or a page objects can also have sections
  sections: {
    app: {
      selector: '#app',

      sections: {
        siteContainer: {
          selector: '.site',

          sections: {
            siteHeader: {
              selector: '.site-header',

              elements: {
                logo: 'img.logo',
              },
            },

            siteContent: {
              selector: '.site-content',

            },

            siteFooter: {
              selector: '.site-footer',

              elements: {
                btnFound: {
                  selector: 'button.btn-primary',
                },
                btnLost: 'button.btn-secondary',
              },
            },
          },
        },
        itemModal: {
          selector: '#item-modal',

          sections: {
            itemForm: {
              selector: '#item-form',

              elements: {
                btnCancel: {
                  selector: '.modal-footer > button[type=button].btn-secondary',
                  locateStrategy: 'css',
                },
                btnSubmit: '.modal-footer > button[type=submit].btn-primary',
              },
            },
          },
        },
      },
    },
  },
};
