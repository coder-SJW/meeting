export default {
    passport: [
        {
            required: true,
            message: '请输入账号',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '账号长度不能超过16'
        }
    ],
    name: [
        {
            required: true,
            message: '请输入名称',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 6,
            trigger: 'blur',
            message: '名称长度不能超过6'
        }
    ],
    password: [
        {
            required: true,
            message: '请输入密码',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 64,
            trigger: 'blur',
            message: '密码长度不能超过64'
        }
    ],
    status: [
        {
            required: true,
            message: '请输入用户状态',
            trigger: 'blur'
        },
        {
            type: 'integer',
            min: 0,
            trigger: 'blur',
            message: '用户状态不能小于0'
        }
    ],
    roleId: [
        {
            required: true,
            message: '请输入角色',
            trigger: 'blur'
        }
    ],

    email: [
        {
            required: true,
            message: '请输入邮箱',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 32,
            trigger: 'blur',
            message: '邮箱长度不能超过32'
        }
    ],
    phone: [
        {
            required: true,
            message: '请输入手机号',
            trigger: 'blur'
        },
        {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '手机号长度不能超过16'
        }
    ],
    address: [
        {
            type: 'string',
            max: 64,
            trigger: 'blur',
            message: '地址长度不能超过64'
        }
    ],
    sex: [
        {
            required: true,
            message: '请输入性别 0：女；1：男',
            trigger: 'blur'
        },
        {
            type: 'integer',
            min: 0,
            trigger: 'blur',
            message: '性别 0：女；1：男不能小于0'
        }
    ],
    description: [
        {
            type: 'string',
            max: 128,
            trigger: 'blur',
            message: '备注长度不能超过128'
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