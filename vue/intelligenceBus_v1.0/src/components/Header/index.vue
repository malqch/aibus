<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-29 08:32
 @file-description:
-->

<template>
    <div class="header_con" style="height:44px !important;">
        <div class="logo">
            <img :src="bus_icon" alt="" style="width:24px;height:24px;float:left;margin-top: 2px;margin-left: 10px;">
            <span style="float: left;margin-top:3px; margin-left:5px;">智能公交后台系统</span>
        </div>
        <div style="position: absolute;left: 225px;top: 10px;">
            <button class="l_change" @click="l_change()">
                <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
            </button>
        </div>
        <div style="position: absolute;left: 250px;">
            <el-breadcrumb class="app-breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item v-for="(item,index) in levelList" :key="index + 'absp'"
                                    style="color:#fff !important;">
                    {{item.title}}
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>


        <div style="display: flex">
            <span @click="handleFullscreen" class="full_btn"><i class="fa fa-arrows-alt"></i></span>
            <span class="header_splice"></span>
            <el-menu class="el-menu-demo" mode="horizontal" text-color="#fff" active-text-color="#558ff2"
                     style="border:none;background-color: #0f95ff;">
                <el-submenu index="2" class="header_menu">
                    <template slot="title">
                        <!--<i class="fa fa-user-circle-o" style="font-size:20px;margin-right:5px;margin-top:-4px;"></i>-->
                        <img src="../../../static/user.png" height="25" width="25">
                        {{this.user.loginName}}
                    </template>
                    <el-menu-item index="3-2" style="font-size:14px;text-align:center;" @click="formAddVisible=true">
                        修改密码
                    </el-menu-item>
                    <el-menu-item index="2-1" style="font-size:14px;margin-right:5px;text-align:center;"
                                  @click="handleExit()">退出
                    </el-menu-item>
                </el-submenu>
            </el-menu>
        </div>
        <!--弹窗-->
        <el-dialog title="修改用户密码" :visible.sync="formAddVisible">
            <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="100px">
                <el-form-item label="账号" prop="loginName">
                    <el-input :disabled="true" v-model="dataForm.loginName"></el-input>
                </el-form-item>
                <el-form-item label="原密码" prop="password">
                    <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newPassword">
                    <el-input v-model="dataForm.newPassword" type="password" placeholder="新密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="comfirmPassword">
                    <el-input v-model="dataForm.comfirmPassword" type="password" placeholder="确认新密码"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="formAddVisible = false">取 消</el-button>
                <el-button type="primary" @click="dataFormSubmit()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import "./index.css";
    import {
        getLoginUser,
        logoutSystem,
        updateUserPassword
    } from '@/api/system'

    export default {
        name: "Header",
        data() {
            var validatePassword = (rule, value, callback) => {
                if (this.dataForm.id && !/\S/.test(value)) {
                    callback(new Error('密码不能为空'))
                } else {
                    callback()
                }
            }
            var validateComfirmPassword = (rule, value, callback) => {
                if (this.dataForm.id && !/\S/.test(value)) {
                    callback(new Error('确认密码不能为空'))
                } else if (this.dataForm.newPassword !== value) {
                    callback(new Error('确认密码与密码输入不一致'))
                } else {
                    callback()
                }
            }
            return {
                pathData: {
                    MerchantList: [
                        {
                            title: '商家管理',
                            pathSatus: 0
                        },
                        {
                            title: '商家列表',
                            pathSatus: 0,
                        }
                    ],
                    MerchantDetail: [
                        {
                            title: '商家管理',
                            pathSatus: 0
                        },
                        {
                            title: '商家列表',
                            pathSatus: 1,
                            path: '/MerchantList'
                        },
                        {
                            title: '商家详情',
                            pathSatus: 0
                        },
                    ],
                    MerchantCard: [
                        {
                            title: '商家管理',
                            pathSatus: 0
                        },
                        {
                            title: '商家卡片',
                            pathSatus: 0,
                        }
                    ],
                    SpecialMIFM: [
                        {
                            title: '工商所管理',
                            pathSatus: 0
                        },
                        {
                            title: '工商所列表',
                            pathSatus: 0,
                        }
                    ],
                    GBasicIFM: [
                        {
                            title: '工商所管理',
                            pathSatus: 0
                        },
                        {
                            title: '工商所列表',
                            pathSatus: 1,
                            path: '/SpecialMIFM'
                        },
                        {
                            title: '工商所详情',
                            pathSatus: 0
                        },
                    ],
                    SpotCheck: [
                        {
                            title: '违规抽检',
                            pathSatus: 0
                        }
                    ],
                    SpecialMCard: [
                        {
                            title: '工商所管理',
                            pathSatus: 0
                        },
                        {
                            title: '工商所卡片',
                            pathSatus: 0,
                        }
                    ],
                    UpDateServer: [
                        {
                            title: '更新服务管理',
                            pathSatus: 0
                        },
                        {
                            title: '更新包管理',
                            pathSatus: 0,
                        }
                    ],
                    EventCRules: [
                        {
                            title: '规则管理',
                            pathSatus: 0
                        },
                        {
                            title: '事件采集规则',
                            pathSatus: 0,
                        }
                    ],
                    MerchantRuleCF: [
                        {
                            title: '规则管理',
                            pathSatus: 0
                        },
                        {
                            title: '商家规则配置',
                            pathSatus: 0,
                        }
                    ],
                    EventSRules: [
                        {
                            title: '规则管理',
                            pathSatus: 0
                        },
                        {
                            title: '事件统计规则',
                            pathSatus: 0,
                        }
                    ],
                    EventDR: [
                        {
                            title: '规则管理',
                            pathSatus: 0
                        },
                        {
                            title: '事件传递关系',
                            pathSatus: 0,
                        }
                    ],
                    MilletIFM: [
                        {
                            title: '设备接口管理',
                            pathSatus: 0
                        },
                        {
                            title: '采集内容',
                            pathSatus: 0,
                        }
                    ],
                    SnCodingM: [
                        {
                            title: '商家入驻管理',
                            pathSatus: 0
                        },
                        {
                            title: 'SN编码管理',
                            pathSatus: 0,
                        }
                    ],
                    RegionalIFM: [
                        {
                            title: '系统参数管理',
                            pathSatus: 0
                        },
                        {
                            title: '区域信息',
                            pathSatus: 0,
                        }
                    ],
                    ConfigParams: [
                        {
                            title: '系统参数管理',
                            pathSatus: 0
                        },
                        {
                            title: '参数配置',
                            pathSatus: 0,
                        }
                    ],
                    Area: [
                        {
                            title: '系统参数管理',
                            pathSatus: 0
                        },
                        {
                            title: '区域信息',
                            pathSatus: 0,
                        }
                    ],
                    DeviceType: [
                        {
                            title: '车厂管理',
                            pathSatus: 0
                        },
                        {
                            title: '设备类型',
                            pathSatus: 0,
                        }
                    ],
                    MotorType: [
                        {
                            title: '车厂管理',
                            pathSatus: 0
                        },
                        {
                            title: '电机类型',
                            pathSatus: 0,
                        }
                    ],
                    BusFactory: [
                        {
                            title: '车厂管理',
                            pathSatus: 0
                        },
                        {
                            title: '公交车厂',
                            pathSatus: 0,
                        }
                    ],
                    BatteryType: [
                        {
                            title: '车厂管理',
                            pathSatus: 0
                        },
                        {
                            title: '电池类型',
                            pathSatus: 0,
                        }
                    ],
                    BusType: [
                        {
                            title: '车厂管理',
                            pathSatus: 0
                        },
                        {
                            title: '公交车型',
                            pathSatus: 0,
                        }
                    ],
                    EventLevel: [
                        {
                            title: '事件管理',
                            pathSatus: 0
                        },
                        {
                            title: '事件级别',
                            pathSatus: 0,
                        }
                    ],
                    EventType: [
                        {
                            title: '事件管理',
                            pathSatus: 0
                        },
                        {
                            title: '事件类型',
                            pathSatus: 0,
                        }
                    ],
                    EventTarget: [
                        {
                            title: '事件管理',
                            pathSatus: 0
                        },
                        {
                            title: '事件标签',
                            pathSatus: 0,
                        }
                    ],
                    CollectEventList: [
                        {
                            title: '事件管理',
                            pathSatus: 0
                        },
                        {
                            title: '事件采集',
                            pathSatus: 0,
                        }
                    ],
                    EventLog: [
                        {
                            title: '事件管理',
                            pathSatus: 0
                        },
                        {
                            title: '事件日志',
                            pathSatus: 0,
                        }
                    ],
                    FaultLevel: [
                        {
                            title: '故障管理',
                            pathSatus: 0
                        },
                        {
                            title: '故障级别',
                            pathSatus: 0,
                        }
                    ],
                    FaultType: [
                        {
                            title: '故障管理',
                            pathSatus: 0
                        },
                        {
                            title: '故障类型',
                            pathSatus: 0,
                        }
                    ],
                    FaultTarget: [
                        {
                            title: '故障管理',
                            pathSatus: 0
                        },
                        {
                            title: '故障标签',
                            pathSatus: 0,
                        }
                    ],
                    FaultCollect: [
                        {
                            title: '故障管理',
                            pathSatus: 0
                        },
                        {
                            title: '故障采集',
                            pathSatus: 0,
                        }
                    ],
                    FaultDetail: [
                        {
                            title: '故障管理',
                            pathSatus: 0
                        },
                        {
                            title: '故障日志',
                            pathSatus: 0,
                        }
                    ],
                    MerchantP: [
                        {
                            title: '系统参数管理',
                            pathSatus: 0
                        },
                        {
                            title: '商家定位',
                            pathSatus: 0,
                        }
                    ],
                    MerchantType: [
                        {
                            title: '系统参数管理',
                            pathSatus: 0
                        },
                        {
                            title: '商家类型',
                            pathSatus: 0,
                        }
                    ],
                    TypeOfWork: [
                        {
                            title: '系统参数管理',
                            pathSatus: 0
                        },
                        {
                            title: '工种类型',
                            pathSatus: 0,
                        }
                    ],
                    User: [
                        {
                            title: '系统管理',
                            pathSatus: 0
                        },
                        {
                            title: '用户管理',
                            pathSatus: 0,
                        }
                    ],
                    Role: [
                        {
                            title: '系统管理',
                            pathSatus: 0
                        },
                        {
                            title: '角色管理',
                            pathSatus: 0,
                        }
                    ],
                    Premission: [
                        {
                            title: '系统管理',
                            pathSatus: 0
                        },
                        {
                            title: '菜单管理',
                            pathSatus: 0,
                        }
                    ],
                    Station: [
                        {
                            title: '系统管理',
                            pathSatus: 0
                        },
                        {
                            title: '岗位管理',
                            pathSatus: 0,
                        }
                    ],
                    Log: [
                        {
                            title: '系统管理',
                            pathSatus: 0
                        },
                        {
                            title: '日志管理',
                            pathSatus: 0,
                        }
                    ],
                    EventRuleSetting: [
                        {
                            title: '态势规则配置',
                            pathSatus: 0
                        },
                        {
                            title: '事件采集配置',
                            pathSatus: 0,
                        }
                    ],
                    EventStatusSetting: [
                        {
                            title: '态势规则配置',
                            pathSatus: 0
                        },
                        {
                            title: '状态采集规则',
                            pathSatus: 0,
                        }
                    ],
                    EventRuleFlow: [
                        {
                            title: '态势规则配置',
                            pathSatus: 0
                        },
                        {
                            title: '事件统计配置',
                            pathSatus: 0,
                        }
                    ],
                    EventStatusFlow: [
                        {
                            title: '态势规则配置',
                            pathSatus: 0
                        },
                        {
                            title: '状态统计配置',
                            pathSatus: 0,
                        }
                    ],
                    RuleStateFollow: [
                        {
                            title: '态势规则配置',
                            pathSatus: 0
                        },
                        {
                            title: '事件配置状态',
                            pathSatus: 0,
                        }
                    ],
                    EventTransmit: [
                        {
                            title: '态势规则配置',
                            pathSatus: 0
                        },
                        {
                            title: '事件扣分链路',
                            pathSatus: 0,
                        }
                    ],
                    SalePreview:[
                        {
                            title: '运营统计',
                            pathSatus: 0
                        },
                        {
                            title: '销售概览',
                            pathSatus: 0,
                        }
                    ],
                    Customer:[
                        {
                            title: '运营统计',
                            pathSatus: 0
                        },
                        {
                            title: '客户详情',
                            pathSatus: 0,
                        }
                    ],
                    FaultStatistics:[
                        {
                            title: '运营统计',
                            pathSatus: 0
                        },
                        {
                            title: '故障分析',
                            pathSatus: 0,
                        }
                    ],
                    AfterSale:[
                        {
                            title: '运营统计',
                            pathSatus: 0
                        },
                        {
                            title: '售后维修',
                            pathSatus: 0,
                        }
                    ],
                    PublicHealth:[
                        {
                            title: '综合采集',
                            pathSatus: 0
                        },
                        {
                            title: '公共卫生',
                            pathSatus: 0,
                        }
                    ],
                    Traffic:[
                        {
                            title: '综合采集',
                            pathSatus: 0
                        },
                        {
                            title: '违规信息',
                            pathSatus: 0,
                        }
                    ],
                    Environment:[
                        {
                            title: '综合采集',
                            pathSatus: 0
                        },
                        {
                            title: '环境数据',
                            pathSatus: 0,
                        }
                    ],
                    BusCompany:[
                        {
                            title: '车辆信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '公交公司',
                            pathSatus: 0,
                        }
                    ],
                    OrderBusCompany:[
                        {
                            title: '车辆信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '公交订单',
                            pathSatus: 0,
                        }
                    ],
                    Bus:[
                        {
                            title: '车辆信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '公交车',
                            pathSatus: 0,
                        }
                    ],
                    OrderBusDelivery:[
                        {
                            title: '车辆信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '车辆交付',
                            pathSatus: 0,
                        }
                    ],
                    BusStation:[
                        {
                            title: '营运信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '公交车站',
                            pathSatus: 0,
                        }
                    ],
                    BusPlanService:[
                        {
                            title: '车辆信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '营运计划',
                            pathSatus: 0,
                        }
                    ],
                    LogBusDrive:[
                        {
                            title: '日志信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '行驶日志',
                            pathSatus: 0,
                        }
                    ],
                    LogBusService:[
                        {
                            title: '日志信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '营运日志',
                            pathSatus: 0,
                        }
                    ],
                    InfoAdvertiseTarget:[
                        {
                            title: '日志信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '广告标签配置',
                            pathSatus: 0,
                        }
                    ],
                    InfoAdvertisePosition:[
                        {
                            title: '日志信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '广告位管理',
                            pathSatus: 0,
                        }
                    ],
                    LogAdvertiseShow:[
                        {
                            title: '日志信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '广告播放日志',
                            pathSatus: 0,
                        }
                    ],
                    LogBusDevice:[
                        {
                            title: '日志信息管理',
                            pathSatus: 0
                        },
                        {
                            title: '设备日志',
                            pathSatus: 0,
                        }
                    ]
                },
                levelList: null,
                fullflag: false,
                formAddVisible: false,
                user: {},
                dataForm: {
                    id: 0,
                    loginName: '',
                    password: '',
                    newPassword: '',
                    comfirmPassword: ''
                },
                dataRule: {
                    password: [{
                        validator: validatePassword,
                        trigger: 'blur'
                    }],
                    newPassword: [{
                        validator: validatePassword,
                        trigger: 'blur'
                    }],
                    comfirmPassword: [{
                        validator: validateComfirmPassword,
                        trigger: 'blur'
                    }]
                },
                isCollapse: '',
                bus_icon : 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk2NDMyMjc1NTQzIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQ1MzQiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNODUxLjIxOTgxNCA5MjIuNTUxMDg0djY2LjU3NTg1MWMwIDE5LjAyMTY3Mi0xNS44NTEzOTMgMzQuODczMDY1LTM0Ljg3MzA2NSAzNC44NzMwNjVoLTEwMS40NDg5MTZjLTE5LjAyMTY3MiAwLTM0Ljg3MzA2NS0xNS44NTEzOTMtMzQuODczMDY1LTM0Ljg3MzA2NXYtNjYuNTc1ODUxSDM0My45NzUyMzJ2NjYuNTc1ODUxYzAgMTkuMDIxNjcyLTE1Ljg1MTM5MyAzNC44NzMwNjUtMzQuODczMDY1IDM0Ljg3MzA2NUgyMDcuNjUzMjUxYy0xOS4wMjE2NzIgMC0zNC44NzMwNjUtMTUuODUxMzkzLTM0Ljg3MzA2NS0zNC44NzMwNjV2LTY2LjU3NTg1MWMtMzguMDQzMzQ0IDAtNjYuNTc1ODUxLTMxLjcwMjc4Ni02Ni41NzU4NTItNjYuNTc1ODUyVjE0NS44MzI4MTdjMC0zOC4wNDMzNDQgNjMuNDA1NTczLTY2LjU3NTg1MSAxMDEuNDQ4OTE3LTY2LjU3NTg1MSAwIDAgMTM2LjMyMTk4MS02Ni41NzU4NTEgMzA0LjM0Njc0OS02Ni41NzU4NTEgMTcxLjE5NTA0NiAwIDMzOS4yMTk4MTQgNjYuNTc1ODUxIDMzOS4yMTk4MTQgNjYuNTc1ODUxIDM4LjA0MzM0NCAwIDY2LjU3NTg1MSAzMS43MDI3ODYgNjYuNTc1ODUyIDY2LjU3NTg1MVY4NTUuOTc1MjMyYzAgMzQuODczMDY1LTMxLjcwMjc4NiA2Ni41NzU4NTEtNjYuNTc1ODUyIDY2LjU3NTg1MnpNMjU4LjM3NzcwOSA4MjEuMTAyMTY3YzQ3LjU1NDE4IDAgODUuNTk3NTIzLTM4LjA0MzM0NCA4NS41OTc1MjMtODUuNTk3NTIzUzMwNS45MzE4ODkgNjQ5LjkwNzEyMSAyNTguMzc3NzA5IDY0OS45MDcxMjFjLTQ3LjU1NDE4IDAtODUuNTk3NTIzIDM4LjA0MzM0NC04NS41OTc1MjMgODUuNTk3NTIzczM4LjA0MzM0NCA4NS41OTc1MjMgODUuNTk3NTIzIDg1LjU5NzUyM3pNNjQ4LjMyMTk4MSA3Ni4wODY2ODdoLTI2OS40NzM2ODRjLTE5LjAyMTY3MiAwLTM0Ljg3MzA2NSAxNS44NTEzOTMtMzQuODczMDY1IDM0Ljg3MzA2NSAwIDE5LjAyMTY3MiAxNS44NTEzOTMgMzQuODczMDY1IDM0Ljg3MzA2NSAzNC44NzMwNjVoMjY5LjQ3MzY4NGMxOS4wMjE2NzIgMCAzNC44NzMwNjUtMTUuODUxMzkzIDM0Ljg3MzA2NS0zNC44NzMwNjUtMy4xNzAyNzktMTkuMDIxNjcyLTE1Ljg1MTM5My0zNC44NzMwNjUtMzQuODczMDY1LTM0Ljg3MzA2NXogbTIwMi44OTc4MzMgMjAyLjg5NzgzM2MwLTE5LjAyMTY3Mi0xNS44NTEzOTMtMzQuODczMDY1LTM0Ljg3MzA2NS0zNC44NzMwNjVIMjA3LjY1MzI1MWMtMTkuMDIxNjcyIDAtMzQuODczMDY1IDE1Ljg1MTM5My0zNC44NzMwNjUgMzQuODczMDY1djIwMi44OTc4MzNjMCAxOS4wMjE2NzIgMTUuODUxMzkzIDM0Ljg3MzA2NSAzNC44NzMwNjUgMzQuODczMDY1aDYwOC42OTM0OThjMTkuMDIxNjcyIDAgMzQuODczMDY1LTE1Ljg1MTM5MyAzNC44NzMwNjUtMzQuODczMDY1VjI3OC45ODQ1MnpNNzY1LjYyMjI5MSA2NDkuOTA3MTIxYy00Ny41NTQxOCAwLTg1LjU5NzUyMyAzOC4wNDMzNDQtODUuNTk3NTIzIDg1LjU5NzUyM3MzOC4wNDMzNDQgODUuNTk3NTIzIDg1LjU5NzUyMyA4NS41OTc1MjMgODUuNTk3NTIzLTM4LjA0MzM0NCA4NS41OTc1MjMtODUuNTk3NTIzLTM4LjA0MzM0NC04NS41OTc1MjMtODUuNTk3NTIzLTg1LjU5NzUyM3oiIGZpbGw9IiNmZmZmZmYiIHAtaWQ9IjQ1MzUiPjwvcGF0aD48L3N2Zz4=',
            }
        },
        methods: {
            //获取登录用户
            handlerUser() {
                getLoginUser().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.user = res.data.data.user
                        this.dataForm.id = res.data.data.user.userId
                        this.dataForm.loginName = res.data.data.user.loginName
                    }
                })
            },
            //用户退出
            handleExit() {
                this.$confirm(`您确定要退出吗?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    logoutSystem().then(({
                                             data
                                         }) => {
                        if (data && data.code === 0) {
                            //删除seession的值
                            //sessionStorage.removeItem("token")
                            sessionStorage.clear()
                            //导航到登录页面
                            this.$router.push({
                                path: '/'
                            })
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作',
                        offset: 300
                    });
                })

            },
            dataFormSubmit() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        updateUserPassword(this.dataForm).then(({
                                                                    data
                                                                }) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    type: 'success',
                                    message: '密码修改成功，即将跳到登录页面.......',
                                    offset: 300
                                });
                                //删除seession的值
                                sessionStorage.removeItem("token")
                                //导航到登录页面
                                this.$router.push({
                                    path: '/'
                                })
                            } else {
                                this.$message({
                                    type: 'error',
                                    message: data.message,
                                    offset: 300
                                });
                            }
                        })
                    }
                })
            },
            handleFullscreen() {
                let main = document.body
                if (this.fullflag) {
                    if (document.exitFullscreen) {
                        document.exitFullscreen()
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen()
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen()
                    } else if (document.msExitFullscreen) {
                        document.msExitFullscreen()
                    }
                } else {
                    if (main.requestFullscreen) {
                        main.requestFullscreen()
                    } else if (main.mozRequestFullScreen) {
                        main.mozRequestFullScreen()
                    } else if (main.webkitRequestFullScreen) {
                        main.webkitRequestFullScreen()
                    } else if (main.msRequestFullscreen) {
                        main.msRequestFullscreen()
                    }
                }
                this.fullflag = !this.fullflag;
            },
            l_change() {
                if (this.isCollapse) {
                    this.isCollapse = false;
                    this.$root.$emit("eventName", false);
                } else {
                    this.isCollapse = true;
                    this.$root.$emit("eventName", true);
                }
            },
            getBreadcrumb() {
                this.levelList = this.pathData[this.$route.name]
            },
            clearCookie :function(){
                var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
                if (keys) {
                    for (var i =  keys.length; i--;)
                        document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString()
                }
            }
        },
        watch: {
            $route(route) {
                if (route.path.startsWith('/redirect/')) {
                    return
                }
                this.getBreadcrumb()
            }
        },
        created() {
            this.getBreadcrumb()
            this.handlerUser()
        },
        mounted : function(){
            this.clearCookie()
        }
    }
</script>

<style scoped>
    .header_con {
        display: flex;
        background: #0f95ff;
        background: -red;
        align-items: center;
        justify-content: space-between;
        padding: 0 20px 0 0;
        position: absolute;
        left: 0;
        top: 0;
        right: 0;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        /*border-bottom: 2px solid #0f95ff;*/
        /*box-shadow: 2px 2px 10px #000;*/
    }

    .header_con .logo {
        color: #fff;
        font-size: 18px;
        background: #0f95ff;
        width: 200px;
        box-sizing: border-box;
        text-align: center;
        /*line-height: 60px;*/
    }

    .header_con {
        color: #333333;
    }

    .full_btn {
        margin-top: 12px;
        margin-right: 20px;
    }

    .header_con >>> .el-submenu__title:hover {
        background-color: #0d0d0d00 !important;
    }

    .header_con >>> .el-menu--popup {
        background-color: #ffffff !important;
    }

    .el-menu .el-menu-item {
        color: #333 !important;
    }

    .full_btn i {
        color: #ffffff;
    }

    .full_btn i:hover {
        color: #2b7dbc;
    }

    .l_change {
        cursor: pointer;
        position: absolute;
        width: 22px;
        height: 22px;
        /*border-radius:100%;*/
        border-radius: 4px;
        border: none;
        line-height: 22px;
        left: 50%;
        top: 3px;
        margin-left: -13px;
        text-align: center;
        padding: 0;
        background: none;
        color: #fff;
        outline: none;
        z-index: 1;
    }

    .l_change i {
        font-size: 20px;
    }

    .l_change:hover {
        background: #2b7dbc;
        color: #fff;
    }

    .header_con >>> .el-submenu__title {
        color: #ffffff !important;
    }

    .header_con >>> .el-submenu__title i {
        color: #ffffff !important;
    }
</style>
