<template>
  <div>
    <div class="container">
      <div class="form-box">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item
              label="账号"
              prop="passport">
            <el-input
                v-model="form.passport"
                :disabled="true"
                placeholder="账号"/>
          </el-form-item>
          <el-form-item
              label="姓名"
              prop="name">
            <el-input v-model="form.name"
                      :disabled="!isEdit"/>
          </el-form-item>
          <el-form-item
              label="角色"
              prop="roleName">
            <el-input
                v-model="form.roleName"
                :disabled="true"
                placeholder="角色"/>
          </el-form-item>
          <el-form-item
              label="邮箱"
              prop="email">
            <el-input v-model="form.email"
                      :disabled="!isEdit"/>
          </el-form-item>
          <el-form-item
              label="手机号"
              prop="phone">
            <el-input v-model="form.phone"
                      :disabled="!isEdit"/>
          </el-form-item>
          <el-form-item
              label="地址"
              prop="address">
            <el-input v-model="form.address"
                      :disabled="!isEdit"/>
          </el-form-item>
          <el-form-item
              label="性别"
              prop="sex">
            <el-radio-group v-model="form.sex"
                            :disabled="!isEdit">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button v-if="!isEdit" type="primary" @click="eableEdit">编辑</el-button>
            <el-button v-if="isEdit" type="primary" @click="submitForm">保存</el-button>
            <el-button v-if="isEdit" type="primary" @click="cancelEdit">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
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
          passport: '',
          name: '',
          roleName: '',
          email: '',
          phone: ''
        };
      }
    },
    roleList: {
      type: Array,
      dafault: () => []
    },
    isEdit: {
      type: Boolean,
      dafault: () => false
    }
  },
  data() {
    return {
      form: this.defaultValues
    };
  },
  created() {
    this.getAccount();
  },
  methods: {
    async getAccount() {
      const accoutInfo = await Account.getCurrentAccount();
      this.form = accoutInfo.data;
    },
    async submitForm() {
      try {
        await this.$refs.form.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      let params = Object.assign({}, this.form);
      try {
        await Account.updateInfo(params);
        this.isEdit = !this.isEdit;
        this.$emit('success');
        this.$message.success(`编辑成功！`);
      } catch (e) {
        this.$message.error('编辑成功！原因：' + e.message);
      }
    },
    eableEdit() {
      this.isEdit = !this.isEdit;
    },
    cancelEdit() {
      this.isEdit = !this.isEdit;
    },
    onSubmit() {
      this.$message.success('提交成功！');
    }
  }
};
</script>
