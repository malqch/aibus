/**
 * @Author: sleeber
 * @Filename: parameter.js
 * @ProjectName:
 * @Mail:
 * @Date: 2020-08-26 10:54
 * @FileDescription:
 */

import fetch from '@/util/fetch'
import { API_SERVER_URL } from '@/api'

//---------------------------------------故障类型模块-开始--------------------------------------

//获取所有数据
export function getInfoFaultTypeListAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/list`,
        method: 'GET',
        data: params
    })
}

//获取分页列表
export function getInfoFaultTypeList (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/page`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoFaultTypeDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/info/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoFaultType (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoFaultType (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoFaultType (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoFaultTypeAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/deleteBatch`,
        method: 'POST',
        data: params
    })
}
//---------------------------------------故障类型模块-结束--------------------------------------

//---------------------------------------故障级别模块-开始--------------------------------------

//获取所有数据
export function getInfoFaultLevelListAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultlevel/list`,
        method: 'GET',
        data: params
    })
}

//获取分页列表
export function getInfoFaultLevelList (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultlevel/page`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoFaultLevelDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultlevel/info/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoFaultLevel (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultlevel/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoFaultLevel (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultlevel/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoFaultLevel (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultlevel/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoFaultLevelAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultlevel/deleteBatch`,
        method: 'POST',
        data: params
    })
}
//---------------------------------------故障级别模块-结束--------------------------------------

//---------------------------------------故障标签模块-结束--------------------------------------
// 获取所有数据
export function getInfoFaultTargetListAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaulttarget/listMaster`,
        method: 'GET',
        data: params
    })
}

// 获取所有数据
export function getInfoFaultTargetListAllSub (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaulttarget/listSub`,
        method: 'GET',
        data: params
    })
}

export function getInfoFaultTargetPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaulttarget/page`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoFaultTargetDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaulttarget/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoFaultTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaulttarget/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoFaultTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaulttarget/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoFaultTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaulttarget/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoFaultTargetAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaulttarget/list/delete`,
        method: 'DELETE',
        data: params
    })
}

export function getInfoFaultTargetGroupS () {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/getListByParamGroup?paramGroup=fault_tag_group`,
        method: 'GET'
    })
}
//---------------------------------------故障标签模块-结束--------------------------------------

//---------------------------------------故障采集模块-结束--------------------------------------
export function getInfoFaultCollectPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infocollectfault/page`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoFaultCollectDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infocollectfault/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoFaultCollect (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infocollectfault/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoFaultCollect (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infocollectfault/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoFaultCollect (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infocollectfault/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoFaultCollectAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infocollectfault/list/delete`,
        method: 'DELETE',
        data: params
    })
}

//---------------------------------------故障采集模块-结束--------------------------------------

//---------------------------------------故障采集(扩展)模块-结束--------------------------------------
export function getInfoFaultExtendPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultextend/page`,
        method: 'GET',
        data: params
    })
}

export function getInfoFaultExtendList (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultextend/listByCollectEventId?collectFaultId=`+params,
        method: 'GET'
    })
}
//查单条
export function getInfoFaultExtendDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultextend/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoFaultExtend (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultextend/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoFaultExtend (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultextend/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoFaultExtend (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultextend/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoFaultExtendAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/infofaultextend/list/delete`,
        method: 'DELETE',
        data: params
    })
}

//---------------------------------------故障采集(扩展)模块-结束--------------------------------------

//---------------------------------------故障日志(附件)模块-结束--------------------------------------
export function getInfoFaultDetailPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/logfaultdetail/page`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoFaultAttachDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/fault/logfaultdetail/info/` + params,
        method: 'GET'
    })
}
//---------------------------------------故障日志(附件)模块-结束--------------------------------------



