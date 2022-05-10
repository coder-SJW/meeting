import fetch from '@/utils/fetch';

export function dataGrid() {
    return fetch.get('/api/statistics/dataGrid');
}

export function accountGrid() {
    return fetch.get('/api/statistics/accountGrid');
}

export default {
    dataGrid,
    accountGrid
};