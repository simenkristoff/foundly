import http from '../http-common';

function upload(file) {
  const formData = new FormData();

  formData.append('file', file);

  return http.post('/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
}

class FileService {
  upload(file) {
    this.upload = upload.bind(this);
    return upload(file);
  }
}
export default new FileService();
