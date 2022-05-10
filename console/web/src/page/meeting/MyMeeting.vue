<template>
  <div>
    <el-dialog title="会议基本信息" :visible.sync="dialogFormVisible" width="400px">
      <el-form>
        <el-form-item label="会议名称">
          <el-input
              v-model="meetDetail.title"
              :disabled="true">
          </el-input>
        </el-form-item>
        <el-form-item label="会议室">
          <el-tag type='success' disable-transitions>{{meetDetail.roomName  }}</el-tag>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-input
              v-model="meetDetail.start"
              :disabled="true">
          </el-input>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-input
              v-model="meetDetail.end"
              :disabled="true">
          </el-input>
        </el-form-item>
        <el-form-item label="签到二维码">
          <el-image style="width: 150px; height: 150px" :src="src"></el-image>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="review()">确 定</el-button>
      </div>
    </el-dialog>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 待办事项
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <FullCalendar :options="calendarOptions"/>
    </div>
  </div>
</template>

<script>
import '@fullcalendar/core/vdom' // solves problem with Vite
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import bootstrapPlugin from '@fullcalendar/bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import '@fortawesome/fontawesome-free/css/all.css';
import Reserve from "@/api/Reserve";
import cnLocale from '@fullcalendar/core/locales/zh-cn'
import QRCode from 'qrcode'
const qrCodeData = {
  loginQRCodeStatus: 'notGet',    // 二维码登录状态 | normal 正常 | timeout 超时 | success 登录成功
  qrToken: "",
  loginQrCode: "", // 二维码图片
  loginQrCodeTimer: 0, // 有效时间
  checkLoginTimer: 0, // 轮询时间
}

export default {
  name: 'Meeting',
  components: {
    FullCalendar
  },
  data() {
    return {
      calendarOptions: {
        locale: cnLocale,
        plugins: [dayGridPlugin, interactionPlugin, resourceTimelinePlugin, bootstrapPlugin],
        initialView: 'dayGridMonth',
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        selectable: true,
        height: 600,
        resources: [],
        events: [],
        select: this.handleSelect,
        eventDrop: this.eventDrop,
        eventResize: this.eventResize,
        eventClick: this.showDetail, //点击日程触发
        eventRender:this.eventRender,
      },
      meetDetail: {
        title: "",
        roomName: "",
        start: "",
        end: ""
      },
      dialogFormVisible: false,
      src: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.xjishu.com%2Fimg%2Fzl%2F2018%2F6%2F30%2F1241359458913.gif&refer=http%3A%2F%2Fimg.xjishu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654143553&t=0bf95221512c047037e05edba03f6f63'
    };
  },
  created() {
    this.queryMyReserveEvents();
  },
  methods: {
    async queryMyReserveEvents() {
      const {data} = await Reserve.myEvents({
        pageSize: 1000,
        pageNum: 1
      });
      this.calendarOptions.events = data.rows;
    },
    dateFormat:function(time) {
      var date=new Date(time);
      var year=date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
      var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
      var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
      var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
      var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    showDetail(clickInfo){
      var splitStr = clickInfo.event.title.split(" ");
      this.meetDetail.title = splitStr[1];
      this.meetDetail.roomName = splitStr[0];
      this.meetDetail.start = this.dateFormat(clickInfo.event.startStr);
      this.meetDetail.end = this.dateFormat(clickInfo.event.endStr);
      //this.getLoginQrCode();
      this.dialogFormVisible = true;
    }
//     async getLoginQrCode() {
//       const {data} = await Reserve.loginQrCode({
//         pageSize: 1000,
//         pageNum: 1
//       });// 获取app扫码登录的二维码标识
//       qrCodeData.qrToken = data. // 将返回的data里的字符串生成二维码
//       QRCode.toDataURL(JSON.stringify({
//         content: qrCodeData.qrToken,
//         copyright: "app",
//         function: "pc_login",
//         time: new Date().getTime()
//       })).then(url => {
//             qrCodeData.loginQrCode = url // 生成的二维码
//           })
//           .catch(err => {
//             console.error(err)
//           })
//       qrCodeData.loginQRCodeStatus = 'normal' // 将状态设置为正常
//       qrCodeData.loginQrCodeTimer = setTimeout(() => {
//         qrCodeData.loginQRCodeStatus = 'timeout'
//         clearInterval(qrCodeData.loginQrCodeTimer)
//       }, 1000 * 60 * 5) // 有效期5分钟
//       this.checkAppLogin()
//     },
//     checkAppLogin () {
//   if ( qrCodeData.loginQRCodeStatus === 'normal') {
//     // 开启检测
//     qrCodeData.checkLoginTimer = setTimeout(async () => { // 轮询
//       // 发起扫app二维码登录的接口
//       let res = axios.post({
//         ......
//       })
//     ....// 根据返回的状态做相应的操作
//       if (res.code === 211) {
//         // 二维码已过期，请刷新
//         qrCodeData.loginQRCodeStatus = 'timeout'
//         return
//       }
//       if (res.code === 210) {
//         // 继续检查是否被使用
//         checkAppLogin()
//         return
//       }
//       if (res.code === 200) {
//         // 登录成功
//         afterLogin(res)
//         return
//       }
//     ....
//       clearInterval(qrCodeData.checkLoginTimer)
//     }, 1000)
//   }
//   else {
//     clearInterval(qrCodeData.checkLoginTimer)
//   }
// }
  },
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}.fc-content

.mr10 {
  margin-right: 10px;
}
</style>
