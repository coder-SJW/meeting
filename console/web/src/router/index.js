import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import('@/components/Home.vue'),
            meta: {title: '自述文件'},
            children: [
                {
                    path: '/dashboard',
                    component: () => import('@/page/Dashboard.vue'),
                    meta: {title: '系统首页'}
                },
                {
                    path: '/reserve-detail',
                    component: () => import('@/page/reserve/ReserveDetail.vue'),
                    meta: {title: '预约记录详情', permission: 'RESERVE_QUERY'}
                },
                {
                    path: '/reserve-create',
                    component: () => import('@/page/reserve/ReserveCreate.vue'),
                    meta: {title: '创建预约记录', permission: 'RESERVE_CREATE'}
                },
                {
                    path: '/meeting-detail',
                    component: () => import('@/page/meeting/MeetingDetail.vue'),
                    meta: {title: '会议详情', permission: 'MEETING_QUERY'}
                },
                {
                    path: '/meeting-create',
                    component: () => import('@/page/meeting/MeetingCreate.vue'),
                    meta: {title: '创建会议', permission: 'MEETING_CREATE'}
                },
                {
                    path: '/repair-detail',
                    component: () => import('@/page/repair/RepairDetail.vue'),
                    meta: {title: '报修详情', permission: 'REPAIR_QUERY'}
                },
                {
                    path: '/repair-create',
                    component: () => import('@/page/repair/RepairCreate.vue'),
                    meta: {title: '创建报修', permission: 'REPAIR_CREATE'}
                },
                {
                    path: '/meeting',
                    component: () => import('@/page/meeting/Meeting.vue'),
                    meta: {title: '会议管理', permission: 'MEETING'}
                },
                {
                    path: '/myMeeting',
                    component: () => import('@/page/meeting/MyMeeting.vue'),
                    meta: {title: '我的会议', permission: 'MY_MEETING'}
                },
                {
                    path: '/repair',
                    component: () => import('@/page/repair/Repair.vue'),
                    meta: {title: '报修管理', permission: 'REPAIR'}
                },
                {
                    path: '/reserve',
                    component: () => import('@/page/reserve/Reserve.vue'),
                    meta: {title: '预约记录管理', permission: 'RESERVE'}
                },
                {
                    path: '/room',
                    component: () => import('@/page/room/Room.vue'),
                    meta: {title: '会议室管理', permission: 'ROOM'}
                },
                {
                    path: '/setting',
                    component: () => import('@/page/Setting.vue'),
                    meta: {title: '个人信息', permission: 'ACCOUNT_PERSONAL'}
                },
                {
                    path: '/changePassword',
                    component: () => import('@/page/ChangePassword.vue'),
                    meta: {title: '修改密码', permission: 'ACCOUNT_CHANGE_PASSWORD'}
                },
                {
                    path: '/sysLog',
                    component: () => import('@/page/sysLog/SysLog.vue'),
                    meta: {title: '系统日志管理', permission: 'SYS_LOG'}
                },
                {
                    path: '/account',
                    component: () => import('@/page/account/Account.vue'),
                    meta: {title: '用户管理', permission: 'ACCOUNT'}
                },
                {
                    path: '/department',
                    component: () => import('@/page/department/Department.vue'),
                    meta: {title: '部门管理', permission: 'DEPARTMENT'}
                },
                {
                    path: '/role',
                    component: () => import('@/page/role/Role.vue'),
                    meta: {title: '角色管理', permission: 'ROLE'}
                },
                {
                    path: '/404',
                    component: () => import('@/page/404.vue'),
                    meta: {title: '404'}
                },
                {
                    path: '/403',
                    component: () => import('@/page/403.vue'),
                    meta: {title: '403'}
                }
            ]
        },
        {
            path: '/login',
            component: () => import('@/page/Login.vue'),
            meta: {title: '登录'}
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
