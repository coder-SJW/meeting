<template>
  <div class="sidebar">
    <el-menu
        :collapse="collapse"
        :default-active="onRoutes"
        active-text-color="#20a0ff"
        background-color="#324157"
        class="sidebar-el-menu"
        router
        text-color="#bfcbd9"
        unique-opened
    >
      <template v-for="item in items">
        <template v-if="item.subs && $hasPermission(item.permission)">
          <el-submenu :key="item.index" :index="item.index">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.title }}</span>
            </template>
            <template v-for="subItem in item.subs">
              <el-submenu
                  v-if="subItem.subs && $hasPermission(item.permission)"
                  :key="subItem.index"
                  :index="subItem.index"
              >
                <template slot="title">{{ subItem.title }}</template>
                <el-menu-item
                    v-for="(threeItem,i) in subItem.subs"
                    :key="i"
                    :index="threeItem.index"
                >{{ threeItem.title }}
                </el-menu-item>
              </el-submenu>
              <el-menu-item
                  v-else
                  :key="subItem.index"
                  :index="subItem.index"
              >{{ subItem.title }}
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item v-if="$hasPermission(item.permission)"
                        :key="item.index"
                        :index="item.index">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.title }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
import bus from '@/components/bus';

export default {
  data() {
    return {
      collapse: false,
      items: [
        {
          icon: 'el-icon-lx-home',
          index: 'dashboard',
          title: '系统首页',
          permission: 'DASHBOARD'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'myMeeting',
          title: '我的会议',
          permission: 'MY_MEETING'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'meeting',
          title: '会议管理',
          permission: 'MEETING'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'repair',
          title: '报修管理',
          permission: 'REPAIR'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'reserve',
          title: '预约记录管理',
          permission: 'RESERVE'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'room',
          title: '会议室管理',
          permission: 'ROOM'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'sysLog',
          title: '日志管理',
          permission: 'SYS_LOG'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'account',
          title: '用户管理',
          permission: 'ACCOUNT'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'department',
          title: '部门管理',
          permission: 'DEPARTMENT'
        },
        {
          icon: 'el-icon-lx-cascades',
          index: 'role',
          title: '角色管理',
          permission: 'ROLE'
        }
      ]
    };
  },
  computed: {
    onRoutes() {
      return this.$route.path.replace('/', '');
    }
  },
  created() {
    // 通过 Event Bus 进行组件间通信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg;
      bus.$emit('collapse-content', msg);
    });
  }
};
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}

.sidebar > ul {
  height: 100%;
}
</style>
