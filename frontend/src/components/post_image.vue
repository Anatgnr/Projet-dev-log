<script setup lang = "ts">
import {handleFileUpload} from "./http-api";

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
          <!-- <button v-on:click="submitFile()">Submit</button> -->
      </div>
    </div>
  </template>