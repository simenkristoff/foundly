/**
 * Iterate a list of selectors and check if they are valid or not
 *
 * Example usage:
 *   browser.expectElementsToBeValid(selectors, expectation)
 *
 * @param {string} selectors
 * @param {boolean} expectation
 */

 exports.command = function(selectors, expectation) {
   selectors.forEach(selectorOrObject => {
     let selector

     // when called from a page object element or section
     if (typeof selectorOrObject === 'object' && selectorOrObject.selector) {
       // eslint-disable-next-line prefer-destructuring
       selector = selectorOrObject.selector
     } else {
       selector = selectorOrObject
     }
     this.assert.isValid(selector, expectation)
   });
   return this;
 };
