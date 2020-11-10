import http from '../http-common';

/**
 * Send a post request for an image-file
 * to the rest-api.
 *
 * @param {File} file the file to upload
 * @return {Promise} promise either response or error
 */
function upload(file) {
  const formData = new FormData();

  formData.append('file', file);

  return http.post('/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
}

/**
 * The class FileService handles
 * upload-requests to the rest-api.
 *
 */
class FileService {
  upload(file) {
    this.upload = upload.bind(this);
    return upload(file);
  }
}
export default new FileService();
