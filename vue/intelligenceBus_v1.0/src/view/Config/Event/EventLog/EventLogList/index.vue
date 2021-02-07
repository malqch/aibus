<template>
    <div>
        <!-- 搜索项 -->
        <div class="cotroller_box">
            <el-form ref="form" :model="formQuery"  :inline="true" class="demo-form-inline" label-width="120px" style="width:100%" >
                <el-form-item label="车辆VIN码" prop="vinCode">
                    <el-select v-model="formQuery.vinCode" filterable placeholder="请选择VIN码" clearable>
                        <el-option :label="item"
                        :value="item"
                        :key="item"
                        v-for="(item, index) in conditionQuery.vinCodeList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车牌号" prop="plateCode">
                    <el-select v-model="formQuery.plateCode" filterable placeholder="请选择车牌号" clearable>
                        <el-option :label="item"
                                   :value="item"
                                   :key="item"
                                   v-for="(item, index) in conditionQuery.plateCodeList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="事件采集" prop="collectEventId">
                    <el-select v-model="formQuery.collectEventId" placeholder="请选择事件采集" clearable>
                        <el-option :label="item.collectEvent"
                        :value="item.collectEventId"
                        :key="item.collectEventId"
                        v-for="(item, index) in conditionQuery.collectEventNameList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="事件级别" prop="eventLevelId">
                    <el-select v-model="formQuery.eventLevelId" placeholder="请选择事件级别" clearable>
                        <el-option :label="item.eventLevelName" :value="item.eventLevelId" :key="item.eventLevelId"
                                   v-for="(item, index) in conditionQuery.eventLevelNameList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="事件类型" prop="eventTypeId">
                    <el-select v-model="formQuery.eventTypeId" placeholder="请选择事件类型" clearable>
                        <el-option :label="item.eventTypeName" :value="item.eventTypeId" :key="item.eventTypeId"
                                   v-for="(item, index) in conditionQuery.eventTypeNameList">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="事件标签" prop="eventTargetId">
                    <el-select v-model="formQuery.eventTargetId" placeholder="请选择事件标签" clearable>
                        <el-option :label="item.eventTargetName" :value="item.eventTargetId" :key="item.eventTargetId"
                                   v-for="(item, index) in conditionQuery.eventTargetNameList">
                        </el-option>
                    </el-select>
                </el-form-item>
                <!-- <el-form-item label="设备名称" prop="busDeviceId">
                    <el-select v-model="formQuery.deviceTypeId" placeholder="请选择设备名称" clearable>
                        <el-option :label="item.busDeviceName" :value="item.busDeviceId" :key="item.busDeviceId"
                                   v-for="(item, index) in conditionQuery.busDeviceNameList">
                        </el-option>
                    </el-select>
                </el-form-item> -->
                <el-form-item label="设备类型" prop="deviceTypeId">
                    <el-select v-model="formQuery.deviceTypeId" placeholder="请选择设备类型" clearable>
                        <el-option :label="item.deviceTypeName" :value="item.deviceTypeId" :key="item.deviceTypeId"
                                   v-for="(item, index) in conditionQuery.deviceTypeNameList">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="开始时间">
                    <el-date-picker v-model="formQuery.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间">
                    </el-date-picker>
                </el-form-item>

                <el-form-item label="结束时间">
                    <el-date-picker v-model="formQuery.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择结束时间" >
                    </el-date-picker>
                </el-form-item>
                <el-form-item style="margin-top: -1px;">
                    <el-button type="primary" @click="getData(1)" size="small">
                        <i class="fa fa-search" style="margin-right: 4px;font-size:14px;"></i>查询
                    </el-button>
                </el-form-item>
            </el-form>
        </div>

        <div class="show_table" style="width:100%;">

            <!-- 刷新、批量删除 -->
            <div class="show_table_controller"  >
                <!--<span class="table_title_span"></span>-->
                <svg t="1575621249210" class="icons" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2551" width="200" height="200"><path d="M729.014 64.868H224.382v250.69H98.244v361.723h126.138v279.284h703.613V256.39L729.014 64.868zM293.116 888.035v-83.946h293.606V408.1h-68.734v327.458H293.116v-58.276h167.921V315.559H293.116V133.4h379.101v187.055h187.045v567.58H293.116z" fill="#2C91E0" p-id="2552"></path><path d="M660.899 417.683h129.305v66.818H660.899zM660.899 577.049h129.305v66.818H660.899zM660.899 736.414h129.305v66.818H660.899z" fill="#2C91E0" p-id="2553"></path></svg>

                <p class="title">事件日志列表</p>
            </div>

            <!-- 分页列表 -->
            <div class="table_div" >
                <div style="position: relative;">
                    <el-table :data="tableData" style="width: 100%" max-height="450">
                        <el-table-column fixed type="selection" width="50px" ></el-table-column>
                        <el-table-column fixed type="index" align="center" :index="tableIndex" label="序号" width="50px"></el-table-column>

                        <el-table-column prop="vinCode" label="车辆VIN码" min-width="100"></el-table-column>
                        <el-table-column prop="plateCode" label="车牌号" min-width="100"></el-table-column>
                        <el-table-column prop="eventLevelName" label="事件级别" min-width="100"></el-table-column>
                        <el-table-column prop="eventTypeName" label="事件类别" min-width="100"></el-table-column>
                        <el-table-column prop="eventTargetName" label="事件标签" min-width="100"></el-table-column>
                        <el-table-column prop="busDeviceName" label="设备名称" min-width="100"></el-table-column>
                        <el-table-column prop="deviceTypeName" label="设备类别" min-width="100"></el-table-column>
                        <el-table-column label="是否启用" width="70px">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1?'是':'否' }}</template>
                        </el-table-column>
                        <el-table-column prop="createdDate" label="创建时间" width="150"></el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template slot-scope="scope">
                                <el-button size="mini" type="success" @click="goToDetail(scope.row.eventDetailId)">
                                    <i class="el-icon-edit"></i>查看
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

        </div>
    </div>
</template>

<script>
    import {getEventLogDetailByPage,getInfoCollectEventList,
    getInfoEventLevelList,getInfoEventTypeList,getInfoEventTargetList} from "@/api/event";
    import {getDeviceTypeS} from '@/api/selectionApi';
    import {getPlateCodeListByCompany,getVinCodeListByCompany} from "@/api/selectionApi"
    export default {
        name: "EventLog",
        data() {
            return {
                conditionQuery: {
                    vinCodeList:[],
                    plateCodeList:[],
                    collectEventNameList:[],
                    eventLevelNameList: [],
                    eventTypeNameList: [],
                    eventTargetNameList: [],
                    busDeviceNameList:[],
                    deviceTypeNameList: []
                },
                formQuery: {
                    page: 1,
                    limit: 10,
                    startTime:'',
                    endTime:'',
                    vinCode:'',
                    plateCode:'',
                    collectEventId:'',
                    eventLevelId:'',
                    eventTypeId:'',
                    eventTargetId:'',
                    deviceId:'',
                    deviceTypeId:''
                },
                queryDt:'',
                tableData: [],
                table: {},
            };
        },
        mounted: function () {
            this.init();
            this.getData();
        },
        methods: {
            init: function () {
                getInfoCollectEventList().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.collectEventNameList = res.data.data.list
                    }
                })
                getInfoEventLevelList().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventLevelNameList = res.data.data.list
                    }
                })
                getInfoEventTypeList().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventTypeNameList = res.data.data.list
                    }
                })
                getInfoEventTargetList('master').then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.eventTargetNameList = res.data.data.list
                    }
                })

                getDeviceTypeS().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.deviceTypeNameList = res.data.data.selection
                    }
                })
                getVinCodeListByCompany().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.vinCodeList = res.data.data.list
                    }
                })
                getPlateCodeListByCompany().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.plateCodeList = res.data.data.list
                    }
                })

            },
            tableIndex(index) {
                return (this.formQuery.page - 1) * this.formQuery.limit + index + 1
            },
            clearInput : function(){
                this.formQuery.startTime='',
                this.formQuery.endTime='',
                this.formQuery.vinCode='',
                this.formQuery.collectEventId='',
                this.formQuery.eventLevelId='',
                this.formQuery.eventTypeId='',
                this.formQuery.eventTargetId='',
                this.formQuery.deviceId='',
                this.formQuery.deviceTypeId=''
                this.getData(1);
            },
            //获取基础列表数据函数
            getData : function(page) {
                const loading = this.$loading({
                    lock: true,
                    text: "加载中.......",
                    spinner: "el-icon-loading",
                    background: "rgba(0, 0, 0, 0.7)"
                });
                if(page){this.formQuery.page=1;}
                console.log("分页查询:"+JSON.stringify(this.formQuery));
                getEventLogDetailByPage(this.formQuery).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        this.tableData = res.data.data.page.list;
                        this.table = res.data.data.page;
                    }
                });
            },
            //更改每页的条数触发的table渲染
            handleSizeChange : function(val){
                this.formQuery.limit = val;
                this.getData(1);
            },
            //点击分页渲染页面函数
            handleCurrentChange : function(val){
                this.formQuery.page = val;
                this.getData()
            },


            goToDetail: function (eventDetailId) {
                this.$router.push({
                    path: './EventLogCard',
                    query: {
                        eventDetailId: eventDetailId
                    }

                })
            }
        },
    };
</script>

<style scoped>

</style>
