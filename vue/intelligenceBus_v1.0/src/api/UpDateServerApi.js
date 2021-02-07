/**
 * @Author: songwenxin
 * @Filename: UpDateServerApi.js
 * @ProjectName: fs-manage-view
 * @Mail: songwenxin666@sina.com
 * @Date: 2019-12-13 18:29
 * @FileDescription:
 */

import fetch from '@/util/fetch'
import { API_SERVER_URL } from '@/api'


//-------------------------------------------------------------------------------------------------------------------------

/**
 * 页面 ： 更新服务管理 - 更新包管理 - Api
 * */

//获取全部
export function getUpDateServer (params) {
    return fetch({
        url: `${API_SERVER_URL}/update/package/list`,
        method: 'GET',
        data: params
    })
}


//查单条
export function getUpDateServerDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/update/package/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function setUpDateServerSave(params) {
    return fetch({
        url: `${API_SERVER_URL}/update/package/save`,
        method: 'POST',
        data: params
    })
}


//删除
export function deleteUpDateServer(params) {
    return fetch({
        url: `${API_SERVER_URL}/update/package/delete/` + params,
        method: 'POST',
        data: params
    })
}

//编辑
export function setUpDateServerEdir (params) {
    return fetch({
        url: `${API_SERVER_URL}/update/package/update`,
        method: 'POST',
        // headers:{'Content-Type':'application/x-www-form-urlencoded'},
        data: params
    })
}

//更新类型下拉
export function setQueryUpdateTypeSelection (params) {
    return fetch({
        url: `${API_SERVER_URL}/update/package/queryUpdateType`,
        method: 'GET',
        data: params
    })
}

//删除所有
export function deleteUpDateServerAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/update/package/deleteBatch`,
        method: 'POST',
        data: params
    })
}
