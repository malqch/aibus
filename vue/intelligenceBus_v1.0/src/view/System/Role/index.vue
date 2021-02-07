<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:角色管理组件页面
-->
<template>
    <div>
        <div class="cotroller_box">
            <el-form ref="form" :model="form" label-width="120px" style="width:100%">
                <el-form-item label="角色名">
                    <el-input v-model="form.name" placeholder="请输入角色名" style="float: left;width:190px;margin-left:10px;height:42px;" clearable></el-input>&nbsp;&nbsp;&nbsp;&nbsp;
                    <el-button type="primary" @click="handleList(1)" size="small">
                        <i class="fa fa-search" style="margin-right: 4px;font-size:14px;"></i>搜索
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller"  >
                <!--<span  class="table_title_span"></span>-->
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>

                <p class="title"  >角色列表</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle()" size="small" style="float: right;margin-right:10px;margin-left:0px !important;"><i class="el-icon-circle-plus-outline"></i>添加</el-button>
                </div>
            </div>
            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="tableData" style="width: 100%" max-height="400">
                        <el-table-column type="index" :index="table_index" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="name" label="角色名" min-width="140"></el-table-column>
                        <el-table-column prop="description" label="角色描述" min-width="200"></el-table-column>
                        <el-table-column label="是否启用" width="80">
                            <template slot-scope="scope">{{scope.row.is_enabled == 1?'是':'否' }}</template>
                        </el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">{{scope.row.create_user_name}}<br>{{scope.row.create_dt}}</template>
                        </el-table-column>
                        <el-table-column label="修改人/修改时间" width="150">
                            <template slot-scope="scope">
                                {{scope.row.modify_user_name}}
                                <span v-if="scope.row.modify_user_name"><br></span>
                                {{scope.row.modify_dt}}
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">

                                <!--                      :disabled="this.res!=null && scope.row.role_id in this.res ? true:false"-->
                                <el-button size="mini" type="warning"  @click="addOrUpdateHandle(scope.row.role_id)">
                                    <i class="el-icon-edit"></i>修改</el-button>
                                <el-button size="mini" type="danger"   @click="handleDelete(scope.row.role_id,scope.row.name)">
                                    <i class="el-icon-delete"></i>删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="table.currPage"
                            :page-sizes="[10, 20, 50, 100]"
                            :page-size="table.pageSize"
                            layout="total, sizes, prev, pager, next, jumper" :total="table.totalCount" background style="width:100%;text-align:right;margin-top:10px;"></el-pagination>
                </div>

            </div>
            <add-or-update v-show="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="handleList()"></add-or-update>
        </div>
    </div>
</template>

<script>
    import {
        getRoleList,
        deleteRoleByRoleId
    } from "@/api/system";
    import AddOrUpdate from "./role-add-or-update";
    export default {
        name: "GfmAdminRole",
        data() {
            return {
                form: {
                    name: "",
                    page: 1,
                    limit: 10
                },
                tableData: [],
                roleList:[],
                roleListAll:[],
                userId:0,
                table: {},
                addOrUpdateVisible: false
            };
        },
        components: {
            AddOrUpdate
        },
        activated() {
            this.handleList();
        },
        methods: {
            judge(role_id){
                var flag=false;

            },
            table_index(index) {
                return (this.form.page - 1) * this.form.limit + index + 1
            },
            handleList(page) {
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                if(page){this.form.page=1;}
                getRoleList(this.form).then(resp => {
                    loading.close();
                    if (resp.data && resp.data.code === 0) {
                        this.tableData = resp.data.data.page.list;
                        this.table = resp.data.data.page;
                        this.roleListAll=resp.data.data.owerRoleList;
                        for(var i=0;i<resp.data.data.owerRoleList.length;i++){
                            this.roleList.push( this.roleListAll[i].role_id)
                        }
                        this.userId=resp.data.data.userId
                    }

                });
            },
            handleSizeChange(val) {
                this.form.limit = val;
                this.handleList(1);
            },
            handleCurrentChange(val) {
                this.form.page = val;
                this.handleList();
            },
            //增加或修改
            addOrUpdateHandle(id) {
                this.addOrUpdateVisible = true;
                this.$nextTick(() => {
                    this.$refs.addOrUpdate.init(id);
                });
            },

            //删除角色(此处不是批量删除)
            handleDelete(id,name) {
                this.$confirm(`确定要删除"${name}"角色吗?`, "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                    .then(() => {
                        deleteRoleByRoleId([id]).then(({
                                                           data
                                                       }) => {
                            if (data && data.code === 0) {
                                this.$message({
                                    message: "操作成功",
                                    type: "success",
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.handleList(1);
                                    }
                                });
                            } else {
                                this.$message({
                                    message: data.message,
                                    type: "error",
                                    offset: 300,
                                    duration: 1500,
                                    onClose: () => {
                                        this.handleList(1);
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
        mounted() {
            this.handleList();
        }
    };
</script>

<style>
</style>
