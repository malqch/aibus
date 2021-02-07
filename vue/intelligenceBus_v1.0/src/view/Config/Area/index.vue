<!--
 @Author: psp
 @Filename: index.vue
 @ProjectName: aibus-manage
 @Mail:
 @Date: 2020-08-29
 @file-description: 区域信息
-->

<template>
    <div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller" >
                <!--<span class="table_title_span"></span>-->
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>
                <p class="title">区域信息</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle()" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加
                    </el-button>
                    <el-button type="danger" @click="handleDeleteAll" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除</el-button>
                    <el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>
                </div>
            </div>
            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="tableData" row-key="areaId" style="width: 100%" max-height="450" @selection-change="handleSelectionChange">
                        <el-table-column  fixed type="selection"  width="50px" ></el-table-column>
                        <el-table-column fixed prop="areaName" label="区域名称" min-width="140"></el-table-column>
                        <el-table-column prop="parentAreaName" label="上级区域" min-width="140"></el-table-column>
                        <el-table-column prop="areaCode" label="区域编码" min-width="100"></el-table-column>
                        <el-table-column prop="areaAlias" label="区域简称" min-width="100"></el-table-column>
                        <el-table-column prop="areaSort" label="排序" width="60"></el-table-column>
                        <el-table-column prop="areaLevel" label="级别" width="60"></el-table-column>
                        <el-table-column label="是否启用" width="80">
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
                                <el-button size="mini" type="warning"   @click="addOrUpdateHandle(scope.row.areaId)">
                                    <i class="el-icon-edit"></i>修改
                                </el-button>
                                <el-button size="mini" type="danger"  @click="handleDelete(scope.row.areaId,scope.row.areaName)">
                                    <i class="el-icon-delete"></i>删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

            </div>
            <!-- 弹窗, 新增 / 修改 -->
            <Dialog v-show="addOrUpdateVisible" ref="Dialogs" @refreshDataList="handleList()"></Dialog>
            <el-dialog
                title="输入用户密码"
                :visible.sync="visible"
                width="30%">
                <el-form  :model="deleteAllForm" >
                    <el-form-item label="">
                        <el-input v-model="deleteAllForm.validatePassword" placeholder="请输入用户密码" maxlength="64" type="password"></el-input>
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
    import {getAreaList, deleteArea, deleteAreaAll} from '@/api/parameter'
    import {treeDataTranslate1} from '@/util/index'
    import Dialog from './Dialog'
    export default {
        name: 'RegionalIFM',
        data() {
            return {
                tableData: [],
                table: {},
                rightListAll: [],
                rightList:[],
                userId:0,
                addOrUpdateVisible: false,
                rightId: 0,
                visible : false,
                checkedList :[],
                deleteAllForm : {
                    validatePassword : '',
                    ids : []
                }
            };
        },
        components: {
            Dialog
        },
        activated() {
            this.handleList()
        },
        methods: {
            handleReload : function(){
                this.handleList();
            },
            dataFormSubmit : function(){
                if(this.deleteAllForm.validatePassword == ''){
                    this.$message({
                        message: '用户密码不能为空,请输入用户密码！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                }else{
                    console.log(this.deleteAllForm);
                    deleteAreaAll(this.deleteAllForm).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                offset: 300,
                                duration: 1500,
                                onClose: () => {
                                    this.visible = false
                                    this.handleList();
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
            handleDeleteAll : function(){
                if(this.checkedList.length == 0){
                    this.$message({
                        message: '请选择删除的数据！',
                        type: "error",
                        offset: 300,
                        duration: 1500
                    });
                }else{
                    this.visible = true;
                    this.deleteAllForm.validatePassword = '';
                }

            },
            handleSelectionChange : function(val){
                console.log('ok');
                this.checkedList = val;
                this.deleteAllForm.ids = [];
                if(this.checkedList.length > 0){
                    for(var i=0;i<this.checkedList.length;i++){
                        this.deleteAllForm.ids.push(this.checkedList[i].areaId)
                    }
                }
                console.log(this.deleteAllForm.ids);
            },
            handleList() {
                const loading = this.$loading({
                    lock: true,
                    text: '加载中.......',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                getAreaList().then((resp) => {
                    loading.close();
                    if (resp.data && resp.data.code === 0) {
                        this.tableData = treeDataTranslate1(resp.data.data.list)
                        //console.log("转变成树形结构数据:");
                        //console.log(JSON.stringify(this.tableData));
                    }
                })
            }, //增加或修改
            addOrUpdateHandle(id) {
                this.addOrUpdateVisible = true
                this.$nextTick(() => {
                    this.$refs.Dialogs.init(id)
                })

            },
            //删除菜单(此处不是批量删除)
            handleDelete(id,name) {
                console.log("删除单条记录,ID:"+id);
                this.$confirm(`确定要删除"${name}"吗?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteArea(id).then(({data}) => {
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

