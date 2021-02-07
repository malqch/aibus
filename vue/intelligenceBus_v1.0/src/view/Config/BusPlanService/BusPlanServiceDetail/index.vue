<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage-view
 @Mail: songwenxin666@sina.com
 @Date: 2019-12-04 10:21
 @file-description: BasicIFM  => 基础信息-事件采集
 http://qyxy.baic.gov.cn/wap/wap/creditWapAction!qr.dhtml?id=ff8080815b575d42015b5bcbb16a55d3
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
                <p class="title">营运线路卡片</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle(InfoDetail.companyLineId)" size="small"
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
                <el-row class="card-title-box" style="padding-bottom:15px;">
                    <el-col :span="5" class="c">
                        <div>
                            <el-row style=" margin-top: 20px;">
                                <el-col :span="24"  class="title-box-svg" >
                                    <div  class="title-bg-box-svg">
                                        <img style="width: 100%;height: 100%;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjAwODM4NzUwNzMxIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjMyMjYiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNODgxLjY2Nzk1NCA2ODAuMDMwODg4bDAgNDcuNDQ0MDE1cTAgMjIuNzMzNTkxLTQuOTQyMDg1IDM5LjUzNjY4dC0xNy43OTE1MDYgMjYuNjg3MjU5LTM4LjU0ODI2MyA5Ljg4NDE3bDAgNjEuMjgxODUzcTAgMjUuNjk4ODQyLTE4LjI4NTcxNCA0My45ODQ1NTZ0LTQzLjk4NDU1NiAxOC4yODU3MTQtNDMuNDkwMzQ3LTE4LjI4NTcxNC0xNy43OTE1MDYtNDMuOTg0NTU2bDAtNjEuMjgxODUzLTM3MS42NDQ3ODggMCAwIDYxLjI4MTg1M3EwIDI1LjY5ODg0Mi0xNy43OTE1MDYgNDMuOTg0NTU2dC00My40OTAzNDcgMTguMjg1NzE0LTQzLjk4NDU1Ni0xOC4yODU3MTQtMTguMjg1NzE0LTQzLjk4NDU1NmwwLTYxLjI4MTg1M3EtMjQuNzEwNDI1IDAtMzcuNTU5ODQ2LTkuODg0MTd0LTE3Ljc5MTUwNi0yNi42ODcyNTktNS40MzYyOTMtMzkuMDQyNDcxLTAuNDk0MjA4LTQ3LjkzODIyNGwwLTU1Ni40Nzg3NjRxMC0yNS42OTg4NDIgOS44ODQxNy00Ny45MzgyMjR0MjYuNjg3MjU5LTM5LjA0MjQ3MSAzOS4wNDI0NzEtMjYuNjg3MjU5IDQ3LjkzODIyNC05Ljg4NDE3bDQ5NC4yMDg0OTQgMHEyNS42OTg4NDIgMCA0Ny45MzgyMjQgOS44ODQxN3QzOS4wNDI0NzEgMjYuNjg3MjU5IDI2LjY4NzI1OSAzOS4wNDI0NzEgOS44ODQxNyA0Ny45MzgyMjRsMCA1NTYuNDc4NzY0ek0zODUuNDgyNjI1IDYxLjI4MTg1M3EtMTIuODQ5NDIxIDAtMjEuNzQ1MTc0IDguODk1NzUzdC04Ljg5NTc1MyAyMS43NDUxNzRxMCAxMy44Mzc4MzggOC44OTU3NTMgMjIuNzMzNTkxdDIxLjc0NTE3NCA4Ljg5NTc1M2wyNTEuMDU3OTE1IDBxMTIuODQ5NDIxIDAgMjEuNzQ1MTc0LTguODk1NzUzdDguODk1NzUzLTIyLjczMzU5MXEwLTEyLjg0OTQyMS04Ljg5NTc1My0yMS43NDUxNzR0LTIxLjc0NTE3NC04Ljg5NTc1M2wtMjUxLjA1NzkxNSAwek0yOTIuNTcxNDI5IDY4OS45MTUwNThxMTguNzc5OTIzIDAgMzIuMTIzNTUyLTEzLjM0MzYyOXQxMy4zNDM2MjktMzIuMTIzNTUyLTEzLjM0MzYyOS0zMi4xMjM1NTItMzIuMTIzNTUyLTEzLjM0MzYyOXEtMTkuNzY4MzQgMC0zMi42MTc3NjEgMTMuMzQzNjI5dC0xMi44NDk0MjEgMzIuMTIzNTUyIDEyLjg0OTQyMSAzMi4xMjM1NTIgMzIuNjE3NzYxIDEzLjM0MzYyOXpNNzI1LjQ5ODA2OSA2ODcuOTM4MjI0cTE3Ljc5MTUwNiAwIDMwLjE0NjcxOC0xMi44NDk0MjF0MTIuMzU1MjEyLTMwLjY0MDkyNy0xMi4zNTUyMTItMzAuNjQwOTI3LTMwLjE0NjcxOC0xMi44NDk0MjFxLTE4Ljc3OTkyMyAwLTMxLjEzNTEzNSAxMi44NDk0MjF0LTEyLjM1NTIxMiAzMC42NDA5MjcgMTIuMzU1MjEyIDMwLjY0MDkyNyAzMS4xMzUxMzUgMTIuODQ5NDIxek04MjAuMzg2MSAxODQuODMzOTc3bC02MTguNzQ5MDM1IDAgMCAyNDcuMTA0MjQ3IDYxOC43NDkwMzUgMCAwLTI0Ny4xMDQyNDd6IiBmaWxsPSIjNDA5RUZGIiBwLWlkPSIzMjI3Ij48L3BhdGg+PC9zdmc+">
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24"  class="title-text-box-svg">
                                    <div class="title-text-content-svg">{{InfoDetail.companyLineName}}</div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="10" class="c">
                        <div class="card-right-content" style="">
                            <el-row>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">线路名称 ：{{ InfoDetail.companyLineName }} </span>
                                    </div>
                                </el-col>


                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">行驶方向 ：{{ InfoDetail.directionName}} </span>
                                    </div>
                                </el-col>

<!--                                <el-col :span="24">-->
<!--                                    <div class="mtop">-->
<!--                                        <span class="text-public">夏季首班时间 ：{{ InfoDetail.summerFirstTime }}</span>-->
<!--                                    </div>-->
<!--                                </el-col>-->

<!--                                <el-col :span="24">-->
<!--                                    <div class="mtop">-->
<!--                                        <span-->
<!--                                            class="text-public">冬季首班时间：{{ InfoDetail.winterFirstTime}}</span>-->
<!--                                    </div>-->
<!--                                </el-col>-->
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span
                                            class="text-public">是否启用 ：{{ InfoDetail.isEnabled == 1 ? '已启用' : '未启用' }}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建人：{{ InfoDetail.createUserName }} </span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">修改人：{{ InfoDetail.modifiedUserName }} </span>
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
                                        <span class="text-public">校车线路编码 ：{{ InfoDetail.companyLineCode }} </span>
                                    </div>
                                </el-col>

<!--                                <el-col :span="24">-->
<!--                                    <div class="mtop">-->
<!--                                        <span class="text-public">夏季结束时间 ：{{ InfoDetail.summerEndTime  }} </span>-->
<!--                                    </div>-->
<!--                                </el-col>-->
<!--                                <el-col :span="24">-->
<!--                                    <div class="mtop">-->
<!--                                        <span class="text-public">夏季末班时间 ：{{ InfoDetail.summerLastTime }}</span>-->
<!--                                    </div>-->
<!--                                </el-col>-->
<!--                                <el-col :span="24">-->
<!--                                    <div class="mtop">-->
<!--                                        <span-->
<!--                                            class="text-public">冬季末班时间 ：{{ InfoDetail.winterLastTime }}</span>-->
<!--                                    </div>-->
<!--                                </el-col>-->

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">校车公司名称：{{ InfoDetail.companyName }} </span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建时间：{{ InfoDetail.createdDate }} </span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">修改时间：{{ InfoDetail.modifiedDate }} </span>
                                    </div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                </el-row>
            </div>

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
                <p class="title">车辆营运列表</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateEventExtendHandle(InfoDetail.companyLineId,'','')" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>新增
                    </el-button>
                    <el-button type="danger" @click="handleDeleteAll" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-delete"></i>删除
                    </el-button>
                </div>
            </div>

            <div class="table_div">
                <div style="position: relative">
                    <el-table :data="eventExtendList" style="width: 100%" max-height="300"
                              @selection-change="handleSelectionChange">
                        <el-table-column fixed type="selection" width="50px"></el-table-column>
                        <el-table-column fixed type="index" align="center" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="busCode" label="车辆编号" width="100"></el-table-column>
                        <el-table-column prop="plateCode" label="车牌号" width="100"></el-table-column>
                        <el-table-column prop="vinCode" label="车辆VIN码" width="140"></el-table-column>
                        <el-table-column prop="beginDate" label="开始时间" min-width="140"></el-table-column>
                        <el-table-column prop="endDate" label="截至时间" min-width="140"></el-table-column>
                        <el-table-column label="是否启用" width="80">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1 ? '是' : '否' }}</template>
                        </el-table-column>
                        <el-table-column label="创建人/创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{ scope.row.createdUserName }}</span><br>
                                <span>{{ scope.row.createdDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="修改人/修改时间" width="150">
                            <template slot-scope="scope">
                                <span>{{ scope.row.modifiedUserName }}</span><br>
                                <span>{{ scope.row.modifiedDate }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="warning"
                                           @click="addOrUpdateEventExtendHandle(InfoDetail.companyLineId,scope.row.planServiceId,scope.row.vinCode)">
                                    <i class="el-icon-edit"></i>修改
                                </el-button>
                                <el-button size="mini" type="danger"
                                           @click="handleDelete(scope.row.planServiceId, '')">
                                    <i class="el-icon-delete"></i>删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

            </div>

            <!-- 弹窗, 新增 / 修改 -->
            <Dialog v-show="DialogVisible" ref="Dialogs" @refreshDataList="getData()"></Dialog>
            <!-- 校车线路弹窗 -->
            <LineDialog v-show="LineDialogVisible" ref="LineDialogRef" @refreshDataList="getData()"></LineDialog>
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
import {getCompanyLineIdDetail,getPlanBusServicePage, deletePlanBusService, deletePlanBusServiceAll} from "@/api/parameter";
import Dialog from "./Dialog";
import LineDialog from "../BusPlanService/Dialog.vue";

export default {
    name: "BusPlan",
    data() {
        return {
            InfoDetail: {
            },
            id:'',
            companyId:'',
            direction:'',
            eventExtendList: [],
            DialogVisible: false,
            LineDialogVisible: false,
            visible: false,
            checkedList: [],
            deleteAllForm: {
                validatePassword: '',
                ids: []
            },
        };
    },
    components: {
        Dialog,
        LineDialog
    },
    methods: {
        goBack: function () {
            this.$router.push({
                path: './BusPlanService'
            })
        },
        handleReload: function () {
            this.getData();
        },
        dataFormSubmit: function () {
            if (this.deleteAllForm.validatePassword == '') {
                this.$message({
                    message: '用户密码不能为空,请输入用户密码！',
                    type: "error",
                    offset: 300,
                    duration: 500
                });
            } else {
                console.log(this.deleteAllForm);
                deletePlanBusServiceAll(this.deleteAllForm).then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.$message({
                            message: '操作成功',
                            type: 'success',
                            offset: 300,
                            duration: 500,
                            onClose: () => {
                                this.visible = false
                                this.handleReload()
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
                for (let i = 0; i < this.checkedList.length; i++) {
                    this.deleteAllForm.ids.push(this.checkedList[i].planServiceId)
                }
            }
            console.log(this.deleteAllForm.ids);
        },
        //获取基础列表数据函数
        getData: function () {
            const loading = this.$loading({
                lock: true,
                text: "加载中.......",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            getCompanyLineIdDetail(this.id).then((res) => {
                loading.close();
                if (res.data && res.data.code === 0) {
                    this.InfoDetail = res.data.data.data
                    this.companyId = this.InfoDetail.companyId;
                    this.direction = this.InfoDetail.direction;
                    this.InfoDetail.summerStartTime=this.InfoDetail.summerStartTime.substr(5,2) + '月'
                        + this.InfoDetail.summerStartTime.substr(8,2) + '日';
                    this.InfoDetail.summerFirstTime=this.InfoDetail.summerFirstTime.substr(11,2) + '点'
                        + this.InfoDetail.summerFirstTime.substr(14,2) + '分';
                    this.InfoDetail.winterFirstTime=this.InfoDetail.winterFirstTime.substr(11,2) + '点'
                        + this.InfoDetail.winterFirstTime.substr(14,2) + '分';
                    this.InfoDetail.summerEndTime=this.InfoDetail.summerEndTime.substr(5,2) + '月'
                        + this.InfoDetail.summerEndTime.substr(8,2) + '日';
                    this.InfoDetail.summerLastTime=this.InfoDetail.summerLastTime.substr(11,2) + '点'
                        + this.InfoDetail.summerLastTime.substr(14,2) + '分';
                    this.InfoDetail.winterLastTime=this.InfoDetail.winterLastTime.substr(11,2) + '点'
                        + this.InfoDetail.winterLastTime.substr(14,2) + '分';
                }
            })
            getPlanBusServicePage(this.id).then((res) => {
                loading.close();
                if (res.data && res.data.code === 0) {
                    this.eventExtendList = res.data.data.list
                }
            })
        },
        //修改主订单
        addOrUpdateHandle: function (id) {
            this.LineDialogVisible = true;
            this.$nextTick(() => {
                this.$refs.LineDialogRef.initData(id);
            });
        },
        //增加或者修改员工的回调函数
        addOrUpdateEventExtendHandle: function (companyLineId,planServiceId,vinCode) {
            this.DialogVisible = true;
            this.$nextTick(() => {
                this.$refs.Dialogs.initData(this.companyId,this.direction,companyLineId,planServiceId,vinCode);
            });
        },
        //删除当前条的数据操作
        handleDelete: function (id, name) {
            this.$confirm(`确定要删除吗?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(() => {
                deletePlanBusService(id)
                    .then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.$message({
                                message: "操作成功",
                                type: "success",
                                offset: 300,
                                duration: 500,
                                onClose: () => {
                                    this.handleReload()
                                }
                            });
                        } else {
                            this.$message({
                                message: res.data.message,
                                type: "error",
                                offset: 300,
                                duration: 500,
                                onClose: () => {
                                    this.handleReload()
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
        }
    },
    mounted: function () {
        this.id = this.$route.query.companyLineId;
        this.getData();
    }
};
</script>
<style scoped>
.table_div {
    margin-bottom: 20px;
}
</style>
