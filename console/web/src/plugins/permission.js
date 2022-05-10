import Permission from '../components/Permission';
import store from '@/store'

export default function install(Vue) {
    Vue.component(Permission.name, Permission)
    Vue.prototype.$hasPermission = function (codes) {
        return Permission.hasPermission(codes, store.getters.authlist)
    }
}
