import { ref } from 'vue'
import axios, { AxiosResponse } from 'axios'



export const count = ref(0)
export const ListImage = ref([{ "name": "", "id": 0 }])
//import from api

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



//print an image

export let print = function (nbid : number){
    // console.log(nbid.id)
    // console.log(imageUrl + nbid.id)
        let imageDataUrl = (imageUrl + nbid.id);
        let imageEL = document.getElementById("img");
        imageEL.setAttribute("src", imageDataUrl);
}

//export to the api

export function handleFileUpload(file :any ) {
    ;
    const formData = new FormData();
    formData.append('file', file);
    console.log(formData);
    // let lastid = ListImage.value[ListImage.value.length - 1].id;
    // lastid = lastid + 1;
    // console.log("last id :"+ lastid);
    // console.log(formData);
    axios.postForm("/images", 
       formData, 
       { headers:{'Content-Type': 'multipart/form-data'
    }
    }
    )
        .then(function (response: AxiosResponse) {
            console.log(response);
        })
        .catch(error => { console.log(error) });
}

// export function submitFile() {
//     const fileInput = document.getElementById('fileInput') as HTMLInputElement;
//     const file = fileInput.files[0];
//     HandleFileUpload(file);
// }







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
