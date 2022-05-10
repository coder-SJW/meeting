<template>
  <af-dialog
      :on-confirm="submitForm"
      :title="isEdit ? '编辑账号' : '创建账号'"
      :visible.sync="visible"
      class="system-dialog"
      @close="$emit('close')">
    <el-form
        ref="accountForm"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="账号"
          prop="passport">
        <el-input
            v-model="form.passport"
            :disabled="isEdit"
            placeholder="账号"/>
      </el-form-item>
      <el-form-item
          label="名称"
          prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item
          label="状态"
          prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">启用</el-radio>
          <el-radio :label="1">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item
          label="部门"
          prop="departmentId">
        <el-select
            v-model="form.departmentId"
            filterable
            placeholder="请选择">
          <el-option
              v-for="item in departmentList"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item
          label="角色"
          prop="roleId">
        <el-select
            v-model="form.roleId"
            filterable
            placeholder="请选择">
          <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item
          label="邮箱"
          prop="email">
        <el-input v-model="form.email"/>
      </el-form-item>
      <el-form-item
          label="手机号"
          prop="phone">
        <el-input v-model="form.phone"/>
      </el-form-item>
      <el-form-item
          label="地址"
          prop="address">
        <el-input v-model="form.address"/>
      </el-form-item>
      <el-form-item
          label="性别"
          prop="sex">
        <el-radio-group v-model="form.sex">
          <el-radio :label="0">女</el-radio>
          <el-radio :label="1">男</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item
          label="备注"
          prop="description">
        <el-input v-model="form.description"
                  type="textarea"/>
      </el-form-item>
    </el-form>
  </af-dialog>
</template>
<script>
import AfDialog from '@/components/Dialog';
import Account from '@/api/Account';
import AccountRules from '@/rules/account/AccountRules'


export default {
  name: 'AccountDialog',
  components: {
    AfDialog
  },
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          passport: '',
          name: '',
          password: '',
          status: '',
          roleId: '',
          departmentId: '',
          email: '',
          phone: '',
          address: '',
          sex: '',
          description: '',
          lastLoginTime: '',
        };
      }
    },
    roleList: {
      type: Array,
      dafault: () => []
    },
    departmentList: {
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
      rules: AccountRules,
      form: this.defaultValues,
      visible: false
    };
  },
  methods: {
    async submitForm() {
      try {
        await this.$refs.accountForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      try {
        let params = Object.assign({}, this.form);
        this.isEdit ? await Account.update(params.id, params) : await Account.create(params);
        this.$emit('success');
        this.$message.success(`${this.isEdit ? '编辑' : '创建'}成功！`);
      } catch (e) {
        this.$message.error(`${this.isEdit ? '编辑' : '创建'}失败！原因：` + e.message);
      }
    }
  }
};
</script>

<style lang="scss">
.system-dialog {
  .el-dialog {
    width: 600px;
  }
}
</style>