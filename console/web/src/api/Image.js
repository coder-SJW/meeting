import fetch from '@/utils/fetch';

export function create(body) {
    return fetch.post('/api/image', body);
}

export function remove(id) {
    return fetch.remove('/api/image', id);
}

export function update(id, body) {
    return fetch.put('/api/image', id, body);
}

export function get(id) {
    return fetch.get('/api/image', id);
}

export function query(params) {
    return fetch.get('/api/image', null, params);
}

export function exportExcel(params) {
    return fetch.download('/api/image/exportExcel', params);
}


export default {
    create,
    remove,
    update,
    get,
    query,
    exportExcel
};