import { ref } from 'vue'
import axios, { AxiosResponse } from 'axios'



export const count = ref(0)
export const ListImage = ref([{ "name": "", "id": 0 }])

export function get(){
axios.get(imageUrl)
    .then(function (response: AxiosResponse) {
        ListImage.value = (response.data);
        
        // console.log(ListImage.value);
    })
    .catch(error => { console.log(error) })
}
const imageUrl = "/images/";
export const imageDataUrl = ref("");


export let print = function (nbid : number){
    // console.log(nbid.id)
    // console.log(imageUrl + nbid.id)
        let imageDataUrl = (imageUrl + nbid.id);
        let imageEL = document.getElementById("img");
        imageEL.setAttribute("src", imageDataUrl);
}










// export function print(nbid: number) {
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
//         // .catch(error => { console.log(error) });
// }
