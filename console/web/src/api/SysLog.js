import fetch from '@/utils/fetch';

export function create(body) {
    return fetch.post('/api/sysLog', body);
}

export function remove(id) {
    return fetch.remove('/api/sysLog', id);
}

export function update(id, body) {
    return fetch.put('/api/sysLog', id, body);
}

export function get(id) {
    return fetch.get('/api/sysLog', id);
}

export function query(params) {
    return fetch.get('/api/sysLog', null, params);
}


export default {
    create,
    remove,
    update,
    get,
    query
};