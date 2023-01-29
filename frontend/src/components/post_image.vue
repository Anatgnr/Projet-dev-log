<script setup lang = "ts">
import {handleFileUpload,get} from "./http-api";

function GetAndUploadFile(event:any){
  let file = event.target.files[0];
  handleFileUpload(file);
}
</script>

<template>
  <h1>UPLOAD</h1>
    <div class="container">
      <div class="large-12 medium-12 small-12 cell">
        <label>File
          <input type="file" id="file" ref="file" @change="GetAndUploadFile"/>
        </label>
        <button @click="get()">Submit</button>
      </div>
    </div>
  </template>

<style>
label{
  color: greenyellow;
}

button{
  color: greenyellow;
  background-color: #181A1B;
}

button:hover{
  color:#181A1B;
  background-color: greenyellow;
  filter:drop-shadow(4px 4px 1px rgba(172, 255, 47, 0.399));
  transform: translateY(-3px);
}
</style>