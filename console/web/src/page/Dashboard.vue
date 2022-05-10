<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="mgb20" shadow="hover" style="height:182px;">
          <div class="user-info">
            <img alt class="user-avator" src="../assets/img/head.jpg"/>
            <div class="user-info-cont">
              <div class="user-info-name">{{ name }}</div>
              <div>{{ role }}</div>
            </div>
          </div>
        </el-card>
        <el-card shadow="hover" style="height:322px;">
          <e-charts
              :options="weeklyOption"
              auto-resize/>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-row :gutter="20" class="mgb20">
          <el-col :span="8">
            <el-card :body-style="{padding: '0px'}" shadow="hover">
              <div class="grid-content grid-con-1">
                <i class="el-icon-lx-people grid-con-icon"/>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ accountGrid.todayVisitors }}</div>
                  <div>今日用户数</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card :body-style="{padding: '0px'}" shadow="hover">
              <div class="grid-content grid-con-2">
                <i class="el-icon-lx-roundcheckfill grid-con-icon"/>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ accountGrid.newAccountInLastWeek }}</div>
                  <div>近一周新用户</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card :body-style="{padding: '0px'}" shadow="hover">
              <div class="grid-content grid-con-3">
                <i class="el-icon-lx-goods grid-con-icon"/>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ accountGrid.totalAccount }}</div>
                  <div>总用户数</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-card shadow="hover" style="height:403px;">
          <e-charts
              :options="hourlyOption"
              auto-resize/>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <e-charts
              :options="dailyOption"
              auto-resize/>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <e-charts
              :options="monthlyOption"
              auto-resize/>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Dashboard from "@/api/Dashboard";
import ECharts from 'vue-echarts/components/ECharts'
import 'echarts/lib/chart/line'
import 'echarts/lib/chart/pie'
import 'echarts/lib/component/tooltip'
import 'echarts/lib/component/legend'
import 'echarts/lib/component/title'
import 'echarts/lib/component/dataset'
import 'echarts/theme/dark'

export default {
  name: 'dashboard',
  data() {
    return {
      name: sessionStorage.getItem('passport'),
      accountGrid: {},
      hourlyOption: {},
      dailyOption: {},
      weeklyOption: {},
      monthlyOption: {}
    };
  },
  created() {
    this.queryDataGrid();
    this.queryAccountGrid();
  },
  components: {
    ECharts
  },
  computed: {
    role() {
      return this.name === 'admin' ? '超级管理员' : '普通用户';
    }
  },
  methods: {
    async queryDataGrid() {
      const {data} = await Dashboard.dataGrid();
      this.hourlyOption = data.hourlyOption;
      this.dailyOption = data.dailyOption;
      this.weeklyOption = data.weeklyOption;
      this.monthlyOption = data.monthlyOption;
    },
    async queryAccountGrid() {
      const {data} = await Dashboard.accountGrid();
      this.accountGrid = data;
    }
  }
};
</script>


<style scoped>
.el-row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}

.grid-con-icon {
  font-size: 50px;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}

.user-info-list span {
  margin-left: 70px;
}

.mgb20 {
  margin-bottom: 20px;
}

.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}

.schart {
  width: 100%;
  height: 300px;
}
</style>
