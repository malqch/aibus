<!--
 @Author: psp
 @Filename: index.vue
 @ProjectName: aibus-manage
 @Mail:
 @Date: 2020-08-28
 @file-description: 广告播放日志表组件页面
-->

<template>
    <div>
        <!-- 搜索项 -->
        <div class="cotroller_box">
            <el-form ref="form" :inline="true" :model="form" label-width="120px" style="width:100%">
                <el-form-item label="广告单号" prop="advertiseNo">
                    <el-input v-model="form.advertiseNo" placeholder="广告单号" clearable></el-input>
                </el-form-item>
                <el-form-item label="广告位" prop="positionDesc">
                    <el-input v-model="form.positionDesc" placeholder="广告位" clearable></el-input>
                </el-form-item>

                <el-form-item label="开始时间">
                    <el-date-picker v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间">
                    </el-date-picker>
                </el-form-item>

                <el-form-item label="结束时间">
                    <el-date-picker v-model="form.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择结束时间" >
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

            <!-- 刷新、批量删除 -->
            <div class="show_table_controller"  >
                <!--<span class="table_title_span"></span>-->
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>

                <p class="title">广告播放日志列表</p>
                <div class="btn_list">
                    <el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>
                </div>
            </div>

            <!-- 分页列表 -->
            <div class="table_div" >
                <div style="position: relative;">
                    <el-table :data="tableData" style="width: 100%" max-height="450" @selection-change="handleSelectionChange">
                        <el-table-column fixed type="selection" width="50px" ></el-table-column>
                        <el-table-column fixed type="index" align="center" :index="tableIndex" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="advertiseNo" label="广告单号" min-width="150"></el-table-column>
                        <el-table-column prop="positionGroup" label="广告位" min-width="100"></el-table-column>
                        <el-table-column prop="showTimes" label="播放时长（s）" min-width="100"></el-table-column>
                        <el-table-column label="素材类型" min-width="100">
                                <template slot-scope="scope">{{scope.row.attachType == 0?'图片':'视频' }}</template>
                        </el-table-column>
                        <el-table-column prop="vinCode" label="车辆VIN码" min-width="100"></el-table-column>
                        <el-table-column prop="plateCode" label="车牌号" min-width="100"></el-table-column>
                        <el-table-column prop="companyLineName" label="线路名称" min-width="160"></el-table-column>
                        <el-table-column prop="peopleCount" label="累计人数" min-width="100"></el-table-column>
                        <el-table-column label="创建时间" width="150">
                            <template slot-scope="scope">
                                <span>{{scope.row.createdDate}}</span>
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

        </div>
    </div>
</template>

<script>
    import { getLogAdvertiseShowList} from "@/api/parameter";

    export default {
        name: "LogBusDevice",
        data() {
            return {
                form: {
                    startTime: "",
                    endTime: "",
                    advertiseNo:'',
                    positionDesc:'',
                    page: 1,
                    limit: 10
                },
                tableData: [],
                table: {},
                visible : false,
                checkedList :[],
                conditionQuery: {
                },
            };
        },
        activated : function() {
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
            handleSelectionChange: function (val) {
                this.checkedList = val;
                this.deleteAllForm.ids = [];
                if (this.checkedList.length > 0) {
                    for (var i = 0; i < this.checkedList.length; i++) {
                        this.deleteAllForm.ids.push(this.checkedList[i].logDeviceId)
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
                console.log("分页查询:" + JSON.stringify(this.form));
                getLogAdvertiseShowList(this.form).then((res) => {
                    loading.close();
                    if (res.data && res.data.code === 0) {
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
            }
        },
        mounted : function() {
            this.getData();
        }
    };
</script>

<style scoped>

</style>
