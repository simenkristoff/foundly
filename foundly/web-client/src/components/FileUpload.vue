<template>
<!-- .file-upload -->
<div class="file-upload form-group text-center">
  <div v-if="preview" class="preview-wrapper">
    <img class="upload-preview" :src="preview" />
  </div>
  <label for="upload-image" class="btn btn-primary">Velg bilde
    <input type="file" accept="image/*" id="upload-image" style="display:none;" ref="file"
      @change="validateFile" />
  </label>
  <input type="text" id="image-path" name="image-path" v-model="filepath" readonly />
  <div class="form-error" v-if="message">
    <div class="error" role="alert">{{ message }}</div>
  </div>
</div><!-- /.file-upload -->
</template>

<style lang="scss" scoped>
.preview-wrapper {
    display: flex;
    flex: 1 1 100%;
    justify-content: center;
    margin-bottom: 1rem;
}

img.upload-preview {
    object-fit: contain;
    height: 200px;
    width: auto;
}

input#image-path {
    margin-left: 0.25rem;
}

input#image-path,
input#image-path:active,
input#image-path:focus {
    outline: none;
    border: none;
    border-bottom: 1px solid rgba(0, 0, 0, .1);
}
</style>

<script>
import FileService from '../services/FileService';

/**
 * Display for file upload. Upload button with preview and selected filepath
 * @displayName File upload
 */
export default {
  name: 'file-upload',
  data() {
    return {
      selectedFile: undefined,
      filepath: '',
      preview: '',
      message: '',
      fileInfos: [],
    };
  },
  methods: {

    /**
     * Prepares the file for upload
     * and displays an image preview.
     *
     * @param {File} file the verified file
     */
    processFile(file) {
      this.selectedFile = file;
      this.preview = URL.createObjectURL(this.selectedFile);
      this.filepath = this.selectedFile.name;
      this.message = '';

      /**
       * New input is set
       *
       * @event input
       */
      this.$emit('input', this.filepath);
    },

    /**
     * Checks if the selected file is of valid input
     * i.e. 'image/jpeg' or 'image/png'.
     * Will process the file if it is valid,
     * or emit 'invalid-file' if invalid.
     *
     * @param {Event} event the input event
     */
    validateFile(event) {
      if (event.target.files[0].type === 'image/jpeg' || event.target.files[0].type
      === 'image/png') {
        this.processFile(event.target.files[0]);
      } else {
        /**
         * Invalid file
         *
         * @event invalid-file
         */
        this.$emit('invalid-file');
        this.message = 'Feil filtype. Filen må være et bilde';
      }
    },

    /**
     * Will send a post-request for the selected file
     * and emit 'file-uploaded' if successful,
     * else emit 'upload-failed'.
     *
     */
    upload() {
      FileService.upload(this.selectedFile)
        .then((response) => {
          this.message = response.data.message;

          /**
           * File uploaded.
           *
           * @event file-uploaded
           */
          this.$emit('file-uploaded');
        })
        .catch((e) => {
          console.error(e);
          this.message = 'Kunne ikke laste opp filen!';

          /**
           * Upload failed.
           *
           * @event upload-failed
           */
          this.$emit('upload-failed');
        });

      this.selectedFile = undefined;
    },
  },
  created() {
    this.$root.$refs.FileUpload = this;
  },
};
</script>

<style>

</style>
