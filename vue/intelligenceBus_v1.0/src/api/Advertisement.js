/*
 * @Author : SongWenXin
 * @Mail : songwenxin666@sina.com
 * @ProjectName : intelligenceBus_v1.0
 * @FileName : Advertisement
 * @Date :  2020-11-07 16:13
 */

import fetch from '@/util/fetch'
import { API_SERVER_URL } from '@/api'

/**
 * ================ 广告投放接口===================
 * */
//广告投放的tab标签
export function getDeliveryTab (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/delivery/tab`,
        method: 'GET',
        data: params
    })
}

//通过itemCode和itemGroup 查询古广告和审核卡片列表
export function getAdvertiseList (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/list`,
        method: 'GET',
        data: params
    })
}

//获取投放方式列表
export function getAdvertiseDeliveryType (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/deliveryType`,
        method: 'GET',
        data: params
    })
}

//获取线路
export function getgetCompanyLineByUser (params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/getCompanyLineByUser`,
        method: 'GET',
        data: params
    })
}

//获取详细站点
export function getListByUser (params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infolinestation/listByUser`,
        method: 'GET',
        data: params
    })
}

//获取投放受众人群类型
export function getAdvertiseTagAge (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertiseTag/age`,
        method: 'GET',
        data: params
    })
}

//获取投放受众人群类型
export function getAdvertiseTagGender (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertiseTag/gender`,
        method: 'GET',
        data: params
    })
}

//上传广告材料素材
export function setOperationAdvertiseSave (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/save`,
        method: 'POST',
        data: params
    })
}

//修改上传
export function setOperationAdvertiseUpdate (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/update`,
        method: 'POST',
        data: params
    })
}
//获取广告详细
export function getOperationAdvertiseInfo (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/info`,
        method: 'GET',
        data: params
    })
}

//获取广告详细
export function getOperationAdvertiseDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/detail`,
        method: 'GET',
        data: params
    })
}

//确认审核结果
export function getOperationAdvertiseConfirm (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/confirm`,
        method: 'GET',
        data: params
    })
}

//删除广告
export function getOperationAdvertiseDelete (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/delete`,
        method: 'GET',
        data: params
    })
}


/**
 * ================ 广告审核接口===================
 * */

//广告审核的tab标签
export function getAuditTab (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/audit/tab`,
        method: 'GET',
        data: params
    })
}

//提交审核结果
export function getAudit (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/audit`,
        method: 'POST',
        data: params
    })
}

//获取违规列表
export function getCheckItemList (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/checkItem/list`,
        method: 'GET',
        data: params
    })
}


/**
 * ================ 插播===================
 * */

//新增插播
export function setInterruptSave (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/interrupt/save`,
        method: 'POST',
        data: params
    })
}

//修改插播
export function setInterruptUpdate (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/interrupt/update`,
        method: 'POST',
        data: params
    })
}

//获取插播详细
export function getInterruptInfo (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/interrupt/info`,
        method: 'GET',
        data: params
    })
}

//获取插播tab
export function getInterruptTab (params) {
    return fetch({
        url: `${API_SERVER_URL}/operation/advertise/interrupt/tab`,
        method: 'GET',
        data: params
    })
}
