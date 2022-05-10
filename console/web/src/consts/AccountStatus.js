export const ACCOUNT_STATUS = {
    ALL: {status: -1, desc: '全部'},
    ENABLE: {status: 0, desc: '开启'},
    DISABLE: {status: 1, desc: '禁用'}
};

export function getLabel(status) {
    return ACCOUNT_STATUS[status].desc;
}