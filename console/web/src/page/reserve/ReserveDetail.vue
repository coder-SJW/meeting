<template>
  <div class="reserve-detail">
    <h1 class="reserve-detail__title">预约记录详情</h1>
    <el-form
        ref="reserveForm"
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
          label="内容"
          prop="content">
        <el-input v-model="form.content"
                  :rows="4"
                  style="width: 500px"
                  type="textarea"/>
      </el-form-item>
      <el-form-item
          label="预约人"
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
          label="开始时间"
          prop="reserveStartTime">
        <el-date-picker
            v-model="form.reserveStartTime"
            default-time="12:00:00"
            placeholder="选择日期"
            type="datetime"
            value-format="timestamp">
        </el-date-picker>
      </el-form-item>
      <el-form-item
          label="结束时间"
          prop="reserveEndTime">
        <el-date-picker
            v-model="form.reserveEndTime"
            default-time="12:00:00"
            placeholder="选择日期"
            type="datetime"
            value-format="timestamp">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <el-form>
      <el-form-item>
        <!--        <el-button-->
        <!--            v-if="$hasPermission('RESERVE_UPDATE')"-->
        <!--            style="margin-left: 100px"-->
        <!--            type="primary"-->
        <!--            @click="showSaveButton">编辑-->
        <!--        </el-button>-->
        <el-button
            v-if="$hasPermission('RESERVE_UPDATE') && saveVisible"
            type="primary"
            @click="submitForm">保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import Reserve from '@/api/Reserve';
import Account from '@/api/Account';
import Room from '@/api/Room';
import FullCalendar from '@fullcalendar/vue'
import '@fullcalendar/core/vdom' // solves problem with Vite
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import resourceTimelinePlugin from "@fullcalendar/resource-timeline";
import bootstrapPlugin from "@fullcalendar/bootstrap";
import 'bootstrap/dist/css/bootstrap.css';
import '@fortawesome/fontawesome-free/css/all.css';
import ReserveRules from "@/rules/reserve/ReserveRules";

export default {
  name: 'ReserveDetail',
  components: {
    FullCalendar
  },
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          id: '',
          content: '',
          reserveStartTime: '',
          reserveEndTime: '',
          roomName: '',
          accountPassport: '',
        };
      }
    }
  },
  data() {
    return {
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin, resourceTimelinePlugin, bootstrapPlugin],
        initialView: 'resourceTimeline',
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        selectable: true,
        height: 600,
        resources: [],
        events: [],
        select: this.handleSelect,
        eventDrop: this.eventDrop,
        eventResize: this.eventResize,
      },
      roomList: [],
      saveVisible: false,
      id: this.$route.query.id,
      form: Object.assign({}, this.defaultValues),
      rules: ReserveRules,
      accountList: [],
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
      if (to.path.includes("/reserve-detail") && typeof (to.query.id) !== 'undefined') {
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
      const {data} = await Reserve.get(id);
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
        await this.$refs.reserveForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      try {
        let params = Object.assign({}, this.form);
        await Reserve.update(params.id, params);
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
.reserve-detail {
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
