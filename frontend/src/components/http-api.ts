// import { defineComponent } from 'vue'
// import { ref } from 'vue'
// import axios, { AxiosResponse } from 'axios'

// export default defineComponent({
// let id = 0

// defineProps<{ msg: "hallp" }>()

// const count = ref(0)
// const ListImage = ref([{ "name": "", "id": 0 }])

// axios.get('/images')
//     .then(function (response: AxiosResponse) {
//         ListImage.value = (response.data);
//         console.log(ListImage.value);
//     })
//     .catch(error => { console.log(error) })

// let IdImage: { id: number }

// function fct() {
//     console.log(IdImage)
// }

// var imageUrl = "/images/";
// var imageEL;

// function print(nbid: number, imageUrl: string) {
//     axios.get(imageUrl + nbid, { responseType: "blob" })
//         .then(function (response: AxiosResponse) {
//             const reader = new window.FileReader();
//             reader.readAsDataURL(response.data);
//             reader.onload = function () {
//                 const imageDataUrl = (reader.result as string);
//                 imageEL = document.getElementById("img");
//                 imageEL.setAttribute("src", imageDataUrl);
//             }
//         })
//         .catch(error => { console.log(error) });
// }
// function call() {
//     print(IdImage.id, imageUrl)
// }
// })