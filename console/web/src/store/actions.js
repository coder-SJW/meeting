import Account from '@/api/Account'
import {UPDATE_USER_INFO} from './mutations'

export default {
    async login({commit}, payload) {
        const {data} = await Account.login(payload)
        commit(UPDATE_USER_INFO, data);
        return data
    },
    async logout({commit}) {
        await Account.logout();
        commit(UPDATE_USER_INFO, null)
    },
    async fetchUserInfo({commit, state}) {
        const {data} = await Account.info();
        commit(UPDATE_USER_INFO, data);
        return data
    }
}
