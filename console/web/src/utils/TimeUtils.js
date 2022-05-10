import dayjs from 'dayjs'

export function formatDate(value, format = 'YYYY-MM-DD HH:mm:ss', placeholder = '-') {
    if (!value && value !== 0) return placeholder
    return dayjs(+value).format(format)
}