/**
 * @Author: sleeber
 * @Filename: parameter.js
 * @ProjectName:
 * @Mail:
 * @Date: 2020-08-26 10:54
 * @FileDescription:
 */

import fetch from '@/util/fetch'
import {API_SERVER_URL} from '@/api'

//-------------------------------------------学校接口开始------------------------------------------------------------------------------
export function getSchoolList(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/school/list`,
        method: 'POST',
        data: params
    })
}

export function getSchoolDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/school/info/` + params,
        method: 'GET',
    })
}

export function saveSchool(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/school/save`,
        method: 'POST',
        data: params
    })
}

//-------------------------------------------学校接口结束------------------------------------------------------------------------------
//-------------------------------------------班级接口开始------------------------------------------------------------------------------
export function getClassesList(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/classes/list`,
        method: 'POST',
        data: params
    })
}

export function getClassesDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/classes/info/` + params,
        method: 'GET',
    })
}

export function saveClassesDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/classes/save`,
        method: 'POST',
        data: params
    })
}

export function deleteClassesDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/classes/delete`,
        method: 'POST',
        data: params
    })
}

export function updateClassesDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/classes/update`,
        method: 'POST',
        data: params
    })
}

//-------------------------------------班级接口结束------------------------------------------------------------------------------------
//---------------------------学生接口-------------------
//      /officer/student/list
export function getStudentList(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/student/list`,
        method: 'POST',
        data: params
    })
}
///officer/student/info/{id}
export function getStudentDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/student/info/` + params,
        method: 'GET'
    })
}
//  /officer/student/save
export function saveStudentInfo(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/student/save`,
        method: 'POST',
        data: params
    })
}
//  /officer/student/update
export function updateStudentInfo(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/student/update`,
        method: 'POST',
        data: params
    })
}

export function getLineStation(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/student/station/`+params,
        method: 'GET',
    })
}


//  /officer/student/upload
export function uploadStudentInfo(params){
    return fetch({
        url: `${API_SERVER_URL}/officer/student/upload`,
        method: 'POST',
        data: params
    })
}

//  /officer/upload
export function uploadOfficerFile(params){
    return fetch({
        url: `${API_SERVER_URL}/officer/upload`,
        method: 'POST',
        data: params
    })
}
//---------------------------学生接口-------------------
//---------------------------安全员-------------------
// /officer/safetyofficer/list
export function getSafetyOfficerList(params){
    return fetch({
        url: `${API_SERVER_URL}/officer/safetyofficer/list`,
        method: 'POST',
        data: params
    })
}
//  /officer/safetyofficer/info/{id}
export function getSavetyOfficerDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/safetyofficer/info/` + params,
        method: 'GET'
    })
}
//  /officer/safetyofficer/save
export function saveSafetyOfficerInfo(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/safetyofficer/save`,
        method: 'POST',
        data: params
    })
}
//  /officer/safetyofficer/update
export function updateSafetyOfficerInfo(params) {
    return  fetch({
        url: `${API_SERVER_URL}/officer/safetyofficer/update`,
        method: 'POST',
        data: params
    })
}
//  /officer/safetyofficer/delete
export function deleteSafetyOfficer(params) {
    console.log("删除安全员记录!!" + params);
    return fetch({
        url: `${API_SERVER_URL}/officer/safetyofficer/delete/`+params,
        method: 'GET'
    })
}

//  /officer/safetyofficer/delete
export function deleteBatchSafetyOfficer(params) {
    console.log("删除安全员记录!!" + params);
    return fetch({
        url: `${API_SERVER_URL}/officer/safetyofficer/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------安全员-------------------
//---------------------------------------营运计划模块-开始--------------------------------------
// 查询公司列表
export function getInfoBusCompanyListAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/companyListAll`,
        method: 'GET',
        data: params
    })
}

// 查询公司集团列表
export function getInfoBusCompanyCliqueListAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/companyCliqueListAll`,
        method: 'GET',
        data: params
    })
}

// 查询营运线路
export function getInfoBusPlanServiceLinePage(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/getCompanyLinePageByCompanyId`,
        method: 'GET',
        data: params
    })
}

//查详情
export function getCompanyLineIdDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/infoDetail/` + params,
        method: 'GET'
    })
}

//获取分页列表
export function getPlanBusServicePage(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/plan-bus-service/detail/list?companyLineId=${params}`,
        method: 'GET'
    })
}

//查单条
export function getPlanBusServiceDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/plan-bus-service/info/` + params,
        method: 'GET'
    })
}

//增加
export function savePlanBusService(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/plan-bus-service/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editPlanBusService(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/plan-bus-service/update`,
        method: 'PUT',
        data: params
    })
}

//单条删除
export function deletePlanBusService(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/plan-bus-service/delete/` + params,
        method: 'DELETE'
    })
}

//批量删除
export function deletePlanBusServiceAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/plan-bus-service/list/delete`,
        method: 'DELETE',
        data: params
    })
}

//获取公交车

export function getNoPlanBusList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/plan-bus-service/no-plan/bus/list`,
        method: 'GET',
        data: params
    })
}

// 查询司机列表
export function getInfoDriverListAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/driver/selection`,
        method: 'GET',
        data: params
    })
}

// 查询安全员列表
export function getInfoSafetyOfficerListAll (params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/selection`,
        method: 'GET',
        data: params
    })
}
//---------------------------------------营运计划模块-结束--------------------------------------


//---------------------------------------公交订单模块-开始--------------------------------------
//获取分页列表
export function getOrderBusCompanyList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompany/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getOrderBusCompanyDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompany/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveOrderBusCompany(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompany/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editOrderBusCompany(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompany/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteOrderBusCompany(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompany/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteOrderBusCompanyAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompany/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交订单模块-结束--------------------------------------

//---------------------------------------公交订单详情模块-开始--------------------------------------
//获取分页列表
export function getOrderBusCompanyDetailList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompanydetail/list`,
        method: 'GET',
        data: params
    })
}

//根据订单ID获取所有详情
export function getOrderBusCompanyDetailByOrderId(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompanydetail/getOrderBusCompanyDetailByOrderId/` + params,
        method: 'GET',
        data: params
    })
}

//查单条
export function getOrderBusCompanyDetailSingle(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompanydetail/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveOrderBusCompanyDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompanydetail/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editOrderBusCompanyDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompanydetail/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteOrderBusCompanyDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompanydetail/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteOrderBusCompanyDetailAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbuscompanydetail/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交订单详情模块-结束--------------------------------------

//---------------------------------------公交订单交付模块-开始--------------------------------------
//获取分页列表
export function getOrderCompanyDeliveryList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/ordercompanydelivery/list`,
        method: 'GET',
        data: params
    })
}

//根据订单ID获取所有订单交付
export function getOrderCompanyDeliveryByOrderId(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/ordercompanydelivery/getOrderCompanyDeliveryByOrderId/` + params,
        method: 'GET',
        data: params
    })
}

//查单条
export function getOrderCompanyDeliveryDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/ordercompanydelivery/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveOrderCompanyDelivery(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/ordercompanydelivery/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editOrderCompanyDelivery(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/ordercompanydelivery/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteOrderCompanyDelivery(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/ordercompanydelivery/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteOrderCompanyDeliveryAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/ordercompanydelivery/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交订单交付模块-结束--------------------------------------

//---------------------------------------车辆交付模块-开始--------------------------------------
//获取分页列表
export function getOrderBusDeliveryList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbusdelivery/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getOrderBusDeliveryDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbusdelivery/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveOrderBusDelivery(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbusdelivery/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editOrderBusDelivery(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbusdelivery/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteOrderBusDelivery(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbusdelivery/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteOrderBusDeliveryAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbusdelivery/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------车辆交付模块-结束--------------------------------------

//---------------------------------------参数配置模块-开始--------------------------------------
//获取分页列表
export function getParameterList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getParameterDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveParameter(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editParameter(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteParameter(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteParameterAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoConfigParam/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------参数配置模块-结束--------------------------------------

//---------------------------------------公交公司模块-开始--------------------------------------
//获取分页列表
export function getInfoBusCompanyList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoBusCompanyDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoBusCompany(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoBusCompany(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoBusCompany(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoBusCompanyAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobuscompany/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交公司模块-结束--------------------------------------

//---------------------------------------公交线路模块-开始--------------------------------------
//获取分页列表
export function getInfoCompanyLineList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/list`,
        method: 'GET',
        data: params
    })
}

//获取公交公司的公交线路
export function getCompanyLineByCompanyId(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/getCompanyLineByCompanyId/` + params,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoCompanyLineDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoCompanyLine(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/save`,
        method: 'POST',
        data: params
    })
}

//增加批量站点
export function saveInfoCompanyLineBatch(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/saveBatch`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoCompanyLine(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoCompanyLine(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoCompanyLineAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infocompanyline/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交线路-结束--------------------------------------

//---------------------------------------设备类型模块-开始--------------------------------------
//获取分页列表
export function getInfoDeviceTypeList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infodevicetype/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoDeviceTypeDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infodevicetype/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoDeviceType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infodevicetype/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoDeviceType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infodevicetype/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoDeviceType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infodevicetype/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoDeviceTypeAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infodevicetype/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------设备类型模块-结束--------------------------------------

//---------------------------------------电池类型模块-开始--------------------------------------
//获取分页列表
export function getInfoBatteryTypeList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobatterytype/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoBatteryTypeDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobatterytype/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoBatteryType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobatterytype/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoBatteryType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobatterytype/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoBatteryType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobatterytype/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoBatteryTypeAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobatterytype/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------电池类型模块-结束--------------------------------------

//---------------------------------------公交车型模块-开始--------------------------------------
//获取分页列表
export function getInfoBusTypeList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobustype/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoBusTypeDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobustype/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoBusType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobustype/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoBusType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobustype/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoBusType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobustype/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoBusTypeAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobustype/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交车型模块-结束--------------------------------------

//---------------------------------------公交电机模块-开始--------------------------------------
//获取分页列表
export function getInfoBusMotorList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusmotor/list`,
        method: 'GET',
        data: params
    })
}

//获取列表指定车辆
export function getInfoBusMotorListByBusId(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusmotor/listByBusId?busId=` + params,
        method: 'GET'
    })
}

//查单条
export function getInfoBusMotorDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusmotor/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoBusMotor(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusmotor/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoBusMotor(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusmotor/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoBusMotor(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusmotor/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoBusMotorAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusmotor/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交电机模块-结束--------------------------------------

//---------------------------------------公交电池模块-开始--------------------------------------
//获取分页列表
export function getInfoBusBatteryList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusbattery/list`,
        method: 'GET',
        data: params
    })
}

//获取列表指定车辆
export function getInfoBusBatteryListByBusId(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusbattery/listByBusId?busId=` + params,
        method: 'GET'
    })
}

//查单条
export function getInfoBusBatteryDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusbattery/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoBusBattery(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusbattery/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoBusBattery(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusbattery/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoBusBattery(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusbattery/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoBusBatteryAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusbattery/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交电池模块-结束--------------------------------------

//---------------------------------------公交设备模块-开始--------------------------------------
//获取分页列表
export function getInfoBusDeviceList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusdevice/list`,
        method: 'GET',
        data: params
    })
}

//获取列表指定车辆
export function getInfoBusDeviceListByBusId(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusdevice/listByBusId?busId=` + params,
        method: 'GET'
    })
}

//查单条
export function getInfoBusDeviceDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusdevice/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoBusDevice(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusdevice/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoBusDevice(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusdevice/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoBusDevice(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusdevice/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoBusDeviceAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infobusdevice/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交设备模块-结束--------------------------------------

//---------------------------------------电机类型模块-开始--------------------------------------
//获取分页列表
export function getInfoMotorTypeList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infomotortype/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoMotorTypeDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infomotortype/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoMotorType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infomotortype/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoMotorType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infomotortype/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoMotorType(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infomotortype/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoMotorTypeAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infomotortype/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------电机类型模块-结束--------------------------------------

//---------------------------------------公交车-开始--------------------------------------
//获取分页列表
export function getInfoBusList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoBusDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/get/` + params,
        method: 'GET'
    })
}

//增加
export function saveInfoBus(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoBus(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoBus(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoBusAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/bus/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交车-结束--------------------------------------

//---------------------------------------公交车厂-开始--------------------------------------
//获取分页列表
export function getBusFactoryList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoBusFactory/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getBusFactoryDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoBusFactory/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveBusFactory(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoBusFactory/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editBusFactory(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoBusFactory/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteBusFactory(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoBusFactory/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteBusFactoryAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoBusFactory/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交车厂-结束--------------------------------------

//---------------------------------------公交车站-开始--------------------------------------
//获取分页列表
export function getInfoBusStationList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getInfoBusStationDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoBusStation(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editInfoBusStation(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoBusStation(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteInfoBusStationAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/infobusstation/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------公交车站-结束--------------------------------------

//---------------------------------------行驶日志-开始--------------------------------------
//获取分页列表
export function getLogBusDriveList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/logbusdrive/list`,
        method: 'GET',
        data: params
    })
}

//---------------------------------------行驶日志-结束--------------------------------------

//---------------------------------------营运日志-开始--------------------------------------
//获取分页列表
export function getLogBusServiceList(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/logbusservice/list`,
        method: 'GET',
        data: params
    })
}

//---------------------------------------行驶日志-结束--------------------------------------


//---------------------------------------广告播放日志-开始--------------------------------------
//获取分页列表
export function getLogAdvertiseShowList(params) {
    return fetch({
        url: `${API_SERVER_URL}/advert/logadvertiseshow/list`,
        method: 'GET',
        data: params
    })
}

//---------------------------------------行驶日志-结束--------------------------------------


//---------------------------------------设备日志-开始--------------------------------------
//获取分页列表
export function getLogBusDeviceList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/logbusdevice/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getLogBusDeviceDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/logbusdevice/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveLogBusDevice(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/logbusdevice/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function editLogBusDevice(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/logbusdevice/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteLogBusDevice(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/logbusdevice/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteLogBusDeviceAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/logbusdevice/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//--------------------------------------设备日志-结束--------------------------------------

//---------------------------------------区域-开始--------------------------------------
export function getAreaList(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoarea/list`,
        method: 'GET',
        data: params
    })
}

//查单条
export function getAreaDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoarea/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function saveArea(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoarea/save`,
        method: 'POST',
        data: params
    })
}

//编辑
export function editArea(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoarea/update`,
        method: 'POST',
        data: params
    })
}

//删除
export function deleteArea(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoarea/delete/` + params,
        method: 'POST',
        data: params
    })
}

//删除所有
export function deleteAreaAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/service/infoarea/deleteBatch`,
        method: 'POST',
        data: params
    })
}

//---------------------------------------区域-结束--------------------------------------

/**
 * 页面 ： 系统参数管理 - 事件类型 - Api
 * */

//事件分组数据
export function getEventGroup(params) {
    return fetch({
        url: `${API_SERVER_URL}/selection/queryEventGroup`,
        method: 'GET',
        data: params
    })
}


//事件上级分类列表
export function getEventTreeSelect(params) {
    return fetch({
        url: `${API_SERVER_URL}/selection/eventType`,
        method: 'GET',
        data: params
    })
}


//获取所有
export function getEventTyle(params) {
    return fetch({
        url: `${API_SERVER_URL}/info/eventType/list`,
        method: 'GET',
        data: params
    })
}


//删除
export function deleteEventTyle(params) {
    return fetch({
        url: `${API_SERVER_URL}/info/eventType/delete/` + params,
        method: 'POST',
        data: params
    })
}

//删除所有
export function deleteEventTyleAll(params) {
    return fetch({
        url: `${API_SERVER_URL}/info/eventType/deleteBatch`,
        method: 'POST',
        data: params
    })
}


//查单条
export function getEventTyleDetail(params) {
    return fetch({
        url: `${API_SERVER_URL}/info/eventType/get/` + params,
        method: 'GET',
        data: params
    })
}

//增加
export function setEventTyleSave(params) {
    return fetch({
        url: `${API_SERVER_URL}/info/eventType/save`,
        method: 'POST',
        data: params
    })
}

//修改
export function setEventTyleEdir(params) {
    return fetch({
        url: `${API_SERVER_URL}/info/eventType/update`,
        method: 'POST',
        data: params
    })
}

// 获取车辆交付模板
export function getDownLoadTemplateExcel(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbusdelivery/templateExcel/downLoad`,
        method: 'GET',
        responseType: 'blob'
    })
}

// 上传车辆交付模板
export function getUpLoadTemplateExcel(params) {
    return fetch({
        url: `${API_SERVER_URL}/customer/orderbusdelivery/templateExcel/upLoad`,
        method: 'GET',
        data: params
    })
}

//获取司机列表
export function getInfoDriverList(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/driver/list`,
        method: 'GET',
        data: params
    })
}

//增加
export function saveInfoDriver(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/driver/save`,
        method: 'POST',
        data: params
    })
}

//查单条
export function getInfoDriver(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/driver/info/` + params,
        method: 'GET',
        data: params
    })
}

//修改
export function editInfoDriver(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/driver/update`,
        method: 'POST',
        data: params
    })
}

//单条删除
export function deleteInfoDriver(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/driver/delete/` + params,
        method: 'POST',
        data: params
    })
}

//批量删除
export function deleteBatchInfoDriver(params) {
    return fetch({
        url: `${API_SERVER_URL}/officer/driver/deleteBatch`,
        method: 'POST',
        data: params
    })
}

/*export function BMapGL() {
    return new Promise(function(resolve, reject) {
        window.init = function() {
            resolve(BMapGL)
        }
        var script = document.createElement('script')
        script.type = 'text/javascript'
        script.src = `http://api.map.baidu.com/api?v=1.0&type=webgl&ak=CWmiAPiwuHW6sGdMbAReZdqU`
        script.onerror = reject
        document.head.appendChild(script)
    })
}*/
