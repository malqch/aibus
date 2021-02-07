<!--
 @Author: psp
 @Filename: index.vue
 @ProjectName: aibus-manage
 @Mail:
 @Date: 2020-08-28
 @file-description: 设备表组件页面
-->

<template>
    <div>
        <!-- 搜索项 -->
        <div class="cotroller_box">
            <el-form :inline="true" :model="form" class="demo-form-inline">

                <el-form-item label="班级名称">
                    <el-input v-model="form.className" clearable @clear="clearInput" placeholder="班级名称"> </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getData(1)">查询</el-button>
                </el-form-item>
            </el-form>

        </div>

        <div class="show_table" style="width:100%;">

            <!-- 刷新、批量删除 -->
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

                <p class="title">班级列表</p>
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

            <!-- 分页列表 -->
            <div class="table_div">
                <div style="position: relative;">
                    <el-table :data="tableData" style="width: 100%" max-height="450" @selection-change="handleSelectionChange">
                        <el-table-column fixed type="selection" width="50px"></el-table-column>
                        <el-table-column fixed type="index" align="center" :index="tableIndex" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="id" header-align="center" align="center" label="id" v-if="false"></el-table-column>
                        <el-table-column prop="className" header-align="center" align="center" label="班级名称"></el-table-column>
                        <el-table-column prop="teacherName" header-align="center" align="center" label="班主任"></el-table-column>
                        <el-table-column prop="teacherMobileNumber" header-align="center" align="center" label="联系方式"></el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{ scope.row.createUserName }}</span><br>
                                <span>{{ scope.row.createDt }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="修改人/修改时间" width="150">
                            <template slot-scope="scope">
                                <span>{{ scope.row.modifiedUserName }}</span><br>
                                <span>{{ scope.row.modifyDt }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="warning" @click="addOrUpdateHandle(scope.row.id,0)">
                                    <i class="el-icon-edit"></i>修改
                                </el-button>
                                <el-button size="mini" type="primary" @click="addOrUpdateHandle(scope.row.id,1)">
                                    <i class="el-icon-edit"></i>导入
                                </el-button>

                            </template>
                        </el-table-column>
                    </el-table>

                    <!-- 底部分页按钮 -->
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
            <Dialog v-show="DialogVisible" ref="Dialogs" @refreshDataList="getData()"></Dialog>
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
// import { getInfoDriverList, deleteInfoDriver, deleteBatchInfoDriver } from "@/api/parameter";
import Dialog from "./Dialog";
import {deleteClassesDetail, getClassesList} from "@/api/parameter";

export default {
    name: "InfoDriver",
    data() {
        return {
            form: {
                deviceName: "",
                vinCode: "",
                page: 1,
                limit: 10
            },
            tableData: [{
                id: 1,
                className: '三年二班',
                teacherName: '宋老师',
                teacherMobileNumber: '182010101234',
                createUserId: '管理员',
                createUserName: '管理员',
                createDt: '2021-01-24 13:00:00',
                modifyUserId: '管理员1',
                modifiedUserName: '管理员1',
                modifyDt: '2021-01-26 09:01:21'

            }],
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
            return (this.form.page - 1) * this.form.limit + index + 1
        },
        clearInput: function () {
            this.form.deviceName = '';
            this.getData(1);
        },
        handleReload: function () {
            this.getData(1);
        },
        dataFormSubmit: function () {
            debugger
            if (this.deleteAllForm.validatePassword == '') {
                this.$message({
                    message: '用户密码不能为空,请输入用户密码！',
                    type: "error",
                    offset: 300,
                    duration: 1500
                });
            } else {
                debugger
                console.log("批量删除:" + JSON.stringify(this.deleteAllForm));
                deleteClassesDetail(this.deleteAllForm).then((res) => {
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
                console.log(this.checkedList)
                // deleteClassesDetail(this.checkedList).then((res) => {})
            }

        },
        handleSelectionChange: function (val) {
            debugger
            this.checkedList = val;
            this.deleteAllForm.ids = [];
            if (this.checkedList.length > 0) {
                for (var i = 0; i < this.checkedList.length; i++) {
                    this.deleteAllForm.ids.push(this.checkedList[i].id)
                }
            }
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
                this.form.page = 1;
            }
            loading.close();

            // getInfoDriverList(this.form).then((res) => {
            //     loading.close();
            //     if(res.data && res.data.code === 0){
            //         this.tableData = res.data.data.page.list;
            //         this.table = res.data.data.page;
            //     }
            // });
            console.log("分页查询:" + JSON.stringify(this.form));
            getClassesList(this.form).then((res)=>{
                loading.close();
                if (res.data && res.data.code === 0){
                    this.tableData = res.data.data.page.list;
                    this.table = res.data.data.page;
                }
            });

        },
        //更改每页的条数触发的table渲染
        handleSizeChange: function (val) {
            this.form.limit = val;
            this.getData(1);
        },
        //点击分页渲染页面函数
        handleCurrentChange: function (val) {
            this.form.page = val;
            this.getData()
        },

        //增加或修改
        addOrUpdateHandle: function (id, type) {
            debugger
            this.DialogVisible = true;
            this.$nextTick(() => {
                this.$refs.Dialogs.initData(id,type);
            });
        },
        formatTime: function (row, column, cellValue, index) {
            if (cellValue) {
                console.log(cellValue)
                return cellValue.substr(0, 4) + '年' + cellValue.substr(5, 2) + '月' + cellValue.substr(8, 2) + '日';
            }
            return cellValue
        },
        formatTime1: function (row, column, cellValue, index) {
            return cellValue.substr(5, 2) + '月' + cellValue.substr(8, 2) + '日';
        },
        formatTime2: function (row, column, cellValue, index) {
            return cellValue.substr(11, 2) + '点' + cellValue.substr(14, 2) + '分';
        },
        //删除当前条的数据操作
        handleDelete: function (id, name) {
            this.$confirm(`确定要删除"${name}"吗?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(() => {
                console.log("删除单条记录,ID:" + id);
                deleteInfoDriver(id)
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
            })
                .catch(() => {
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

</style>
