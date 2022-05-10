<template>
  <af-dialog
      :on-confirm="submitForm"
      :title="isEdit ? '编辑系统日志' : '创建系统日志'"
      :visible.sync="visible"
      class="system-dialog"
      @close="$emit('close')">
    <el-form
        ref="sysLogForm"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="账号"
          prop="passport">
        <el-input v-model="form.passport"/>
      </el-form-item>
      <el-form-item
          label="请求URL"
          prop="url">
        <el-input v-model="form.url"/>
      </el-form-item>
      <el-form-item
          label="请求方法"
          prop="method">
        <el-input v-model="form.method"/>
      </el-form-item>
      <el-form-item
          label="请求参数"
          prop="params">
        <el-input v-model="form.params"/>
      </el-form-item>
      <el-form-item
          label="请求ip"
          prop="ip">
        <el-input v-model="form.ip"/>
      </el-form-item>
      <el-form-item
          label="请求耗时(单位毫秒)"
          prop="cost">
        <el-input v-model="form.cost"/>
      </el-form-item>
    </el-form>
  </af-dialog>
</template>
<script>
import AfDialog from '@/components/Dialog';
import SysLog from '@/api/SysLog';
import SysLogRules from '@/rules/sysLog/SysLogRules'


export default {
  name: 'SysLogDialog',
  components: {
    AfDialog
  },
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          passport: '',
          url: '',
          method: '',
          params: '',
          ip: '',
          cost: '',
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
      rules: SysLogRules,
      form: this.defaultValues,
      visible: false
    };
  },
  methods: {
    async submitForm() {
      try {
        await this.$refs.sysLogForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      let params = Object.assign({}, this.form);
      this.isEdit ? await SysLog.update(params.id, params) : await SysLog.create(params);
      this.$emit('success');
      this.$message.success(`${this.isEdit ? '编辑' : '创建'}成功！`);
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