import fetch from '@/utils/fetch';

export function create(body) {
    return fetch.post('/api/room', body);
}

export function remove(id) {
    return fetch.remove('/api/room', id);
}

export function update(id, body) {
    return fetch.put('/api/room', id, body);
}

export function get(id) {
    return fetch.get('/api/room', id);
}

export function query(params) {
    return fetch.get('/api/room', null, params);
}

export function resource(params) {
    return fetch.get('/api/room/resource', null, params);
}

export function exportExcel(params) {
    return fetch.download('/api/room/exportExcel', params);
}


export default {
    create,
    remove,
    update,
    get,
    query,
    resource,
    exportExcel
};
