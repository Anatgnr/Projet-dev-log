<script setup lang="ts">
import {ListImage} from './http-api'

// for(let i = 0; i < ListImage.length; i++){
//     let name = listImage[i].name
//     let id = listImage[i].id
// }
const path = "/images/"
</script>

<template>

    <h1>C'EST UNE GALERIE DE PHOTO OU QUOI LA ?</h1>

    <img id="expo" v-for="image in ListImage" :src="path+image.id">
</template>

<style>
h1{
    color: greenyellow;
    font-size: 30px;
    filter:drop-shadow(3px 3px 1px white);
}
.div_app{
    padding: 0;
}

img{
    max-height: 300px;
    max-width: 300px; 
    margin:10px;   
}
#expo {
    max-width: 250px;
    max-height: 250px;
}
</style>