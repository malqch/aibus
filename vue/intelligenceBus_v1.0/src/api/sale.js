import fetch from '@/util/fetch'
import { API_SERVER_URL } from '@/api'



//获取百分比
export function getPercent(number1,number2) {
    return Math.round(number1/number2*100)
}

//时间戳转时间格式
export function formatDate(date) {
    var date = new Date(date);
    var YY = date.getFullYear() + '-';
    var MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
    var hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var ss = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
    return YY + MM + DD +" "+hh + mm + ss;
}

/******************销售概览********************/

//获取初始化省、市、公司
export function getInitData() {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/user/companies/valid`,
        method: 'GET'
    })
}

//获取初始化省、市、公司(综合采集平台)
export function getInitDataByEventType(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/initInfoByBusStation?eventTargetCode=`+params,
        method: 'GET'
    })
}

//获取省市
export function getAreaList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoarea/listByUserAndParentAreaId?parentAreaId=`+params,
        method: 'GET'
    })
}
//获取客户数量，汽车交付总量，当前地标信息
export function getCompanyStat(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/companyStat?areaId=`+params,
        method: 'GET'
    })
}
//根据地区获取客户信息列表
export function getCompanyListData(params){
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/companyAndStatList?areaId=`+params,
        method: 'GET'
    })
}
//客户车辆交付量统计
export function getDeliveryStatData(params){
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/busType/deliveryStat?areaId=`+params,
        method: 'GET'
    })
}
//客户车辆出保量统计

export function getOutDateStatByAreaIdData(params){
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/busType/outDateStat?areaId=`+params,
        method: 'GET'
    })
}
//获取指定市的告警信息统计
export function getEventStatData(params){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/eventStat?areaId=`+params,
        method: 'GET'
    })
}

/****************销售客户详情***************/
//横条 客户基本信息
export function getInfobuscompanyData(companyId){
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/info/${companyId}`,
        method: 'GET'
    })
}


//雷达图
export function getRadarData(companyId){
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/company/radar?companyId=${companyId}`,
        method: 'GET'
    })
}
//车辆统计分析
export function getBusStatusCompanyStat(companyId){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/stat/status?companyId=${companyId}`,
        method: 'GET'
    })
}
//故障级别统计分析
export function getFaultLevelCompanyStat(companyId){
    return fetch({
        url: `${API_SERVER_URL}/fault/faultLevel/company/stat?companyId=${companyId}`,
        method: 'GET'
    })
}
//车辆订单及交付统计
export function getDeliveryCompanyStatData(companyId){
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/busType/delivery/stat?companyId=${companyId}`,
        method: 'GET'
    })
}
//客户车辆出保统计
export function getOutDateCompanyStatData(companyId){
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/busType/outDate/stat?companyId=${companyId}`,
        method: 'GET'
    })
}


/*******************故障分析*******************/

//获取配置历程段数据
export function getConfigBusMilesData(){
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/config/busMiles`,
        method: 'GET'
    })
}

//故障排行统计
export function getBusStatByDateAndMileData(searchDate,busMile){
    return fetch({
        url: `${API_SERVER_URL}/fault/type/busStat?searchDate=${searchDate}&busMile=${busMile}`,
        method: 'GET'
    })
}

//查询车型列表
export function getInfobustypeAllListData(){
    return fetch({
        url: `${API_SERVER_URL}/service/infobustype/allList`,
        method: 'GET'
    })
}

//车型故障统计
export function getFaultStatByDateAndTypeIdData(searchDate,busTypeId){
    return fetch({
        url: `${API_SERVER_URL}/fault/busType/stat?searchDate=${searchDate}&busTypeId=${busTypeId}`,
        method: 'GET'
    })
}
//客户车辆故障统计 按客户分类，统计每个客户售卖数量、故障数量。

export function getFaultBusTypeByDateData(searchDate){
    return fetch({
        url: `${API_SERVER_URL}/fault/busType/company/stat?searchDate=${searchDate}`,
        method: 'GET'
    })
}
//客户车辆故障统计
//环境（冷热），人员（驾驶习惯）、天气（大雨等），制造（主要部件批次），维护（保养周期），当前阶段暂时实现温度因素影响。
export function getFaultTemperatureStatData(searchDate){
    return fetch({
        url: `${API_SERVER_URL}/fault/busType/company/temperatureStat?searchDate=${searchDate}`,
        method: 'GET'
    })
}


/*****************售后维修*****************/
//公交公司下拉框
export function getCustomerCompanyListData(areaId){
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/companyList?areaId=${areaId}`,
        method: 'GET'
    })
}

//售后车辆分析 - 故障 知识库文本
export function getKnowledgeFaultStatData(){
    return fetch({
        url: `${API_SERVER_URL}/service/knowledge/fault/stat`,
        method: 'GET'
    })
}
//售后车辆分析 - 故障
export function getFaultTypeStatByCompanyIdData(companyId){
    return fetch({
        url: `${API_SERVER_URL}/fault/faultType/company/stat?companyId=${companyId}`,
        method: 'GET'
    })
}
//维修车辆清单查看

export function getMaintainByCompanyIdData(companyId,startTime,endTime,status){
    return fetch({
        url: `${API_SERVER_URL}/maintenance/status/${status}/buses/?startTime=${startTime}&endTime=${endTime}&companyId=${companyId}`,
        method: 'GET'
    })
}
//
//售后车辆数据展示 - 故障 车辆状态下拉框
export function getBusStatusListData(){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/status/list`,
        method: 'GET'
    })
}
//售后车辆数据展示 - 故障 - 获取VIN列表
export function getBusVinListData(busStatus,companyId){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/vin/list?busStatus=${busStatus}&companyId=${companyId}`,
        method: 'GET'
    })
}
//售后车辆数据展示 - 故障
export function getFaultBusesPageData(params){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/status/page`,
        method: 'GET',
        data: params
    })
}
//售后车辆数据展示 - 故障 车辆基础信息查看
export function getBaseInfoByBusIdData(busId){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/${busId}/base/info`,
        method: 'GET'
    })
}
//售后车辆数据展示 - 故障 修改维修信息
export function getMaintainUpdateData(params){
    return fetch({
        url: `${API_SERVER_URL}/maintenance/update`,
        method: 'PUT',
        data:params
    })
}
//售后车辆数据展示 - 修改车辆状态
export function changeBusStatusByBusId(busId,busStatusId){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/${busId}/status/${busStatusId}`,
        method: 'PUT'
    })
}
//售后车辆数据展示 - 故障 维修
export function maintainDesc(busId,busStatusId,params){
    return fetch({
        url: `${API_SERVER_URL}/maintenance//bus/${busId}/status/${busStatusId}`,
        method: 'PUT',
        data:params
    })
}
//售后车辆数据展示 - 故障 车辆维修信息查看
export function getMaintainInfoByBusIdData(busId){
    return fetch({
        url: `${API_SERVER_URL}/service/bus/${busId}/maintain/info`,
        method: 'GET'
    })
}



/*************************交通违章***************************/

//公交车采集交通违规数统计
export function getTrafficDimStatData(dim,companyId){
    return fetch({
        url: `${API_SERVER_URL}/event/traffic/dim/${dim}/stat?companyId=${companyId}`,
        method: 'GET'
    })
}
//采集交通违章类型统计
export function getTrafficTypeStatData(companyId){
    return fetch({
        url: `${API_SERVER_URL}/event/traffic/type/stat?companyId=${companyId}`,
        method: 'GET'
    })
}
//交通违规列表搜索
export function getTrafficStatByCompanyData(params){
    return fetch({
        url: `${API_SERVER_URL}/event/traffic/statByCompany/page`,
        method: 'GET',
        data:params
    })
}
//导出（选择开始-结束时间）
export function getTrafficExportData(companyId,startTime,endTime){

    return fetch({
        url: `${API_SERVER_URL}/event/traffic/statByCompany/export?companyId=${companyId}&startTime=${startTime}&endTime=${endTime}`,
        method: 'GET',
        responseType: 'blob'
    })
}

/****************公共卫生*****************/
//查询公交公司下公交线路
export function getLineListByCompanyIdData(companyId){
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/lineListByCompanyId?companyId=${companyId}`,
        method: 'GET'
    })
}
//分页查询公共卫生统计信息
export function getHealthStatByCompanyAndLineData(params){
    return fetch({
        url: `${API_SERVER_URL}/event/health/temperature/statByCompanyAndLine`,
        method: 'GET',
        data:params
    })
}
//查看体温抓拍照片
export function getTemperatureHealthDetailData(eventDetailId){
    return fetch({
        url: `${API_SERVER_URL}/event/health/temperature/healthDetail?eventDetailId=${eventDetailId}`,
        method: 'GET'
    })
}
