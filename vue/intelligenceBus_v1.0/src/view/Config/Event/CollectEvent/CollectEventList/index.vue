<template>
    <div>
        <div class="cotroller_box">
            <el-form :inline="true" :model="formQuery" class="demo-form-inline" label-width="120px">
                <el-form-item label="事件采集名称">
                    <el-input v-model="formQuery.collectEvent" placeholder="请输入事件采集名称" clearable   maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="事件级别" prop="eventLevelId">
                    <el-select v-model="formQuery.eventLevelId" placeholder="请选择事件级别" clearable>
                        <el-option :label="item.eventLevelName" :value="item.eventLevelId" :key="item.eventLevelId"
                                   v-for="(item, index) in conditionQuery.eventLevelNameList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="事件类型" prop="eventTypeId">
                    <el-select v-model="formQuery.eventTypeId" placeholder="请选择事件类型" clearable>
                        <el-option :label="item.eventTypeName" :value="item.eventTypeId" :key="item.eventTypeId"
                                   v-for="(item, index) in conditionQuery.eventTypeNameList">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="事件标签" prop="eventTargetId">
                    <el-select v-model="formQuery.eventTargetId" placeholder="请选择事件标签" clearable>
                        <el-option :label="item.eventTargetName" :value="item.eventTargetId" :key="item.eventTargetId"
                                   v-for="(item, index) in conditionQuery.eventTargetNameList">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="设备类型" prop="deviceTypeId">
                    <el-select v-model="formQuery.deviceTypeId" placeholder="请选择设备类型" clearable>
                        <el-option :label="item.deviceTypeName" :value="item.deviceTypeId" :key="item.deviceTypeId"
                                   v-for="(item, index) in conditionQuery.deviceTypeNameList">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item style="margin-top: -1px;">
                    <el-button type="primary" @click="getData(1)" size="small"><i class="fa fa-search"
                                                                                  style="margin-right: 4px;font-size:14px;"></i>查询
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller">
                <!--<span class="table_title_span"></span>-->
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1"
                     xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200">
                    <path
                        d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z"
                        fill="#2C91E0" p-id="2552"></path>
                    <path
                        d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z"
                        fill="#2C91E0" p-id="2553"></path>
                </svg>
                <p class="title">事件采集列表</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle()" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加
                    </el-button>
                    <el-button type="danger" @click="handleDeleteAll" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除
                    </el-button>
                    <el-button type="warning" @click="handleReload" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新
                    </el-button>
                </div>
            </div>
            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="tableData" style="width: 100%" max-height="450"
                              @selection-change="handleSelectionChange">
                        <el-table-column fixed type="selection" width="50px"></el-table-column>
                        <el-table-column fixed type="index" :index="tableIndex" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="collectEvent" label="采集事件名称" min-width="150"></el-table-column>
                        <el-table-column prop="collectCode" label="采集事件编码" width="100"></el-table-column>
                        <el-table-column prop="eventLevelName" label="事件级别" min-width="100"></el-table-column>
                        <el-table-column prop="eventTypeName" label="事件类型" width="100"></el-table-column>
                        <el-table-column prop="eventTargetName" label="事件标签" min-width="150"></el-table-column>
                        <el-table-column prop="deviceTypeName" label="设备类型" min-width="120"></el-table-column>
                        <el-table-column prop="eventType" label="事件大类" min-width="100"></el-table-column>
                        <el-table-column prop="eventDetail" label="事件小类" min-width="100"></el-table-column>
                        <el-table-column prop="descriptionContent" label="事件描述" min-width="160"></el-table-column>
                        <el-table-column label="是否启用" width="80">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1 ? '是' : '否' }}</template>
                        </el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createdUserName}}</span><br>
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
                                <el-button size="mini" type="success" @click="goToDetail(scope.row.collectEventId)">
                                    <i class="el-icon-edit"></i>查看
                                </el-button>
                                <el-button size="mini" type="danger"
                                           @click="handleDelete(scope.row.collectEventId, scope.row.collectEvent)">
                                    <i class="el-icon-delete"></i>删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="table.currPage"
                        :page-sizes="[10, 20, 50, 100]"
                        :page-size="table.pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="table.totalCount"
                        background
                        style="width:100%;text-align:right;margin-top:10px;"></el-pagination>
                </div>
            </div>
            <!-- 弹窗, 新增 / 修改 -->
            <Dialog v-show="DialogVisible" ref="Dialogs" @refreshDataList="getData(1)"></Dialog>
            <el-dialog
                title="输入用户密码"
                :visible.sync="visible"
                width="30%">
                <el-form :model="deleteAllForm">
                    <el-form-item label="">
                        <el-input v-model="deleteAllForm.validatePassword" placeholder="请输入用户密码" maxlength="64"
                                  type="password"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="visible = false">取 消</el-button>
                    <el-button type="danger" @click="dataFormSubmit">删除</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>
<script>
    import {getInfoCollectEventPage, deleteInfoCollectEvent, deleteInfoCollectEventAll,
        getInfoEventLevelList,getInfoEventTypeList,getInfoEventTargetList} from "@/api/event";
    import {getDeviceTypeS} from '@/api/selectionApi';
    import Dialog from "./Dialog";

    export default {
        name: "InfoCollectEventList",
        data() {
            return {
                conditionQuery: {
                    eventLevelNameList: [],
                    eventTypeNameList: [],
                    eventTargetNameList: [],
                    deviceTypeNameList: []
                },
                formQuery: {
                    collectEvent: '',
                    eventLevelId:'',
                    eventTypeId:'',
                    eventTargetId:'',
                    deviceTypeId:'',
                    page: 1,
                    limit: 10
                },
                tableData: [],
                table: {},
                DialogVisible: false,
                visible: false,
                checkedList: [],
                deleteAllForm: {
                    validatePassword: '',
                    ids: []
                }
            };
        },
        components: {
            Dialog
        },
        activated: function () {
            this.getData();
        },
        methods: {
            tableIndex(index) {
                return (this.formQuery.page - 1) * this.formQuery.limit + index + 1
            },
            handleReload: function () {
                this.getData(1);
            },
            dataFormSubmit: function () {
                if (this.deleteAllForm.validatePassword == '') {
                    this.$message({
                        message: '用户密码不能为空,请输入用户密码！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                } else {
                    console.log(this.deleteAllForm);
                    deleteInfoCollectEventAll(this.deleteAllForm).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                offset: 300,
                                duration: 1500,
                                onClose: () => {
                                    this.visible = false
                                    this.getData(1);
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
            handleDeleteAll: function () {
                if (this.checkedList.length == 0) {
                    this.$message({
                        message: '请选择删除的数据！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                } else {
                    this.visible = true;
                    this.deleteAllForm.validatePassword = '';
                }

            },
            handleSelectionChange: function (val) {
                console.log('ok');
                this.checkedList = val;
                this.deleteAllForm.ids = [];
                if (this.checkedList.length > 0) {
                    for (var i = 0; i < this.checkedList.length; i++) {
                        this.deleteAllForm.ids.push(this.checkedList[i].collectEventId)
                    }
                }
                console.log(this.deleteAllForm.ids);
            },
            goToDetail: function (id) {
                sessionStorage.setItem("objectId", id)
                this.$router.push({
                    path: './Card',
                    query: {
                        collectEventId: id
                    }

                })
            },
            //获取基础列表数据函数
            getData: function (page) {
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                if (page) {
                    this.formQuery.page = page;
                }
                getInfoCollectEventPage(this.formQuery).then((res) => {
                    loading.close();
                    if (res.data && res.data.code === 0) {
                        console.log(res.data);
                        this.tableData = res.data.data.page.list;
                        this.table = res.data.data.page;
                    }
                });
            },
            //更改每页的条数触发的table渲染
            handleSizeChange: function (val) {
                this.formQuery.limit = val;
                this.getData(1);
            },
            //点击分页渲染页面函数
            handleCurrentChange: function (val) {
                this.formQuery.page = val;
                this.getData()
            },

            //增加或修改
            addOrUpdateHandle: function (id) {
                this.DialogVisible = true;
                this.$nextTick(() => {
                    this.$refs.Dialogs.initData(id);
                });
            },
            //删除当前条的数据操作
            handleDelete: function (id, name) {
                this.$confirm(`确定要删除"${name}"吗?`, "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    deleteInfoCollectEvent(id)
                        .then((res) => {
                            console.log('ok');
                            if (res.data && res.data.code === 0) {
                                console.log(res);
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
                })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消删除"
                        });
                    });
            },
            init: function () {
                getInfoEventLevelList().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventLevelNameList = res.data.data.list
                    }
                })
                getInfoEventTypeList().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventTypeNameList = res.data.data.list
                    }
                })
                getInfoEventTargetList('master').then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventTargetNameList = res.data.data.list
                    }
                })

                getDeviceTypeS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.deviceTypeNameList = res.data.data.selection
                    }
                })

            },
        },
        mounted: function () {
            this.init();
            this.getData();
        }
    };
</script>

<style scoped>
    .el-dialog .el-dialog__body, .el-dialog .el-dialog__header, .el-dialog__header {
        border-bottom: 1px solid #fff !important;
    }
</style>
