export default {
    status: [
        {
            required: true,
            message: '请输入状态;1:未处理 2:处理中 3: 已完成',
            trigger: 'blur'
        },
        {
            type: 'integer',
            min: 0,
            trigger: 'blur',
            message: '状态;1:未处理 2:处理中 3: 已完成不能小于0'
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
            message: '请输入会议室',
            trigger: 'blur'
        }
    ],

    accountId: [
        {
            required: true,
            message: '请输入报修人',
            trigger: 'blur'
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