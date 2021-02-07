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
                <p class="title">事件采集卡片</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateHandle(id)" size="small"
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
                <el-row class="card-title-box">
                    <el-col :span="5" class="c">
                        <div>
                            <el-row style=" margin-top: 20px;">
                                <el-col :span="24"  class="title-box-svg" >
                                    <div  class="title-bg-box-svg">
                                        <img style="width: 100%;height: 100%;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNjAwODM5MjYxODczIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQwNTciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMTAwNC43NDM5NTggMjkwLjE4MzM0YTM2Ljg2MjI4NSAzNi44NjIyODUgMCAwIDAtMy40MjkwNS0xMC43MTU3OHYtNS4xNDM1NzVhMzguMTQ4MTc5IDM4LjE0ODE3OSAwIDAgMC05LjQyOTg4Ny0xMS4xNDQ0MTIgMzguNTc2ODEgMzguNTc2ODEgMCAwIDAtMTYuMjg3OTg2LTYuMDAwODM3bC0yMzcuODkwMzMxLTU1LjcyMjA2YTM4LjMxOTYzMiAzOC4zMTk2MzIgMCAwIDAtMTcuNTczODggNzQuNTgxODM0bDk3LjcyNzkxOSAyMi43MTc0NTUtMzA3LjMyODU4OSA4OS41ODM5MjYtMzA3LjMyODU5LTkxLjI5ODQ1MSA5Ny4yOTkyODktMjIuNzE3NDU1YTM4LjU3NjgxIDM4LjU3NjgxIDAgMCAwIDI5LjU3NTU1NC0yNi4xNDY1MDUgMzguMTQ4MTc5IDM4LjE0ODE3OSAwIDAgMC05LjAwMTI1NS0zOC4xNDgxNzkgMzkuMDA1NDQyIDM5LjAwNTQ0MiAwIDAgMC0zOC4xNDgxNzktMTAuMjg3MTQ5TDQ1LjAzODY0MiAyNTcuMTc4NzM2YTM4LjE0ODE3OSAzOC4xNDgxNzkgMCAwIDAtMTQuMTQ0ODMxIDYuNDI5NDY4IDM3LjcxOTU0OCAzNy43MTk1NDggMCAwIDAtOS44NTg1MTggMTEuNTczMDQzdjQuNzE0OTQ0YTM2Ljg2MjI4NSAzNi44NjIyODUgMCAwIDAtNS41NzIyMDYgMTAuMjg3MTQ5djU1OS43OTIzODJhMzguNTc2ODEgMzguNTc2ODEgMCAwIDAgMjcuNDMyMzk5IDM2Ljg2MjI4Nmw0NTYuNDkyMjU2IDEzNS40NDc0NjdhMzkuNDM0MDczIDM5LjQzNDA3MyAwIDAgMCAyMi4yODg4MjQgMGwyNjIuNzUwOTQyLTc5LjI5Njc3N2EzOC4xNDgxNzkgMzguMTQ4MTc5IDAgMCAwIDI1LjcxNzg3My00Ny41NzgwNjYgMzguNTc2ODEgMzguNTc2ODEgMCAwIDAtNDguMDA2Njk3LTI1LjcxNzg3NGwtMjE0LjMxNTYxMyA2My40Mzc0MjJWNDU2LjkyMDg4N2wzODAuMTk1ODk3LTExNC4wMTU5MDZ2Mzc3LjE5NTQ3OWEzOC41NzY4MSAzOC41NzY4MSAwIDAgMCA3Ny4xNTM2MjEgMHYtNDI4LjYzMTIyNmEyNC44NjA2MTEgMjQuODYwNjExIDAgMCAwIDAtMy4wMDA0MTl6TTkyLjYxNjcwOCAzNDIuOTA0OTgxbDM3OS4zMzg2MzUgMTEyLjczMDAxM3Y0NzUuNzgwNjYxbC0zNzkuMzM4NjM1LTExMS40NDQxMTl6IG04NzQuNDA3NzAyIDQ1OC4yMDY3ODFhMzguNTc2ODEgMzguNTc2ODEgMCAwIDAtMzguNTc2ODEgMzguMTQ4MTc5djMuODU3NjgxYTM2LjQzMzY1NCAzNi40MzM2NTQgMCAwIDAgMTEuNTczMDQzIDI2LjE0NjUwNSAzNy4yOTA5MTcgMzcuMjkwOTE3IDAgMCAwIDI3LjAwMzc2NyA5Ljg1ODUxOCA0MC4yOTEzMzUgNDAuMjkxMzM1IDAgMCAwIDM4LjU3NjgxLTQwLjI5MTMzNSAzOC4xNDgxNzkgMzguMTQ4MTc5IDAgMCAwLTExLjE0NDQxMi0yNy4wMDM3NjcgMzkuMDA1NDQyIDM5LjAwNTQ0MiAwIDAgMC0yNy40MzIzOTgtMTEuMTQ0NDEyeiBtMCAwIiBwLWlkPSI0MDU4IiBmaWxsPSIjNDA5RUZGIj48L3BhdGg+PHBhdGggZD0iTTQ4Ni45NTc0MzYgMzAwLjA0MTg1OWEzOC41NzY4MSAzOC41NzY4MSAwIDAgMCA5LjQyOTg4NyA2LjAwMDgzNyAzOC41NzY4MSAzOC41NzY4MSAwIDAgMCAxNC41NzM0NjIgMy4wMDA0MTggMzAuNDMyODE3IDMwLjQzMjgxNyAwIDAgMCAxOC44NTk3NzQtNS41NzIyMDZsNi4wMDA4MzctMy40MjkwNDkgMTAwLjI5OTcwNy04NS43MjYyNDZhMzguMTQ4MTc5IDM4LjE0ODE3OSAwIDAgMC0xMi44NTg5MzctNjYuMDA5MjA5IDM5LjAwNTQ0MiAzOS4wMDU0NDIgMCAwIDAtMzguNTc2ODEgOC41NzI2MjVsLTM2LjAwNTAyMyAzMS43MTg3MTFWMzguNTc2ODFhMzguNTc2ODEgMzguNTc2ODEgMCAwIDAtNzcuMTUzNjIxIDB2MTQ5LjE2MzY2N2wtMzguNTc2ODEtMzMuNDMzMjM1YTM5LjAwNTQ0MiAzOS4wMDU0NDIgMCAwIDAtMzguNTc2ODExLTguMTQzOTk0IDM4LjE0ODE3OSAzOC4xNDgxNzkgMCAwIDAtMTEuNTczMDQzIDY4LjE1MjM2NXogbTAgMCIgcC1pZD0iNDA1OSIgZmlsbD0iIzQwOUVGRiI+PC9wYXRoPjwvc3ZnPg==">
                                    </div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col :span="24"  class="title-text-box-svg">
                                    <div class="title-text-content-svg">{{InfoCollectEventDetail.collectEvent}}</div>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                    <el-col :span="10" class="">
                        <div class="card-right-content">
                            <el-row>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件采集名称 ：{{ InfoCollectEventDetail.collectEvent }} </span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件级别 ：{{ InfoCollectEventDetail.eventLevelName }} </span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件标签 ：{{ InfoCollectEventDetail.eventTargetName }}</span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件大类 ：{{ InfoCollectEventDetail.eventType }}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件采集编码 ：{{ InfoCollectEventDetail.collectCode }} </span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建人：{{ InfoCollectEventDetail.createdUserName }} </span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">修改人：{{ InfoCollectEventDetail.modifiedUserName }} </span>
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
                                        <span class="text-public">事件描述 ：{{ InfoCollectEventDetail.descriptionContent }}</span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件类别 ：{{ InfoCollectEventDetail.eventTypeName }} </span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">设备类型 ：{{ InfoCollectEventDetail.deviceTypeName }}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">事件小类 ：{{ InfoCollectEventDetail.eventDetail }}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span
                                            class="text-public">是否启用 ：{{ InfoCollectEventDetail.isEnabled == 1 ? '已启用' : '未启用' }}</span>
                                    </div>
                                </el-col>
                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">创建时间：{{ InfoCollectEventDetail.createdDate }} </span>
                                    </div>
                                </el-col>

                                <el-col :span="24">
                                    <div class="mtop">
                                        <span class="text-public">修改时间：{{ InfoCollectEventDetail.modifiedDate }} </span>
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
                <p class="title">事件扩展列表</p>
                <div class="btn_list">
                    <el-button type="primary" @click="addOrUpdateEventExtendHandle(InfoCollectEventDetail.collectEventId, null)" size="small"
                               style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="el-icon-edit"></i>新增事件扩展
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
                        <el-table-column prop="eventTargetName" label="子标签名称" min-width="140"></el-table-column>
                        <el-table-column prop="eventTargetCode" label="子标签编码" min-width="140"></el-table-column>
                        <el-table-column prop="eventTargetGrope" label="子标签类型" min-width="140"></el-table-column>
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
                                           @click="addOrUpdateEventExtendHandle(InfoCollectEventDetail.collectEventId, scope.row.eventExtendId)">
                                    <i class="el-icon-edit"></i>修改
                                </el-button>
                                <el-button size="mini" type="danger"
                                           @click="handleDelete(scope.row.eventExtendId, scope.row.eventTargetName)">
                                    <i class="el-icon-delete"></i>删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

            </div>

            <!-- 弹窗, 新增 / 修改 -->
            <Dialog v-show="DialogVisible" ref="Dialogs" @refreshDataList="getData(id)"></Dialog>
            <DialogEventExtend v-show="DialogVisibleEventExtend" ref="DialogEventExtends"
                         @refreshDataList="getData(id)"></DialogEventExtend>
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
import {getInfoCollectEventWithExtendDetail, deleteInfoEventExtend, deleteInfoEventExtendAll} from "@/api/event";
import Dialog from "../CollectEventList/Dialog";
import DialogEventExtend from "../CollectEventExtend/DialogEventExtend";

export default {
    name: "CollectEventCard",
    data() {
        return {
            InfoCollectEventDetail: {
                collectEventId:'',
                collectEvent: '',
                collectCode: '',
                eventLevelId: '',
                eventLevelName:'',
                eventTypeId: '',
                eventTypeName:'',
                eventTargetId: '',
                eventTargetName:'',
                deviceTypeId: '',
                deviceTypeName:'',
                eventType: '',
                eventDetail: '',
                eventDescriptionId: '',
                descriptionContent: '',
                isEnabled: 1,
                isEntered: 0,
                createUserName: '',
                createdDate: '',
                modifiedUserName: '',
                modifiedDate: ''
            },
            id:'',
            eventExtendList: [],
            DialogVisible: false,
            DialogVisibleEventExtend: false,
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
        DialogEventExtend
    },
    methods: {
        goBack: function () {
            this.$router.push({
                path: './List'
            })
        },
        handleReload: function () {
            this.id = sessionStorage.getItem('objectId')
            this.getData(this.id);
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
                deleteInfoEventExtendAll(this.deleteAllForm).then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.$message({
                            message: '操作成功',
                            type: 'success',
                            offset: 300,
                            duration: 1500,
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
                    this.deleteAllForm.ids.push(this.checkedList[i].eventExtendId)
                }
            }
            console.log(this.deleteAllForm.ids);
        },
        //获取基础列表数据函数
        getData: function (id) {
            console.log(id)
            const loading = this.$loading({
                lock: true,
                text: "加载中.......",
                spinner: "el-icon-loading",
                background: "rgba(0, 0, 0, 0.7)"
            });
            getInfoCollectEventWithExtendDetail(id).then((res) => {
                loading.close();
                if (res.data && res.data.code === 0) {
                    this.InfoCollectEventDetail = res.data.data.data
                    this.eventExtendList = res.data.data.data.eventExtendList
                }
            })
        },
        //增加或修改
        addOrUpdateHandle: function (id) {
            this.DialogVisible = true;
            this.$nextTick(() => {
                this.$refs.Dialogs.initData(id);
            });
        },
        //增加或者修改员工的回调函数
        addOrUpdateEventExtendHandle: function (collectEventId, eventExtendId) {
            this.DialogVisibleEventExtend = true;
            this.$nextTick(() => {
                this.$refs.DialogEventExtends.initData(collectEventId, eventExtendId);
            });
        },
        //删除当前条的数据操作
        handleDelete: function (id, name) {
            this.$confirm(`确定要删除"${name}"吗?`, "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(() => {
                deleteInfoEventExtend(id)
                    .then((res) => {
                        if (res.data && res.data.code === 0) {
                            this.$message({
                                message: "操作成功",
                                type: "success",
                                offset: 300,
                                duration: 1500,
                                onClose: () => {
                                    this.handleReload()
                                }
                            });
                        } else {
                            this.$message({
                                message: res.data.message,
                                type: "error",
                                offset: 300,
                                duration: 1500,
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
        this.id = sessionStorage.getItem('objectId')
        this.getData(this.id);
    }
};
</script>
<style scoped>
.table_div {
    margin-bottom: 20px;
}
.card-right-content{
    padding-bottom:15px;
}
</style>
