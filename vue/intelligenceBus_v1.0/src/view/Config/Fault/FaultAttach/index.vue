<template>
    <div>
        <div class="show_table" style="width:100%;">
            <div class="show_table_controller"  >
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>
                <p class="title" >故障日志卡片</p>
                <div class="btn_list">
                    <!--<el-button type="primary" @click="addOrUpdateHandle(tableData.faultDetailId)" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>修改</el-button>-->

                    <el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>
                    <el-button type="primary" @click="goBack" size="small" style="float: right;margin-right:10px;margin-left:0px !important;" plain>
                        <i class=" fa fa-mail-reply-all"></i>返回</el-button>
                </div>
            </div>
            <div class="table_div card-text" >
                <el-row  class="card-title-box " style="padding-bottom:15px">
                    <el-col :span="5" class="">
                        <div>
                            <el-row style=" margin-top: 20px;">
                                <el-col :span="24"  class="title-box-svg" >
                                    <div  class="title-bg-box-svg">
                                        <img style="width: 100%;height: 100%;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjAwODM5NzMyOTI3IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQ4NzciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTEyIDM2NGgtMTM5LjJhMzIgMzIgMCAwIDEgMC02My4wNEg1MTJhMzIgMzIgMCAwIDEgMCA2My4wNHpNNzEyLjY0IDc1OS41MmgtMzUyYTMyIDMyIDAgMCAxIDAtNjMuMDRoMzUyYTMyIDMyIDAgMCAxIDAgNjMuMDR6TTcwOS4yOCA1NjEuNzZIMzYwLjE2YTMyIDMyIDAgMCAxIDAtNjMuMDRoMzQ5LjEyYTMyIDMyIDAgMCAxIDAgNjMuMDR6IiBwLWlkPSI0ODc4IiBmaWxsPSIjNDA5RUZGIj48L3BhdGg+PHBhdGggZD0iTTgwNC42NCA5NjBIMjg2Ljg4YTEwOC45NiAxMDguOTYgMCAwIDEtMTA3LjM2LTExMC40VjUyMi40YTMwLjcyIDMwLjcyIDAgMSAxIDYxLjI4IDBWODQ5LjZhNDYuNzIgNDYuNzIgMCAwIDAgNDYuMDggNDhoNTE3Ljc2YTQ2LjcyIDQ2LjcyIDAgMCAwIDQ2LjA4LTQ4VjM1Ny4yOEw2MTIuOTYgOTUuMDRIMjg2Ljg4YTQ2LjcyIDQ2LjcyIDAgMCAwLTQ2LjA4IDQ4djI1MC4yNGEzMC43MiAzMC43MiAwIDEgMS02MS4yOCAwVjE0Mi40QTEwOC45NiAxMDguOTYgMCAwIDEgMjg2Ljg4IDMySDY0MGwyNzIgMzAwLjQ4Vjg0OS42YTEwOC45NiAxMDguOTYgMCAwIDEtMTA3LjM2IDExMC40eiIgcC1pZD0iNDg3OSIgZmlsbD0iIzQwOUVGRiI+PC9wYXRoPjxwYXRoIGQ9Ik04ODEuMjggMzc4LjA4SDY4NC44YTk4LjcyIDk4LjcyIDAgMCAxLTk3LjEyLTk5Ljg0VjY0YTMxLjA0IDMxLjA0IDAgMCAxIDMwLjcyLTMyaDcuODRhMzAuMjQgMzAuMjQgMCAwIDEgMjIuNCAxMC4wOGwyNTYgMjgxLjI4YTMyIDMyIDAgMCAxIDcuMzYgMjEuNnYxLjZhMzEuMDQgMzEuMDQgMCAwIDEtMzAuNzIgMzEuNTJ6TTY0OC45NiAxMzQuODh2MTQ0YTM2LjQ4IDM2LjQ4IDAgMCAwIDM1Ljg0IDM2LjhoMTI4eiIgcC1pZD0iNDg4MCIgZmlsbD0iIzQwOUVGRiI+PC9wYXRoPjwvc3ZnPg==">
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
                    <el-col :span="10"  class="card-right-box-h2">
                        <div  class="card-right-content card-right-content-h2">
                            <el-row>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障采集 : {{tableData.collectFault}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">采集编码 ：{{tableData.collectCode}}</span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">车辆VIN ：{{tableData.vinCode}}</span>
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
                                        <span class="text-public">故障标签 ：{{tableData.faultTargetName}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障级别 ：{{tableData.faultLevelName}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">故障类型 ：{{tableData.faultTypeName}}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建时间 : {{tableData.createdDate}}</span>
                                    </div>
                                </el-col>

                            </el-row>
                        </div>
                    </el-col>
                </el-row>
            </div>


            <div class="show_table_controller"  >
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>
                <p class="title" >故障日志附件列表</p>
                <div class="btn_list">
<!--                    <el-button type="primary" @click="SpecialControllerEdir(tableData.faultDetailId, '','新增')" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-circle-plus-outline"></i>添加</el-button>
                    <el-button type="danger" @click="handleDeleteAll" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除</el-button>-->
                    <!--<el-button type="warning" @click="handleReload" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新
                    </el-button>-->
                </div>
            </div>
            <div class="table_div" >
                <div style="position: relative">
                    <el-table :data="PageData" style="width: 100%" max-height="300" @selection-change="handleSelectionChange">
                        <el-table-column  fixed type="selection"  width="50px" ></el-table-column>
                        <el-table-column fixed type="index" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="faultTargetCode" label="故障标签编码" min-width="100"></el-table-column>
                        <el-table-column prop="faultTargetGrope" label="标签分类编码" min-width="100"></el-table-column>
                        <el-table-column prop="faultTargetName" label="故障标签名称" min-width="100"></el-table-column>
                        <el-table-column prop="collectAttachValue" label="数值" min-width="100"></el-table-column>
                        <el-table-column prop="collectAttachLink" label="外连值" min-width="120"></el-table-column>
                        <el-table-column prop="collectAttachLinkName" label="外链实际值" min-width="160"></el-table-column>
                        <el-table-column prop="collectAttachChar" label="字符值" min-width="140">
                            <template slot-scope="scope">
                                <span v-if="scope.row.eventTargetGrope=='image'">
                                    <el-image
                                        style="width: 30px;height: 20px;margin: 3px 2px 0 2px;"
                                        :src="scope.row.collectAttachChar"
                                        :preview-src-list="[scope.row.collectAttachChar]">
                                    </el-image>
                                </span>
                                <span v-else>{{ scope.row.collectAttachChar }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createdDate}}</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

            </div>

            <!-- 弹窗, 新增 / 修改 -->
            <!--<Dialog v-show="DialogVisible" ref="Dialogs" @refreshDataList="getData(1)"></Dialog>
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
            </el-dialog>-->
        </div>
    </div>
</template>

<script>
    import {getInfoFaultAttachDetail} from "@/api/fault";
    export default {
        name: "FaultAttach",
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
                    path : './FaultDetail'
                })
            },
            //获取基础列表数据函数
            getData : function() {
                const id = this.$route.query.faultDetailId;
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                getInfoFaultAttachDetail(id).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        this.tableData = res.data.data.data;
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
</style>
