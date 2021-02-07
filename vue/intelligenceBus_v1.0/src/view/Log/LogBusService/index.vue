<!--
 @Author: psp
 @Filename: index.vue
 @ProjectName: aibus-manage
 @Mail:
 @Date: 2020-08-28
 @file-description: 设备日志表组件页面
-->

<template>
    <div>
        <!-- 搜索项 -->
        <div class="cotroller_box">
            <el-form ref="form" :inline="true" :model="form" label-width="120px" style="width:100%">
                <el-form-item label="公交线路" prop="companyLine">
                    <el-select v-model="form.companyLine" @change="changeLine" filterable placeholder="请选择公交线路" clearable>
                        <el-option :label="item.companyLineName"
                                   :value="item.companyLineId"
                                   :key="item.companyLineId"
                                   v-for="(item, index) in conditionQuery.companyLineList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="公交站点" prop="busStation">
                    <el-select v-model="form.busStation" filterable placeholder="请选择公交站点" clearable>
                        <el-option :label="item.busStationName"
                                   :value="item.busStationId"
                                   :key="item.busStationId"
                                   v-for="(item, index) in conditionQuery.busStationList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车辆VIN码" prop="vinCode">
                    <el-select v-model="form.vinCode" filterable placeholder="请选择车辆VIN码" clearable>
                        <el-option :label="item"
                                   :value="item"
                                   :key="item"
                                   v-for="(item, index) in conditionQuery.vinCodeList"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车牌号" prop="plateCode">
                    <el-select v-model="form.plateCode" filterable placeholder="请选择车牌号" clearable>
                        <el-option :label="item"
                                   :value="item"
                                   :key="item"
                                   v-for="(item, index) in conditionQuery.plateCodeList"></el-option>
                    </el-select>
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

                <p class="title">营运日志列表</p>
                <div class="btn_list">
                    <el-button type="warning" @click="handleReload" size="small" style="float: right;margin-right:10px;margin-left:0px !important;">
                        <i class="fa fa-refresh"></i>刷新</el-button>
                </div>
            </div>

            <!-- 分页列表 -->
            <div class="table_div" >
                <div style="position: relative;">
                    <el-table :data="tableData" style="width: 100%" max-height="450">
                        <el-table-column fixed type="selection" width="50px" ></el-table-column>
                        <el-table-column fixed type="index" align="center" :index="tableIndex" label="序号" width="50px"></el-table-column>
                        <el-table-column prop="vinCode" label="车辆VIN码" min-width="120"></el-table-column>
                        <el-table-column prop="plateCode" label="车牌号" min-width="100"></el-table-column>
                        <el-table-column prop="companyLineName" label="公交线路" min-width="150"></el-table-column>
                        <el-table-column prop="driverCode" label="司机特征码" min-width="100"></el-table-column>
                        <el-table-column label="司机照片" min-width="100">
                            <template slot-scope="scope">
                                <el-image
                                    style="height: 40px"
                                    :src="scope.row.driverImage"
                                    :preview-src-list="[scope.row.driverImage]"
                                    fit="scale-down"></el-image>
                            </template>
                        </el-table-column>
                        <el-table-column prop="companyLongitude" label="经度" min-width="100"></el-table-column>
                        <el-table-column prop="companytLatitude" label="纬度" min-width="100"></el-table-column>
                        <el-table-column prop="busStationName" label="停靠站" min-width="130"></el-table-column>
                        <el-table-column prop="nextStationName" label="下一站" min-width="130"></el-table-column>
                        <el-table-column prop="busGetOn" label="上车人数" min-width="100"></el-table-column>
                        <el-table-column prop="busGetOff" label="下车人数" min-width="100"></el-table-column>
                        <el-table-column prop="busKeepRide" label="乘车人数" min-width="100"></el-table-column>
                        <el-table-column prop="childrenNum" label="儿童人数" min-width="100"></el-table-column>
                        <el-table-column prop="adultNum" label="成年人数" min-width="100"></el-table-column>
                        <el-table-column prop="oldMun" label="老年人数" min-width="100"></el-table-column>
                        <el-table-column prop="childrenTotal" label="儿童总人数" min-width="100"></el-table-column>
                        <el-table-column prop="adultTotal" label="成年总人数" min-width="100"></el-table-column>
                        <el-table-column prop="oldTotal" label="老年总人数" min-width="100"></el-table-column>
                        <el-table-column prop="maleTotal" label="男性累计" min-width="100"></el-table-column>
                        <el-table-column prop="femaleTotal" label="女性累计" min-width="100"></el-table-column>
                        <el-table-column label="是否启用" width="70px">
                            <template slot-scope="scope">{{scope.row.isEnabled == 1?'是':'否' }}</template>
                        </el-table-column>
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
    import { getLogBusServiceList} from "@/api/parameter";
    import {getBusStationByLineS,getPlateCodeListByCompany,getVinCodeListByCompany} from "@/api/selectionApi";
    import{getLineListByCompanyIdData} from "@/api/sale";
    export default {
        name: "LogBusDrive",
        data() {
            return {
                form: {
                    vinCode: "",
                    plateCode:"",
                    companyLine:'',
                    busStation:'',
                    startTime: "",
                    endTime: "",
                    page: 1,
                    limit: 10
                },
                conditionQuery: {
                    vinCodeList:[],
                    plateCodeList:[],
                    companyLineList:[],
                    busStationList:[]
                },
                tableData: [],
                table: {},
                visible : false,
                checkedList :[],
                deleteAllForm : {
                    validatePassword : '',
                    ids : []
                }
            };
        },
        activated : function() {
            this.getData();
        },
        methods: {
            changeLine(data){
                this.form.companyLine=data;
                this.conditionQuery.busStationList=[];
                this.form.busStation='';
                getBusStationByLineS(data).then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.busStationList = res.data.data.list;
                    }
                });
            },
            tableIndex(index) {
                return (this.form.page - 1) * this.form.limit + index + 1
            },
            clearInput : function(){
                this.form.deviceName = '';
                this.getData(1);
            },
            handleReload : function(){
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
                if(page){this.form.page=1;}
                console.log("分页查询:"+JSON.stringify(this.form));
                getLogBusServiceList(this.form).then((res) => {
                    loading.close();
                    if(res.data && res.data.code === 0){
                        this.tableData = res.data.data.page.list;
                        this.table = res.data.data.page;
                    }
                });
            },
            getInitData(){
                getVinCodeListByCompany().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.vinCodeList = res.data.data.list
                    }
                });
                getPlateCodeListByCompany().then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.plateCodeList = res.data.data.list
                    }
                });
                getLineListByCompanyIdData(0).then((res) => {
                    if (res.data && res.data.code === 0) {
                        this.conditionQuery.companyLineList = res.data.data.list
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
            }
        },
        mounted : function() {
            this.getInitData();
            this.getData();
        }
    };
</script>

<style scoped>

</style>
