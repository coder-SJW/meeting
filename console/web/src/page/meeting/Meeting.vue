<template>
  <div>
    <el-dialog title="会议审核" :visible.sync="dialogFormVisible" width="300px">
      <el-form>
        <el-form-item>
          <el-radio-group v-model="reviewStatus.status">
            <el-radio label="0">审核中</el-radio>
            <el-radio label="1">通过</el-radio>
            <el-radio label="2">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="review()">确 定</el-button>
      </div>
    </el-dialog>

    <div class="container">
      <div class="handle-box">
        <el-form inline>
          <el-form-item label="会议名称">
            <el-input v-model="filter.name" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item label="内容">
            <el-input v-model="filter.content" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item label="会议室">
            <el-input v-model="filter.roomName" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item>
            <el-button v-if="$hasPermission('MEETING_QUERY')" icon="el-icon-search"
                       type="primary"
                       @click="queryList()">查询
            </el-button>
            <el-button v-if="$hasPermission('MEETING_EXPORT')" icon="el-icon-search"
                       type="primary"
                       @click="exportExcel()">导出
            </el-button>
            <el-button v-if="$hasPermission('MEETING_CREATE')" icon="el-icon-search"
                       type="primary"
                       @click="create()">预约会议
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
        <el-table-column label="会议名称" prop="name"/>
        <el-table-column label="内容" prop="content" show-overflow-tooltip/>
        <el-table-column label="人员名单" prop="accountNames"/>
        <el-table-column label="创建时间">
          <template slot-scope="{ row }">{{ row.createTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="{ row }">{{ row.updateTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="会议室" prop="roomName"/>
        <c label="状态" prop="status"/>
        <el-table-column
            prop="status"
            label="状态"
            width="100"
            filter-placement="bottom-end">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === '0'"
                    type='warning'
                    disable-transitions>审核中</el-tag>
            <el-tag v-if="scope.row.status === '1'"
                    type='success'
                    disable-transitions>通过</el-tag>
            <el-tag v-if="scope.row.status === '2'"
                    type='danger'
                    disable-transitions>驳回</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
                v-if="$hasPermission('MEETING_REVIEW')"
                size="small"
                type="text"
                @click="open(scope.row.id, scope.row.status)"
            >审核
            </el-button>
            <el-button
                v-if="$hasPermission('MEETING_QUERY')"
                size="small"
                type="text"
                @click="detail(scope.row.id)"
            >详情
            </el-button>
            <el-button
                v-if="$hasPermission('MEETING_DELETE')"
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
import Meeting, {review} from '@/api/Meeting';
import PagedTable from '@/components/Table';
export default {
  name: 'Meeting',
  components: {
    PagedTable
  },
  data() {
    return {
      filter: {
        name: '',
        content: '',
        roomName: '',
        pageNum: 1,
        pageSize: 20
      },
      total: 0,
      roomList: [],
      tableData: [],
      // 0审核中1通过2驳回
      reviewStatus: {
        id: 0,
        status: 0
      },
      dialogFormVisible: false
    };
  },
  created() {
    this.queryList();
  },
  methods: {
    filterTag(value, row) {
      return row.tag === value;
    },
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await Meeting.query(params);
      this.total = data.count;
      this.tableData = data.rows;
    },
    async exportExcel() {
      this.filter.pageNum = 1;
      const params = Object.assign({}, this.filter);
      await Meeting.exportExcel(params);
    },
    pageSizeChange(pageSize) {
      this.filter.pageSize = pageSize;
      this.queryList();
    },
    detail(id) {
      this.$router.push(`/meeting-detail?id=${id}`);
    },
    create() {
      this.$router.push(`/meeting-create`);
    },

    async remove(id) {
      await this.$confirm('是否确认删除？', '删除', {
        type: 'warn'
      });
      try {
        await Meeting.remove(id);
        this.$message.success('删除成功！');
        this.queryList();
      } catch (e) {
        this.$message.error('删除失败！原因：' + e.message);
      }
    },
    open(id, status){
      this.reviewStatus.id = id;
      this.reviewStatus.status = status;
      this.dialogFormVisible = true;
    },
    async review() {
      await this.$confirm('是否确认？', '审核', {
        type: 'warn'
      });
      try {
        await Meeting.review(this.reviewStatus);
        this.$message.success('审核成功！');
        this.queryList();
        this.dialogFormVisible = false;
      } catch (e) {
        this.$message.error('审核失败！原因：' + e.message);
        this.dialogFormVisible = false;
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
