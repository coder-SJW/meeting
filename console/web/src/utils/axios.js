import axios from 'axios'
import {RESULT_CODE} from '../consts/ResultCode';

const UNKNOWN_ERROR = '未知错误';

const instance = axios.create({
    timeout: 10000
});

instance.interceptors.response.use(response => {
    const {data: res, config, request} = response
    if (res.code !== 200) {
        const error = new Error(res.msg || UNKNOWN_ERROR)
        error.config = config;
        error.request = request;
        error.response = response;
        error._code = res.code;
        error.data = res.result;
        // 未登录或者session过期
        if (res.code === RESULT_CODE.UNAUTHORIZED.code) {
            sessionStorage.clear();
            return Promise.reject(error)
        } else {
            return Promise.reject(error)
        }
    }
    return {
        code: res.code,
        data: res.data,
        msg: res.msg,
        originalRes: response
    }
});

export default instance
