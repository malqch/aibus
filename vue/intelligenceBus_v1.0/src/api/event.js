import fetch from '@/util/fetch'
import {API_SERVER_URL} from "./index";

//---------------------------------------事件级别-开始--------------------------------------
//获取分页列表


export function getInfoEventLevelPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-level/page`,
        method: 'GET',
        data: params
    })
}

//获取列表
export function getInfoEventLevelList () {
    return fetch({
        url: `${API_SERVER_URL}/event/event-level/list`,
        method: 'GET'
    })
}

//查单条
export function getInfoEventLevelDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-level/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoEventLevel (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-level/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoEventLevel (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-level/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoEventLevel (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-level/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoEventLevelAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-level/list/delete`,
        method: 'DELETE',
        data: params
    })
}
//---------------------------------------事件级别-结束--------------------------------------

//---------------------------------------事件类型-开始--------------------------------------
//获取分页列表


export function getInfoEventTypePage (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-type/page`,
        method: 'GET',
        data: params
    })
}

//获取列表
export function getInfoEventTypeList () {
    return fetch({
        url: `${API_SERVER_URL}/event/event-type/list`,
        method: 'GET'
    })
}

//查单条
export function getInfoEventTypeDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-type/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoEventType (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-type/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoEventType (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-type/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoEventType (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-type/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoEventTypeAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-type/list/delete`,
        method: 'DELETE',
        data: params
    })
}
//---------------------------------------事件类型-结束--------------------------------------

//---------------------------------------事件标签-开始--------------------------------------
//获取分页列表


export function getInfoEventTargetPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-tag/page`,
        method: 'GET',
        data: params
    })
}

//获取列表
export function getInfoEventTargetList (eventTagGroup) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-tag/list?targetGrope=${eventTagGroup}`,
        method: 'GET'
    })
}
//获取子标签列表
export function getSubInfoEventTargetList (eventTagGroup) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-tag/sub/list?targetGrope=${eventTagGroup}`,
        method: 'GET'
    })
}

//查单条
export function getInfoEventTargetDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-tag/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoEventTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-tag/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoEventTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-tag/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoEventTarget (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-tag/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoEventTargetAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-tag/list/delete`,
        method: 'DELETE',
        data: params
    })
}

export function getInfoEventTargetGroupS () {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/getListByParamGroup?paramGroup=event_tag_group`,
        method: 'GET'
    })
}
//---------------------------------------事件标签-结束--------------------------------------

//---------------------------------------事件描述-开始--------------------------------------

//查单条
export function getInfoEventDescriptionDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-desc/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoEventDescription (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-desc/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoEventDescription (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-desc/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoEventDescription (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-desc/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoEventDescriptionAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-desc/list/delete`,
        method: 'DELETE',
        data: params
    })
}

//---------------------------------------事件描述-结束--------------------------------------

//---------------------------------------事件采集-开始--------------------------------------

//获取分页列表
export function getInfoCollectEventPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-collect/page`,
        method: 'GET',
        data: params
    })
}

//获取列表
export function getInfoCollectEventList (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-collect/list`,
        method: 'GET',
        data: params
    })
}

//查详细单条
export function getInfoCollectEventWithExtendDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-collect/detail/` + params,
        method: 'GET'
    })
}

//查单条
export function getInfoCollectEventDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-collect/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoCollectEvent (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-collect/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoCollectEvent (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-collect/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoCollectEvent (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-collect/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoCollectEventAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-collect/list/delete`,
        method: 'DELETE',
        data: params
    })
}

//---------------------------------------事件采集-结束--------------------------------------

//---------------------------------------事件扩展-开始--------------------------------------

//获取列表
export function getInfoEventExtendList (collectEventId) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-extend/list?collectEventId=${collectEventId}`,
        method: 'GET'
    })
}

//查单条
export function getInfoEventExtendDetail (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-extend/info/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoEventExtend (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-extend/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoEventExtend (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-extend/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deleteInfoEventExtend (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-extend/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deleteInfoEventExtendAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/event-extend/list/delete`,
        method: 'DELETE',
        data: params
    })
}

//---------------------------------------事件扩展-结束--------------------------------------
/***********************事件日志---开始********************/
//获取列表
export function getEventLogDetailByPage (params) {
    return fetch({
        url: `${API_SERVER_URL}/event/log-detail/page`,
        method: 'GET',
        data: params
    })
}
//获取VIN列表
export function getBusVinListData(params){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/vin/list`,
        method: 'GET',
        data: params
    })
}
//获取卡片
export function getEventLogDetailByEventDetailId(params){
    return fetch({
        url: `${API_SERVER_URL}/event/log-detail/detail/${params}`,
        method: 'GET',
    })
}
/***********************事件日志---结束********************/
