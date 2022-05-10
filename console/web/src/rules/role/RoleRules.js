export default {
    name: [
        {
            required: true,
            message: '请输入角色名',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 32,
            trigger: 'blur',
            message: '角色名长度不能超过32'
        }
    ],

}