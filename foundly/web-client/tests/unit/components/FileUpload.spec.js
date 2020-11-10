/**
 * Unit test for FileUpload.vue.
 *
 * This tests purpose is to verify that components are loaded properly,
 * and functions as expected.
 *
 * Functionality to test:
 * - Test if only image-files are excepted.
 * - Test if successful upload is handled correctly.
 * - Test if failed upload is handled correctly.
 *
 */

import FileUpload from '@/components/FileUpload.vue';
import FileService from '@/services/FileService';
import {
  mount,
  createLocalVue,
} from '@vue/test-utils';

// Register jQuery for use in the test
window.$ = require('jquery');

window.jQuery = window.$;

// Create mocks
const mockProcessFile = jest.spyOn(FileUpload.methods, 'processFile');
const mockValidateFile = jest.spyOn(FileUpload.methods, 'validateFile');
const mockUpload = jest.spyOn(FileUpload.methods, 'upload');
window.URL.createObjectURL = function () {};

// Invalid file-object for testing
const falseEvent = {
  target: {
    files: [{
      name: 'index.html',
      size: 50000,
      type: 'text/html',
    }],
  },
};

// Valid file-object for testing
const validEvent = {
  target: {
    files: [{
      name: 'image.png',
      size: 50000,
      type: 'image/png',
    }],
  },
};

const localVue = createLocalVue();

const wrapper = mount(FileUpload, {
  localVue,
});

describe('FileUpload.vue', () => {
  // File input should only accept 'image/jpeg'
  test('Test if only image-files are accepted', () => {
    // In case of an invalid file-input, the component will emit
    // 'invalid-file' and return
    wrapper.vm.validateFile(falseEvent);
    expect(mockValidateFile).toHaveBeenCalled();
    expect(wrapper.emitted('invalid-file')).toBeTruthy();
    expect(mockProcessFile).not.toHaveBeenCalled();

    // In case of an valid file-input, the component will emit 'input'
    // along with the filename and call processFile()
    wrapper.vm.validateFile(validEvent);
    expect(mockValidateFile).toHaveBeenCalled();
    expect(wrapper.emitted('input', 'image.png')).toBeTruthy();
    expect(mockProcessFile).toHaveBeenCalled();
  });

  // Test file upload function. We will expect the component to call
  // FileService.upload with the selected file.
  // If the promise is resolved the component should emit 'file-uploaded'
  // and set the selected file to null/undefined
  test('Test if a successful upload is handled correctly', async () => {
    FileService.upload = jest.fn(() => Promise.resolve({
      status: 200,
      data: {
        message: 'Success!',
      },
    }));
    wrapper.vm.validateFile(validEvent);
    await wrapper.vm.upload();
    expect(FileService.upload).toHaveBeenCalled();
    expect(mockUpload).toHaveBeenCalled();
    expect(wrapper.emitted('file-uploaded')).toBeTruthy();
    expect(wrapper.vm.selectedFile).toBe(undefined);
  });

  // Test a failed file upload request. We will expect the component to
  // call FileService.upload with the selected file.
  // If the promise is rejected the component should emit 'upload-failed'
  // and set the selected file to null/undefined
  it('Test if a failed upload is handled correctly', (done) => {
    FileService.upload = jest.fn(() => Promise.reject(new Error('Upload failed!')));
    setTimeout(() => {
      expect(wrapper.emitted('upload-failed')).toBeTruthy();
      done();
    });
    wrapper.vm.validateFile(validEvent);
    wrapper.vm.upload();
    expect(FileService.upload).toHaveBeenCalled();
    expect(mockUpload).toHaveBeenCalled();
    expect(wrapper.vm.selectedFile).toBe(undefined);
  });
});
