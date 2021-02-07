<template>
    <div>
        <div class="cotroller_box">
            <el-form :inline="true" :model="formQuery" class="demo-form-inline" label-width="120px">
<!--                <el-form-item label="校车公司" prop="companyId">-->
<!--                    <el-select v-model="formQuery.companyId" filterable placeholder="请选择校车公司" clearable>-->
<!--                        <el-option :label="item.companyName" :value="item.companyId" :key="item.companyId"-->
<!--                                   v-for="(item, index) in conditionQuery.companyList"></el-option>-->
<!--                    </el-select>-->
<!--                </el-form-item>-->
                <el-form-item label="线路名称">
                    <el-input v-model="formQuery.companyLineName" placeholder="请输入线路名称" style="float: left;width:190px;margin-left:10px;height:42px;" clearable></el-input>&nbsp;&nbsp;&nbsp;&nbsp;
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
                <p class="title">校车线路列表</p>
                <div class="btn_list">
<!--                    <el-tooltip content="通过“自动添加”可完成校车线路信息、校车车站信息录入" placement="top-start">-->
<!--                        <el-button type="success" @click="addBatchHandle()" size="small"-->
<!--                                   style="float: right;margin-right:10px;margin-left:0px !important;">-->
<!--                            <i class="el-icon-document"></i>自动添加-->
<!--                        </el-button>-->
<!--                    </el-tooltip>-->
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
                    <el-table :data="tableData" max-height="450" style="width: 100%"
                              @selection-change="handleSelectionChange">
                        <el-table-column fixed type="selection" width="50px"></el-table-column>
                        <el-table-column fixed type="index" :index="tableIndex" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="companyName" label="公司名称" width="160"></el-table-column>
                        <el-table-column prop="companyLineCode" label="线路编码" min-width="80"></el-table-column>
                        <el-table-column prop="companyLineName" label="线路名称" width="100"></el-table-column>
                        <el-table-column prop="directionName" label="行驶方向" min-width="100"></el-table-column>
                        <el-table-column prop="lineMileage" label="里程(公里)" min-width="100"></el-table-column>
                        <!--<el-table-column prop="summerStartTime" :formatter="formatTime1" label="夏季开始时间" min-width="120"></el-table-column>
                        <el-table-column prop="summerEndTime" :formatter="formatTime1" label="夏季结束时间" min-width="120"></el-table-column>-->

                        <!--<el-table-column prop="summerFirstTime" :formatter="formatTime2" label="夏季首班时间" width="120"></el-table-column>
                        <el-table-column prop="summerLastTime" :formatter="formatTime2" label="夏季末班时间" width="120"></el-table-column>-->

<!--                        <el-table-column label="夏季发车时间" width="120">-->
<!--                            <template slot-scope="scope">-->
<!--                                <span>{{scope.row.summerFirstTime.substr(11,2) + '点' + scope.row.summerFirstTime.substr(14,2) + '分' }}</span><br>-->
<!--                                <span>{{scope.row.summerLastTime.substr(11,2) + '点' + scope.row.summerLastTime.substr(14,2) + '分'}}</span>-->
<!--                            </template>-->
<!--                        </el-table-column>-->

                        <!--<el-table-column prop="winterFirstTime" :formatter="formatTime2" label="冬季首班时间" width="120"></el-table-column>
                        <el-table-column prop="winterLastTime" :formatter="formatTime2" label="冬季末班时间" width="120"></el-table-column>-->
<!--                        <el-table-column label="冬季发车时间" width="120">-->
<!--                            <template slot-scope="scope">-->
<!--                                <span>{{scope.row.winterFirstTime.substr(11,2) + '点' + scope.row.winterFirstTime.substr(14,2) + '分' }}</span><br>-->
<!--                                <span>{{scope.row.winterLastTime.substr(11,2) + '点' + scope.row.winterLastTime.substr(14,2) + '分'}}</span>-->
<!--                            </template>-->
<!--                        </el-table-column>-->
<!--                        <el-table-column label="夏季起止时间" width="150">-->
<!--                            <template slot-scope="scope">-->
<!--                                <span>{{scope.row.summerStartTime.substr(5,2) + '月' + scope.row.summerStartTime.substr(8,2) + '日' }}</span><br>-->
<!--                                <span>{{scope.row.summerEndTime.substr(5,2) + '月' + scope.row.summerEndTime.substr(8,2) + '日'}}</span>-->
<!--                            </template>-->
<!--                        </el-table-column>-->
                        <el-table-column label="是否启用" width="80">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1 ? '是' : '否' }}</template>
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
                                <el-button size="mini" type="success" @click="goToDetail(scope.row.companyLineId)">
                                    <i class="el-icon-edit"></i>查看
                                </el-button>
                              <el-button size="mini" type="danger"
                                           @click="handleDelete(scope.row.companyLineId  , scope.row.companyLineName)">
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
            <DialogBatch v-show="DialogBatchVisible" ref="DialogBatch" @refreshDataList="getData(1)"></DialogBatch>
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
    import {getInfoBusPlanServiceLinePage, getInfoBusCompanyListAll,deleteInfoCompanyLine,deleteInfoCompanyLineAll} from "@/api/parameter";
    import Dialog from "./Dialog.vue";
    import DialogBatch from "./DialogBatch.vue";
    export default {
        name: "BusPlanService",
        data() {
            return {
                visible: false,
                conditionQuery: {
                    companyList:[]
                },
                formQuery: {
                    companyLineName:'',
                    companyId:'',
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
                DialogBatchVisible: false,
                checkedList: [],
                deleteAllForm: {
                    validatePassword: '',
                    ids: []
                }
            };
        },
        components: {
            Dialog,
            DialogBatch
        },
        activated: function () {
            //this.getData();
        },
        methods: {
            formatTime1: function (row, column, cellValue, index) {
                return cellValue.substr(5,2) + '月' + cellValue.substr(8,2) + '日';
            },
            formatTime2: function (row, column, cellValue, index) {
                return cellValue.substr(11,2) + '点' + cellValue.substr(14,2) + '分';
            },
            getDate(){
                this.formQuery.startTime=this.queryDt[0];
                this.formQuery.endTime=this.queryDt[1];
            },
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
                    deleteInfoCompanyLineAll(this.deleteAllForm).then((res) => {
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
                        this.deleteAllForm.ids.push(this.checkedList[i].companyLineId)
                    }
                }
                console.log(this.deleteAllForm.ids);
            },
            goToDetail: function (id) {
                this.$router.push({
                    path: './BusPlanServiceDetail',
                    query: {
                        companyLineId: id
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
                getInfoBusPlanServiceLinePage(this.formQuery).then((res) => {
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
                    this.$refs.Dialogs.initData(id,1);
                });
            },
            //增加或修改
            addBatchHandle: function (id) {
                this.DialogBatchVisible = true;
                this.$nextTick(() => {
                    this.$refs.DialogBatch.initData(id,1);
                });
            },
            //删除当前条的数据操作
            handleDelete: function (id, name) {
                this.$confirm(`确定要删除"${name}"吗?`, "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    deleteInfoCompanyLine(id)
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
                // getInfoBusCompanyListAll().then((res) => {
                //     if (res.data && res.data.code == 0) {
                //         this.conditionQuery.companyList = res.data.data.list
                //         // if(this.conditionQuery.companyList.length > 0){
                //         //     this.formQuery.companyId = this.conditionQuery.companyList[0].companyId;
                //         // }
                //     }
                // });
                this.getData();
            }
        },
        mounted: function () {
            this.init();
        }
    };
</script>

<style scoped>

</style>
