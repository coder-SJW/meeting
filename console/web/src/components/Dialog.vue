<template>
  <el-dialog
      v-bind="$attrs"
      v-on="listeners"
      :close-on-click-modal="false"
      :modal-append-to-body="false"
      :visible="elDialogVisible"
      class="dialog"
      @close="onClose"
      @update:visible="toggleVisible">
    <template
        v-if="!$attrs.title"
        slot="title">
      <slot name="title"/>
    </template>
    <slot/>
    <template v-if="!noFooter" slot="footer">
      <slot
          v-if="$slots.footer"
          :closeDialog="() => toggleVisible(false)"
          name="footer"/>
      <template v-else>
        <el-button
            v-if="cancelBtn"
            @click="toggleVisible(false)">
          取消
        </el-button>
        <el-button
            :disabled="disabledBtn"
            :loading="pending"
            type="primary"
            @click="confirm">
          {{ confirmText }}
        </el-button>
      </template>
    </template>
  </el-dialog>
</template>

<script>
import {getAnimationEndEvent, getTransitionEndEvent} from '@/utils/cssAnimationEvent.js'

export default {
  name: 'VDialog',
  inheritAttrs: false,
  props: {
    visible: {
      type: Boolean,
      default: true
    },
    onConfirm: Function,
    cancelBtn: {
      type: Boolean,
      default: true
    },
    disabledBtn: {
      type: Boolean,
      dafault: false
    },
    noFooter: {
      type: Boolean,
      default: false
    },
    confirmText: {
      type: String,
      default: '确定'
    }
  },
  data() {
    return {
      elDialogVisible: false,
      pending: false
    }
  },
  computed: {
    listeners() {
      let listeners = {}
      for (let key in this.$listeners) {
        if (['update:visible', 'close', 'confirm'].indexOf(key) === -1) {
          listeners[key] = this.$listeners[key]
        }
      }
      return listeners
    }
  },
  watch: {
    visible: {
      handler(val) {
        if (val !== this.elDialogVisible) {
          this.elDialogVisible = val
        }
      },
      immediate: true
    }
  },
  methods: {
    toggleVisible(visible) {
      this.elDialogVisible = visible
      this.$emit('update:visible', visible)
    },
    onClose() {
      let transitionEndEvent = getTransitionEndEvent()
      let animationEndEvent = getAnimationEndEvent()

      if (transitionEndEvent && animationEndEvent) {
        let handler = () => {
          this.$emit('close')
          this.$el.removeEventListener(transitionEndEvent, handler)
          this.$el.removeEventListener(animationEndEvent, handler)
        }
        this.$el.addEventListener(transitionEndEvent, handler)
        this.$el.addEventListener(animationEndEvent, handler)
      } else {
        this.$nextFrame(() => this.$emit('close'))
      }
    },
    confirm() {
      const done = (err) => {
        this.pending = false
        !(err instanceof Error) && this.toggleVisible(false)
      }
      this.pending = true
      if (this.onConfirm) {
        this.onConfirm()
            .then(done, done)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dialog {
  .el-dialog {
    display: inline-block;
    left: 50%;
    transform: translateX(-50%);
    width: auto;
  }

  .el-input,
  .el-textarea {
    width: 360px;
  }
}
</style>
