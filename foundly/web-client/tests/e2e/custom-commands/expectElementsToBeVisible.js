/**
 * Iterate a list of selectors and check if they are visible
 *
 * Example usage:
 *   browser.expectElementsToBeVisible(selectors)
 *
 * @param {string} selectors
 */

exports.command = function (selectors) {
  selectors.forEach((selectorOrObject) => {
    let selector;

    // when called from a page object element or section
    if (typeof selectorOrObject === 'object' && selectorOrObject.selector) {
      // eslint-disable-next-line prefer-destructuring
      selector = selectorOrObject.selector;
    } else {
      selector = selectorOrObject;
    }
    this.expect.element(selector).to.be.visible;
  });
  return this;
};
