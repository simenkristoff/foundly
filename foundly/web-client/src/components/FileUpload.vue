<template>
<div class="form-group text-center">
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

</div>
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
    /* eslint-disable no-console */
    processFile(file) {
      this.selectedFile = file;
      this.preview = URL.createObjectURL(this.selectedFile);
      this.filepath = this.selectedFile.name;
      this.message = '';
      this.$emit('input', this.filepath);
    },
    validateFile(event) {
      if (event.target.files[0].type === 'image/jpeg' || event.target.files[0].type
      === 'image/png') {
        this.processFile(event.target.files[0]);
      } else {
        this.$emit('invalid-file');
        this.message = 'Feil filtype. Filen må være et bilde';
      }
    },
    upload() {
      FileService.upload(this.selectedFile)
        .then((response) => {
          this.message = response.data.message;
          this.$emit('file-uploaded');
        })
        .catch((e) => {
          console.error(e);
          this.message = 'Kunne ikke laste opp filen!';
          this.$emit('upload-failed');
        });

      this.selectedFile = undefined;
    },
    /* eslint-enable no-console */
  },
  created() {
    this.$root.$refs.FileUpload = this;
  },
};
</script>

<style>

</style>
