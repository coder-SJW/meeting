<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 会议室管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-form inline>
          <el-form-item label="会议室">
            <el-input v-model="filter.name" class="handle-input mr10" style="width:180px"/>
          </el-form-item>
          <el-form-item label="会议室类型">
          <el-select v-model="filter.type" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
          </el-form-item>
          <el-form-item>
            <el-button v-if="$hasPermission('ROOM_QUERY')" icon="el-icon-search"
                       type="primary"
                       @click="queryList()">查询
            </el-button>
            <el-button v-if="$hasPermission('ROOM_EXPORT')" icon="el-icon-search"
                       type="primary"
                       @click="exportExcel()">导出
            </el-button>
            <el-button v-if="$hasPermission('ROOM_CREATE')" icon="el-icon-search"
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
        <el-table-column label="会议室" prop="name" width="120px"/>
        <el-table-column label="实景照片" prop="imageList" width="200px">
          <template slot-scope="scope">
            <el-carousel height="120px">
              <el-carousel-item v-for="item in scope.row.imageList" :key="item.imageId">
                <el-image :src="item.url"></el-image>
              </el-carousel-item>
            </el-carousel>
          </template>
        </el-table-column>
        <el-table-column label="备注" width="300px" prop="description" show-overflow-tooltip/>
        <el-table-column label="创建时间">
          <template slot-scope="{ row }">{{ row.createTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="{ row }">{{ row.updateTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="类型">
          <template slot-scope="{ row }">
            <el-tag type='success' disable-transitions>{{ row.type}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
                v-if="$hasPermission('ROOM_UPDATE')"
                size="small"
                type="text"
                @click="edit(scope.row)"
            >编辑
            </el-button>
            <el-button
                v-if="$hasPermission('ROOM_DELETE')"
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
import RoomDialog from '@/page/room/RoomDialog';
import Room from '@/api/Room';
import PagedTable from '@/components/Table';

export default {
  name: 'Room',
  components: {
    PagedTable
  },
  data() {
    return {
      filter: {
        name: '',
        type: '',
        pageNum: 1,
        pageSize: 20
      },
      options: [{
        value: '',
        label: '所有类型'
      },{
        value: '阶梯会议室',
        label: '阶梯会议室'
      }, {
        value: '普通会议室',
        label: '普通会议室'
      }, {
        value: '多媒体会议室',
        label: '多媒体会议室'
      }],
      total: 0,
      tableData: []
    };
  },
  created() {
    this.$dialog.register('room-dialog', RoomDialog);
    this.queryList();
  },
  methods: {
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await Room.query(params);
      this.total = data.count;
      this.tableData = data.rows;
    },
    async exportExcel() {
      this.filter.pageNum = 1;
      const params = Object.assign({}, this.filter);
      await Room.exportExcel(params);
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
      this.openDialog('room-dialog', props);
    },
    openDialog(name, props = {}, on = {'success': this.queryList}) {
      this.$dialog.open(name, {props, on});
    },
    // 新增操作
    create() {
      const props = {
        isEdit: false
      };
      this.openDialog('room-dialog', props);
    },

    async remove(id) {
      await this.$confirm('是否确认删除？', '删除', {
        type: 'warn'
      });
      try {
        await Room.remove(id);
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
