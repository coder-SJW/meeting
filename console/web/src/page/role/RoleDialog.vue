<template>
  <af-dialog
      :on-confirm="submitForm"
      :title="isEdit ? '编辑角色' : '创建角色'"
      :visible.sync="visible"
      class="system-role-dialog"
      @close="$emit('close')">
    <el-form
        ref="roleForm"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="名称"
          prop="name">
        <el-input
            v-model="form.name"
            :disabled="isEdit"
            placeholder="名称"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input
            v-model="form.description"
            type="textarea"/>
      </el-form-item>
      <el-form-item class="tree__item" label="菜单权限">
        <el-tree
            ref="authTree"
            :data="menu"
            :expand-on-click-node="false"
            :props="defaultProps"
            check-on-click-node
            class="auth__tree"
            default-expand-all
            node-key="id"
            show-checkbox>
        </el-tree>
      </el-form-item>
    </el-form>
  </af-dialog>
</template>
<script>
import AfDialog from '@/components/Dialog';
import Role from '@/api/Role';
import RoleRules from '@/rules/role/RoleRules'
import {AUTH_MENU, FILTER_AUTH} from '@/consts/Role';


export default {
  name: 'RoleDialog',
  components: {
    AfDialog
  },
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          name: ''
        };
      }
    },
    isEdit: {
      type: Boolean,
      dafault: () => false
    }
  },
  data() {
    return {
      rules: RoleRules,
      form: this.defaultValues,
      visible: false,
      menu: AUTH_MENU,
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    };
  },
  computed: {},
  async created() {
    if (this.form.id) {
      const result = await Role.get(this.form.id);
      this.$refs.authTree.setCheckedKeys(result.data.authorities.filter(item => !FILTER_AUTH.includes(item)));
      this.description = result.data.description;
      this.form.name = result.data.name;
    }
  },
  methods: {
    async submitForm() {
      try {
        await this.$refs.roleForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      try {
        let params = Object.assign({}, this.form);
        params.authorities = this.$refs.authTree.getCheckedKeys().concat(this.$refs.authTree.getHalfCheckedKeys()).filter(item => typeof (item) !== 'number');
        this.isEdit ? await Role.update(params.id, params) : await Role.create(params);
        this.$emit('success');
        this.$message.success(`${this.isEdit ? '编辑' : '创建'}成功！`);
      } catch (e) {
        this.$message.error(`${this.isEdit ? '编辑' : '创建'}失败！原因：` + e.message);
      }
    }
  }
};
</script>

<style lang="scss">
.auth__tree {
  height: 500px;
  overflow-y: auto;

  &.auth__tree__service {
    height: auto;
  }

  &::-webkit-scrollbar {
    display: block;
    width: 3px;
  }

  &::-webkit-scrollbar-thumb {
    background-color: #ddd
  }
}

:global(.tree__item .el-form-item__label) {
  float: none;
}

:global(.auth__tree .el-tree-node__content) {
  padding: 8px !important;
  width: 200px;
  float: left;
}

:global(.auth__tree .el-tree-node) {
  overflow: hidden;
}

:global(.auth__tree > .el-tree-node>.el-tree-node__content) {
  position: absolute;
  top: -42px;
  left: 80px;
}

:global(.auth__tree .el-tree-node__content > .el-tree-node__expand-icon) {
  display: none;
}

:global(.auth__tree .el-tree-node__content:hover) {
  background-color: transparent;
}

:global(.auth__tree .el-tree-node:focus >.el-tree-node__content) {
  background-color: transparent;
}

// :global(.auth__tree>.el-tree-node>.el-tree-node__children>.el-tree-node .el-tree-node) {
//   float: left;
// }

:global(.auth__tree>.el-tree-node>.el-tree-node__children>.el-tree-node>.el-tree-node__children>.el-tree-node>.el-tree-node__children .el-tree-node) {
  float: left;
}

:global(.auth__tree.auth__tree__service>.el-tree-node>.el-tree-node__children>.el-tree-node .el-tree-node) {
  float: left;
}

.system-Role-dialog {
  .el-dialog {
    width: 600px;
  }
}

:global(.tree__item .el-form-item__label) {
  float: none;
}

.roleDesc-tip {
  color: #999;
  margin-left: 10px;
}
</style>
