import fetch from '@/util/fetch'
import { API_SERVER_URL } from '@/api'

//获取岗位里诶包
export function getStationList (params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/list`,
        method: 'GET',
        data: params
    })
}

// 删除岗位
export function deleteStationOne (params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/delete`,
        method: 'POST',
        data: params
    })
}

// 查询岗位详情
export function getStationDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/info`,
        method: 'GET',
        data: params
    })
}

// 查询岗位详情
export function getStationDetailCompanyByClique (params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/infoCompanyListByPer`,
        method: 'GET',
        data: params
    })
}

// 新增岗位
export function addStationSave (params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/save`,
        method: 'POST',
        data: params
    })
}

// 修改岗位
export function addStationUpdate (params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/position/update`,
        method: 'POST',
        data: params
    })
}
