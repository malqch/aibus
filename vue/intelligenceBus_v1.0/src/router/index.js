import Vue from 'vue'
import Router from 'vue-router'
import {

    Main,
    Login,
    Home,
    User,
    Role,
    Premission,
    Station,
    Log,
    Error404,
    OrderBusCompany,
    OrderBusCompanyDetail,
    OrderBusDelivery,
    CompanyLine,
    ConfigParams,
    MotorType,
    BatteryType,
    Bus,
    BusInfo,
    BusType,
    BusMotor,
    BusBattery,
    BusCompany,
    BusDevice,
    BusStation,
    Area,
    BusFactory,
    DeviceType,
    LogBusDevice,
    LogBusDrive,
    LogBusService,
    LogAdvertiseShow,
    InfoAdvertiseTarget,
    InfoAdvertisePosition,
    SalePreview,
    FaultStatistics,
    Customer,
    AfterSale,
    PublicHealth,
    Traffic,
    Environment,
    EventLevel,
    EventType,
    EventLog,
    EventLogCard,
    EventTarget,
    CollectEventList,
    CollectEventCard,
    FaultType,
    FaultLevel,
    FaultTarget,
    FaultCollect,
    FaultExtend,
    FaultDetail,
    FaultAttach,
    BusPlanService,
    BusPlanServiceDetail,
    UpDateServer,
    Uploader,
    Reviewer,
    CutInUploader,
    Driver,
    classList,
    security,
    studentList
} from "@/view";
import {Parameter} from "../view";

Vue.use(Router)

/**
 * 项目刚开始使用的是静态路由，由于没有权限数据没办法进行动态路由配置
 * 下面两行代码解决的问题是 连续点击两次同一菜单并进行路由跳转的错误
 * */
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export default new Router({
    routes: [
        {
            path: '/Main',
            name: 'Main',
            component: Main,
            children: [
                {
                    path: '/Home',
                    name: 'Home',
                    component: Home,
                    meta: {title: '首页', noCache: true}
                },
                {
                    path: '/User',
                    name: 'User',
                    component: User,
                    meta: {title: '用户管理', noCache: true}
                },
                {
                    path: '/Role',
                    name: 'Role',
                    component: Role,
                    meta: {title: '角色管理', noCache: true}
                },
                {
                    path: '/Station',
                    name: 'Station',
                    component: Station,
                    meta: {title: '岗位管理', noCache: true}
                },
                {
                    path: '/Premission',
                    name: 'Premission',
                    component: Premission,
                    meta: {title: '菜单管理', noCache: true}
                },
                {
                    path: '/Log',
                    name: 'Log',
                    component: Log,
                    meta: {title: '日志管理', noCache: true}
                },
                {
                    path: '/OrderBusCompany',
                    name: 'OrderBusCompany',
                    component: OrderBusCompany,
                    meta: {title: '公交订单', noCache: true}
                },
                {
                    path: '/BusPlanService',
                    name: 'BusPlanService',
                    component: BusPlanService,
                    meta: {title: '营运计划线路', noCache: true}
                },
                {
                    path: '/BusPlanServiceDetail',
                    name: 'BusPlanServiceDetail',
                    component: BusPlanServiceDetail,
                    meta: {title: '营运计划详情', noCache: true}
                },
                {
                    path: '/OrderBusCompanyDetail',
                    name: 'OrderBusCompanyDetail',
                    component: OrderBusCompanyDetail,
                    meta: {title: '公交订单', noCache: true}
                },
                {
                    path: '/OrderBusDelivery',
                    name: 'OrderBusDelivery',
                    component: OrderBusDelivery,
                    meta: {title: '车辆交付', noCache: true}
                },
                {
                    path: '/CompanyLine',
                    name: 'CompanyLine',
                    component: CompanyLine,
                    meta: {title: '公交线路', noCache: true}
                },
                {
                    path: '/ConfigParams',
                    name: 'ConfigParams',
                    component: ConfigParams,
                    meta: {title: '配置参数', noCache: true}
                },
                {
                    path: '/MotorType',
                    name: 'MotorType',
                    component: MotorType,
                    meta: {title: '电机类型', noCache: true}
                },
                {
                    path: '/BusMotor',
                    name: 'BusMotor',
                    component: BusMotor,
                    meta: {title: '公交电机', noCache: true}
                },
                {
                    path: '/Bus',
                    name: 'Bus',
                    component: Bus,
                    meta: {title: '公交车', noCache: true}
                },
                {
                    path: '/BusType',
                    name: 'BusType',
                    component: BusType,
                    meta: {title: '公交车型', noCache: true}
                },
                {
                    path: '/BusBattery',
                    name: 'BusBattery',
                    component: BusBattery,
                    meta: {title: '公交电池', noCache: true}
                },
                {
                    path: '/BusCompany',
                    name: 'BusCompany',
                    component: BusCompany,
                    meta: {title: '公交公司', noCache: true}
                },
                {
                    path: '/BusInfo',
                    name: 'BusInfo',
                    component: BusInfo,
                    meta: {title: '公交详情', noCache: true}
                },
                {
                    path: '/BusDevice',
                    name: 'BusDevice',
                    component: BusDevice,
                    meta: {title: '公交设备', noCache: true}
                },
                {
                    path: '/BusStation',
                    name: 'BusStation',
                    component: BusStation,
                    meta: {title: '公交车站', noCache: true}
                },
                {
                    path: '/BatteryType',
                    name: 'BatteryType',
                    component: BatteryType,
                    meta: {title: '电池类型', noCache: true}
                },
                {
                    path: '/Area',
                    name: 'Area',
                    component: Area,
                    meta: {title: '区域', noCache: true}
                },
                {
                    path: '/BusFactory',
                    name: 'BusFactory',
                    component: BusFactory,
                    meta: {title: '公交车厂', noCache: true}
                },
                {
                    path: '/DeviceType',
                    name: 'DeviceType',
                    component: DeviceType,
                    meta: {title: '设备类型', noCache: true}
                },
                {
                    path: '/LogBusDevice',
                    name: 'LogBusDevice',
                    component: LogBusDevice,
                    meta: {title: '设备日志', noCache: true}
                },
                {
                    path: '/LogBusDrive',
                    name: 'LogBusDrive',
                    component: LogBusDrive,
                    meta: {title: '行驶日志', noCache: true}
                },
                {
                    path: '/LogBusService',
                    name: 'LogBusService',
                    component: LogBusService,
                    meta: {title: '营运日志', noCache: true}
                },
                {
                    path: '/LogAdvertiseShow',
                    name: 'LogAdvertiseShow',
                    component: LogAdvertiseShow,
                    meta: {title: '广告播放日志', noCache: true}
                },
                {
                    path: '/InfoAdvertiseTarget',
                    name: 'InfoAdvertiseTarget',
                    component: InfoAdvertiseTarget,
                    meta: {title: '广告标签', noCache: true}
                },
                {
                    path: '/InfoAdvertisePosition',
                    name: 'InfoAdvertisePosition',
                    component: InfoAdvertisePosition,
                    meta: {title: '广告位', noCache: true}
                },
                {
                    path: '/SalePreview',
                    name: 'SalePreview',
                    component: SalePreview,
                    meta: {title: '销售预览页', noCache: true}
                },
                {
                    path: '/FaultStatistics',
                    name: 'FaultStatistics',
                    component: FaultStatistics,
                    meta: {title: '故障统计分析', noCache: true}
                },
                {
                    path: '/Customer',
                    name: 'Customer',
                    component: Customer,
                    meta: {title: '客户详情', noCache: true}
                },
                {
                    path: '/AfterSale',
                    name: 'AfterSale',
                    component: AfterSale,
                    meta: {title: '售后维修', noCache: true}
                },
                {
                    path: '/PublicHealth',
                    name: 'PublicHealth',
                    component: PublicHealth,
                    meta: {title: '公共卫生', noCache: true}
                },
                {
                    path: '/Traffic',
                    name: 'Traffic',
                    component: Traffic,
                    meta: {title: '违规信息', noCache: true}
                },
                {
                    path: '/Environment',
                    name: 'Environment',
                    component: Environment,
                    meta: {title: '环境数据', noCache: true}
                },
                {
                    path: '/FaultType',
                    name: 'FaultType',
                    component: FaultType,
                    meta: {title: '故障类型', noCache: true}
                },
                {
                    path: '/FaultLevel',
                    name: 'FaultLevel',
                    component: FaultLevel,
                    meta: {title: '故障级别', noCache: true}
                },
                {
                    path: '/FaultTarget',
                    name: 'FaultTarget',
                    component: FaultTarget,
                    meta: {title: '故障标签', noCache: true}
                },
                {
                    path: '/FaultCollect',
                    name: 'FaultCollect',
                    component: FaultCollect,
                    meta: {title: '故障采集', noCache: true}
                },
                {
                    path: '/FaultExtend',
                    name: 'FaultExtend',
                    component: FaultExtend,
                    meta: {title: '故障扩展', noCache: true}
                },
                {
                    path: '/FaultDetail',
                    name: 'FaultDetail',
                    component: FaultDetail,
                    meta: {title: '故障日志', noCache: true}
                },
                {
                    path: '/FaultAttach',
                    name: 'FaultAttach',
                    component: FaultAttach,
                    meta: {title: '故障日志附件', noCache: true}
                },

                /******************** 事件相关配置 开始 **********************/
                {
                    path: '/Event/EventLevel',
                    name: 'EventLevel',
                    component: EventLevel,
                    meta: {title: '事件级别', noCache: true}
                },
                {
                    path: '/Event/EventType',
                    name: 'EventType',
                    component: EventType,
                    meta: {title: '事件类型', noCache: true}
                },
                {
                    path: '/Event/EventTarget',
                    name: 'EventTarget',
                    component: EventTarget,
                    meta: {title: '事件标签', noCache: true}
                },
                {
                    path: '/Event/EventLog',
                    name: 'EventLog',
                    component: EventLog,
                    meta: {title: '事件日志', noCache: true}

                },
                {
                    path: '/Event/EventLogCard',
                    name: 'EventLogCard',
                    component: EventLogCard,
                    meta: {title: '事件日志卡片', noCache: true}

                },
                {
                    path: '/Event/CollectEvent/List',
                    name: 'CollectEventList',
                    component: CollectEventList,
                    meta: {title: '事件采集列表', noCache: true}

                }
                ,
                {
                    path: '/Event/CollectEvent/Card',
                    name: 'CollectEventCard',
                    component: CollectEventCard,
                    meta: {title: '事件采集卡片', noCache: true}
                }
                /******************** 事件相关配置 结束 **********************/
                ,
                {
                    path: '/UpDateServer',
                    name: 'UpDateServer',
                    component: UpDateServer,
                    meta: {title: '更新包管理', noCache: true}
                },
                {
                    path: '/Uploader',
                    name: 'Uploader',
                    component: Uploader,
                    meta: {title: '广告投放', noCache: true}
                },
                {
                    path: '/Reviewer',
                    name: 'Reviewer',
                    component: Reviewer,
                    meta: {title: '广告审核', noCache: true}
                },
                {
                    path: '/CutInUploader',
                    name: 'CutInUploader',
                    component: CutInUploader,
                    meta: {title: '广告插播', noCache: true}
                },
                {
                    path: '/Officer/driver',
                    name: 'Driver',
                    component: Driver,
                    meta: {title: '驾驶员信息管理', noCache: true}
                },
                {
                    path: '/School/List',
                    name: 'classList',
                    component: classList,
                    meta: {title: '班级信息管理', noCache: true}
                },
                {
                    path: '/Student/List',
                    name: 'classList',
                    component: studentList,
                    meta: {title: '学生信息管理', noCache: true}
                },
                {
                    path: '/Safety/List',
                    name: 'classList',
                    component: security,
                    meta: {title: '学生信息管理', noCache: true}
                }
            ]
        },
        {
            path: '/',
            name: 'Login',
            component: Login
        },

        {
            path: '*',
            component: Error404
        }
    ]
})
