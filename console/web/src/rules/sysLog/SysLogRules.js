export default {
    passport: [
        {
            type: 'string',
            max: 128,
            trigger: 'blur',
            message: '账号长度不能超过128'
        }
    ],
    url: [
        {
            type: 'string',
            max: 255,
            trigger: 'blur',
            message: '请求URL长度不能超过255'
        }
    ],
    method: [
        {
            type: 'string',
            max: 255,
            trigger: 'blur',
            message: '请求方法长度不能超过255'
        }
    ],
    params: [
        {
            type: 'string',
            max: 2048,
            trigger: 'blur',
            message: '请求参数长度不能超过2048'
        }
    ],
    ip: [
        {
            type: 'string',
            max: 255,
            trigger: 'blur',
            message: '请求ip长度不能超过255'
        }
    ],

    createTime: [
        {
            required: true,
            message: '请输入创建时间',
            trigger: 'blur'
        }
    ],

}