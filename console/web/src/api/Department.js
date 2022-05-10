import fetch from '@/utils/fetch';

export function create(body) {
    return fetch.post('/api/department', body);
}

export function remove(id) {
    return fetch.remove('/api/department', id);
}

export function update(id, body) {
    return fetch.put('/api/department', id, body);
}

export function get(id) {
    return fetch.get('/api/department', id);
}

export function query(params) {
    return fetch.get('/api/department', null, params);
}

export function exportExcel(params) {
    return fetch.download('/api/department/exportExcel', params);
}


export default {
    create,
    remove,
    update,
    get,
    query,
    exportExcel
};