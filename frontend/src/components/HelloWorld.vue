<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'

let id = 0

defineProps<{ msg: string }>()

const count = ref(0)

// axios.get('http://localhost:5550/images').then(response => {
//   console.timeLog(response.data)
// })

//   });

new Vue({
  el: '#app',
  data () {
    return {
      info: null
    }
  },
  mounted () {
    axios
      .get('http://localhost:5550/images')
      .then(response => (this.info = response))
      .catch(error => console.log(error))
  }
})


</script>

<template>
  <h1>{{ msg }}</h1>

  <div class="card">
    <button type="button" @click="count++">count is {{ count }}</button>
    <p>
      Edit
      <code>components/HelloWorld.vue</code> to test HMR
    </p>
  </div>

  <p>
    Check out
    <a href="https://vuejs.org/guide/quick-start.html#local" target="_blank">create-vue</a>, the official Vue + Vite
    starter
  </p>
  <p>
    Install
    <a href="https://github.com/johnsoncodehk/volar" target="_blank">Volar</a>
    in your IDE for a better DX
  </p>
  <p class="read-the-docs">Click on the Vite and Vue logos to learn more</p>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
