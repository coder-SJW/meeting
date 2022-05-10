import fetch from '@/utils/fetch';

export function create(body) {
    return fetch.post('/api/reserve', body);
}

export function remove(id) {
    return fetch.remove('/api/reserve', id);
}

export function update(id, body) {
    return fetch.put('/api/reserve', id, body);
}

export function get(id) {
    return fetch.get('/api/reserve', id);
}

export function query(params) {
    return fetch.get('/api/reserve', null, params);
}

export function events(params) {
    return fetch.get('/api/reserve/events', null, params);
}

export function myEvents(params) {
    return fetch.get('/api/reserve/myEvents', null, params);
}

export function exportExcel(params) {
    return fetch.download('/api/reserve/exportExcel', params);
}

export function loginQrCode(body) {
    return fetch.post('/api/qr/generate', body);
}


export default {
    create,
    remove,
    update,
    get,
    query,
    events,
    myEvents,
    exportExcel,
    loginQrCode
};
