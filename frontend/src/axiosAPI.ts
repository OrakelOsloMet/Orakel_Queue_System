import axios from "axios";
import {API_BASE_PATH} from "./constants/constants";

export const REST_INSTANCE = axios.create({
    baseURL: API_BASE_PATH
});

export const FILE_INSTANCE = axios.create({
    baseURL: API_BASE_PATH,
    responseType: "blob"
});

export const FILE_DOWNLOAD_CONFIG = (filename: String, filetype: String, responseData: Blob) => {
    const url = window.URL.createObjectURL(new Blob([responseData]));
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", `${filename}.${filetype}`);
    document.body.appendChild(link);
    link.click();
}