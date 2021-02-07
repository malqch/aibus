import fetch from '@/util/fetch'
import {API_SERVER_URL} from "./index";


//---------------------------------------广告标签-开始--------------------------------------
//获取分页列表


export function getInfoAdvertiseTargetPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertiseTag/page`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoAdvertiseTargetDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertiseTag/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoAdvertiseTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertiseTag/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoAdvertiseTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertiseTag/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoAdvertiseTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertiseTag/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoAdvertiseTargetAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertiseTag/list/delete`,
        method: 'DELETE',
        data: params
    })
}

//---------------------------------------广告标签-结束--------------------------------------

//---------------------------------------广告位-开始--------------------------------------
//获取分页列表

export function getInfoAdvertisePositionPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/advert/operation/advertisePosition/page`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoAdvertisePositionDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/advert/operation/advertisePosition/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoAdvertisePosition (params) {
    return fetch({
        url: `${API_SERVER_URL}/advert/operation/advertisePosition/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoAdvertisePosition (params) {
    return fetch({
        url: `${API_SERVER_URL}/advert/operation/advertisePosition/update`,
        method: 'PUT',
        data: params
    })
}

//---------------------------------------广告位-结束--------------------------------------
