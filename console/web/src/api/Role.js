import fetch from '../utils/fetch';

export function create(body) {
    return fetch.post('/api/role', body);
}

export function remove(id) {
    return fetch.remove('/api/role', id);
}

export function update(id, body) {
    return fetch.put('/api/role', id, body);
}

export function get(id) {
    return fetch.get('/api/role', id);
}

export function query(params) {
    return fetch.get('/api/role', null, params);
}


export default {
    create,
    remove,
    update,
    get,
    query
};