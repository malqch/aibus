<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:环境数据组件页面
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
                <el-select class="selectArea" filterable style="width: 200px" v-model="busStationName" placeholder="请选择" @change="changeBusStation" :popper-append-to-body="false">
                    <el-option
                        v-for="item in busStationList"
                        :key="item.busStationId"
                        :label="item.busStationCode+'：'+item.companyLineName"
                        :value="item.busStationId">
                    </el-option>
                </el-select>
                <el-time-picker class="busDate"
                                is-range
                                v-model="rangeDate"
                                range-separator="~"
                                start-placeholder="开始时间"
                                end-placeholder="结束时间"
                                placeholder="选择时间范围"
                                @change="changeTimeRange">
                </el-time-picker>
            </div>
            <div>
                <el-row :gutter="15">
                    <el-col :span="12">
                        <div class="box">
                            <div class="boxTitle">
                                <img :src="infoicon">
                                温度统计-{{ displayBusStationName }}
                            </div>
                            <echarts-line :lineObj="temperatureStat" :height="height" :color="temperatureColor"
                                          :boxid="temperatureDivId"></echarts-line>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="box">
                            <div class="boxTitle" style="position: relative;">
                                <img :src="infoicon">
                                湿度统计-{{ displayBusStationName }}
                            </div>
                            <echarts-line :lineObj="humidityStat" :height="height" :color="humidityColor"
                                          :boxid="humidityDivId"></echarts-line>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="box" style="margin-bottom: 0;">
                            <div class="boxTitle">
                                <img :src="infoicon">
                                PM2.5统计-{{ displayBusStationName }}
                            </div>
                            <echarts-line :lineObj="PMStat" :height="height" :color="PMColor"
                                          :boxid="PMDivId"></echarts-line>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="box" style="margin-bottom: 0;">
                            <div class="boxTitle" style="position: relative;">
                                <img :src="infoicon">
                                基础信息展示-{{ displayBusStationName }}
                            </div>
                            <el-table border :data="stationDetailData1" style="width: 80%;margin: 25px 10%;"
                            :header-cell-style="{background:'#fff!important',color:'#333!important'}">
                                <el-table-column prop="busStationName" label="公交车站名" align="center"></el-table-column>
                                <el-table-column prop="companyLines" label="途径线路" align="center"  :show-overflow-tooltip="false">
                                    <template slot-scope="scope">
                                        <el-tooltip class="item" effect="dark" placement="top">
                                            <div v-html="scope.row.companyLines" slot="content"></div>
                                            <div class="lineCls">{{changeBr(scope.row.companyLines)}}</div>
                                        </el-tooltip>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="busCount" label="途径车辆数" align="center"></el-table-column>
                            </el-table>
                            <el-table border :data="stationDetailData2" style="width: 80%;margin: 25px 10%;"
                            :header-cell-style="{background:'#fff!important',color:'#333!important'}">
                                <el-table-column prop="busStationName" label="地理坐标" align="center">
                                    <template slot-scope="scope">
                                        {{ scope.row.busStationLongitude }},{{ scope.row.busStationLatitude }}
                                    </template>
                                </el-table-column>
                                <el-table-column prop="companyLines" label="PM2.5状态" align="center">
                                    <template slot-scope="scope">
                                        <span style="">{{ scope.row.pm_2_5.name}}</span>
                                        <img v-for="(item,index) in scope.row.pm_2_5.img" :key="'img'+index" :src="item" style="width: 18px;align-items: center;">
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-col>
                </el-row>

            </div>
        </div>

    </div>
</template>

<script>
// import areaData from './areaData.json'
import EchartsLine from "@/components/bus/echarts-line";
import {
    getAreaList, getCustomerCompanyListData, getBusStationListData, getEnvMetricListData,
    getBusStationDetailData,formatDate,getPM_2_5Level
} from "@/api/env";
import {getInitDataByEventType} from "@/api/sale"

export default {
    name: "GfmAdminSalePreview",
    data() {
        return {
            infoicon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk3ODA3NjY3MDYwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjYyNTYiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTcxLjkxNzEzNiA1OTEuNTk5OTE5Yy0xMy4yOTA3MDEgMTIuNjg4OTk3LTIyLjYyNDI3NyAyMC42MTI0NTctMjguODUyMTE4IDI1LjYzNjg5IDMuMDQyMjg5LTE2LjQ1MDY3IDEwLjk2ODgxOS00OS4wNjQ0NjIgMzAuMDU5NjItMTEzLjI4OTIwNiAxOS4wMDY4ODktNjMuNjIyMDE2IDIwLjY0MjEzMy03NC43MzIwNTIgMjAuNjQyMTMzLTgwLjE4NzI5NyAwLTkuNTMxMDc0LTMuOTYxMjE4LTE3Ljk3MjMyNy0xMC44ODA4MTUtMjMuODAxMDc5LTE1LjUzMzc4OC0xMi44MDQ2My00Mi45MjI1NzgtMTAuMjc4MDg3LTc1Ljk5Njg1OCA4LjQ0MTI1My0xOC40MzE3OTEgMTAuMzM2NDE2LTM4LjAxMDcwOSAyNy4yMTc4OTgtNTkuODg2OTQ5IDUxLjIxOTU0NWwtMTEuNDAwNjU0IDEyLjY4ODk5NyAzNy44NDE4NjMgMjkuMTY5MzQzIDkuNjczMzEzLTkuNTYwNzVjMTAuNTM2OTg0LTEwLjIxOTc1OSAxNy42ODM3NTQtMTYuODgxNDgyIDIyLjQ1MjM2MS0yMS4zMzE4NDEtMjguODg0ODY0IDk1LjA2MDAyOS00Mi45NTEyMzEgMTU0LjE0MzY4My00Mi45NTEyMzEgMTgwLjU4NTkxNSAwIDEyLjAwMjM1OCAzLjQ3NDEyNSAyMS45NjUyNjggMTAuNTA5MzU1IDI5LjUxNDE5NyA3LjA5MTUxMiA3Ljc1MjU2OCAxNy4wNTIzNzQgMTEuNzk5NzQ0IDI4LjQ0ODkzNiAxMS43OTk3NDQgMTEuMTY4MzY0IDAgMjMuOTQzMzE4LTQuMzA1MDQ5IDM5LjQ3NzEwNy0xMy4xMTg3ODUgMTQuMDEwMDg1LTcuOTUyMTEyIDM1LjAyNzc3MS0yNS4zMjI3MzUgNjQuNDI0Mjg4LTUzLjAwMDA5OGwxMS45NDQwMy0xMS40MjYyMzctMzQuMzkyMjk4LTMzLjUwNTA5Mkw1NzEuOTE3MTM2IDU5MS41OTk5MTkgNTcxLjkxNzEzNiA1OTEuNTk5OTE5ek05MzIuMDAxMTkxIDMzNC4yMTI4MjhjLTIyLjk0MDQ3OC01NC4zMjAxNjMtNTUuOTU4NDc2LTEwMy4xODQwNTctOTcuNzI5ODM1LTE0NC45Mjg4MS00MS44MzE3MzQtNDEuODg5MDM5LTkwLjYxMDY5NC03NC44MTkwMzMtMTQ0LjczMDI4OS05Ny44NDQ0NDUtMTEyLjQ4Mzg2NC00Ny41NDM4MjktMjQyLjQyNzM3OC00Ny41NDM4MjktMzU0Ljk3MTYxNyAwLTU0LjE0NzIyNCAyMy4wMjU0MTMtMTAyLjgzODE4IDU1Ljk1NjQzLTE0NC43MjYxOTYgOTcuODQ0NDQ1LTQxLjgzMTczNCA0MS44MzA3MTEtNzQuODQ5NzMyIDkwLjc1MjkzNC05Ny43MDIyMDYgMTQ0LjkyODgxLTIzLjcxNjE0NCA1Ni40MTQ4NzEtMzUuODMyMDkgMTE2LjA3NDY0NS0zNS44MzIwOSAxNzcuNzQ0MTkzIDAgNjEuNjExMjIgMTIuMDU5NjY0IDEyMS4yNjk5NzEgMzUuNzQ4MTc5IDE3Ny42Mjk1ODMgMjIuODgwMTAzIDU0LjIzMzE4MiA1NS44OTgxMDEgMTAzLjAzOTc3MSA5Ny43MjU3NDIgMTQ1LjA0MzQyMSA0MS43NzU0NTIgNDEuODI5Njg3IDkwLjQ5NTA2IDc0LjgxNjk4NiAxNDQuNzMwMjg5IDk3Ljc4NjExNyA1Ni4zMjk5MzYgMjMuODU3MzYxIDExNS45ODk3MTEgMzUuODAxMzkxIDE3Ny40NTU2MjEgMzUuODAxMzkxIDYxLjUyNjI4NSAwIDEyMS4xNTc0MDctMTEuOTQ0MDMgMTc3LjUxNTk5Ni0zNS44NTk3MTkgNTQuMjM1MjI5LTIyLjk2ODEwOCAxMDIuOTUzODEzLTU1Ljg5ODEwMSAxNDQuNzMwMjg5LTk3LjcyNzc4OSA0MS44Mjc2NDEtNDEuOTQ1MzIxIDc0Ljc4OTM1Ny05MC44MTAyMzkgOTcuNjk4MTEzLTE0NS4wNzQxMiAyMy43NzE0MDMtNTYuMzI5OTM2IDM1Ljc3NTgwOC0xMTUuOTg5NzExIDM1Ljc3NTgwOC0xNzcuNTk5OTA3Qzk2Ny43NDUyNzcgNDUwLjI4NzQ3MyA5NTUuNzQ0OTY1IDM5MC42Mjc2OTggOTMyLjAwMTE5MSAzMzQuMjEyODI4TDkzMi4wMDExOTEgMzM0LjIxMjgyOHpNOTA5LjY5MjA5MyA1MTEuOTU4MDQ0YzAgMjE5LjU0NTIyOC0xNzguMzQ2OTIxIDM5OC4xMjIzOTMtMzk3LjY2MTkwNSAzOTguMTIyMzkzLTIxOS4yNTk3MjYgMC0zOTcuNjA2NjQ3LTE3OC41NzcxNjUtMzk3LjYwNjY0Ny0zOTguMTIyMzkzIDAtMjE5LjYzMzIzMyAxNzguMzQ2OTIxLTM5OC4xODA3MjEgMzk3LjYwNjY0Ny0zOTguMTgwNzIxQzczMS4zNDUxNzIgMTEzLjc3NjMgOTA5LjY5MjA5MyAyOTIuMzI0ODEyIDkwOS42OTIwOTMgNTExLjk1ODA0NEw5MDkuNjkyMDkzIDUxMS45NTgwNDR6TTU2MC4wNjExMTEgMjc2LjAxNzQwNGMtOC42MTQxOTIgOS40NzQ3OTItMTMuMDM0ODc0IDIwLjk1NzMxMS0xMy4wMzQ4NzQgMzQuMjIyNDI5IDAgMTAuOTA5NDY3IDMuNTI5MzgzIDIwLjQxMTg4OSAxMC41MDkzNTUgMjcuNzkwOTUgNy4wOTE1MTIgNy40OTI2NDggMTYuMjc2NzA4IDExLjM0MDI3OSAyNi43NTc0MSAxMS4zNDAyNzkgOC40Njk5MDUgMCAyMS4wNzM5NjgtMi41ODM4NDggMzIuNjEzNzkyLTE1LjQxNzEzMSA5LjAxNDMwNC05LjcwMjk4OSAxMy41NTA2MjEtMjEuMzg4MTIzIDEzLjU1MDYyMS0zNC4zOTQzNDUgMC0xMC42NTE1OTQtMy42NzM2NjktMTkuOTgyMS0xMC42ODEyNy0yNy4yMTc4OThDNjA0LjQ0NzAxNyAyNTYuNTUyMDc0IDU3Ni41NDI0OCAyNTguMjQ1NjQ1IDU2MC4wNjExMTEgMjc2LjAxNzQwNEw1NjAuMDYxMTExIDI3Ni4wMTc0MDR6IiBwLWlkPSI2MjU3IiBmaWxsPSIjMzMzMzMzIj48L3BhdGg+PC9zdmc+',
            parentAreaId: 0,
            initSearchFlag:true,
            initData:{},
            companyList: [],
            companyName: '',
            companyId: '',
            busStationId: '',
            busStationName: '',
            displayBusStationName:'',
            busStationList: [],
            rangeDate: [new Date().setHours(new Date().getHours() - 1),new Date()],
            height: '200px',
            temperatureColor:['#559b64','#d3f1e1'],
            temperatureDivId: 'temperatureLine',
            temperatureStat: {
                xAxisData: ['06:00', '06:10', '06:20', '06:30', '06:40'],
                //xAxisName:'',
                legendData: ['温度'],
                yAxisName:'温度(℃)',
                unit:'℃',
                data: []
            },
            humidityColor:['#0c55c2','#deeaf4'],
            humidityDivId: 'humidityLine',
            humidityStat: {
                xAxisData: ['06:00', '06:10', '06:20', '06:30', '06:40'],
                //xAxisName:'',
                legendData: ['湿度'],
                yAxisName:'湿度(%RH)',
                unit:'%RH',
                data: []
            },
            PMColor:['#686696','#e5e8f5'],
            PMDivId: 'PMLine',
            PMStat: {
                xAxisData: ['06:00', '06:10', '06:20', '06:30', '06:40'],
                //xAxisName:'',
                legendData: ['PM2.5值'],
                yAxisName:'PM2.5值(μg/m3)',
                unit:'μg/m3',
                data: []
            },
            stationDetailData1: [
                {
                    busStationName: '',
                    companyLines: '',
                    busCount: ''
                }
            ],
            stationDetailData2: [
                {
                    busStationLongitude: '',
                    busStationLatitude: '',
                    pm_2_5: ''
                }
            ],
            areaList: [],
            areaIdList: [],

        }
    },
    components: {
        EchartsLine
    },
    created() {
    },
    mounted() {
        this.getInitData();
        // this.getProvinceData();
    },
    methods: {
        changeBr : function(val){
            if(val){
                return val.replace(/<br\/>/g, '\n')
            }

        },
        getInitData(){
            getInitDataByEventType('envEvent').then(resp => {
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
        getCompanyData(id) {
            this.companyName = '';
            this.companyId = '';
            this.companyList = [];
            getCustomerCompanyListData(id).then(resp => {
                if (resp.data.code == 0) {
                    this.companyList = resp.data.data.list;
                    // 第一次
                    if(this.initSearchFlag && this.initData.companyId != null){
                        this.companyName=this.initData.companyName;
                        this.companyId=this.initData.companyId;
                    }else{
                        if(this.companyList.length !== 0) {
                            this.companyName = this.companyList[0].companyName;
                            this.companyId = this.companyList[0].companyId;
                        }
                    }
                    if(this.companyList.length !== 0) {
                        this.getBusStationListData();
                    }
                }else{
                    this.companyName = '';
                    this.companyId = '';
                    this.companyList = [];
                    this.busStationName = '';
                    this.busStationId = '';
                    this.busStationList = [];
                    //this.rangeDate = '';
                    this.temperatureStat.data = [];
                    this.humidityStat.data = [];
                    this.PMStat.data = [];
                    this.displayBusStationName=''
                }
            })

        },
        changeCompany(data) {
            this.companyId = data;
            this.busStationName = '';
            this.busStationId = '';
            this.busStationList = [];
            //this.rangeDate = '';
            this.temperatureStat.data = [];
            this.humidityStat.data = [];
            this.PMStat.data = [];
            this.getBusStationListData();
        },
        getBusStationListData() {
            this.busStationName = '';
            this.busStationId = '';
            this.busStationList = [];
            getBusStationListData(this.companyId).then(resp => {
                if (resp.data.code == 0) {
                    this.busStationList = resp.data.data.list;
                    // 第一次
                    if(this.initSearchFlag && this.initData.busStationId != null){
                        this.busStationName = this.initData.busStationName;
                        this.busStationId = this.initData.busStationId;

                    }else{
                        if(this.busStationList.length !== 0) {
                            this.busStationName = this.busStationList[0].busStationName;
                            this.busStationId = this.busStationList[0].busStationId;
                        }
                    }
                    this.displayBusStationName = this.busStationName;
                    this.initSearchFlag = false;
                    if(this.busStationList.length !== 0) {
                        this.getTemperatureLineData();
                        this.getHumidityLineData();
                        this.getPm_2_5LineData();
                        this.getBusStationDetailData();
                    }
                }
            });
        },
        getBusStationName(busStationId){
            for (let i = 0; i < this.busStationList.length; i++) {
                if(this.busStationList[i].busStationId === busStationId){
                    return this.busStationList[i].busStationName
                }
            }
            return undefined;
        },
        changeBusStation(data) {
            this.busStationId = data;

            this.displayBusStationName = this.getBusStationName(data)

            //this.rangeDate = '';

            this.getTemperatureLineData();
            this.getHumidityLineData();
            this.getPm_2_5LineData();
            this.getBusStationDetailData();
        },
        getTimeRange() {
            let startTime = this.rangeDate[0]
            let endTime = this.rangeDate[1]
            let now1 =  new Date()
            let now2 =  new Date()
            if (startTime == null) {
                startTime = now1.setHours(now1.getHours() - 1)
            }
            if (endTime == null) {
                endTime = now2
            }
            return {
                "startTime": formatDate(startTime),
                "endTime": formatDate(endTime)
            }
        },
        changeTimeRange(data){
            this.getTemperatureLineData();
            this.getHumidityLineData();
            this.getPm_2_5LineData();
            this.getBusStationDetailData();
        },
        getTemperatureLineData() {
            this.temperatureStat.data = [];

            let params = this.getTimeRange();
            params["busStationId"] = this.busStationId;

            getEnvMetricListData("temperature", params).then(resp => {
                if (resp.data.code == 0) {
                    let textList = resp.data.data.data.textList;
                    for (let i = 0; i < textList.length; i++) {
                        textList[i] = textList[i].split(' ')[1]
                    }
                    this.temperatureStat.xAxisData = textList;
                    this.temperatureStat.data = resp.data.data.data.valueList;
                }
            });
        },
        getHumidityLineData() {
            this.humidityStat.data = [];
            let params = this.getTimeRange();
            params["busStationId"] = this.busStationId;

            getEnvMetricListData("humidity", params).then(resp => {
                if (resp.data.code == 0) {
                    let textList = resp.data.data.data.textList;
                    for (let i = 0; i < textList.length; i++) {
                        textList[i] = textList[i].split(' ')[1]
                    }
                    this.humidityStat.xAxisData = textList;
                    this.humidityStat.data = resp.data.data.data.valueList;
                }
            });
        },
        getPm_2_5LineData() {
            this.PMStat.data = [];

            let params = this.getTimeRange();
            params["busStationId"] = this.busStationId;

            getEnvMetricListData("pm_2_5", params).then(resp => {
                if (resp.data.code == 0) {
                    let textList = resp.data.data.data.textList;
                    for (let i = 0; i < textList.length; i++) {
                        textList[i] = textList[i].split(' ')[1]
                    }
                    this.PMStat.xAxisData = textList;
                    this.PMStat.data = resp.data.data.data.valueList;
                }
            });
        },
        getBusStationDetailData() {

            this.stationDetailData1 = [];
            this.stationDetailData2 = [];

            let params = this.getTimeRange();
            params["busStationId"] = this.busStationId;
            params["companyId"] = this.companyId;
            getBusStationDetailData(params).then(resp => {
                if (resp.data.code == 0) {
                    let busStationDetail = resp.data.data.data;
                    this.stationDetailData1 = [{
                        busStationName: busStationDetail.busStationName,
                        companyLines: busStationDetail.companyLines,
                        busCount: busStationDetail.busCount
                    }];
                    this.stationDetailData2 = [{
                        busStationLongitude: busStationDetail.busStationLongitude,
                        busStationLatitude: busStationDetail.busStationLatitude,
                        pm_2_5: getPM_2_5Level(busStationDetail.pm_2_5)
                    }]
                }
            });
        }
    }
};
</script>

<style scoped>
body {
    color: #333333;
    background-color: #f1f7fd;
}

.pageTitle {
    margin-bottom: 10px;
}

.title {
    margin: 0 0 5px 0;
}

.box {
    padding: 15px 10px;
    border: 1px solid #e5e8ed;
    border-radius: 3px;
    background-color: #fff;
    font-size: 14px;
    margin-bottom: 10px;
    overflow: hidden;
    box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
}

.boxTitle {
    margin-bottom: 10px;
    font-size: 14px;
    font-weight: bold;
    position: relative;
    padding-left: 25px;
}

.boxTitle img {
    width: 20px;
    height: 20px;
    position: absolute;
    left: 0;
    top: 0;
}

.icon {
    font-size: 18px;
}

.selectArea {
    width: 160px;
    margin-bottom: 10px;
    margin-right: 10px;
}

.busDate {
    width: 240px !important;
    margin-bottom: 10px;
}

.busDate .el-range-input {
    width: 85px;
}

.el-table th {
    background-color: #fff !important;
    color: #91979d !important;
}
.el-table .cell{
    display: flex;
    align-items: center;
    justify-content: center;
}
.lineCls {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
/deep/ .el-scrollbar .el-scrollbar__wrap{
    margin-bottom: 0!important;
}
/deep/ .el-table .cell{
       display: flex;
       align-items: center;
       justify-content: center;
   }
</style>
