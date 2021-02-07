import '@/style/view-common.css'

//主页面
export const Main = () => import('./Main')

//登录
export const Login = ()=> import('./Login')

//首页
export const Home = ()=> import('./Home')

//404
export const Error404 = ()=> import('./Error404')

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 系统权限管理 start<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//用户管理
export const User = ()=> import('./System/User')
//角色管理
export const Role = ()=> import('./System/Role')
//菜单管理
export const Premission = ()=> import('./System/Premission')
//岗位管理
export const Station = ()=> import('./System/Station')
//日志管理
export const Log = ()=> import('./System/Log')
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 系统权限管理 end<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

//线路营运
export const BusPlanService = ()=> import('./Config/BusPlanService/BusPlanService')

//线路营运计划
export const BusPlanServiceDetail = ()=> import('./Config/BusPlanService/BusPlanServiceDetail')

//公交订单
export const OrderBusCompany = ()=> import('./Config/OrderBusCompany')

//公交订单
export const OrderBusCompanyDetail = ()=> import('./Config/OrderBusCompanyDetail')

//公交订单
export const OrderBusDelivery = ()=> import('./Config/OrderBusDelivery')

//公交线路
export const CompanyLine = ()=> import('./Config/CompanyLine')

//公交公司
export const BusCompany = ()=> import('./Config/BusCompany')

//配置参数
export const ConfigParams = ()=> import('./Config/ConfigParams')

//区域
export const Area = ()=> import('./Config/Area')

//公交车厂
export const BusFactory = ()=> import('./Config/BusFactory')

//设备类型
export const DeviceType = ()=> import('./Config/DeviceType')

//电机类型
export const MotorType = ()=> import('./Config/MotorType')

//公交车
export const Bus = ()=> import('./Config/Bus')

//公交车型
export const BusType = ()=> import('./Config/BusType')

//公交电机
export const BusMotor = ()=> import('./Config/BusMotor')

//公交电池
export const BusBattery = ()=> import('./Config/BusBattery')

//公交详情
export const BusInfo = ()=> import('./Config/BusInfo')

//公交设备
export const BusDevice = ()=> import('./Config/BusDevice')

//公交车站
export const BusStation = ()=> import('./Config/BusStation')

//电池类型
export const BatteryType = ()=> import('./Config/BatteryType')

//设备日志
export const LogBusDevice = ()=> import('./Log/LogBusDevice')

//行驶日志
export const LogBusDrive = ()=> import('./Log/LogBusDrive')

//营运日志
export const LogBusService = ()=> import('./Log/LogBusService')

//广告播放日志
export const LogAdvertiseShow = ()=> import('./Log/LogAdvertiseShow')

//广告标签
export const InfoAdvertiseTarget = ()=> import('./Advertise/InfoAdvertiseTarget')

//广告位
export const InfoAdvertisePosition = ()=> import('./Advertise/InfoAdvertisePosition')

//销售预览
export const SalePreview = ()=> import('./SalePreview')

//故障统计
export const FaultStatistics = ()=> import('./FaultStatistics')

//客户详情
export const Customer = ()=> import('./Customer')

//售后维修
export const AfterSale = ()=> import('./AfterSale')

//公共卫生
export const PublicHealth = ()=> import('./PublicHealth')

//违规信息
export const Traffic = ()=> import('./Traffic')

//环境数据
export const Environment = ()=> import('./Environment')

//>>>>>>故障管理 start>>>>>>
//故障类型
export const FaultType = ()=> import('./Config/Fault/FaultType')

//故障级别
export const FaultLevel = ()=> import('./Config/Fault/FaultLevel')

//故障标签
export const FaultTarget = ()=> import('./Config/Fault/FaultTarget')

//故障采集
export const FaultCollect = ()=> import('./Config/Fault/FaultCollect')

//故障扩展
export const FaultExtend = ()=> import('./Config/Fault/FaultExtend')

//故障日志
export const FaultDetail = ()=> import('./Config/Fault/FaultDetail')

//故障日志附件
export const FaultAttach = ()=> import('./Config/Fault/FaultAttach')

//>>>>>>故障管理 end>>>>>>

/**************** 事件相关页面 开始 ***************/
//事件收集 事件扩展

//事件类型
export const EventType = ()=> import('./Config/Event/EventType')
//事件级别
export const EventLevel = ()=> import('./Config/Event/EventLevel')
//事件标签
export const EventTarget = ()=> import('./Config/Event/EventTarget')
//事件采集列表
export const CollectEventList = ()=> import('./Config/Event/CollectEvent/CollectEventList')
//事件采集卡片
export const CollectEventCard = ()=> import('./Config/Event/CollectEvent/CollectEventCard')
//事件日志 事件日志附件
export const EventLog = ()=> import('./Config/Event/EventLog/EventLogList')
//事件日志卡片
export const EventLogCard = ()=> import('./Config/Event/EventLog/EventLogCard')
/**************** 事件相关页面 结束 ***************/

/**************** 更新服务管理 开始 ***************/
//更新包管理
export const UpDateServer = ()=> import('./Config/UpDateServer')

/**************** 更新服务管理 结束 ***************/

/**************** 广告管理 开始 ***************/

export const Reviewer = () => import('./Advertisement/Reviewer')
export const Uploader = () => import('./Advertisement/Uploader')
export const CutInUploader = () => import('./Advertisement/CutInUploader')
/**************** 广告管理 结束 ***************/

export const Driver = () => import('./Officer/Driver/index')

export const classList = () => import('./Officer/ClassList/index')

export const studentList = () => import('./Officer/studentList/index')
export const security = () => import('./Officer/security/index')
