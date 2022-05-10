<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 角色管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-form inline>
          <el-form-item label="角色名称">
            <el-input v-model="filter.name" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item>
            <el-button v-if="$hasPermission('ROLE_QUERY')" icon="el-icon-search"
                       type="primary"
                       @click="queryList()">查询
            </el-button>
            <el-button v-if="$hasPermission('ROLE_CREATE')" icon="el-icon-search"
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
        <el-table-column label="名称" prop="name"/>
        <el-table-column label="角色类型" prop="type">
          <template slot-scope="scope">
            <el-tag :type="scope.row.type === 2 ? 'success' : 'danger'">{{
                scope.row.type === 2 ?
                    '系统默认角色' : '自定义角色'
              }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="description"/>
        <el-table-column label="创建时间">
          <template slot-scope="{ row }">{{ row.createTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="{ row }">{{ row.updateTime | formatDate }}</template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
                v-if="$hasPermission('ROLE_UPDATE')"
                size="small"
                type="text"
                @click="edit(scope.row)"
            >编辑
            </el-button>
            <el-button
                v-if="$hasPermission('ROLE_DELETE')"
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
import RoleDialog from './RoleDialog';
import Role from '@/api/Role';
import PagedTable from '../../components/Table';

export default {
  name: 'Role',
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
      tableData: [],
      roleList: []
    };
  },
  created() {
    this.$dialog.register('role-dialog', RoleDialog);
    this.queryList();
  },
  methods: {
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await Role.query(params);
      this.total = data.count;
      this.tableData = data.rows;
    },
    pageSizeChange(pageSize) {
      this.filter.pageSize = pageSize;
      this.queryList();
    },
    edit(row) {
      const props = {
        isEdit: true,
        roleList: this.roleList
      };
      props.defaultValues = Object.assign({}, row);
      this.openDialog('role-dialog', props);
    },
    openDialog(name, props = {}, on = {'success': this.queryList}) {
      this.$dialog.open(name, {props, on});
    },
    async remove(id) {
      await this.$confirm('是否确认删除？', '删除', {
        type: 'warn'
      });
      try {
        await Role.remove(id);
        this.$message.success('删除成功！');
        this.queryList();
      } catch (e) {
        this.$message.error('删除失败！原因：' + e.message);
      }
    },
    // 新增操作
    create() {
      const props = {
        isEdit: false,
        roleList: this.roleList
      };
      this.openDialog('role-dialog', props);
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
