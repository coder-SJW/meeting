<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"/> 图片管理
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-form inline>
          <el-form-item>
            <el-button v-if="$hasPermission('IMAGE_QUERY')" icon="el-icon-search"
                       type="primary"
                       @click="queryList()">查询
            </el-button>
            <el-button v-if="$hasPermission('IMAGE_EXPORT')" icon="el-icon-search"
                       type="primary"
                       @click="exportExcel()">导出
            </el-button>
            <el-button v-if="$hasPermission('IMAGE_CREATE')" icon="el-icon-search"
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
        <el-table-column label="图片集" prop="images">
          <template slot-scope="scope">
            <el-carousel height="250px">
              <el-carousel-item v-for="item in scope.row.images" :key="item.imageId">
                <el-image :src="item.url"></el-image>
              </el-carousel-item>
            </el-carousel>
          </template>
        </el-table-column>
        <el-table-column label="图片名称" prop="name"/>
        <el-table-column label="创建时间">
          <template slot-scope="{ row }">{{ row.createTime | formatDate }}</template>
        </el-table-column>
        <el-table-column label="修改时间">
          <template slot-scope="{ row }">{{ row.updateTime | formatDate }}</template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="180">
          <template slot-scope="scope">
            <el-button
                v-if="$hasPermission('IMAGE_UPDATE')"
                size="small"
                type="text"
                @click="edit(scope.row)"
            >编辑
            </el-button>
            <el-button
                v-if="$hasPermission('IMAGE_DELETE')"
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
import ImageDialog from '@/page/image/ImageDialog';
import Image from '@/api/Image';
import PagedTable from '@/components/Table';

export default {
  name: 'Image',
  components: {
    PagedTable
  },
  data() {
    return {
      filter: {
        pageNum: 1,
        pageSize: 20
      },
      total: 0,
      tableData: []
    };
  },
  created() {
    this.$dialog.register('image-dialog', ImageDialog);
    this.queryList();
  },
  methods: {
    async queryList(pageNum = 1) {
      this.filter.pageNum = pageNum;
      const params = Object.assign({}, this.filter);
      const {data} = await Image.query(params);
      this.total = data.count;
      this.tableData = data.rows;
    },
    async exportExcel() {
      this.filter.pageNum = 1;
      const params = Object.assign({}, this.filter);
      await Image.exportExcel(params);
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
      this.openDialog('image-dialog', props);
    },
    openDialog(name, props = {}, on = {'success': this.queryList}) {
      this.$dialog.open(name, {props, on});
    },
    async remove(id) {
      await this.$confirm('是否确认删除？', '删除', {
        type: 'warn'
      });
      try {
        await Image.remove(id);
        this.$message.success('删除成功！');
        this.queryList();
      } catch (e) {
        this.$message.error('删除失败！原因：' + e.message);
      }
    },
    // 新增操作
    create() {
      const props = {
        isEdit: false
      };
      this.openDialog('image-dialog', props);
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