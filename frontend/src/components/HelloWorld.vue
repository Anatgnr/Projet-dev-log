<script setup lang="ts">
import { ref } from 'vue'
import axios, { AxiosResponse } from 'axios'

let id = 0

defineProps<{ msg: "hallp" }>()

const count = ref(0)
const ListImage = ref([{ "name": "", "id": 0 }])

axios.get('/images')
  .then(function (response: AxiosResponse) {
    ListImage.value = (response.data);
    console.log(ListImage.value);
  })
  .catch(error => { console.log(error) })

let IdImage: { id: number }

function fct() {
  console.log(IdImage)
}

var imageUrl = "/images/";
var imageEL;

function print(nbid: number, imageUrl: string) {
  axios.get(imageUrl + nbid, { responseType: "blob" })
    .then(function (response: AxiosResponse) {
      const reader = new window.FileReader();
      reader.readAsDataURL(response.data);
      reader.onload = function () {
        const imageDataUrl = (reader.result as string);
        imageEL = document.getElementById("img");
        imageEL.setAttribute("src", imageDataUrl);
      }
    })
    .catch(error => { console.log(error) });
}
function call() {
  print(IdImage.id, imageUrl)
}

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
  <select v-model="IdImage" @change="fct(); call()">
    <option v-for="image in ListImage" v-bind:value="{ id: image.id }">
      {{ image.name }}
    </option> >
  </select>
  <br><br><br>
  <div class="div_img">
    <img id="img">
  </div>


</template>

<style scoped>
.read-the-docs {
  color: #888;
}

#img {
  justify-content: center;
  max-height: 500px;
  display: flex;
}
</style>
