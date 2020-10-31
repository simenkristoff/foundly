<template>
  <div class="form-group text-center">
    <div v-if="preview" class="preview-wrapper">
      <img class="upload-preview" :src="preview" />
    </div>
    <label for="upload-image" class="btn btn-primary">Velg bilde
      <input type="file" id="upload-image" style="display:none;" ref="file" @change="selectFile" />

    </label>
    <input type="text" id="image-path" name="image-path" v-model="filepath" readonly />
    <div v-if="message" class="alert alert-light" role="alert">{{ message }}</div>
  </div>
</template>

<style>

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
  margin-left: .25rem;
}

input#image-path, input#image-path:focus, input#image-path:active {
  outline: none;
  border: none;
  border-bottom: 1px solid rgba(0, 0, 0, .1);
}
</style>

<script>
import FileService from "../services/FileService";

export default {
  name: "file-upload",
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
    selectFile() {
      this.selectedFile = this.$refs.file.files.item(0);
      this.preview = URL.createObjectURL(this.selectedFile);
      this.filepath = this.selectedFile.name;
      this.$emit('input', this.filepath);
    },
    upload() {
      FileService.upload(this.selectedFile)
        .then(response => {
          this.message = response.data.message;
        })
        .catch(() => {
          this.message = "Kunne ikke laste opp filen!";
        });

      this.selectedFile = undefined;
    }
    /* eslint-enable no-console */
  },
  created() {
    this.$root.$refs.FileUpload = this;
  }
};
</script>

<style>

</style>
