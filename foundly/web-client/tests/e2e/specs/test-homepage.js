/**
 * End to end test
 *
 * This tests purpose is to verify that components are loaded properly,
 * and functions as expected
 *
 */

module.exports = {


  'Testing homepage elements': (browser) => {
    browser.init()

    const homepage = browser.page.homepage()

    homepage.waitForElementVisible('@appContainer')
    const app = homepage.section.app

    // Test if app is loaded
    app.expect.section('@siteContainer').to.be.present
    const container = app.section.siteContainer

    // Test the header-section
    container.expect.section('@siteHeader').to.be.present
    const header = container.section.siteHeader

    header.assert.elementCount('@logo', 1)

    // Test the content-section
    container.expect.section('@siteContent').to.be.present
    const content = container.section.siteContent

    // Test the footer-section
    container.expect.section('@siteFooter').to.be.present
    const footer = container.section.siteFooter

    footer.expect.element('@btnFound').to.be.present
    footer.expect.element('@btnFound').text.to.equal('Har du funnet noe?')

    footer.expect.element('@btnLost').to.be.present
    footer.expect.element('@btnLost').text.to.equal('Har du mistet noe?')
  },

  'Test Items-modal, open and close': (browser) => {
    const homepage = browser.page.homepage()

    homepage.waitForElementVisible('@appContainer')
    const app = homepage.section.app

    // Setup previously tested elements
    const footer = homepage.section.app.section.siteContainer.section.siteFooter
    const btnFound = footer.elements.btnFound
    const btnLost = footer.elements.btnLost

    // Modal is hidden to begin with
    app.expect.section('@itemModal').to.not.be.visible
    const modal = app.section.itemModal

    // Open modal with "Found item"-button and assert components
    app.click(btnFound)
    app.expect.section('@itemModal').to.be.visible
    modal.expect.section('@itemForm').to.be.visible
    const form = modal.section.itemForm

    form.expectElementsToBePresent(['#upload-image', 'input[name=title]', 'textarea[name=description]', 'input[name=email]', 'input[name=phone]'])
    form.expect.element('@btnCancel').to.be.present
    const btnCancel = form.elements.btnCancel
    form.expect.element('@btnSubmit').to.be.present
    const btnSubmit = form.elements.btnSubmit

    // Close modal and verify that it is hidden
    modal.click(btnCancel)
    app.expect.section('@itemModal').to.not.be.visible

    // Open modal with "Lost item"-button and verify that a blank submit invalidates input files
    app.click(btnLost)
    app.expect.section('@itemModal').to.be.visible
    modal.click(btnSubmit)
    form.expectElementsToBeValid(['input[name=title]', 'textarea[name=description]', 'input[name=email]', 'input[name=phone]'], false)

    // Verify a successful submit, should close after success
    form.setValue('input[name=title]', 'Mistet kjelefrosken min i går');
    form.setValue('textarea[name=description]', 'Den er gul med sorte prikker. Sist sett ved Kiwi på Nardo');
    form.setValue('input[name=email]', 'test.testesen@test.no');
    form.setValue('input[name=phone]', '90360922');
    modal.click(btnSubmit)
    form.expectElementsToBeValid(['input[name=title]', 'textarea[name=description]', 'input[name=email]', 'input[name=phone]'], true)

    browser.end()
  },


}
