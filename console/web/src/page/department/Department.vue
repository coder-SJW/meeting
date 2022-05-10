<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 部门管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-form inline>
          <el-form-item label="部门">
            <el-input v-model="filter.name" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item>
            <el-button v-if="$hasPermission('DEPARTMENT_QUERY')" icon="el-icon-search"
                       type="primary"
                       @click="queryList()">查询
            </el-button>
            <el-button v-if="$hasPermission('DEPARTMENT_EXPORT')" icon="el-icon-search"
                       type="primary"
                       @click="exportExcel()">导出
            </el-button>
            <el-button v-if="$hasPermission('DEPARTMENT_CREATE')" icon="el-icon-search"
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
        <el-table-column label="部门" prop="name"/>
        <el-table-column label="备注" prop="description" show-overflow-tooltip/>
        <el-table-column label="创建时间">
          <template slot-scope="{ row }">{{ row.createTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="{ row }">{{ row.updateTime | formatDate }}</template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
                v-if="$hasPermission('DEPARTMENT_UPDATE')"
                size="small"
                type="text"
                @click="edit(scope.row)"
            >编辑
            </el-button>
            <el-button
                v-if="$hasPermission('DEPARTMENT_DELETE')"
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
import DepartmentDialog from '@/page/department/DepartmentDialog';
import Department from '@/api/Department';
import PagedTable from '@/components/Table';

export default {
  name: 'Department',
  components: {
    PagedTable
  },
  data() {
    return {
      filter: {
        name: '',
        pageNum: 1,
        pageSize: 20
      },
      total: 0,
      tableData: []
    };
  },
  created() {
    this.$dialog.register('department-dialog', DepartmentDialog);
    this.queryList();
  },
  methods: {
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await Department.query(params);
      this.total = data.count;
      this.tableData = data.rows;
    },
    async exportExcel() {
      this.filter.pageNum = 1;
      const params = Object.assign({}, this.filter);
      await Department.exportExcel(params);
    },
    pageSizeChange(pageSize) {
      this.filter.pageSize = pageSize;
      this.queryList();
    },
    edit(row) {
      const props = {
        isEdit: true
      };
      props.defaultValues = Object.assign({}, row);
      this.openDialog('department-dialog', props);
    },
    openDialog(name, props = {}, on = {'success': this.queryList}) {
      this.$dialog.open(name, {props, on});
    },
    // 新增操作
    create() {
      const props = {
        isEdit: false
      };
      this.openDialog('department-dialog', props);
    },

    async remove(id) {
      await this.$confirm('是否确认删除？', '删除', {
        type: 'warn'
      });
      try {
        await Department.remove(id);
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