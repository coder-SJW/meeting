export const UPDATE_USER_INFO = 'UPDATE_USER_INFO'
export const UPDATE_MENU_STATUS = 'UPDAYE_MENU_STATUS'

export default {
    [UPDATE_USER_INFO](state, userInfo = {}) {
        state.userInfo = userInfo
    },
    [UPDATE_MENU_STATUS](state, status) {
        state.isCollapse = !!status
    }
}
