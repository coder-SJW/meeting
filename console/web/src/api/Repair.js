import fetch from '@/utils/fetch';

export function create(body) {
    return fetch.post('/api/repair', body);
}

export function remove(id) {
    return fetch.remove('/api/repair', id);
}

export function update(id, body) {
    return fetch.put('/api/repair', id, body);
}

export function get(id) {
    return fetch.get('/api/repair', id);
}

export function query(params) {
    return fetch.get('/api/repair', null, params);
}

export function exportExcel(params) {
    return fetch.download('/api/repair/exportExcel', params);
}


export default {
    create,
    remove,
    update,
    get,
    query,
    exportExcel
};