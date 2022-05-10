export default {
    oldPassword: [
        {
            required: true,
            message: '请输入旧密码',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '密码长度不能超过16'
        }
    ],
    newPassword: [
        {
            required: true,
            message: '请输入新密码',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '密码长度不能超过16'
        }
    ],
    newPasswordConfirm: [
        {
            required: true,
            message: '请输入确认密码',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '密码长度不能超过16'
        }
    ],


}