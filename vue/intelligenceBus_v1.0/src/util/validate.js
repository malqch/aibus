/**
 * 邮箱
 * @param {*} s
 */
export function isEmail (s) {
    var reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    return reg.test(s);
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (s) {
    return /^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
    return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
    return /^http[s]?:\/\/.*/.test(s)
}
/**
 * IP地址
 * @param {*} s
 */
export function isIP (s) {
    return /((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})(\.((2(5[0-5]|[0-4]\d))|[0-1]?\d{1,2})){3}/g.test(s)
}

/**
 * 正整数
 * @param {*} s
 */
export function isPositiveInteger (s) {
    return /(^[1-9]\d*$)/.test(s)
}



