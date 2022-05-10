<template>
  <af-dialog
      :on-confirm="submitForm"
      :title="isEdit ? '编辑部门' : '创建部门'"
      :visible.sync="visible"
      class="system-dialog"
      @close="$emit('close')">
    <el-form
        ref="departmentForm"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="部门"
          prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item
          label="备注"
          prop="description">
        <el-input v-model="form.description"
                  :rows="4"
                  style="width: 500px"
                  type="textarea"/>
      </el-form-item>
    </el-form>
  </af-dialog>
</template>
<script>
import AfDialog from '@/components/Dialog';
import Department from '@/api/Department';
import DepartmentRules from '@/rules/department/DepartmentRules'


export default {
  name: 'DepartmentDialog',
  components: {
    AfDialog
  },
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          name: '',
          description: '',
        };
      }
    },
    isEdit: {
      type: Boolean,
      dafault: () => false
    }
  },
  data() {
    return {
      rules: DepartmentRules,
      form: this.defaultValues,
      visible: false
    };
  },
  methods: {
    async submitForm() {
      try {
        await this.$refs.departmentForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      try {
        let params = Object.assign({}, this.form);
        this.isEdit ? await Department.update(params.id, params) : await Department.create(params);
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
    width: 900px;
  }
}

.el-form {
  .el-input {
    width: 250px;
  }
}
</style>