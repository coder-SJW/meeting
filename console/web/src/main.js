import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from '@/store';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import {messages} from './components/i18n';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
// import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './components/directives';
import 'babel-polyfill';
import DialogManager from '@/plugins/dialogManager';
import Permission from '@/plugins/permission';
import account from '@/api/Account';
import '@/filters'

Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(DialogManager);
Vue.use(Permission);
Vue.use(ElementUI, {
    size: 'small'
});
const i18n = new VueI18n({
    locale: 'zh',
    messages
});

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title}`;
    const logined = sessionStorage.getItem('passport');
    if (!logined && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permission) {
        // 权限校验
        store.getters.authlist.includes(to.meta.permission) ? next() : next('/403');
    } else {
        next();
    }
});

async function createApp() {
    try {
        // F5刷新页面时会清空store中的数据，此时从sessionStorage查询是否已经存在登录信息
        if (sessionStorage.getItem('passport')) {
            const accountInfo = await account.getCurrentAccount();
            store.commit('UPDATE_USER_INFO', accountInfo.data);
        }
        new Vue({
            router,
            store: store,
            i18n,
            render: h => h(App)
        }).$mount('#app');
    } catch (error) {
    }
}

createApp();
