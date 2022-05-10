<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 系统日志管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-form inline>
          <el-form-item label="账号">
            <el-input v-model="filter.passport" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item label="请求方法">
            <el-input v-model="filter.method" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item>
            <el-button v-if="$hasPermission('SYS_LOG_QUERY')" icon="el-icon-search"
                       type="primary"
                       @click="queryList()">查询
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      <paged-table
          :current-page="filter.pageNum"
          :data="tableData"
          :page-size="filter.pageSize"
          :total="total"
          @current-change="queryList"
          @size-change="pageSizeChange">
        <el-table-column label="账号" prop="passport"/>
        <el-table-column label="请求URL" prop="url"/>
        <el-table-column label="请求方法" prop="method"/>
        <el-table-column label="请求参数" prop="params"/>
        <el-table-column label="请求ip" prop="ip"/>
        <el-table-column label="请求耗时(单位毫秒)" prop="cost"/>
        <el-table-column label="创建时间">
          <template slot-scope="{ row }">{{ row.createTime | formatDate }}</template>
        </el-table-column>
      </paged-table>
    </div>
  </div>
</template>

<script>
import SysLog from '@/api/SysLog';
import PagedTable from '@/components/Table';

export default {
  name: 'SysLog',
  components: {
    PagedTable
  },
  data() {
    return {
      filter: {
        passport: '',
        method: '',
        pageNum: 1,
        pageSize: 20
      },
      total: 0,
      tableData: []
    };
  },
  created() {
    this.queryList();
  },
  methods: {
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await SysLog.query(params);
      this.total = data.count;
      this.tableData = data.rows;
    },
    pageSizeChange(pageSize) {
      this.filter.pageSize = pageSize;
      this.queryList();
    }
  }
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-input {
  width: 300px;
  display: inline-block;
}

.mr10 {
  margin-right: 10px;
}
</style>