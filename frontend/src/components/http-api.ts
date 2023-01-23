import { ref } from 'vue'
import axios, { AxiosResponse } from 'axios'

export let id = 0

export const count = ref(0)
export const ListImage = ref([{ "name": "", "id": 0 }])

axios.get('/images')
    .then(function (response: AxiosResponse) {
        ListImage.value = (response.data);
        console.log(ListImage.value);
    })
    .catch(error => { console.log(error) })

export let IdImage: { id: number }

export function fct() {
    console.log(IdImage)
}

export var imageUrl = "/images/";
export var imageEL;

export function print(nbid: number, imageUrl: string) {
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
export function call() {
    print(IdImage.id, imageUrl)
}