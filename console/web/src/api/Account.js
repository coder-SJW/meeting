import fetch from '../utils/fetch';

export function login(params) {
    return fetch.get('/api/account/login', null, params);
}

export function logout() {
    return fetch.post('/api/account/logout');
}

export function get(id) {
    return fetch.get('/api/account', id);
}

export function getCurrentAccount() {
    return fetch.get('/api/account/current');
}

export function create(body) {
    return fetch.post('/api/account', body);
}

export function register(body) {
    return fetch.post('/api/account/register', body);
}

export function remove(id) {
    return fetch.remove('/api/account', id);
}

export function update(id, body) {
    return fetch.put('/api/account', id, body);
}

export function updateInfo(body) {
    return fetch.post('/api/account/updateInfo', body);
}

export function changePassword(body) {
    return fetch.post('/api/account/changePassword', body);
}

export function query(params) {
    return fetch.get('/api/account', null, params);
}


export default {
    login,
    logout,
    get,
    create,
    remove,
    update,
    getCurrentAccount,
    query,
    changePassword,
    register,
    updateInfo
};
