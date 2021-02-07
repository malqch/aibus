<template>
    <div>
        <div class="cotroller_box">
            <el-form :inline="true" :model="formQuery" class="demo-form-inline" label-width="120px">
                <el-form-item label="车辆VIN码" prop="vinCode">
                    <el-select v-model="formQuery.vinCode" filterable placeholder="请选择车辆VIN码" clearable>
                        <el-option :label="item"
                        :value="item"
                        :key="item"
                        v-for="(item, index) in conditionQuery.vinCodeList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车牌号" prop="plateCode">
                    <el-select v-model="formQuery.plateCode" filterable placeholder="请选择车牌号" clearable>
                        <el-option :label="item"
                                   :value="item"
                                   :key="item"
                                   v-for="(item, index) in conditionQuery.plateCodeList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="采集内容">
                    <el-input v-model="formQuery.collectFault" placeholder="故障采集内容" clearable></el-input>
                </el-form-item>

                <el-form-item label="故障类型" prop="faultTypeId">
                    <el-select v-model="formQuery.faultTypeId" placeholder="请选择故障类型" clearable>
                        <el-option :label="item.faultTypeName" :value="item.faultTypeId" :key="item.faultTypeId"
                                   v-for="(item, index) in conditionQuery.faultTypeNameList">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="故障标签" prop="faultTargetId">
                    <el-select v-model="formQuery.faultTargetId" placeholder="请选择故障标签" clearable>
                        <el-option :label="item.faultTargetName" :value="item.faultTargetId" :key="item.faultTargetId"
                                   v-for="(item, index) in conditionQuery.faultTargetNameList">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="故障级别" prop="faultLevelId">
                    <el-select v-model="formQuery.faultLevelId" placeholder="请选择故障级别" clearable>
                        <el-option :label="item.faultLevelName" :value="item.faultLevelId" :key="item.faultLevelId"
                                   v-for="(item, index) in conditionQuery.faultLevelNameList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="开始时间">
                    <el-date-picker v-model="formQuery.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间">
                    </el-date-picker>
                </el-form-item>

                <el-form-item label="结束时间">
                    <el-date-picker v-model="formQuery.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择结束时间" >
                    </el-date-picker>
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
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1"
                     xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200">
                    <path
                        d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z"
                        fill="#2C91E0" p-id="2552"></path>
                    <path
                        d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z"
                        fill="#2C91E0" p-id="2553"></path>
                </svg>
                <p class="title">故障日志列表</p>
                <div class="btn_list">
<!--                    <el-button type="primary" @click="addOrUpdateHandle()" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加
                    </el-button>
                    <el-button type="danger" @click="handleDeleteAll" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除
                    </el-button>-->
                    <el-button type="warning" @click="handleReload" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新
                    </el-button>
                </div>
            </div>
            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="tableData" max-height="450" style="width: 100%"
                              @selection-change="handleSelectionChange">
                        <el-table-column fixed type="selection" width="50px"></el-table-column>
                        <el-table-column fixed type="index" :index="tableIndex" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="vinCode" label="车辆VIN码" width="160"></el-table-column>
                        <el-table-column prop="plateCode" label="车牌号" width="160"></el-table-column>
                        <el-table-column prop="collectFault" label="故障采集内容" min-width="120"></el-table-column>
                        <el-table-column prop="collectCode" label="故障采集编码" min-width="120"></el-table-column>
                        <el-table-column prop="faultTypeName" label="故障类型" min-width="120"></el-table-column>
                        <el-table-column prop="faultTargetName" label="故障标签" width="120"></el-table-column>
                        <el-table-column prop="faultLevelName" label="故障级别" width="120"></el-table-column>

                        <el-table-column label="是否启用" width="80">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1 ? '是' : '否' }}</template>
                        </el-table-column>
                        <el-table-column label="创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createdDate}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="success" @click="goToDetail(scope.row.faultDetailId)">
                                    <i class="el-icon-edit"></i>查看
                                </el-button>
<!--                                <el-button size="mini" type="danger"
                                           @click="handleDelete(scope.row.collectFaultId  , scope.row.collectFault)">
                                    <i class="el-icon-delete"></i>删除
                                </el-button>-->
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
<!--            <Dialog v-show="DialogVisible" ref="Dialogs" @refreshDataList="getData(1)"></Dialog>
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
            </el-dialog>-->
        </div>
    </div>
</template>
<script>
    import { getInfoFaultTypeListAll,getInfoFaultTargetListAll,getInfoFaultLevelListAll, getInfoFaultDetailPage} from "@/api/fault";
    import {getPlateCodeListByCompany,getVinCodeListByCompany} from "@/api/selectionApi"
    export default {
        name: "FaultDetail",
        data() {
            return {
                visible: false,
                conditionQuery: {
                    faultLevelNameList: [],
                    faultTypeNameList: [],
                    faultTargetNameList: [],
                    vinCodeList:[],
                    plateCodeList:[]
                },
                formQuery: {
                    collectFault: "",
                    vinCode: "",
                    plateCode:"",
                    startTime: "",
                    endTime: "",
                    faultLevelId:'',
                    faultTypeId:'',
                    faultTargetId:'',
                    page: 1,
                    limit: 10
                },
                queryDt:'',
                tableData: [

                ],
                table: {
                    currPage: 1,
                    totalPage: 1,
                    pageSize: 10,
                    totalCount: 1
                },
                DialogVisible: false,
                checkedList: [],
                deleteAllForm: {
                    validatePassword: '',
                    ids: []
                }
            };
        },
        components: {

        },
        activated: function () {
            //this.getData();
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
                    deleteInfoFaultCollectAll(this.deleteAllForm).then((res) => {
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
                console.log(this.checkedList);
                this.deleteAllForm.ids = [];
                if (this.checkedList.length > 0) {
                    for (var i = 0; i < this.checkedList.length; i++) {
                        this.deleteAllForm.ids.push(this.checkedList[i].collectFaultId)
                    }
                }
                console.log(this.deleteAllForm.ids);
            },
            goToDetail: function (id) {
                this.$router.push({
                    path: './FaultAttach',
                    query: {
                        faultDetailId: id
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
                    this.formQuery.page = 1;
                }
                getInfoFaultDetailPage(this.formQuery).then((res) => {
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
                    deleteInfoFaultCollect(id)
                        .then((res) => {
                            console.log('ok');
                            if (res.data && res.data.code == 0) {
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
                getInfoFaultTypeListAll().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.conditionQuery.faultTypeNameList = res.data.data.list
                    }
                })
                getInfoFaultTargetListAll().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.conditionQuery.faultTargetNameList = res.data.data.list
                    }
                })
                getInfoFaultLevelListAll().then((res) => {
                    if (res.data && res.data.code == 0) {
                        this.conditionQuery.faultLevelNameList = res.data.data.list
                    }
                })
                getVinCodeListByCompany().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.vinCodeList = res.data.data.list
                    }
                })
                getPlateCodeListByCompany().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.plateCodeList = res.data.data.list
                    }
                })
            }
        },
        mounted: function () {
            this.init();
            this.getData();
        }
    };
</script>

<style scoped>

</style>
