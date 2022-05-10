<template>
  <div class="repair-create">
    <h1 class="repair-create__title">创建报修</h1>
    <el-form
        ref="repairForm"
        :disabled="!saveVisible"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="会议室"
          prop="roomId">
        <el-select
            v-model="form.roomId"
            filterable
            placeholder="请选择">
          <el-option
              v-for="item in roomList"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item
          label="报修人"
          prop="accountId">
        <el-select
            v-model="form.accountId"
            filterable
            placeholder="请选择">
          <el-option
              v-for="item in accountList"
              :key="item.id"
              :label="item.passport"
              :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item
          label="内容"
          prop="content">
        <el-input v-model="form.content"
                  :rows="4"
                  style="width: 500px"
                  type="textarea"/>
      </el-form-item>
    </el-form>
    <el-form>
      <el-form-item>
        <el-button v-if="$hasPermission('REPAIR_CREATE') && saveVisible"
                   style="margin-left: 100px"
                   type="primary"
                   @click="submitForm">保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import Repair from '@/api/Repair';
import RepairRules from '@/rules/repair/RepairRules'
import Account from '@/api/Account';
import Room from '@/api/Room';

export default {
  name: 'RepairDetail',
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          id: '',
          status: '',
          content: '',
          roomName: '',
          accountPassport: '',
        };
      }
    }
  },
  data() {
    return {
      form: Object.assign({}, this.defaultValues),
      rules: RepairRules,
      accountList: [],
      roomList: [],
      saveVisible: true,
    };
  },
  created() {
    this.queryAccountList();
    this.queryRoomList();
  },
  watch: {
    $route(to, from) {
      if (to.path.includes("/$tool.firstLowerCase($foreignModel)-create")) {
        this.form = this.defaultValues;
        this.defaultValues.imageList = [];
        this.saveVisible = true;
      }
    }
  },
  methods: {
    async queryAccountList() {
      const {data} = await Account.query({
        pageSize: 1000,
        pageNum: 1
      });
      this.accountList = data.rows;
    },
    async queryRoomList() {
      const {data} = await Room.query({
        pageSize: 1000,
        pageNum: 1
      });
      this.roomList = data.rows;
    },
    async submitForm() {
      try {
        await this.$refs.repairForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      try {
        let params = Object.assign({}, this.form);
        this.saveVisible = false;
        await Repair.create(params);
        this.$emit('success');
        this.$message.success(`创建成功！`);
      } catch (e) {
        this.$message.error(`创建失败！原因：` + e.message);
      }
    },
  }
};
</script>

<style lang="scss">
.repair-create {
  &__title {
    font-size: 18px;
    font-weight: normal;
    color: #606266;
  }

  &__form {
    width: 1000px;
  }

  .el-form {
    .el-input {
      width: 250px;
    }
  }

  .item__content {
    line-height: 20px;
    display: inline-block;
    color: #606266;
  }
}
</style>
