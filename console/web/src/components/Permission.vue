<script>

function getVnodeFromSlot(slot) {
  if (!slot) return slot
  // vue v2.5： "Multiple root nodes returned from render function" error with functional component
  if (slot.length > 1) console.warn('Permission warn: slot 仅能是一个根节点')
  return slot[0]
}

function hasPermission(codes, authlist) {
  const any = Array.isArray(codes) ? codes : [codes]
  return any.some(code => authlist.includes(code))
}

/**
 * Permission 在使用的时候，保证 slot 内的元素仅包含一个根节点
 */
export default {
  name: 'Permission',
  props: {
    code: String,
    any: Array
  },
  computed: {
    authlist() {
      return this.$store.getters.authlist
    }
  },
  render() {
    const any = this.any ? this.any : [this.code]
    return hasPermission(any, this.authlist)
        ? getVnodeFromSlot(this.$slots.default)
        : getVnodeFromSlot(this.$slots.forbidden)
  },
  hasPermission
}
</script>
