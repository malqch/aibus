<template>
    <div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller"  >
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>
                <p class="title" >故障采集卡片</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle(tableData.collectFaultId)" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>修改</el-button>

                    <el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>
                    <el-button type="primary" @click="goBack" size="small" style="float: right;margin-right:10px;margin-left:0px !important;" plain>
                        <i class=" fa fa-mail-reply-all"></i>返回</el-button>
                </div>
            </div>
            <div class="table_div card-text" >
                <el-row  class="card-title-box ">
                    <el-col :span="5" class="">
                        <div>
                            <el-row style=" margin-top: 20px;">
                                <el-col :span="24"  class="title-box-svg" >
                                    <div  class="title-bg-box-svg">
                                        <img style="width: 100%;height: 100%;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjAwODQwMDk0MTM1IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjU3MDciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNDMuNzU0MTIzIDgyMC4zMjU4MzdzLTkuMjExMzk0IDYxLjQwOTI5NSAzMC4xOTI5MDMgMTAwLjgxMzU5M2MzMS43MjgxMzYgMzEuNzI4MTM2IDkwLjA2Njk2NyAyNS4wNzU0NjIgOTAuMDY2OTY3IDI1LjA3NTQ2M2wyNzguMzg4ODA2LTI5Mi43MTc2NDJMMzI3Ljc3MjExNCA1NDMuOTg0MDA4bC0yODQuMDE3OTkxIDI3Ni4zNDE4Mjl6IiBwLWlkPSI1NzA4IiBmaWxsPSIjNDA5RUZGIj48L3BhdGg+PHBhdGggZD0iTTYxNS44ODQwNTggNTkyLjA4Nzk1NmwtMzI0LjQ0NTc3Ny0zMTIuMTYzOTE4LTYuNjUyNjc0LTg4LjUzMTczNEwxNjIuOTkwNTA1IDExMi41ODM3MDggMTI4LjE5MTkwNCAxNDcuMzgyMzA5bC0zNC43OTg2MDEgMzQuNzk4NjAxLTM0Ljc5ODYgMzQuNzk4NiA3Ny43ODUxMDcgMTIxLjc5NTEwMyA4Mi45MDI1NDkgNy4xNjQ0MTcgMzI3LjUxNjI0MiAzMTMuNjk5MTUxLTY4LjU3MzcxMyA3MC4xMDg5NDUgMjk0LjI1Mjg3MyAyOTQuMjUyODc0YzEwMC44MTM1OTMtMjYuNjEwNjk1IDE1OS4xNTI0MjQtOTcuMjMxMzg0IDE5MS45MDQwNDgtMTkyLjQxNTc5Mkw2NzAuNjQwNjggNTM2LjgxOTU5bC01NC43NTY2MjIgNTUuMjY4MzY2eiIgcC1pZD0iNTcwOSIgZmlsbD0iIzQwOUVGRiI+PC9wYXRoPjxwYXRoIGQ9Ik01OTYuOTQ5NTI1IDQ4Mi4wNjI5NjlsNDkuMTI3NDM3LTUxLjE3NDQxM2M5OC4yNTQ4NzMgNDIuNDc0NzYzIDE5OS4wNjg0NjYgMjUuMDc1NDYyIDI2Ny42NDIxNzgtMzMuNzc1MTEzIDY3LjAzODQ4MS01OC4zMzg4MzEgNjcuMDM4NDgxLTE3Mi40NTc3NzEgNjcuMDM4NDgxLTE3Mi40NTc3NzFsLTUzLjIyMTM4OS0yMC45ODE1MDlMODI0LjE2MzkxOCAzMDguMDY5OTY1IDY3My43MTExNDQgMTU4LjY0MDY4bDEwMy44ODQwNTgtMTA1LjQxOTI5MUw3NTYuNjEzNjkzIDBTNjUyLjcyOTYzNSAwIDU4My42NDQxNzggNjguNTczNzEzYy02NC45OTE1MDQgNjMuNDU2MjcyLTg0Ljk0OTUyNSAxNjguODc1NTYyLTM5LjkxNjA0MiAyNjQuMDU5OTdsLTUxLjE3NDQxMyA1MC42NjI2NjkgMTA0LjM5NTgwMiA5OC43NjY2MTd6IiBwLWlkPSI1NzEwIiBmaWxsPSIjNDA5RUZGIj48L3BhdGg+PC9zdmc+">
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24"  class="title-text-box-svg">
                                    <div class="title-text-content-svg">{{tableData.collectFault}}</div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="10"  class="">
                        <div  class="card-right-content ">
                            <el-row>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">采集内容 : {{tableData.collectFault}}</span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障类型 ：{{tableData.faultTypeName}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障级别 ：{{tableData.faultLevelName}}</span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障方案 ：{{tableData.suggestionContent}}</span>
                                    </div>
                                </el-col>


                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建人 : {{tableData.createUserName}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">修改人 : {{tableData.modifiedUserName}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">是否启用 : {{tableData.isEnabled == 1 ? '是':'否'}}</span>
                                    </div>
                                </el-col>

                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="9">
                        <div class="">
                            <el-row>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">采集编码 ：{{tableData.collectCode}}</span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障ID ：{{tableData.faultType}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障索引码 ：{{tableData.faultDetail}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障标签 ：{{tableData.faultTargetName}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建时间 : {{tableData.createdDate}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">修改时间 : {{tableData.modifiedDate}}</span>
                                    </div>
                                </el-col>

                            </el-row>
                        </div>
                    </el-col>
                </el-row>
            </div>


            <div class="show_table_controller"  >
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>
                <p class="title" >故障采集扩展列表</p>
                <div class="btn_list">
                    <el-button type="primary" @click="SpecialControllerEdir(tableData.collectFaultId, '','新增')" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加</el-button>
                    <el-button type="danger" @click="handleDeleteAll" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除</el-button>
                    <el-button type="warning" @click="handleReload" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新
                    </el-button>
                </div>
            </div>
            <div class="table_div" >
                <div style="position: relative">
                    <el-table :data="PageData" style="width: 100%" max-height="300" @selection-change="handleSelectionChange">
                        <el-table-column  fixed type="selection"  width="50px" ></el-table-column>
                        <el-table-column fixed type="index" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="faultTargetName" label="故障标签名称" width="160"></el-table-column>
                        <el-table-column label="是否启用" width="120">
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
                                <el-button size="mini" type="warning" @click="SpecialControllerEdir(tableData.collectFaultId,scope.row.faultExtendId, '修改')">
                                    <i class="el-icon-edit"></i>修改</el-button>
                                <el-button size="mini" type="danger" @click="handleDelete(scope.row.faultExtendId  ,scope.row.faultTargetName)">
                                    <i class="el-icon-delete"></i>删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

            </div>

            <!-- 弹窗, 新增 / 修改 -->
            <Dialog v-show="DialogVisible" ref="Dialogs" @refreshDataList="getData(1)"></Dialog>
            <DialogExtend v-show="DialogExtendVisible" ref="DialogExtend" @refreshDataList="getData(1)"></DialogExtend>
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
    import {getInfoFaultCollectDetail,getInfoFaultExtendList, deleteInfoFaultExtend, deleteInfoFaultExtendAll} from "@/api/fault";
    import Dialog from "./Dialog";
    import DialogExtend from "./DialogExtend";
    export default {
        name: "FaultExtend",
        data() {
            return {
                form: {
                    name: "",
                    page: 1,
                    limit: 10
                },
                tableData: {},
                PageData : [],
                DialogVisible: false,
                DialogExtendVisible : false,
                visible : false,
                checkedList :[],
                deleteAllForm : {
                    validatePassword : '',
                    ids : []
                }
            };
        },
        components: {
            Dialog,
            DialogExtend
        },
        activated : function() {
            //this.getData();
        },
        methods: {
            handleReload : function(){
                this.getData();
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
                    deleteInfoFaultExtendAll(this.deleteAllForm).then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.$message({
                                message: '操作成功',
                                type: 'success',
                                offset: 300,
                                duration: 1500,
                                onClose: () => {
                                    this.visible = false
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
                        this.deleteAllForm.ids.push(this.checkedList[i].faultExtendId)
                    }
                }
                console.log(this.deleteAllForm.ids);
            },
            goBack : function(){
                this.$router.push({
                    path : './FaultCollect'
                })
            },
            //获取基础列表数据函数
            getData : function() {
                const id = this.$route.query.collectFaultId;
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                getInfoFaultCollectDetail(id).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        console.log(res.data);
                        this.tableData = res.data.data.data;
                    }
                });
                getInfoFaultExtendList(id).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        console.log(res.data);
                        this.PageData = res.data.data.list;
                    }
                });
            },
            //更改每页的条数触发的table渲染
            handleSizeChange : function(val){
                this.form.limit = val;
                this.getData(1);
            },
            //点击分页渲染页面函数
            handleCurrentChange : function(val){
                this.form.page = val;
                this.getData()
            },

            //增加或修改
            addOrUpdateHandle : function(id) {
                this.DialogVisible = true;
                this.$nextTick(() => {
                    this.$refs.Dialogs.initData(id);
                });
            },
            SpecialControllerEdir: function(collectFaultId, id, mark) {
                this.DialogExtendVisible = true;
                this.$nextTick(() => {
                    this.$refs.DialogExtend.initData(collectFaultId,id, mark);
                });
            },
            //删除当前条的数据操作
            handleDelete : function(id, name) {
                this.$confirm(`确定要删除"${name}"吗?`, "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    deleteInfoFaultExtend(id)
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
            }
        },
        mounted : function() {
            this.getData();
        }
    };
</script>

<style scoped>
    /deep/ .red_table .el-table th{
        background-color:#E54444 !important;
    }
    .card-right-content{
        padding-bottom:15px;
    }
</style>
