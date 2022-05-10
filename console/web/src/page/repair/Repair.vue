<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 报修管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-form inline>
          <el-form-item label="状态">
            <el-select
                v-model="filter.status"
                filterable
                placeholder="请选择">
              <el-option
                  v-for="item in statusList"
                  :key="item.code"
                  :label="item.desc"
                  :value="item.code"/>
            </el-select>
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="filter.content" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item label="会议室">
            <el-input v-model="filter.roomName" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item label="报修人">
            <el-input v-model="filter.accountPassport" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item>
            <el-button v-if="$hasPermission('REPAIR_QUERY')" icon="el-icon-search"
                       type="primary"
                       @click="queryList()">查询
            </el-button>
            <el-button v-if="$hasPermission('REPAIR_EXPORT')" icon="el-icon-search"
                       type="primary"
                       @click="exportExcel()">导出
            </el-button>
            <el-button v-if="$hasPermission('REPAIR_CREATE')" icon="el-icon-search"
                       type="primary"
                       @click="create()">创建
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
        <el-table-column label="状态">
          <template slot-scope="{ row }">
            <el-tag v-if="row.status ===1">未处理</el-tag>
            <el-tag v-else-if="row.status ===2">处理中</el-tag>
            <el-tag v-else-if="row.status ===3">已完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="内容" prop="content" show-overflow-tooltip/>
        <el-table-column label="创建时间">
          <template slot-scope="{ row }">{{ row.createTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="{ row }">{{ row.updateTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="会议室" prop="roomName"/>
        <el-table-column label="报修人" prop="accountPassport"/>
        <el-table-column align="center" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
                v-if="$hasPermission('REPAIR_QUERY')"
                size="small"
                type="text"
                @click="detail(scope.row.id)"
            >详情
            </el-button>
            <el-button
                v-if="$hasPermission('REPAIR_DELETE')"
                size="small"
                type="text"
                @click="remove(scope.row.id)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </paged-table>
    </div>
  </div>
</template>

<script>
import Repair from '@/api/Repair';
import PagedTable from '@/components/Table';

export default {
  name: 'Repair',
  components: {
    PagedTable
  },
  data() {
    return {
      filter: {
        status: '',
        content: '',
        roomName: '',
        accountPassport: '',
        pageNum: 1,
        pageSize: 20
      },
      total: 0,
      accountList: [],
      roomList: [],
      statusList: [
        {'code': -1, 'desc': '所有'},
        {'code': 1, 'desc': '未处理'},
        {'code': 2, 'desc': '处理中'},
        {'code': 3, 'desc': '已完成'},
      ],
      tableData: []
    };
  },
  created() {
    this.filter.status = this.statusList[0].code;
    this.queryList();
  },
  methods: {
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await Repair.query(params);
      this.total = data.count;
      this.tableData = data.rows;
    },
    async exportExcel() {
      this.filter.pageNum = 1;
      const params = Object.assign({}, this.filter);
      await Repair.exportExcel(params);
    },
    pageSizeChange(pageSize) {
      this.filter.pageSize = pageSize;
      this.queryList();
    },
    detail(id) {
      this.$router.push(`/repair-detail?id=${id}`);
    },
    create() {
      this.$router.push(`/repair-create`);
    },

    async remove(id) {
      await this.$confirm('是否确认删除？', '删除', {
        type: 'warn'
      });
      try {
        await Repair.remove(id);
        this.$message.success('删除成功！');
        this.queryList();
      } catch (e) {
        this.$message.error('删除失败！原因：' + e.message);
      }
    },
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
