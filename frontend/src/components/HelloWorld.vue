<script setup lang="ts">
import { print, call, fct, ListImage, IdImage, count } from './http-api'

defineProps<{ msg: "hallp" }>()

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
