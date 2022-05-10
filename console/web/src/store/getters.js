export default {
    logined: state => {
        return state.userInfo && !state.userInfo.needLogin
    },
    username: state => {
        return state.userInfo ? state.userInfo.username : 'unkown'
    },
    authlist: state => {
        return state.userInfo ? state.userInfo.authorities : []
    }
}
