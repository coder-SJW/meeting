export default {
    name: [
        {
            required: true,
            message: '请输入会议室',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 255,
            trigger: 'blur',
            message: '会议室长度不能超过255'
        }
    ],
    roomImageIds: [
        {
            required: true,
            message: '请输入实景照片',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 64,
            trigger: 'blur',
            message: '实景照片长度不能超过64'
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