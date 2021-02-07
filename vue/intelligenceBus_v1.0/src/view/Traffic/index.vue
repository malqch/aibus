<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:违规信息组件页面
-->
<template>
    <div>
        <div class="box" style="margin-bottom: 0;">
            <div class="mb10">
                <el-select class="selectArea" v-for="(itemList,index) in areaList" :key="'itemList'+index" v-model="areaIdList[index]"
                    placeholder="请选择" @change="changeAreaList($event,index)" :popper-append-to-body="false">
                    <el-option v-for="item in itemList" :key="item.areaId" :label="item.areaName" :value="item.areaId">
                    </el-option>
                </el-select>
                <el-select class="" v-model="companyName" placeholder="请选择" @change="changeCompany" :popper-append-to-body="false">
                    <el-option
                      v-for="item in companyList"
                      :key="item.companyId"
                      :label="item.companyName"
                      :value="item.companyId">
                    </el-option>
                </el-select>
            </div>
            <div>
                <el-row :gutter="15">
                    <el-col :span="12">
                        <div class="box">
                            <div class="boxTitle">
                                <img :src="infoicon">
                                采集交通违规数统计
                                <el-select v-model="dim" @change='getDimStatData' placeholder="请选择" style="width: 120px;float: right;" :popper-append-to-body="false">
                                    <el-option label="车辆维度" value="bus"></el-option>
                                    <el-option label="时间维度" value="time"></el-option>
                                </el-select>
                            </div>
                            <echarts-single-xbar :barObj="trafficDimStat" :height="height" :color="trafficStatColor" :boxid="trafficStatbox"></echarts-single-xbar>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="box">
                            <div class="boxTitle" style="position: relative;">
                                <img :src="infoicon">
                                采集交通违章类型统计
                            </div>
                            <echarts-y-bar :barObj="trafficTypeStat" :height="height" :color="trafficTypeStatColor" :boxid="trafficTypeStatbox"></echarts-y-bar>
                        </div>
                    </el-col>
                </el-row>
            </div>
            <div class="box" style="margin-bottom: 0;">
                <div class="boxTitle" style="position: relative;">
                    <div class="statusDiv">
                        <el-select class="" style="width:200px" filterable v-model="form.vinCode" clearable placeholder="请选择VIN码">
                            <el-option
                                v-for="item in vinCodeList"
                                :key="item"
                                :label="item"
                                :value="item">
                            </el-option>
                        </el-select>

                        <el-select class="" style="width:160px" filterable v-model="form.plateCode" clearable placeholder="请选择车牌号">
                            <el-option
                                v-for="item in plateCodeList"
                                :key="item"
                                :label="item"
                                :value="item">
                            </el-option>
                        </el-select>

                        <el-input class="vin" v-model="form.busCode" placeholder="请输入车辆编码"></el-input>
                        <el-select class="" style="width:160px" clearable filterable v-model="form.eventTargetName" placeholder="请选择违规类型">
                            <el-option v-for="item in eventTargetNameList"
                                       :key="item"
                                       :label="item"
                                       :value="item">
                            </el-option>
                        </el-select>
                        <el-button type="primary" size="small" icon="el-icon-search" @click="handleList()">搜索</el-button>
                        <el-tooltip content="通过“数据导出”可将违规列表信息导出到Excel文件中" placement="top-start">
                            <el-button type="success" size="small" icon="el-icon-files" @click="dataExport()">导出</el-button>
                        </el-tooltip>
                    </div>
                </div>
                <el-table border :data="trafficStatByCompany" style="width: 100%;margin-top: 45px;">
                    <el-table-column width="60"  type="index" :index="table_index" label="序号" prop="busId"></el-table-column>
                    <el-table-column label="车辆VIN码" prop="vinCode" width="140px" ></el-table-column>
                    <el-table-column label="车牌号" prop="plateCode" width="120px" ></el-table-column>
                    <el-table-column label="车辆编号" prop="busCode" width="120px" ></el-table-column>
                    <el-table-column label="所属线路" prop="companyLineName" width="160px" ></el-table-column>
                    <el-table-column label="违规类型" prop="eventTargetName" width="100px" ></el-table-column>
                    <el-table-column label="违规车牌号" prop="numberPlate" width="100px" ></el-table-column>
                    <el-table-column width="160" label="违规证据查看" prop="evidenceImageList">
                        <template slot-scope="scope">
                            <!-- <el-image
                                style="width: 30px;height: 20px;margin: 3px 2px 0 2px;"
                                v-for="(item,index) in scope.row.evidenceImageList" :src="item"
                                :key="'img'+index"
                                :preview-src-list="[item]">
                            </el-image> -->
                            <img style="width: 30px;height: 20px;margin: 3px 2px 0 2px;"
                                v-for="(item,index) in scope.row.evidenceImageList" :src="item"
                                :key="'img'+index" @click="showPhoto(scope.row.evidenceImageList)"/>
                        </template>
                    </el-table-column>
                    <el-table-column label="位置坐标" prop="eventTargetName" width="150px" >
                        <template slot-scope="scope">
                            {{scope.row.longitude}},{{scope.row.latitude}}
                        </template>
                    </el-table-column>
                    <el-table-column label="违规地点" prop="address"></el-table-column>
                    <el-table-column label="发生时间" prop="happenTime"></el-table-column>
                </el-table>
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="table.currPage"
                    :page-sizes="[10, 20, 50, 100]"
                    :page-size="table.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="table.totalCount" background
                    style="width:100%;text-align:right;margin-top:10px;">
                </el-pagination>
            </div>
        </div>
        <data-export v-show="exportVisible" ref='carDetail'></data-export>
        <show-photo v-show="showPhotoVisible" ref='showPhoto'></show-photo>
    </div>
</template>

<script>
    import areaData from './areaData.json'
    import EchartsSingleXbar from "@/components/bus/echarts-singlebar-xcategory";
    import EchartsYBar from "@/components/bus/echarts-bar-ycategory";
    import dataExport from './dataExport'
    import showPhoto from './showPhoto'
    import {getAreaList,getCustomerCompanyListData,getTrafficDimStatData,getTrafficTypeStatData,
    getTrafficStatByCompanyData,getInitDataByEventType} from "@/api/sale"
    import {getPlateCodeListByCompany,getVinCodeListByCompany} from "@/api/selectionApi"
    export default {
        name: "GfmAdminSalePreview",
        data() {
            return {
                infoicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk3ODA3NjY3MDYwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjYyNTYiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTcxLjkxNzEzNiA1OTEuNTk5OTE5Yy0xMy4yOTA3MDEgMTIuNjg4OTk3LTIyLjYyNDI3NyAyMC42MTI0NTctMjguODUyMTE4IDI1LjYzNjg5IDMuMDQyMjg5LTE2LjQ1MDY3IDEwLjk2ODgxOS00OS4wNjQ0NjIgMzAuMDU5NjItMTEzLjI4OTIwNiAxOS4wMDY4ODktNjMuNjIyMDE2IDIwLjY0MjEzMy03NC43MzIwNTIgMjAuNjQyMTMzLTgwLjE4NzI5NyAwLTkuNTMxMDc0LTMuOTYxMjE4LTE3Ljk3MjMyNy0xMC44ODA4MTUtMjMuODAxMDc5LTE1LjUzMzc4OC0xMi44MDQ2My00Mi45MjI1NzgtMTAuMjc4MDg3LTc1Ljk5Njg1OCA4LjQ0MTI1My0xOC40MzE3OTEgMTAuMzM2NDE2LTM4LjAxMDcwOSAyNy4yMTc4OTgtNTkuODg2OTQ5IDUxLjIxOTU0NWwtMTEuNDAwNjU0IDEyLjY4ODk5NyAzNy44NDE4NjMgMjkuMTY5MzQzIDkuNjczMzEzLTkuNTYwNzVjMTAuNTM2OTg0LTEwLjIxOTc1OSAxNy42ODM3NTQtMTYuODgxNDgyIDIyLjQ1MjM2MS0yMS4zMzE4NDEtMjguODg0ODY0IDk1LjA2MDAyOS00Mi45NTEyMzEgMTU0LjE0MzY4My00Mi45NTEyMzEgMTgwLjU4NTkxNSAwIDEyLjAwMjM1OCAzLjQ3NDEyNSAyMS45NjUyNjggMTAuNTA5MzU1IDI5LjUxNDE5NyA3LjA5MTUxMiA3Ljc1MjU2OCAxNy4wNTIzNzQgMTEuNzk5NzQ0IDI4LjQ0ODkzNiAxMS43OTk3NDQgMTEuMTY4MzY0IDAgMjMuOTQzMzE4LTQuMzA1MDQ5IDM5LjQ3NzEwNy0xMy4xMTg3ODUgMTQuMDEwMDg1LTcuOTUyMTEyIDM1LjAyNzc3MS0yNS4zMjI3MzUgNjQuNDI0Mjg4LTUzLjAwMDA5OGwxMS45NDQwMy0xMS40MjYyMzctMzQuMzkyMjk4LTMzLjUwNTA5Mkw1NzEuOTE3MTM2IDU5MS41OTk5MTkgNTcxLjkxNzEzNiA1OTEuNTk5OTE5ek05MzIuMDAxMTkxIDMzNC4yMTI4MjhjLTIyLjk0MDQ3OC01NC4zMjAxNjMtNTUuOTU4NDc2LTEwMy4xODQwNTctOTcuNzI5ODM1LTE0NC45Mjg4MS00MS44MzE3MzQtNDEuODg5MDM5LTkwLjYxMDY5NC03NC44MTkwMzMtMTQ0LjczMDI4OS05Ny44NDQ0NDUtMTEyLjQ4Mzg2NC00Ny41NDM4MjktMjQyLjQyNzM3OC00Ny41NDM4MjktMzU0Ljk3MTYxNyAwLTU0LjE0NzIyNCAyMy4wMjU0MTMtMTAyLjgzODE4IDU1Ljk1NjQzLTE0NC43MjYxOTYgOTcuODQ0NDQ1LTQxLjgzMTczNCA0MS44MzA3MTEtNzQuODQ5NzMyIDkwLjc1MjkzNC05Ny43MDIyMDYgMTQ0LjkyODgxLTIzLjcxNjE0NCA1Ni40MTQ4NzEtMzUuODMyMDkgMTE2LjA3NDY0NS0zNS44MzIwOSAxNzcuNzQ0MTkzIDAgNjEuNjExMjIgMTIuMDU5NjY0IDEyMS4yNjk5NzEgMzUuNzQ4MTc5IDE3Ny42Mjk1ODMgMjIuODgwMTAzIDU0LjIzMzE4MiA1NS44OTgxMDEgMTAzLjAzOTc3MSA5Ny43MjU3NDIgMTQ1LjA0MzQyMSA0MS43NzU0NTIgNDEuODI5Njg3IDkwLjQ5NTA2IDc0LjgxNjk4NiAxNDQuNzMwMjg5IDk3Ljc4NjExNyA1Ni4zMjk5MzYgMjMuODU3MzYxIDExNS45ODk3MTEgMzUuODAxMzkxIDE3Ny40NTU2MjEgMzUuODAxMzkxIDYxLjUyNjI4NSAwIDEyMS4xNTc0MDctMTEuOTQ0MDMgMTc3LjUxNTk5Ni0zNS44NTk3MTkgNTQuMjM1MjI5LTIyLjk2ODEwOCAxMDIuOTUzODEzLTU1Ljg5ODEwMSAxNDQuNzMwMjg5LTk3LjcyNzc4OSA0MS44Mjc2NDEtNDEuOTQ1MzIxIDc0Ljc4OTM1Ny05MC44MTAyMzkgOTcuNjk4MTEzLTE0NS4wNzQxMiAyMy43NzE0MDMtNTYuMzI5OTM2IDM1Ljc3NTgwOC0xMTUuOTg5NzExIDM1Ljc3NTgwOC0xNzcuNTk5OTA3Qzk2Ny43NDUyNzcgNDUwLjI4NzQ3MyA5NTUuNzQ0OTY1IDM5MC42Mjc2OTggOTMyLjAwMTE5MSAzMzQuMjEyODI4TDkzMi4wMDExOTEgMzM0LjIxMjgyOHpNOTA5LjY5MjA5MyA1MTEuOTU4MDQ0YzAgMjE5LjU0NTIyOC0xNzguMzQ2OTIxIDM5OC4xMjIzOTMtMzk3LjY2MTkwNSAzOTguMTIyMzkzLTIxOS4yNTk3MjYgMC0zOTcuNjA2NjQ3LTE3OC41NzcxNjUtMzk3LjYwNjY0Ny0zOTguMTIyMzkzIDAtMjE5LjYzMzIzMyAxNzguMzQ2OTIxLTM5OC4xODA3MjEgMzk3LjYwNjY0Ny0zOTguMTgwNzIxQzczMS4zNDUxNzIgMTEzLjc3NjMgOTA5LjY5MjA5MyAyOTIuMzI0ODEyIDkwOS42OTIwOTMgNTExLjk1ODA0NEw5MDkuNjkyMDkzIDUxMS45NTgwNDR6TTU2MC4wNjExMTEgMjc2LjAxNzQwNGMtOC42MTQxOTIgOS40NzQ3OTItMTMuMDM0ODc0IDIwLjk1NzMxMS0xMy4wMzQ4NzQgMzQuMjIyNDI5IDAgMTAuOTA5NDY3IDMuNTI5MzgzIDIwLjQxMTg4OSAxMC41MDkzNTUgMjcuNzkwOTUgNy4wOTE1MTIgNy40OTI2NDggMTYuMjc2NzA4IDExLjM0MDI3OSAyNi43NTc0MSAxMS4zNDAyNzkgOC40Njk5MDUgMCAyMS4wNzM5NjgtMi41ODM4NDggMzIuNjEzNzkyLTE1LjQxNzEzMSA5LjAxNDMwNC05LjcwMjk4OSAxMy41NTA2MjEtMjEuMzg4MTIzIDEzLjU1MDYyMS0zNC4zOTQzNDUgMC0xMC42NTE1OTQtMy42NzM2NjktMTkuOTgyMS0xMC42ODEyNy0yNy4yMTc4OThDNjA0LjQ0NzAxNyAyNTYuNTUyMDc0IDU3Ni41NDI0OCAyNTguMjQ1NjQ1IDU2MC4wNjExMTEgMjc2LjAxNzQwNEw1NjAuMDYxMTExIDI3Ni4wMTc0MDR6IiBwLWlkPSI2MjU3IiBmaWxsPSIjMzMzMzMzIj48L3BhdGg+PC9zdmc+',
                parentAreaId:0,
                initSearchFlag:true,
                initData:{},
                companyList:[],
                companyName:'',
                companyId:'',
                vinCodeList:[],
                plateCodeList:[],
                eventTargetNameList:["压线行驶","车辆违停","占用公交车道"],
                height:'200px',
                dim:'bus',
                trafficStatColor:['#3b30b1','#dc7acf'],
                trafficStatbox:'trafficStatbox',
                trafficDimStat:{},
                trafficTypeStatbox:'trafficTypeStatbox',
                trafficTypeStatColor:['#ff005c','#ca0015'],
                trafficTypeStat:{},
                form: {
                    companyId:'',
                    eventTargetName:'',
                    vinCode:'',
                    plateCode:'',
                    busCode:'',
                    page: 1,
                    limit: 10
                },
                table: {
                    currPage:1,
                    pageSize:5,
                    totalCount:50
                },
                trafficStatByCompany:[],
                faultDetailList:[
                    {
                    	VIN:'1234422',
                    	voltage:220,
                    	current:120,
                    	faultLevelName:'一般',//故障名称
                    	faultLevelCode:'0',//故障编码
                    	faultTime:'2020-08-25',//故障时间
                    	motorStatus:0,
                    },
                    {
                    	VIN:'1234422',
                    	voltage:220,
                    	current:120,
                    	faultLevelName:'轻级',//故障名称
                    	faultLevelCode:'1',//故障编码
                    	faultTime:'2020-08-25',//故障时间
                    	motorStatus:1,
                    },
                    {
                    	VIN:'1234422',
                    	voltage:220,
                    	current:120,
                    	faultLevelName:'严重',//故障名称
                    	faultLevelCode:'2',//故障编码
                    	faultTime:'2020-08-25',//故障时间
                    	motorStatus:1,
                    },
                ],
                exportVisible:false,
                showPhotoVisible: false,
                areaList: [],
                areaIdList: [],
            }
        },
        components: {
            EchartsSingleXbar,
            EchartsYBar,
            dataExport,
            showPhoto
        },
        created(){
            this.getInitData();
            // this.getProvinceData();
        },
        mounted(){

        },
        methods: {
            getInitData(){
                getInitDataByEventType('trafficEvent').then(resp => {
                    if(resp.data.code==0 && resp.data.data.info != null){
                        this.initData=resp.data.data.info;
                    }
                    this.initAreaList();
                })
            },
            initAreaList() {
                getAreaList(0).then(resp => {
                    if (resp.data.code == 0) {
                        this.areaList.push(resp.data.data.list);
                        let initAreaId = resp.data.data.list[0].areaId
                        this.areaIdList[0] = initAreaId
                        this.getSubAreaList(initAreaId,0)
                        this.getCompanyData(initAreaId)
                    }

                })
            },
            getSubAreaList(parentAreaId,index) {

                getAreaList(parentAreaId).then(resp => {
                    if (resp.data.code == 0) {
                        let list = resp.data.data.list;
                        list.unshift({
                            areaId:parentAreaId,
                            areaName:'全部'
                            //没有hasChild属性，不会产生下级下拉框
                        })
                        this.areaList.push(list);
                        this.areaIdList[index + 1] = parentAreaId
                    }

                })
            },
            changeAreaList(data, index) {
                console.log(data + '~~~~' + index)
                this.areaList = this.areaList.slice(0, index + 1);
                this.areaIdList = this.areaIdList.slice(0,index + 1)
                console.log(this.areaList)
                for (var i = 0; i < this.areaList[index].length; i++) {
                    if (this.areaList[index][i].areaId == data) {
                        this.areaIdList[index] = this.areaList[index][i].areaId
                        if(this.areaList[index][i].hasChild){
                            this.getSubAreaList(data,index);
                        }
                    }
                }
                this.getCompanyData(data)

            },
            getCompanyData(id){
                this.companyName='';
                this.companyId='';
                this.companyList=[];
                this.table ={}
                this.trafficStatByCompany=[]
                this.trafficDimStat={}
                this.trafficTypeStat={}
                getCustomerCompanyListData(id).then(resp => {
                    if(resp.data.code==0){
                        this.companyList=resp.data.data.list;
                        // 第一次
                        if(this.initSearchFlag && this.initData.companyId != null){
                            this.companyName=this.initData.companyName;
                            this.companyId=this.initData.companyId;
                        }else{
                            this.companyName=this.companyList[0].companyName;
                            this.companyId=this.companyList[0].companyId;
                        }
                        this.initSearchFlag = false;
                        this.getInitVinAndPlateCode();
                        this.getTrafficDimStatData(this.companyId);
                        this.getTrafficTypeStatData(this.companyId)
                        this.handleList(1)

                    }

                })


            },
            changeCompany(data){
                this.companyId=data;
                this.trafficStatByCompany=[]
                this.trafficDimStat={}
                this.trafficTypeStat={}
                this.getTrafficDimStatData();
                this.getTrafficTypeStatData()
                this.getInitVinAndPlateCode();
                this.handleList(1);
            },
            getInitVinAndPlateCode(){
                getVinCodeListByCompany({'companyId':this.companyId}).then(resp =>{
                    if(resp.data.code==0){
                        this.vinCodeList=resp.data.data.list;
                    }
                });
                getPlateCodeListByCompany({'companyId':this.companyId}).then(resp =>{
                    if(resp.data.code==0){
                        this.plateCodeList=resp.data.data.list;
                    }
                })
            },
            getDimStatData(val){
                this.dim=val;
                this.getTrafficDimStatData();
            },
            getTrafficDimStatData(){
                getTrafficDimStatData(this.dim,this.companyId).then(resp =>{
                    if(resp.data.code==0){
                        this.trafficDimStat=resp.data.data.data;
                        if(this.dim=="bus"){
                            this.trafficDimStat.xAxisName='VIN码';
                        }else{
                            this.trafficDimStat.xAxisName='时间';
                        }

                        this.trafficDimStat.yAxisName='违规数';
                    }
                })

            },
            getTrafficTypeStatData(){
                getTrafficTypeStatData(this.companyId).then(resp =>{
                    if(resp.data.code==0){
                        this.trafficTypeStat=resp.data.data.data;
                        //this.trafficTypeStat.xAxisName='违章数';
                        this.trafficTypeStat.yAxisName='';
                    }
                })

            },
            table_index(index) {
                return (this.form.page - 1) * this.form.limit + index + 1
            },
            handleSizeChange(val) {
                this.form.limit = val;
                this.handleList(1);
                console.log(this.form.page);
            },
            handleCurrentChange(val) {
                this.form.page = val;
                this.handleList();
                console.log(this.form.page);
            },
            handleList(page) {
                if(page){this.form.page=1;}
                this.form.companyId=this.companyId;
                this.trafficStatByCompany=[]
                getTrafficStatByCompanyData(this.form).then(resp =>{
                    if(resp.data.code==0){
                        if(resp.data.data.list.list.length>0){
                            this.trafficStatByCompany=resp.data.data.list.list;
                            this.table=resp.data.data.list
                        }
                    }
                })

            },

            dataExport(){
                this.exportVisible=true
                 this.$nextTick(()=>{
                     //this.init();
                     this.$refs.carDetail.init(this.companyId);

                 })

            },
            showPhoto(res){
                this.showPhotoVisible=true;
                this.$nextTick(()=>{
                    this.$refs.showPhoto.init(res);

                })
            }


        },

    };
</script>

<style scoped>
body{
    color: #333333;
    background-color: #f1f7fd;
}
.pageTitle{
    margin-bottom: 10px;
}
.title{
    margin:0 0 5px 0;
}
.mr5{
    margin-right: 5px;
}
.mb10{
    margin-bottom: 10px;
}
.font20{
    font-size: 20px;
}
.box{
    padding:15px 10px;
    border: 1px solid #e5e8ed;
    border-radius: 3px;
    background-color: #fff;
    font-size: 14px;
    margin-bottom: 10px;
    overflow: hidden;
    box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
}
.boxTitle{
    margin-bottom: 10px;
    font-size: 14px;
    font-weight: bold;
    position: relative;
    padding-left: 25px;
}
.boxTitle img{
    width: 20px;
    height: 20px;
    position: absolute;
    left: 0;
    top: 0;
}
.icon{
    font-size: 18px;
}
.faultTip{
    border: 1px dashed #fc8310;
    color: #fc8310;
    padding: 10px;
    border-radius: 3px;
    line-height: 40px;
}
.faultTipimg{
    width: 40px;
    height: 40px;
    background-size:40px 40px;
    background-position:center center;
    background-repeat: no-repeat;
    float: left;
    margin-right: 10px;
}
.faultTipimg img{
    width: 24px;
    height: 24px;
    margin: 8px 0 0 8px;
}

.statusDiv{
    position: absolute;
    right: 0;
    top: 0;
    font-weight: normal;
    font-size: 12px;
    padding: 0;
    margin: 0;
}
.statusDiv li{
    float: left;
    padding: 3px 5px;
    border: 1px solid #479cf6;
    color: #479cf6;
    cursor: pointer;
}
.statusActived{
    background-color: #479cf6;
    color: #fff!important;
}
.busesDate {
    width: 240px!important;
    margin-bottom: 10px;
    float: right;
}
.busesDate .el-range-input{
    width: 85px;
}
.lookupBtn{
    background-color: #edaf1d;
    color: #fff;
    border: none;
}
.editBtn{
    background-color: #da151c;
    color: #fff;
    border: none;
}
.editBtn i{
    margin-right: 3px;
}
.lookupBtn:focus, .lookupBtn:hover{
    background-color: #edaf1d;
    color: #fff;
    border: none;
}
.editBtn:focus, .editBtn:hover{
    background-color: #da151c;
    color: #fff;
    border: none;
}
.faultType{
    width: 150px;
}
.vin{
    width: 150px;
}
.selectArea{
    width: 150px;
    margin-right: 10px;
}

/deep/ .el-scrollbar .el-scrollbar__wrap{
    margin-bottom: 0!important;
}
</style>
