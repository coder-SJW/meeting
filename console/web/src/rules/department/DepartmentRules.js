export default {
    name: [
        {
            required: true,
            message: '请输入部门',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 255,
            trigger: 'blur',
            message: '部门长度不能超过255'
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