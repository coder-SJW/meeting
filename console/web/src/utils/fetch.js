import {stringify} from 'qs';
import axiosInstance from '@/utils/axios'

export function post(url, body) {
    const header = {headers: {'Content-Type': 'application/json'}};
    return axiosInstance.post(url, body, {headers: header});
}

export function postForm(url, params) {
    return axiosInstance.post(url, {
        body: params,
        transformRequest: [
            convertDataToFormData
        ]
    });
}

const convertDataToFormData = function (data) {
    const form = new FormData();
    Object.keys(data).forEach(key => {
        form.append(key, data[key])
    })
    return form
}

export function get(url, pathVariable, params) {
    if (pathVariable) {
        url = url + '/' + pathVariable;
    }
    if (params) {
        url = url + '?' + stringify(params);
    }
    return axiosInstance.get(url);
}

export function put(url, pathVariable, body) {
    if (pathVariable) {
        url = url + '/' + pathVariable;
    }
    return axiosInstance.put(url, body);
}

export function remove(url, pathVariable) {
    if (pathVariable) {
        url = url + '/' + pathVariable;
    }
    return axiosInstance.delete(url);
}

export function download(url, params) {
    window.open(url + '?' + stringify(params));
    return Promise.resolve(true);
}

export default {
    post,
    get,
    put,
    remove,
    download
};
