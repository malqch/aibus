/**
 * @Author: songwenxin
 * @Filename: selectionApi.js
 * @ProjectName: fs-manage-view
 * @Mail: songwenxin666@sina.com
 * @Date: 2019-12-16 10:57
 * @FileDescription:
 */


import fetch from '@/util/fetch'
import { API_SERVER_URL } from '@/api'

// 设备类型下拉菜单数据
export function getDeviceTypeS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infodevicetype/getComboBox`,
        method: 'GET',
        data: params
    })
}

// 电池类型下拉菜单数据
export function getBatteryTypeS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobatterytype/getComboBox`,
        method: 'GET',
        data: params
    })
}

// 电机类型下拉菜单数据
export function getMotorTypeTypeS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infomotortype/getComboBox`,
        method: 'GET',
        data: params
    })
}

// 公交车下拉菜单数据
export function getBusS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/getComboBox`,
        method: 'GET',
        data: params
    })
}

// 公交车下拉菜单数据
export function getBusSNoDelivery (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/getComboBoxNoDelivery`,
        method: 'GET',
        data: params
    })
}


// 公交公司下拉菜单数据
export function getBusCompanyS (params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/selection`,
        method: 'GET',
        data: params
    })
}

// 公交车厂下拉菜单数据
export function getBusFactoryS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoBusFactory/getComboBox`,
        method: 'GET',
        data: params
    })
}

// 公交车型下拉菜单数据
export function getBusTypeS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobustype/allList`,
        method: 'GET',
        data: params
    })
}

// 公交公司订单下拉菜单数据
export function getBusCompanyOrderS (params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompany/getByOrderStatus`,
        method: 'GET',
        data: params
    })
}

// 交付订单下拉菜单数据
export function getDeliveryOrderS (params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/ordercompanydelivery/allList`,
        method: 'GET',
        data: params
    })
}

// 销售员下拉菜单数据
export function getSellOrderS (params) {
    return fetch({
        url: `${API_SERVER_URL}/sys/user/getByOrderSell`,
        method: 'GET',
        data: params
    })
}

// 公交车站下拉菜单数据
export function getAllBusStationS (params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/listAllByCompanyId`,
        method: 'GET',
        data: params
    })
}

// 公交车站下拉菜单通过线路数据
export function getBusStationByLineS (params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/companyLine/`+params,
        method: 'GET'
    })
}

// 公交车状态下拉菜单数据
export function getBusStatusS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/getListByParamGroup?paramGroup=bus_status`,
        method: 'GET',
        data: params
    })
}

// 公交车电机状态下拉菜单数据
export function getBusMotorStatusS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/getListByParamGroup?paramGroup=motor_status`,
        method: 'GET',
        data: params
    })
}

// 公交车电池状态下拉菜单数据
export function getBusBatteryStatusS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/getListByParamGroup?paramGroup=battery_status`,
        method: 'GET',
        data: params
    })
}

// 运行状态下拉菜单数据
export function getRunStatusS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/getListByParamGroup?paramGroup=car_status`,
        method: 'GET',
        data: params
    })
}

// 线路类型下拉菜单数据
export function getLineTypeS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/getListByParamGroup?paramGroup=line_type`,
        method: 'GET',
        data: params
    })
}

// 线路方向下拉菜单数据
export function getLineDirectionS (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/getListByParamGroup?paramGroup=line_direction`,
        method: 'GET',
        data: params
    })
}

// 事件级别下拉菜单数据
export function getEventLeveS (params) {
    return fetch({
        url: `${API_SERVER_URL}/selection/eventLevel`,
        method: 'GET',
        data: params
    })
}


// 事件类型下拉菜单数据
export function getEventTypeS (params) {
    return fetch({
        url: `${API_SERVER_URL}/selection/eventType`,
        method: 'GET',
        data: params
    })
}

// 区域下拉菜单
export function getRegionalS(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoarea/getComboBox`,
        method: 'GET',
        data: params
    })
}

// 事件类型树形菜单数据
export function getTreeEventTypeS(params) {
    return fetch({
        url: `${API_SERVER_URL}/info/eventType/getComboBox`,
        method: 'GET',
        data: params
    })
}


// 事件分组下拉
export function getEventGroupS(params) {
    return fetch({
        url: `${API_SERVER_URL}/rule/statistics/getComboBox`,
        method: 'GET',
        data: params
    })
}

// 公司下VIN码下拉菜单数据
export function getVinCodeListByCompany (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/vin/listByCompanyId`,
        method: 'GET',
        data: params
    })
}

// 公司下车牌号下拉菜单数据
export function getPlateCodeListByCompany (params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/plateCode/listByCompanyId`,
        method: 'GET',
        data: params
    })
}
