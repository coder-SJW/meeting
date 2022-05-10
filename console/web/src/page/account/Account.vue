<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 用户管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-form inline>
          <el-form-item label="账号">
            <el-input v-model="filter.passport" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item label="名称">
            <el-input v-model="filter.name" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item
              label="部门"
              prop="departmentId">
            <el-select
                v-model="filter.departmentId"
                filterable
                placeholder="请选择">
              <el-option
                  v-for="item in departmentList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item
              label="角色"
              prop="roleId">
            <el-select
                v-model="filter.roleId"
                filterable
                placeholder="请选择">
              <el-option
                  v-for="item in roleList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="filter.phone" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item>
            <el-button v-if="$hasPermission('ACCOUNT_QUERY')" icon="el-icon-search"
                       type="primary"
                       @click="queryList()">查询
            </el-button>
            <el-button v-if="$hasPermission('ACCOUNT_CREATE')" icon="el-icon-search"
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
        <el-table-column label="账号" prop="passport"/>
        <el-table-column label="名称" prop="name"/>
        <el-table-column label="状态" prop="status">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">{{
                scope.row.status === 0 ?
                    '启用' : '禁用'
              }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="邮箱" prop="email"/>
        <el-table-column label="手机号" prop="phone"/>
        <el-table-column label="性别" prop="sex">
          <template slot-scope="scope">
            {{ scope.row.sex == 0 ? '女' : '男' }}
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="description"/>
        <el-table-column label="最近登录时间">
          <template slot-scope="{ row }">{{ row.lastLoginTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="角色" prop="roleName"/>
        <el-table-column label="部门" prop="departmentName"/>
        <el-table-column align="center" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
                v-if="$hasPermission('ACCOUNT_UPDATE')"
                size="small"
                type="text"
                @click="edit(scope.row)"
            >编辑
            </el-button>
            <el-button
                v-if="$hasPermission('ACCOUNT_DELETE')"
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
import AccountDialog from '@/page/account/AccountDialog';
import Account from '@/api/Account';
import PagedTable from '@/components/Table';
import Role from '@/api/Role';
import Department from '@/api/Department';

export default {
  name: 'Account',
  components: {
    PagedTable
  },
  data() {
    return {
      filter: {
        passport: '',
        name: '',
        phone: '',
        roleId: '',
        departmentId: '',
        pageNum: 1,
        pageSize: 20
      },
      total: 0,
      roleList: [],
      departmentList: [],
      tableData: []
    };
  },
  created() {
    this.$dialog.register('account-dialog', AccountDialog);
    this.queryList();
    this.queryRoleList();
    this.queryDepartmentList();
  },
  methods: {
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await Account.query(params);
      this.total = data.count;
      this.tableData = data.rows;
    },
    async queryRoleList() {
      const {data} = await Role.query({
        pageSize: 100,
        pageNum: 1
      });
      this.roleList = data.rows;
    },
    async queryDepartmentList() {
      const {data} = await Department.query({
        pageSize: 100,
        pageNum: 1
      });
      this.departmentList = data.rows;
    },
    pageSizeChange(pageSize) {
      this.filter.pageSize = pageSize;
      this.queryList();
    },
    edit(row) {
      const props = {
        roleList: this.roleList,
        departmentList: this.departmentList,
        isEdit: true
      };
      props.defaultValues = Object.assign({}, row);
      this.openDialog('account-dialog', props);
    },
    openDialog(name, props = {}, on = {'success': this.queryList}) {
      this.$dialog.open(name, {props, on});
    },
    async remove(id) {
      await this.$confirm('是否确认删除？', '删除', {
        type: 'warn'
      });
      try {
        await Account.remove(id);
        this.$message.success('删除成功！');
        this.queryList();
      } catch (e) {
        this.$message.error('删除失败！原因：' + e.message);
      }
    },
    // 新增操作
    create() {
      const props = {
        roleList: this.roleList,
        departmentList: this.departmentList,
        isEdit: false
      };
      this.openDialog('account-dialog', props);
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