<template>
  <af-dialog
      :on-confirm="submitForm"
      :title="isEdit ? '编辑会议室' : '创建会议室'"
      :visible.sync="visible"
      class="system-dialog"
      @close="$emit('close')">
    <el-form
        ref="roomForm"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="会议室"
          prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-form-item
          label="产品图"
          prop="imageList">
        <el-upload
            :before-upload="beforeUploadHandler"
            :file-list="form.imageList"
            :limit="3"
            :on-error="errorHandler"
            :on-exceed="exceedHandler"
            :on-remove="removeHandler"
            :on-success="uploadSuccessHandler"
            accept=".jpg,.jpeg,.png"
            action="/api/image/upload"
            drag
            list-type="picture-card">
          <i class="el-icon-plus"/>
        </el-upload>
      </el-form-item>
      <el-form-item
          label="备注"
          prop="description">
        <el-input v-model="form.description"
                  :rows="4"
                  style="width: 500px"
                  type="textarea"/>
      </el-form-item>
      <el-form-item
          label="会议室类型"
          prop="type">
        <el-select v-model="form.type" placeholder="请选择">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
  </af-dialog>
</template>
<script>
import AfDialog from '@/components/Dialog';
import Room from '@/api/Room';
import RoomRules from '@/rules/room/RoomRules'


export default {
  name: 'RoomDialog',
  components: {
    AfDialog
  },
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          name: '',
          imageList: [],
          roomImageIds: [],
          description: '',
          type: ''
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
      rules: RoomRules,
      form: this.defaultValues,
      visible: false,
      options: [{
        value: '阶梯会议室',
        label: '阶梯会议室'
      }, {
        value: '普通会议室',
        label: '普通会议室'
      }, {
        value: '多媒体会议室',
        label: '多媒体会议室'
      }],
    };
  },
  methods: {
    async submitForm() {
      try {
        await this.$refs.roomForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      try {
        let ids = [];
        this.form.imageList.map((item) => {
          ids.push(item.imageId)
        })
        this.form.roomImageIds = ids;
        let params = Object.assign({}, this.form);
        this.isEdit ? await Room.update(params.id, params) : await Room.create(params);
        this.$emit('success');
        this.$message.success(`${this.isEdit ? '编辑' : '创建'}成功！`);
      } catch (e) {
        this.$message.error(`${this.isEdit ? '编辑' : '创建'}失败！原因：` + e.message);
      }
    }
    ,
    beforeUploadHandler(image) {
      let isImage = /\.(jpeg|jpg|png)$/i.test(image.name)
      const sizeLimit = image.size <= 10 * 1024 * 1024
      if (!isImage) {
        this.$message.error('上传图片只能是jpg、png、jpeg格式!')
        return false
      }
      if (!sizeLimit) {
        this.$message.error('上传图片大小不能超过 10M!')
        return false
      }
    },
    exceedHandler() {
      this.$message.error('上传图片数量超过限制')
    },
    errorHandler(err) {
      err.message && this.$message.error(err.message)
    },
    uploadSuccessHandler(res, image) {
      if (res.code === 200) {
        image.imageId = res.data;
        this.form.imageList.push(image)
      } else {
        this.$message.error('上传失败')
      }
    },
    removeHandler(image) {
      const index = this.form.imageList.findIndex(item => item.imageId === image.imageId)
      index !== -1 && this.form.imageList.splice(index, 1)
    }
  }
};
</script>

<style lang="scss">
.system-dialog {
  .el-dialog {
    width: 900px;
  }
}

.el-form {
  .el-input {
    width: 250px;
  }
}

.el-form {
  .el-upload {
    height: 180px;
  }
}
</style>
