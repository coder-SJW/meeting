<template>
  <div class="paged-table">
    <el-table
        v-bind="$attrs"
        v-on="tableListeners">
      <slot/>
    </el-table>
    <div
        v-show="total"
        class="paged-pagination">
      <el-pagination
          :current-page="currentPage"
          :layout="pagerLayout"
          :page-size="pageSize"
          :page-sizes="[10, 20, 50, 100, 200]"
          :total="total"
          background
          style="display: inline-block"
          @current-change="currentPageChange"
          @size-change="sizeChange">
        <slot name="pagination"/>
      </el-pagination>
    </div>
  </div>
</template>

<script>
import omit from 'lodash/omit'

export default {
  name: 'PagedTable',
  inheritAttrs: false,
  props: {
    pageSize: {
      type: Number,
      default: 10
    },
    total: Number,
    currentPage: Number,
    enablePageSize: {
      type: Boolean,
      default: false
    },
    layout: {
      type: String,
      default: ''
    }
  },
  computed: {
    tableListeners() {
      return omit(this.$listeners, ['current-change', 'size-change'])
    },
    pagerLayout() {
      const layout = this.enablePageSize ? 'total, sizes, prev, pager, next' : 'total, prev, pager, next'
      return this.layout || layout
    }
  },
  methods: {
    currentPageChange(currentPage) {
      this.$emit('current-change', currentPage)
    },
    sizeChange(size) {
      this.$emit('size-change', size)
    }
  }
}
</script>

<style lang="scss" scoped>
.paged-table {
  background-color: #fff;
  padding: 12px 0 22px;
  min-height: 50vh;
}

.paged-pagination {
  padding-top: 10px;
  text-align: right;
}
</style>
