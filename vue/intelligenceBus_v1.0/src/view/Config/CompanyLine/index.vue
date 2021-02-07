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
                <p class="title">公交公司</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle(companyDetail.companyId)" size="small"
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
                <el-row class="card-title-box">
                    <el-col :span="5">
                        <div class="card-left-content">
                            <el-row >
                                <el-col :span="24" class="title-box">
                                    <div class="title-bg-box">
                                        <img :src="companyDetail.companySnapshot" alt="" width="100%" height="100%">
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24" class="title-text-box">
                                    <div class="title-text-content">{{companyDetail.companyName}}</div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="19">
                        <el-col :span="12">
                            <div class="card-right-content">
                                <el-row>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">公交公司 ：{{companyDetail.companyName}} </span>
                                        </div>
                                    </el-col>

                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">联系人：{{companyDetail.companyPerson }} </span>
                                        </div>
                                    </el-col>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">联系邮箱：{{companyDetail.companyEmail }} </span>
                                        </div>
                                    </el-col>
                                    <!--<el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">创建人：{{ companyDetail.createUserName }} </span>
                                        </div>
                                    </el-col>-->
                                </el-row>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="">
                                <el-row>
                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">统一信用代码 ：{{companyDetail.companyCode}} </span>
                                        </div>
                                    </el-col>

                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">所在地区 ：{{companyDetail.areaName}} </span>
                                        </div>
                                    </el-col>


                                    <el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">联系电话：{{companyDetail.companyPhone }} </span>
                                        </div>
                                    </el-col>
                                    <!--<el-col :span="24">
                                        <div class="mtop">
                                            <span class="text-public">创建时间：{{ companyDetail.createdDate }} </span>
                                        </div>
                                    </el-col>-->
                                </el-row>
                            </div>
                        </el-col>
                        <el-col :span="24">
                            <div class="mtop">
                                <span class="text-public">经营范围 ：{{companyDetail.companyScope}} </span>
                            </div>
                        </el-col>
                        <el-col :span="24">
                            <div class="mtop">
                                <span class="text-public">公司地址：{{companyDetail.companyAddress }} </span>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="mtop">
                                <span class="text-public">创建人：{{ companyDetail.createUserName }} </span>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="mtop">
                                <span class="text-public">创建时间：{{ companyDetail.createdDate }} </span>
                            </div>
                        </el-col>
                    </el-col>


                </el-row>
            </div>

            <!-- 公交公司弹窗 -->
            <OrderBusCompanyDialog v-show="OrderBusCompanyDialogVisible" ref="OrderBusCompanyDialogRef" @refreshDataList="getData(1)"></OrderBusCompanyDialog>
            <!-- 公交线路弹窗 -->
            <CompanyLineDialog v-show="CompanyLineDialogVisible" ref="CompanyLineDialogRef" @refreshDataList="getData(1)"></CompanyLineDialog>

            <!-- 公交线路批量删除 -->
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
            <!-- 公交线路删除、新增 -->
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
                <p class="title">公交线路</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addUpdateCompanyLine(null,companyId)" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>新增
                    </el-button>
                    <el-button type="danger" @click="handleDeleteOrderDetailAll" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除
                    </el-button>
                </div>
            </div>

            <!-- 公交线路列表 -->
            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="companyLineList" style="width: 100%" max-height="300"
                              @selection-change="handleSelectionChange1">
                        <el-table-column fixed type="selection" width="50px"></el-table-column>
                        <el-table-column fixed type="index" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="companyLineName" label="公交线路名称" min-width="140"></el-table-column>
                        <el-table-column prop="companyLineCode" label="公交线路编码" min-width="160"></el-table-column>
                        <el-table-column prop="summerStartTime" :formatter="formatTime1" label="夏季开始时间" min-width="160"></el-table-column>
                        <el-table-column prop="summerEndTime" :formatter="formatTime1" label="夏季结束时间" min-width="160"></el-table-column>
                        <el-table-column prop="summerFirstTime" :formatter="formatTime2" label="夏季首班时间" min-width="160"></el-table-column>
                        <el-table-column prop="summerLastTime" :formatter="formatTime2" label="夏季末班时间" min-width="160"></el-table-column>
                        <el-table-column prop="winterFirstTime" :formatter="formatTime2" label="冬季首班时间" min-width="160"></el-table-column>
                        <el-table-column prop="winterLastTime" :formatter="formatTime2" label="冬季末班时间" min-width="160"></el-table-column>
                        <el-table-column label="是否启用" width="70px">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1?'是':'否' }}</template>
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
                                           @click="addUpdateCompanyLine(scope.row.companyLineId,companyId)">
                                    <i class="el-icon-edit"></i>修改
                                </el-button>
                                <el-button size="mini" type="danger"
                                           @click="handleDelete(scope.row.companyLineId, scope.row.companyLineName)">
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
    import {getInfoBusCompanyDetail} from "@/api/parameter";
    import {getCompanyLineByCompanyId,deleteInfoCompanyLine,deleteInfoCompanyLineAll} from "@/api/parameter";

    import OrderBusCompanyDialog from "../BusCompany/Dialog.vue";
    import CompanyLineDialog from "./CompanyLineDialog.vue";

    export default {
        name: "CompanyLine",
        data() {
            return {
                OrderBusCompanyDialogVisible: false,
                CompanyLineDialogVisible: false,
                form: {
                    companyId: '',
                    name: "",
                    page: 1,
                    limit: 10
                },
                companyId: '',
                companyDetail: {
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
                companyLineList: [],
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
            CompanyLineDialog
        },
        activated: function () {
            //this.getData();
        },
        methods: {
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
                    deleteInfoCompanyLineAll(this.deleteAllForm).then((res) => {
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
                        this.deleteAllForm.ids.push(this.checkedList1[i].companyLineId)
                    }
                }
                console.log(this.deleteAllForm.ids);
            },
            goBack: function () {
                this.$router.push({
                    path: './BusCompany'
                })
            },
            //获取基础列表数据函数
            getData: function () {
                this.companyId = this.$route.query.companyId;
                this.form.companyId = this.companyId;
                console.log("this.companyId:"+this.companyId);
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                getInfoBusCompanyDetail(this.companyId).then((res) => {
                    loading.close();
                    if (res.data && res.data.code === 0) {
                        this.companyDetail = res.data.data.data
                    }
                });

                getCompanyLineByCompanyId(this.companyId).then((res) => {
                    if(res.data && res.data.code === 0){
                        this.companyLineList = res.data.data.list;
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
            addUpdateCompanyLine: function (companyLineId,companyId) {
                console.log("打开窗口,传参,companyLineId:"+companyLineId+" companyId:"+companyId);
                this.CompanyLineDialog = true;
                this.$nextTick(() => {
                    this.$refs.CompanyLineDialogRef.initData(companyLineId,companyId);
                });
            },
            //删除当前条的数据操作
            handleDelete: function (id, name) {
                console.log("删除:"+id);
                this.$confirm(`确定要删除"${name}"吗?`, "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    deleteInfoCompanyLine(id)
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
            formatTime1: function (row, column, cellValue, index) {
                return cellValue.substr(5,2) + '月' + cellValue.substr(8,2) + '日';
            },
            formatTime2: function (row, column, cellValue, index) {
                return cellValue.substr(11,2) + '点' + cellValue.substr(14,2) + '分';
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
    .card-left-content {
        width: 100%;
        border-right: 1px solid #ccc;
    }
</style>
