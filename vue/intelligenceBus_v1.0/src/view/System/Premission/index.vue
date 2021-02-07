<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 11:58
 @file-description:菜单管理组件页面
-->


<template>
    <div>
        <div v-if="false" class="cotroller_box">
        </div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller" >
                <!--<span class="table_title_span"></span>-->
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>

                <p class="title">菜单列表</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle()" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加</el-button>
                </div>
            </div>
            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="tableData" row-key="rightId" style="width: 100%" max-height="450">
                        <el-table-column prop="name" label="名称" width="180px">
                        </el-table-column>
                        <el-table-column prop="parentName" label="上级菜单" width="180px">
                        </el-table-column>
                        <el-table-column prop="iconCss" header-align="center" align="center" label="图标" width="100px">
                            <template slot-scope="scope">
                                <i :class="'fa fa-'+scope.row.iconCss"></i>
                            </template>
                        </el-table-column>
                        <el-table-column prop="type" header-align="center" align="center" label="类型">
                            <template slot-scope="scope">
                                <span v-if="scope.row.type === 0" size="small">目录</span>
                                <span v-else-if="scope.row.type === 1" size="small" type="success">菜单</span>
                                <el-tag v-else-if="scope.row.type === 2" size="small" type="info">按钮</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column prop="sort" label="排序">
                        </el-table-column>
                        <el-table-column prop="endpointPath" label="菜单URL">
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="warning" @click="addOrUpdateHandle(scope.row.rightId)">
                                    <i class="el-icon-edit"></i>修改
                                </el-button>
                                <el-button size="mini" type="danger" @click="handleDelete(scope.row.rightId,scope.row.name)">
                                    <i class="el-icon-delete"></i>删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                </div>
            </div>
            <!-- 弹窗, 新增 / 修改 -->
            <add-or-update v-show="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="handleList()"></add-or-update>
        </div>
    </div>
</template>

<script>
    import {
        getRightList,
        deleteRightByRightId
    } from '@/api/system'
    import {
        treeDataTranslate
    } from '@/util/index'
    import AddOrUpdate from './right-add-or-update'
    export default {
        name: 'GfmAdminRight',
        data() {

            return {
                tableData: [],
                table: {},
                rightListAll: [],
                rightList:[],
                userId:0,
                addOrUpdateVisible: false,
                rightId: 0,
            };
        },
        components: {
            AddOrUpdate
        },
        activated() {
            this.handleList()
        },
        methods: {
            handleList() {
                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                getRightList().then((resp) => {
                    loading.close();
                    if (resp.data && resp.data.code === 0) {
                        this.tableData = treeDataTranslate(resp.data.data.rightList, 'rightId')
                        this.table = resp.data.data.rightList
                        this.rightListAll=resp.data.data.rightListByUser
                        for(var i=0;i<this.rightListAll.length;i++){
                            this.rightList.push( this.rightListAll[i].rightId)
                        }
                        this.userId=resp.data.data.userId
                    }



                })
            }, //增加或修改
            addOrUpdateHandle(id) {
                this.addOrUpdateVisible = true
                this.$nextTick(() => {
                    this.$refs.addOrUpdate.init(id)
                })

            },
            //删除菜单(此处不是批量删除)
            handleDelete(id,name) {
                this.$confirm(`确定要删除"${name}"菜单或目录吗?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteRightByRightId(id).then(({
                                                       data
                                                   }) => {
                        if (data && data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                offset: 300,
                                duration: 1500,
                                onClose: () => {
                                    this.handleList()
                                }
                            })
                        } else {
                            this.$message({
                                message: data.message,
                                type: 'error',
                                offset: 300,
                                duration: 1500
                            })
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                })

            },
        },
        mounted() {
            this.handleList();
        }
    }
</script>

<style>
    .el-icon-arrow-right{
        color: #000000 !important
    }
</style>
