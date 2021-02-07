import fetch from '@/util/fetch'
import { API_SERVER_URL } from '@/api'


// 用户列表
export function getUserList(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/list`,
        method: 'GET',
        data: params
    })
}

//用户删除
export function deleteUserByUserId(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/delete`,
        method: 'POST',
        data: params
    })
}

// 修改用户
export function addUserUpdate(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/update`,
        method: 'POST',
        data: params
    })
}

// 新增用户
export function addUserSave(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/save`,
        method: 'POST',
        data: params
    })
}


// 获取用户详细
export function getUserDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/detail`,
        method: 'GET',
        data: params
    })
}