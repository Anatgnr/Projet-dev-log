<script setup lang="ts">
import {print, ListImage, count, get, imageDataUrl} from './http-api'
import { ref } from 'vue'

onload = () => {
  get()
}

let id = ref(0)
</script>

<template onload="get()">
  <h1>IMAGE CENTER</h1>
  <div class="div_img">
    <img id="img" :src="imageDataUrl">
  </div>
  <select v-model="id" @change="print(id)">
    <option v-for="image in ListImage" v-bind:value="{ id: image.id }">
      {{ image.name }}
    </option> >
  </select>
  <br>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}

#img {
  justify-content: center;
  max-height: 500px;
}

</style>
