<script setup lang="ts">
import {print, ListImage, count, get, imageDataUrl} from './http-api'
import { ref } from 'vue'

defineProps<{ msg: "hallp" }>()

onload = () => {
  get()
}

let id = ref(0)

</script>

<template onload="get()">
  <h1>IMAGE CENTER</h1>
  <div class="card">
    <button type="button" @click="count++">count is {{ count }}</button>
    <!-- <p>
      Edit
      <code>components/HelloWorld.vue</code> to test HMR
    </p> -->
    <button @click="get()">Update List</button>

  </div>
  <select v-model="id" @change="print(id)">
    <option v-for="image in ListImage" v-bind:value="{ id: image.id }">
      {{ image.name }}
    </option> >
  </select>
  <br>
  <div class="div_img">
    <img id="img" :src="imageDataUrl">
  </div>


</template>

<style scoped>
.read-the-docs {
  color: #888;
}
button{
  color: greenyellow;
  margin:10px;
}

button:hover{
  color:greenyellow;
  filter:drop-shadow(4px 4px 1px rgba(172, 255, 47, 0.399));
}

#img {
  justify-content: center;
  max-height: 500px;
}
</style>
