<!--
 @Author: psp
 @Filename: index.vue
 @ProjectName: aibus-manage
 @Mail:
 @Date: 2020-08-28
 @file-description: 公交订单详情页面
-->
<template>
    <div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller">
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1"
                     xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200">
                    <path
                        d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z"
                        fill="#2C91E0" p-id="2552"></path>
                    <path
                        d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z"
                        fill="#2C91E0" p-id="2553"></path>
                </svg>
                <p class="title">公交订单</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle(orderDetail.orderId)" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>修改
                    </el-button>
                    <el-button type="warning" @click="handleReload" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新
                    </el-button>
                    <el-button type="primary" @click="goBack" size="small" plain
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class=" fa fa-mail-reply-all"></i>返回
                    </el-button>
                </div>
            </div>
            <div class="table_div card-text">
                <el-row class=" card-title-box">
                    <el-col :span="5">
                        <div class="card-left-content">
                            <el-row>
                                <el-col :span="24" class="title-box">
                                    <div class="title-bg-box">
                                        <img :src="orderDetail.factorySnapshot" alt="" width="100%" height="100%">
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24" class="title-text-box">
                                    <div class="title-text-content">{{orderDetail.companyName}}</div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="19">
                        <el-col :span="12" class="">
                            <div>
                                <el-row>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">公交公司 ：{{orderDetail.companyName}} </span>
                                        </div>
                                    </el-col>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">订单编号：{{orderDetail.orderCode }} </span>
                                        </div>
                                    </el-col>

                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">联系人：{{orderDetail.companyPerson }} </span>
                                        </div>
                                    </el-col>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">签订时间：{{orderDetail.orderDate }} </span>
                                        </div>
                                    </el-col>

                                </el-row>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="">
                                <el-row>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">公司信用代码 ：{{orderDetail.companyCode}} </span>
                                        </div>
                                    </el-col>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">公交车厂 ：{{orderDetail.factoryName}} </span>
                                        </div>
                                    </el-col>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">联系电话：{{orderDetail.companyPhone }} </span>
                                        </div>
                                    </el-col>

                                </el-row>
                            </div>
                        </el-col>
                        <el-col :span="24">
                            <el-col :span="24">
                                <div class="mtop">
                                    <span class="text-public">经营范围 ：{{orderDetail.companyScope}} </span>
                                </div>
                            </el-col>
                            <el-col :span="24">
                                <div class="mtop">
                                    <span class="text-public">公司地址：{{orderDetail.companyAddress }} </span>
                                </div>
                            </el-col>
                        </el-col>
                    </el-col>

                </el-row>
            </div>

            <!-- 公交订单交付弹窗 -->
            <OrderBusCompanyDialog v-show="OrderBusCompanyDialogVisible" ref="OrderBusCompanyDialogRef" @refreshDataList="getData(1)"></OrderBusCompanyDialog>
            <!-- 订单详情交付弹窗 -->
            <OrderBusCompanyDetailDialog v-show="OrderBusCompanyDetailDialogVisible" ref="OrderBusCompanyDetailDialogRef" @refreshDataList="getData(1)"></OrderBusCompanyDetailDialog>
            <!-- 订单交付弹窗 -->
            <OrderCompanyDeliveryDialog v-show="OrderCompanyDeliveryDialogVisible" ref="OrderCompanyDeliveryDialogRef" @refreshDataList="getData(1)"></OrderCompanyDeliveryDialog>

            <!-- 订单详情批量删除 -->
            <el-dialog
                title="输入用户密码"
                :visible.sync="visible1"
                width="30%">
                <el-form :model="deleteAllForm">
                    <el-form-item label="">
                        <el-input v-model="deleteAllForm.validatePassword" placeholder="请输入用户密码" maxlength="64"
                                  type="password"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="visible1 = false">取 消</el-button>
                    <el-button type="danger" @click="dataFormSubmit1">删除</el-button>
                </span>
            </el-dialog>
            <!-- 订单详情删除、新增 -->
            <div class="show_table_controller">
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1"
                     xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200">
                    <path
                        d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z"
                        fill="#2C91E0" p-id="2552"></path>
                    <path
                        d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z"
                        fill="#2C91E0" p-id="2553"></path>
                </svg>
                <p class="title">订单详情</p><p class="title" style="font-size: 14px;color: #909399">（添加“公交订单”后新增订单详情信息）</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addUpdateOrderDetail(null,orderId)" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>新增
                    </el-button>
                    <el-button type="danger" @click="handleDeleteOrderDetailAll" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除
                    </el-button>
                </div>
            </div>

            <!-- 订单详情表列表 -->
            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="orderBusCompanyDetailList" style="width: 100%" max-height="300"
                              @selection-change="handleSelectionChange1">
                        <el-table-column fixed type="selection" width="50px"></el-table-column>
                        <el-table-column fixed type="index" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="busTypeName" label="公交车型" min-width="140"></el-table-column>
                        <el-table-column prop="orderDetailNum" label="订单数量" min-width="120"></el-table-column>
                        <el-table-column prop="orderDetailDesc" label="说明" min-width="140" :show-overflow-tooltip="false">
                            <template slot-scope="scope">
                                <el-tooltip class="item" effect="dark" placement="top">
                                    <div v-html="scope.row.orderDetailDesc" slot="content"></div>
                                    <div class="lineCls">{{changeBr(scope.row.orderDetailDesc)}}</div>
                                </el-tooltip>
                            </template>
                        </el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createUserName}}</span><br>
                                <span>{{scope.row.createdDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="修改人/修改时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.modifiedUserName}}</span><br>
                                <span>{{scope.row.modifiedDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="warning"
                                           @click="addUpdateOrderDetail(scope.row.orderDetailId,orderId)">
                                    <i class="el-icon-edit"></i>修改
                                </el-button>
                                <el-button size="mini" type="danger"
                                           @click="handleDelete(scope.row.orderDetailId, null)">
                                    <i class="el-icon-delete"></i>删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>

            <!-- 订单交付批量删除 -->
            <el-dialog
                title="输入用户密码"
                :visible.sync="visible2"
                width="30%">
                <el-form :model="deleteAllForm">
                    <el-form-item label="">
                        <el-input v-model="deleteAllForm.validatePassword" placeholder="请输入用户密码" maxlength="64"
                                  type="password"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="visible1 = false">取 消</el-button>
                    <el-button type="danger" @click="dataFormSubmit2">删除</el-button>
                </span>
            </el-dialog>
            <!-- 订单交付删除、新增 -->
            <div class="show_table_controller">
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1"
                     xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200">
                    <path
                        d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z"
                        fill="#2C91E0" p-id="2552"></path>
                    <path
                        d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z"
                        fill="#2C91E0" p-id="2553"></path>
                </svg>
                <p class="title">订单交付</p><p class="title" style="font-size: 14px;color: #909399">（添加“车辆交付”前新增订单交付信息）</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addUpdateOrderCompanyDelivery(null,orderId)" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>新增
                    </el-button>
                    <el-button type="danger" @click="handleDeleteOrderCompanyDeliveryAll" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除
                    </el-button>
                </div>
            </div>

            <!-- 订单交付列表 -->
            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="orderBusCompanyDeliveryList" style="width: 100%" max-height="300"
                              @selection-change="handleSelectionChange2">
                        <el-table-column fixed type="selection" width="50px"></el-table-column>
                        <el-table-column fixed type="index" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="busTypeName" label="公交车型" min-width="140"></el-table-column>
                        <el-table-column prop="orderDeliveryNum" label="交付数量" min-width="80"></el-table-column>
                        <el-table-column prop="orderDeliveryDate" label="交付日期" min-width="120"></el-table-column>
                        <el-table-column prop="orderDetailDesc" label="说明" min-width="120"  :show-overflow-tooltip="false">
                            <template slot-scope="scope">
                                <el-tooltip class="item" effect="dark" placement="top">
                                    <div v-html="scope.row.orderDetailDesc" slot="content"></div>
                                    <div class="lineCls">{{changeBr(scope.row.orderDetailDesc)}}</div>
                                </el-tooltip>
                            </template>
                        </el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createUserName}}</span><br>
                                <span>{{scope.row.createdDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="修改人/修改时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.modifiedUserName}}</span><br>
                                <span>{{scope.row.modifiedDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="warning"
                                           @click="addUpdateOrderCompanyDelivery(scope.row.companyDeliveryId,orderId)">
                                    <i class="el-icon-edit"></i>修改
                                </el-button>
                                <el-button size="mini" type="danger"
                                           @click="handleDelete1(scope.row.companyDeliveryId, null)">
                                    <i class="el-icon-delete"></i>删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {getOrderBusCompanyDetail} from "@/api/parameter";
    import {getOrderBusCompanyDetailByOrderId,deleteOrderBusCompanyDetailAll,deleteOrderBusCompanyDetail,saveOrderBusCompanyDetail, editOrderBusCompanyDetail, getOrderBusCompanyDetailSingle} from "@/api/parameter";
    import {getOrderCompanyDeliveryByOrderId,deleteOrderCompanyDelivery,deleteOrderCompanyDeliveryAll} from "@/api/parameter";
    import OrderBusCompanyDialog from "../OrderBusCompany/Dialog.vue";
    import OrderBusCompanyDetailDialog from "../OrderBusCompanyDetail/Dialog.vue";
    import OrderCompanyDeliveryDialog from "../OrderCompanyDelivery/Dialog.vue";

    export default {
        name: "OrderBusCompanyDetail",
        data() {
            return {
                OrderBusCompanyDialogVisible: false,
                OrderBusCompanyDetailDialogVisible: false,
                OrderCompanyDeliveryDialogVisible: false,
                form: {
                    name: "",
                    page: 1,
                    limit: 10
                },
                orderId: '',
                orderDetail: {
                    orderId: '',
                    orderCode: '',
                    factoryId: '',
                    factoryName: '',
                    factorySnapshot: '',
                    companyId: '',
                    companyName: '',
                    orderDate: '',
                    createdDate: '',
                    createUserName: '',
                    modifiedUserName: ''
                },
                orderBusCompanyDetailList: [],
                orderBusCompanyDeliveryList: [],
                DialogVisible: false,
                visible1: false,
                visible2: false,
                checkedList1: [],
                checkedList2: [],
                deleteAllForm: {
                    validatePassword: '',
                    ids: []
                },
                formQuery1 : {
                    restaurantId : '',
                    page: 1,
                    limit : 10
                },
                formQuery2 : {
                    restaurantId : '',
                    page: 1,
                    limit : 10
                },
                DialogFlag : false
            };
        },
        components: {
            OrderBusCompanyDialog,
            OrderBusCompanyDetailDialog,
            OrderCompanyDeliveryDialog
        },
        activated: function () {
            //this.getData();
        },
        methods: {
            changeBr : function(val){
                if(val){
                    return val.replace(/<br\/>/g, '\n')
                }

            },
            //更改每页的条数触发的table渲染
            handleSizeChange: function (val) {
                this.formQuery.limit = val;
                this.getTableData(1);
            },
            //点击分页渲染页面函数
            handleCurrentChange: function (val) {
                this.formQuery.page = val;
                console.log(this.formQuery.page)
                this.getTableData()
            },
            handleReload: function () {
                this.getData();
            },
            dataFormSubmit1: function () {
                if (this.deleteAllForm.validatePassword == '') {
                    this.$message({
                        message: '用户密码不能为空,请输入用户密码！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                } else {
                    console.log(this.deleteAllForm);
                    deleteOrderBusCompanyDetailAll(this.deleteAllForm).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                offset: 300,
                                duration: 1500,
                                onClose: () => {
                                    this.visible1 = false
                                    this.getData();
                                }
                            })
                        } else {
                            this.$message({
                                message: res.data.message,
                                type: 'error',
                                offset: 300
                            })
                        }
                    })
                }
            },
            dataFormSubmit2: function () {
                if (this.deleteAllForm.validatePassword == '') {
                    this.$message({
                        message: '用户密码不能为空,请输入用户密码！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                } else {
                    console.log(this.deleteAllForm);
                    deleteOrderCompanyDeliveryAll(this.deleteAllForm).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                offset: 300,
                                duration: 1500,
                                onClose: () => {
                                    this.visible2 = false
                                    this.getData();
                                }
                            })
                        } else {
                            this.$message({
                                message: res.data.message,
                                type: 'error',
                                offset: 300
                            })
                        }
                    })
                }
            },
            handleDeleteOrderDetailAll: function () {
                console.log("批量删除订单详情");
                console.log(this.checkedList1);
                if (this.checkedList1.length == 0) {
                    this.$message({
                        message: '请选择删除的数据！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                } else {
                    this.visible1 = true;
                    this.deleteAllForm.validatePassword = '';
                }

            },
            handleDeleteOrderCompanyDeliveryAll: function () {
                if (this.checkedList2.length == 0) {
                    this.$message({
                        message: '请选择删除的数据！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                } else {
                    this.visible2 = true;
                    this.deleteAllForm.validatePassword = '';
                }

            },
            handleSelectionChange1: function (val) {
                console.log('订单详情批量删除选中');
                this.checkedList1 = val;
                this.deleteAllForm.ids = [];
                if (this.checkedList1.length > 0) {
                    for (var i = 0; i < this.checkedList1.length; i++) {
                        this.deleteAllForm.ids.push(this.checkedList1[i].orderDetailId)
                    }
                }
                console.log(this.deleteAllForm.ids);
            },
            handleSelectionChange2: function (val) {
                console.log('订单交付批量删除选中');
                this.checkedList2 = val;
                this.deleteAllForm.ids = [];
                if (this.checkedList2.length > 0) {
                    for (var i = 0; i < this.checkedList2.length; i++) {
                        this.deleteAllForm.ids.push(this.checkedList2[i].companyDeliveryId)
                    }
                }
                console.log(this.deleteAllForm.ids);
            },
            goBack: function () {
                this.$router.push({
                    path: './OrderBusCompany'
                })
            },
            //获取基础列表数据函数
            getData: function () {
                this.orderId = this.$route.query.orderId;
                console.log("this.orderId:"+this.orderId);
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                getOrderBusCompanyDetail(this.orderId).then((res) => {
                    loading.close();
                    if (res.data && res.data.code === 0) {
                        this.orderDetail = res.data.data.data
                    }
                });

                getOrderBusCompanyDetailByOrderId(this.orderId).then((res) => {
                    if(res.data && res.data.code === 0){
                        this.orderBusCompanyDetailList = res.data.data.list;
                    }
                });

                getOrderCompanyDeliveryByOrderId(this.orderId).then((res) => {
                    if(res.data && res.data.code === 0){
                        this.orderBusCompanyDeliveryList = res.data.data.list;
                    }
                });

            },
            //修改主订单
            addOrUpdateHandle: function (id) {
                this.OrderBusCompanyDialogVisible = true;
                this.$nextTick(() => {
                    this.$refs.OrderBusCompanyDialogRef.initData(id);
                });
            },
            //增加或者修改订单详细
            addUpdateOrderDetail: function (orderDetailId,orderId) {
                console.log("打开窗口,传参,orderDetailId:"+orderDetailId+" orderId:"+orderId);
                this.OrderBusCompanyDetailDialog = true;
                this.$nextTick(() => {
                    this.$refs.OrderBusCompanyDetailDialogRef.initData(orderDetailId,orderId);
                });
            },
            //增加或者修改订单交付
            addUpdateOrderCompanyDelivery: function (companyDeliveryId,orderId) {
                console.log("打开窗口,传参,companyDeliveryId:"+companyDeliveryId+" orderId:"+orderId);
                this.OrderCompanyDeliveryDialog = true;
                this.$nextTick(() => {
                    this.$refs.OrderCompanyDeliveryDialogRef.initData(companyDeliveryId,orderId);
                });
            },
            //删除当前条的数据操作
            handleDelete: function (id, name) {
                console.log("删除:"+id);
                this.$confirm(`确定要删除该条记录吗?`, "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    deleteOrderBusCompanyDetail(id)
                        .then((res) => {
                            if (res.data && res.data.code === 0) {
                                this.$message({
                                    message: "操作成功",
                                    type: "success",
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.getData(1);
                                    }
                                });
                            } else {
                                this.$message({
                                    message: res.data.message,
                                    type: "error",
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.getData(1);
                                    }
                                });
                            }
                        });
                }).catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除"
                    });
                });
            },
            handleDelete1: function (id, name) {
                this.$confirm(`确定要删除该条记录吗?`, "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    deleteOrderCompanyDelivery(id)
                        .then((res) => {
                            if (res.data && res.data.code === 0) {
                                this.$message({
                                    message: "操作成功",
                                    type: "success",
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.getData();
                                    }
                                });
                            } else {
                                this.$message({
                                    message: res.data.message,
                                    type: "error",
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.getData();
                                    }
                                });
                            }
                        });
                }).catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除"
                    });
                });
            }
        },
        mounted: function () {
            this.getData();
        }
    };
</script>
<style scoped>
    .table_div {
        margin-bottom: 20px;
    }
    .card-left-content{
        width: 100%;
        border-right: 1px solid #ccc;
    }
    .lineCls {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
</style>
