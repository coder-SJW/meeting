export default {
    name: [
        {
            required: true,
            message: '请输入会议名称',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 15,
            trigger: 'blur',
            message: '会议名称长度不能超过15'
        }
    ],
    content: [
        {
            required: true,
            message: '请输入内容',
            trigger: 'blur'
        }
    ],

    roomId: [
        {
            required: true,
            message: '请选择会议室',
            trigger: 'blur'
        }
    ],

    accountIds: [
        {
            required: true,
            message: '请输入人员名单',
            trigger: 'blur'
        }
    ],

}
