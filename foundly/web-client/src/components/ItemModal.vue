<template>
  <!-- Modal -->
  <div class="modal fade" id="ItemModal" tabindex="-1" event="dialog"
  aria-labeledby="ItemModal" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" event="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" v-html="state === 'LOST' ? 'Legg til mistet gjenstand' : 'Legg til funnet gjenstand'"></h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div><!-- /.modal-header -->
        <form id="ItemForm" @submit.prevent="submit()">
          <div class="modal-body text-left">

            <file-upload v-model="form.image"></file-upload>

            <div class="form-group">
              <input name="title" id="title" type="text" class="form-control" :class="{ 'is-invalid': $v.form.title.$error }"
              v-model.trim="$v.form.title.$model" :placeholder="[[state === 'LOST' ? 'Hva har du mistet?' : 'Hva har du funnet?']]"/>

              <div class="form-error" v-if="$v.form.title.$dirty">
                <div class="error" v-if="!$v.form.title.required">Dette feltet må fylles</div>
                <div class="error" v-if="!$v.form.title.minLength">Feltet må inneholde minst {{$v.form.title.$params.minLength.min}} tegn.</div>
              </div>
            </div>

            <div class="form-group">
              <textarea name="description" id="description" class="form-control" :class="{ 'is-invalid': $v.form.description.$error }"
              v-model.trim="$v.form.description.$model" placeholder="Beskrivelse" rows="3"></textarea>

              <div class="form-error" v-if="$v.form.description.$dirty">
                <div class="error" v-if="!$v.form.description.required">Dette feltet må fylles</div>
                <div class="error" v-if="!$v.form.description.minLength">Feltet må inneholde minst {{$v.form.description.$params.minLength.min}} tegn.</div>
              </div>
            </div>

            <div class="form-group">
              <input name="email" id="email" type="email" class="form-control" :class="{ 'is-invalid': $v.form.email.$error }"
              v-model.trim="$v.form.email.$model" placeholder="E-mail"/>

              <div class="form-error" v-if="$v.form.email.$dirty">
                <div class="error" v-if="!$v.form.email.required">Dette feltet må fylles</div>
                <div class="error" v-if="!$v.form.email.email">Fyll inn gyldig e-mail</div>
              </div>
            </div>

            <div class="form-group">
              <input name="phone" id="phone" type="number" class="form-control" :class="{ 'is-invalid': $v.form.phone.$error }"
              v-model.trim="$v.form.phone.$model" placeholder="Tlf.nummer"/>

              <div class="form-error" v-if="$v.form.phone.$dirty">
                <div class="error" v-if="!$v.form.phone.required">Dette feltet må fylles</div>
                <div class="error" v-if="!$v.form.phone.isPhoneNumber">Fyll inn gyldig nummer</div>
              </div>
            </div>

          </div>
          <div class="modal-footer mx-auto">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">
              Avbryt
            </button>

            <button type="submit" class="btn btn-primary">Legg til</button>

          </div><!-- /.modal-footer -->
        </form>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
</template>

<style scoped>
.modal-dialog {
    max-width: 600px%!important;
    width: 100%;
    margin: 0 auto!important;
}
</style>

<script>
import FileUpload from "./FileUpload";
import { required, email, minLength } from 'vuelidate/lib/validators'
export default {
    name: "modal",
    components: {
      FileUpload
    },
    props: ['state'],
    data() {
        return {
          form: {
            title: '',
            description: '',
            state: '',
            email: '',
            phone: '',
            image: '',
            date: ''
          }
        }
    },
    validations: {
      form: {
        title: {
          required,
          minLength: minLength(4)
        },
        description: {
          required,
          minLength: minLength(20)
        },
        email: {
          required,
          email
        },
        phone: {
          required,
          isPhoneNumber(phone){
            const regex = /^\d{8}$/;
            return regex.test(phone);
          }
        }
      }
    },
    methods: {
        submit() {
          this.$v.$touch()
          if (this.$v.$invalid) {
            this.submitStatus = 'ERROR'
          } else {
            this.form.state = this.state;

            if(this.$root.$refs.FileUpload.selectedFile !== undefined){
              this.$root.$refs.FileUpload.upload();
            }
            this.$emit('close', this.form)

            this.submitStatus = 'PENDING'
            setTimeout(() => {
              this.submitStatus = 'OK'
            }, 500)
          }
        }
    },
    mounted() {

    }
}


</script>
