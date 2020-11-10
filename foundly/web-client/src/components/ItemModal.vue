<template>
<!-- .modal -->
<div class="modal fade" id="item-modal" tabindex="-1" event="dialog" aria-labeledby="item-modal"
  aria-hidden="true">
  <!-- .modal-dialog -->
  <div class="modal-dialog modal-dialog-centered" event="document">
    <!-- .modal-content -->
    <div class="modal-content">
      <!-- .modal-header -->
      <div class="modal-header">
        <h5 class="modal-title"
          v-html="state === 'LOST' ? 'Legg til mistet gjenstand' : 'Legg til funnet gjenstand'">
        </h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div><!-- /.modal-header -->
      <!-- #item-form -->
      <form id="item-form" @submit.prevent="submit()">
        <!-- .modal-body -->
        <div class="modal-body text-left">

          <file-upload v-model="form.image"></file-upload>

          <div class="form-group">
            <input name="title" id="title" type="text" class="form-control"
              :class="{ 'is-invalid': $v.form.title.$error }" v-model.trim="$v.form.title.$model"
              :placeholder="[[state === 'LOST' ? 'Hva har du mistet?' : 'Hva har du funnet?']]" />

            <div class="form-error" v-if="$v.form.title.$dirty">
              <div class="error" v-if="!$v.form.title.required">Dette feltet må fylles</div>
              <div class="error" v-if="!$v.form.title.minLength">Feltet må inneholde minst
                {{$v.form.title.$params.minLength.min}} tegn.</div>
            </div>
          </div>

          <div class="form-group">
            <textarea name="description" id="description" class="form-control"
              :class="{ 'is-invalid': $v.form.description.$error }"
              v-model.trim="$v.form.description.$model" placeholder="Beskrivelse"
              rows="3"></textarea>

            <div class="form-error" v-if="$v.form.description.$dirty">
              <div class="error" v-if="!$v.form.description.required">Dette feltet må fylles</div>
              <div class="error" v-if="!$v.form.description.minLength">Feltet må inneholde minst
                {{$v.form.description.$params.minLength.min}} tegn.</div>
            </div>
          </div>

          <div class="form-group">
            <input name="email" id="email" type="email" class="form-control"
              :class="{ 'is-invalid': $v.form.email.$error }" v-model.trim="$v.form.email.$model"
              placeholder="E-mail" />

            <div class="form-error" v-if="$v.form.email.$dirty">
              <div class="error" v-if="!$v.form.email.required">Dette feltet må fylles</div>
              <div class="error" v-if="!$v.form.email.email">Fyll inn gyldig e-mail</div>
            </div>
          </div>

          <div class="form-group">
            <input name="phone" id="phone" type="number" class="form-control"
              :class="{ 'is-invalid': $v.form.phone.$error }" v-model.trim="$v.form.phone.$model"
              placeholder="Tlf.nummer" />

            <div class="form-error" v-if="$v.form.phone.$dirty">
              <div class="error" v-if="!$v.form.phone.required">Dette feltet må fylles</div>
              <div class="error" v-if="!$v.form.phone.isPhoneNumber">Fyll inn gyldig nummer</div>
            </div>
          </div>
        </div><!-- /.modal-body -->
        <!-- .modal-footer -->
        <div class="modal-footer mx-auto">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">
            Avbryt
          </button>
          <button type="submit" class="btn btn-primary">Legg til</button>
        </div><!-- /.modal-footer -->
      </form><!-- /#item-form -->
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</template>

<style lang="scss" scoped>
.modal-dialog {
    max-width: 600px!important;
    width: 100%;
    margin: 0 auto!important !important;
}
</style>

<script>
import {
  required,
  email,
  minLength,
} from 'vuelidate/lib/validators';
import FileUpload from './FileUpload.vue';

/**
 * Popup-window for adding lost or found items.
 * @displayName Item modal
 */
export default {
  name: 'modal',
  components: {
    FileUpload,
  },
  props: {
    /**
     * The state of the item to add.
     * @values LOST, FOUND
     */
    state: {
      type: String,
    },
  },
  data() {
    return {
      form: {
        title: '',
        description: '',
        state: '',
        email: '',
        phone: '',
        image: '',
        date: '',
      },
    };
  },

  /**
   * Set the rules for validating input.
   *
   */
  validations: {
    form: {
      title: {
        required,
        minLength: minLength(4),
      },
      description: {
        required,
        minLength: minLength(20),
      },
      email: {
        required,
        email,
      },
      phone: {
        required,
        isPhoneNumber(phone) {
          const regex = /^\d{8}$/;
          return regex.test(phone);
        },
      },
    },
  },
  methods: {

    /**
     * Will verify the input-fields and check if a file is selected,
     * if selected it will upload the file. If form is invalid
     * the errors will be displayed and the form will not be submitted.
     *
     * Upon success a 'close'-event will be emitted and the component
     * will return the form to it's parent component.
     */
    submit() {
      this.$v.$touch();
      if (this.$v.$invalid) {
        this.submitStatus = 'ERROR';
      } else {
        this.form.state = this.state;

        if (this.$root.$refs.FileUpload.selectedFile !== undefined) {
          this.$root.$refs.FileUpload.upload();
        }
        /**
         * Close event, and return the form.
         *
         * @event close
         * @return {object} form the item to be added
         */
        this.$emit('close', this.form);

        this.submitStatus = 'PENDING';
        setTimeout(() => {
          this.submitStatus = 'OK';
        }, 500);
      }
    },
  },
  mounted() {

  },
};
</script>
