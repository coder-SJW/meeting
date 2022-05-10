<template>
  <div class="form-box">
    <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="100px"
        status-icon
    >
      <el-form-item label="旧密码：" prop="oldPassword">
        <el-input v-model="form.oldPassword" auto-complete="off" type="password"/>
      </el-form-item>
      <el-form-item label="新密码：" prop="newPassword">
        <el-input v-model="form.newPassword" auto-complete="off" type="password"/>
      </el-form-item>
      <el-form-item label="确认密码：" prop="newPasswordConfirm">
        <el-input v-model="form.newPasswordConfirm" auto-complete="off" type="password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">确认修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import Account from "@/api/Account";

export default {
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          oldPassword: '',
          newPassword: '',
          newPasswordConfirm: '',
        };
      }
    }
  },
  data() {
    //此处即表单发送之前验证  验证新密码与原密码
    let validateNewPassword = (rule, value, callback) => {
      if (value === this.form.oldPassword) {
        callback(new Error('新密码不能与原密码相同!'))
      } else {
        callback()
      }
    }
    //此处即表单发送之前验证  验证新密码与再次确认
    let validateNewPasswordConfirm = (rule, value, callback) => {
      if (value !== this.form.newPassword) {
        callback(new Error('与新密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      form: this.defaultValues,
      rules: {
        //验证规则
        oldPassword: [{
          required: true,
          message: '请输入原密码',
          trigger: 'blur'
        }],
        newPassword: [{
          required: true,
          message: '请设置新密码',
          trigger: 'blur'
        },
          {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '密码长度不能超过16'
          },
          {
            validator: validateNewPassword,
            trigger: 'blur'
          }
        ],
        newPasswordConfirm: [{
          required: true,
          message: '请确认新密码',
          trigger: 'blur'
        },
          {
            type: 'string',
            max: 16,
            trigger: 'blur',
            message: '密码长度不能超过16'
          },
          {
            validator: validateNewPasswordConfirm,
            trigger: 'blur'
          }
        ]
      }
    };
  },
  created() {
  },
  methods: {
    async submitForm() {
      try {
        await this.$refs.form.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }

      try {
        let params = Object.assign({}, this.form);
        await Account.changePassword(params);
        this.$refs['form'].resetFields()
        this.$emit('success');
        this.$message.success(`修改成功！`);
      } catch (e) {
        this.$message.error('修改失败！原因：' + e.message);
      }
    }
  }
};
</script>
