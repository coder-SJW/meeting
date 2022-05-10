<template>
  <div class="meeting-detail">
    <h1 class="meeting-detail__title">会议详情</h1>
    <el-form
        ref="meetingForm"
        :disabled="!saveVisible"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="会议名称"
          prop="name">
        <el-input v-model="form.name"/>
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
          label="人员名单"
          prop="accountIds">
        <el-select v-model="form.accountIds" multiple placeholder="请选择">
          <el-option
              v-for="item in accountList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
          label="会议室预定">
        <FullCalendar :options="calendarOptions"/>
      </el-form-item>
    </el-form>
    <el-form>
      <el-form-item>
        <el-button
            v-if="$hasPermission('MEETING_UPDATE')"
            style="margin-left: 100px"
            type="primary"
            @click="showSaveButton">编辑
        </el-button>
        <el-button
            v-if="$hasPermission('MEETING_UPDATE') && saveVisible"
            type="primary"
            @click="submitForm">保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import Meeting from '@/api/Meeting';
import MeetingRules from '@/rules/meeting/MeetingRules'
import Account from "@/api/Account";
import Room from '@/api/Room';
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import bootstrapPlugin from '@fullcalendar/bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import '@fortawesome/fontawesome-free/css/all.css';
import Reserve from "@/api/Reserve";

export default {
  name: 'MeetingDetail',
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          id: '',
          name: '',
          content: '',
          accountIds: '',
          roomName: '',
          accounts: '',
          roomId: '',
          reserveStartTime: '',
          reserveEndTime: '',
        };
      }
    }
  },
  components: {
    FullCalendar
  },
  data() {
    return {
      rules: MeetingRules,
      form: Object.assign({}, this.defaultValues),
      roomList: [],
      accountList: [],
      saveVisible: false,
      hasSelected: false,
      id: this.$route.query.id,
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
        eventResize: this.eventResize
      }
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
    this.queryRoomList();
    this.queryAccountList();
    this.queryRoomResource();
    this.queryReserveEvents();
  },
  watch: {
    $route(to, from) {
      if (to.path.includes("/meeting-detail") && typeof (to.query.id) !== 'undefined') {
        // 多个tab详情页切换时重新刷新数据
        this.id = to.query.id;
        this.form.imageList = [];
        this.getDetail(this.id);
        this.saveVisible = false;
      }
    }
  },
  methods: {
    async queryRoomResource() {
      const {data} = await Room.resource({
        pageSize: 1000,
        pageNum: 1
      });
      this.calendarOptions.resources = data.rows;
    },
    async queryReserveEvents() {
      const {data} = await Reserve.events({
        pageSize: 1000,
        pageNum: 1
      });
      this.calendarOptions.events = data.rows;
    },
    eventResize(info) {
      if (info.startDelta.milliseconds + this.form.reserveStartTime < new Date().getTime()) {
        this.$message.error(`预约时间不能小于当前时间`);
        return;
      }
      this.form.reserveStartTime = this.form.reserveStartTime + info.startDelta.milliseconds;
      this.form.reserveEndTime = this.form.reserveEndTime + info.endDelta.milliseconds;
    },
    eventDrop(info) {
      if (this.form.reserveStartTime + info.delta.milliseconds < new Date().getTime()) {
        info.revert;
        this.$message.error(`预约时间不能小于当前时间`);
        return;
      }
      this.form.reserveStartTime = this.form.reserveStartTime + info.delta.milliseconds;
      this.form.reserveEndTime = this.form.reserveEndTime + info.delta.milliseconds;
    },
    handleSelect(info) {
      if (new Date(info.start).getTime() < new Date().getTime()) {
        this.$message.error(`预约时间不能小于当前时间`);
        return;
      }
      if (this.hasSelected) {
        this.$message.error(`一次只能预约一个时间段`);
        return;
      }
      this.hasSelected = true;
      this.form.roomId = info.resource.id;
      this.form.reserveStartTime = new Date(info.start).getTime();
      this.form.reserveEndTime = new Date(info.end).getTime();
      this.calendarOptions.events.push({
        "color": "#0099C",
        "displayEventTime": true,
        "editable": true,
        "start": info.start,
        "end": info.end,
        "title": this.form.name,
        "resourceIds": [info.resource.id]
      });
    },
    async getDetail(id) {
      const {data} = await Meeting.get(id);
      this.form = data;
    },
    async queryRoomList() {
      const {data} = await Room.query({
        pageSize: 1000,
        pageNum: 1
      });
      this.roomList = data.rows;
    },
    async queryAccountList() {
      const {data} = await Account.query({
        pageSize: 1000,
        pageNum: 1
      });
      this.accountList = data.rows;
    },
    async submitForm() {
      try {
        await this.$refs.meetingForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      try {
        let params = Object.assign({}, this.form);
        await Meeting.update(params.id, params);
        await this.queryReserveEvents();
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
.meeting-detail {
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
