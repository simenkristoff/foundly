/**
 * A custom assertion for fields that use vuelidate for validation
 *
 * Example usage:
 *   browser.assert.isValid(selector, expectation)
 *
 * @param {string|object} selectorOrObject
 * @param {boolean} expectation
 */

exports.assertion = function isValid (selectorOrObject, expectation) {
  let selector

  // when called from a page object element or section
  if (typeof selectorOrObject === 'object' && selectorOrObject.selector) {
    // eslint-disable-next-line prefer-destructuring
    selector = selectorOrObject.selector
  } else {
    selector = selectorOrObject
  }

  this.message = expectation ? `Testing if element <${selector}> is valid` : `Testing if element <${selector}> is invalid`
  this.expected = expectation
  this.pass = val => val === !expectation
  this.value = res => res.value
  function evaluator (_selector) {
    return document.querySelector(_selector).classList.contains('is-invalid')
  }
  this.command = cb => this.api.execute(evaluator, [selector], cb)
}
