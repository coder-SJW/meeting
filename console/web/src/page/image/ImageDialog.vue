<template>
  <af-dialog
      :on-confirm="submitForm"
      :title="isEdit ? '编辑图片' : '创建图片'"
      :visible.sync="visible"
      class="system-dialog"
      @close="$emit('close')">
    <el-form
        ref="imageForm"
        :model="form"
        :rules="rules"
        label-width="100px">
      <el-form-item
          label="图片名称"
          prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
      <el-upload
          :before-upload="beforeUploadHandler"
          :file-list="form.images"
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
    </el-form>
  </af-dialog>
</template>
<script>
import AfDialog from '@/components/Dialog';
import Image from '@/api/Image';
import ImageRules from '@/rules/image/ImageRules'


export default {
  name: 'ImageDialog',
  components: {
    AfDialog
  },
  props: {
    defaultValues: {
      type: Object,
      default() {
        return {
          data: '',
          name: '',
          images: [],
          imageIds: [],
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
      rules: ImageRules,
      form: this.defaultValues,
      visible: false,
    };
  },
  methods: {
    async submitForm() {
      try {
        await this.$refs.imageForm.validate();
      } catch (error) {
        return Promise.reject(new Error('表单校验失败'));
      }
      try {
        let ids = [];
        this.form.images.map((item) => {
          ids.push(item.imageId)
        })
        this.form.imageIds = ids;
        let params = Object.assign({}, this.form);
        this.isEdit ? await Image.update(params.id, params) : await Image.create(params);
        this.$emit('success');
        this.$message.success(`${this.isEdit ? '编辑' : '创建'}成功！`);
      } catch (e) {
        this.$message.error(`${this.isEdit ? '编辑' : '创建'}失败！原因：` + e.message);
      }
    },
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
        this.form.images.push(image)
      } else {
        this.$message.error('上传失败')
      }
    },
    removeHandler(image) {
      const index = this.form.images.findIndex(item => item.imageId === image.imageId)
      index !== -1 && this.form.images.splice(index, 1)
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
</style>