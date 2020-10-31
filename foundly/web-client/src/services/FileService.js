import http from "../http-common";

class FileService {
  upload(file) {
    let formData = new FormData();

    formData.append("file", file);

    return http.post("/upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    });
  }
}

export default new FileService();
