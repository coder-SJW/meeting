<template>
  <div class="repair-detail">
    <h1 class="repair-detail__title">报修详情</h1>
    <el-form
        ref="repairForm"
        :disabled="!saveVisible"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="状态"
          prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="1">未处理</el-radio>
          <el-radio :label="2">处理中</el-radio>
          <el-radio :label="3">已完成</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item
          label="内容"
          prop="content">
        <el-input v-model="form.content"
                  :rows="4"
                  style="width: 500px"
                  type="textarea"/>
      </el-form-item>
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
    </el-form>
    <el-form>
      <el-form-item>
        <el-button
            v-if="$hasPermission('REPAIR_UPDATE')"
            style="margin-left: 100px"
            type="primary"
            @click="showSaveButton">编辑
        </el-button>
        <el-button
            v-if="$hasPermission('REPAIR_UPDATE') && saveVisible"
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
      rules: RepairRules,
      form: Object.assign({}, this.defaultValues),
      accountList: [],
      roomList: [],
      saveVisible: false,
      id: this.$route.query.id,
    };
  },
  mounted() {
    if (this.id) {
      this.getDetail(this.id);
      this.isEdit = true;
      this.saveVisible = false;
    }
  },
  created() {
    this.queryAccountList();
    this.queryRoomList();
  },
  watch: {
    $route(to, from) {
      if (to.path.includes("/repair-detail") && typeof (to.query.id) !== 'undefined') {
        // 多个tab详情页切换时重新刷新数据
        this.id = to.query.id;
        this.form.imageList = [];
        this.getDetail(this.id);
        this.saveVisible = false;
      }
    }
  },
  methods: {
    async getDetail(id) {
      const {data} = await Repair.get(id);
      this.form = data;
    },
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
        await Repair.update(params.id, params);
        this.saveVisible = false;
        this.$emit('success');
        this.$message.success(`编辑成功！`);
      } catch (e) {
        this.$message.error(`编辑失败！原因：` + e.message);
      }
    },
    showSaveButton() {
      this.saveVisible = true;
    },
  }
};
</script>

<style lang="scss">
.repair-detail {
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
