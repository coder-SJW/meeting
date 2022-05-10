import fetch from '@/utils/fetch';

export function create(body) {
    return fetch.post('/api/meeting', body);
}

export function remove(id) {
    return fetch.remove('/api/meeting', id);
}

export function update(id, body) {
    return fetch.put('/api/meeting', id, body);
}

export function get(id) {
    return fetch.get('/api/meeting', id);
}

export function query(params) {
    return fetch.get('/api/meeting', null, params);
}

export function exportExcel(params) {
    return fetch.download('/api/meeting/exportExcel', params);
}

export function review(body) {
    return fetch.post('/api/meeting/review', body);
}

export default {
    create,
    remove,
    update,
    get,
    query,
    exportExcel,
    review
};
