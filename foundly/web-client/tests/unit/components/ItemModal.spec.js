import ItemModal from '@/components/ItemModal'
import FileUpload from "@/components/FileUpload";
import { mount, createLocalVue } from '@vue/test-utils'
import Vuelidate from 'vuelidate'

/** Create mocks **/
const mockUpload = jest.spyOn(FileUpload.methods, 'upload')

const localVue = createLocalVue()
localVue.use(Vuelidate)

const wrapper = mount(ItemModal, {
  localVue
})

/** Image-object for testing **/
const event = {
  target: {
    files: [
      {
        name: 'image.png',
        size: 50000,
        type: 'image/png',
      },
    ],
  },
}

describe('ItemModal.vue', () => {

  // Tests for title-field
  test('Validate title field', () => {
    const form = wrapper.find("form");
    const title = wrapper.find('#title')

    // Test empty title-input
    title.element.value = "";
    title.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.title.$error).toBe(true)

    // Test valid title-input
    title.element.value = "Mistet kjelefrosken min";
    title.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.title.$error).toBe(false)
  });

  // Tests for description-field
  test('Validate description field', () => {
    wrapper.vm.$v.$reset();
    wrapper.vm.$forceUpdate();
    const form = wrapper.find("form");
    const description = wrapper.find('#description')

    // Test empty description-input
    description.element.value = "";
    description.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.description.$error).toBe(true)

    // Test valid description-input
    description.element.value = "Den er gul og svart med sorte prikker. Så den sist ved Kiwi på Nardo";
    description.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.description.$error).toBe(false)
  });

  // Tests for email-field
  test('Validate email field', () => {
    wrapper.vm.$v.$reset();
    wrapper.vm.$forceUpdate();
    const form = wrapper.find("form");
    const email = wrapper.find('#email')

    // Test empty email-input
    email.element.value = "";
    email.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.email.$error).toBe(true)

    // Test invalid email-input
    email.element.value = "detteerentestemail.test";
    email.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.email.$error).toBe(true)

    // Test valid email-input
    email.element.value = "test.test@email.no";
    email.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.email.$error).toBe(false)
  });

  // Tests for phone-field
  test('Validate phone field', () => {
    wrapper.vm.$v.$reset();
    wrapper.vm.$forceUpdate();
    const form = wrapper.find("form");
    const phone = wrapper.find('#phone')

    // Test empty phone-input
    phone.element.value = "";
    phone.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.phone.$error).toBe(true)

    // Test invalid phone-input
    phone.element.value = "913710";
    phone.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.phone.$error).toBe(true)

    // Test valid phone-input
    phone.element.value = "91371023";
    phone.trigger("input");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(wrapper.vm.$v.form.phone.$error).toBe(false)
  });

  // Tests if an image-upload request is sent when image-file is null
  test('Test submit with empty image file', () => {
    wrapper.vm.$v.$reset();
    wrapper.vm.$forceUpdate();
    const form = wrapper.find("form");
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(mockUpload).not.toHaveBeenCalled();
  });

  // Tests if an image-upload request is sent when an image is selected
  it('Test submit with selected image file', async () => {
    wrapper.vm.$v.$reset();
    wrapper.vm.$forceUpdate();
    const FileUpload = wrapper.vm.$root.$refs.FileUpload
    const form = wrapper.find("form");
    FileUpload.selectedFile = event
    form.trigger("submit.prevent");
    wrapper.vm.$forceUpdate();
    expect(mockUpload).toHaveBeenCalled();
  });

  // Test if modal closes on submit
  test('Close on submit', () => {
    expect(wrapper.emitted('close')).toBeTruthy();
  });
})
