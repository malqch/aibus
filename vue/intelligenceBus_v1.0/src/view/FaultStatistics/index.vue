<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:故障统计组件页面
-->
<template>
    <div id='pagebox'>
        <div class="pageTitle" style="position: relative;">
            <div class="selectDate">
                <el-date-picker style="width: 150px;" @change="getDate"
                  v-model="dateVal"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
            </div>
        </div>
        <div>
            <el-row :gutter="15">
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <!-- <i class="el-icon-info icon"></i> -->
                            <img :src="infoicon">
                            故障排行统计
                        </div>
                        <!-- <div id="bar" style="height:280px;"></div> -->
                        <echarts-y-bar :barObj="faultTypeBusStat" :height="height" :color="faultColor" :boxid="milesFaultBox"></echarts-y-bar>
                        <div class="horizontal">
                            <div class="circle"
                                :class="mileActive==index?'circleCur':''"
                                v-for="(item,index) in milesData"
                                :style="{left:milesWidth*index+'%'}"
                                @click="changeBusMile(item,index)">
                                <span>{{item}}</span>
                            </div>
                            <span style="position: absolute;right: -60px;top: -5px;font-size: 12px;">里程数</span>
                        </div>

                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            车型故障排行统计
                            <el-select v-model="busType.busTypeName" @change='changeBusType' placeholder="请选择" style="width: 200px;float: right;" :popper-append-to-body="false">
                                <el-option v-for="item in bustypeList"
                                    :key="item.busTypeId"
                                    :label="item.busTypeName"
                                    :value="item">
                                </el-option>
                            </el-select>
                        </div>
                        <echarts-pie :pieObj="faultBusTypeData" :height="height"></echarts-pie>
                    </div>
                </el-col>
            </el-row>
            <el-row :gutter="15">
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            客户车辆故障统计
                        </div>
                        <!-- <div id="xbar" style="height: 200px;"></div> -->
                        <echarts-x-bar :barObj="faultBusTypeStatData" :height="height" :boxid="faultBusTypeStatbox"></echarts-x-bar>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            客户车辆故障环境温度关联分析
                        </div>
                        <!-- <div id="linexbar" style="height: 200px;"></div> -->
                        <echarts-line-xbar :linebarObj="temperatureStatData" :height="height" :boxid="tempFaultbox"></echarts-line-xbar>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import EchartsYBar from "@/components/bus/echarts-bar-ycategory";
    import EchartsPie from "@/components/bus/echarts-pie-doughnut";
    import EchartsXBar from "@/components/bus/echarts-bar-xcategory";
    import EchartsLineXbar from "@/components/bus/echarts-line-xbar";
    import customerListData from './customerListData.json'
    import{formatDate,getConfigBusMilesData,getBusStatByDateAndMileData,
    getInfobustypeAllListData,getFaultStatByDateAndTypeIdData,getFaultBusTypeByDateData,getFaultTemperatureStatData} from "@/api/sale";
    export default {
        name: "GfmAdminFault",
        data() {
            return {
                infoicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk3ODA3NjY3MDYwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjYyNTYiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTcxLjkxNzEzNiA1OTEuNTk5OTE5Yy0xMy4yOTA3MDEgMTIuNjg4OTk3LTIyLjYyNDI3NyAyMC42MTI0NTctMjguODUyMTE4IDI1LjYzNjg5IDMuMDQyMjg5LTE2LjQ1MDY3IDEwLjk2ODgxOS00OS4wNjQ0NjIgMzAuMDU5NjItMTEzLjI4OTIwNiAxOS4wMDY4ODktNjMuNjIyMDE2IDIwLjY0MjEzMy03NC43MzIwNTIgMjAuNjQyMTMzLTgwLjE4NzI5NyAwLTkuNTMxMDc0LTMuOTYxMjE4LTE3Ljk3MjMyNy0xMC44ODA4MTUtMjMuODAxMDc5LTE1LjUzMzc4OC0xMi44MDQ2My00Mi45MjI1NzgtMTAuMjc4MDg3LTc1Ljk5Njg1OCA4LjQ0MTI1My0xOC40MzE3OTEgMTAuMzM2NDE2LTM4LjAxMDcwOSAyNy4yMTc4OTgtNTkuODg2OTQ5IDUxLjIxOTU0NWwtMTEuNDAwNjU0IDEyLjY4ODk5NyAzNy44NDE4NjMgMjkuMTY5MzQzIDkuNjczMzEzLTkuNTYwNzVjMTAuNTM2OTg0LTEwLjIxOTc1OSAxNy42ODM3NTQtMTYuODgxNDgyIDIyLjQ1MjM2MS0yMS4zMzE4NDEtMjguODg0ODY0IDk1LjA2MDAyOS00Mi45NTEyMzEgMTU0LjE0MzY4My00Mi45NTEyMzEgMTgwLjU4NTkxNSAwIDEyLjAwMjM1OCAzLjQ3NDEyNSAyMS45NjUyNjggMTAuNTA5MzU1IDI5LjUxNDE5NyA3LjA5MTUxMiA3Ljc1MjU2OCAxNy4wNTIzNzQgMTEuNzk5NzQ0IDI4LjQ0ODkzNiAxMS43OTk3NDQgMTEuMTY4MzY0IDAgMjMuOTQzMzE4LTQuMzA1MDQ5IDM5LjQ3NzEwNy0xMy4xMTg3ODUgMTQuMDEwMDg1LTcuOTUyMTEyIDM1LjAyNzc3MS0yNS4zMjI3MzUgNjQuNDI0Mjg4LTUzLjAwMDA5OGwxMS45NDQwMy0xMS40MjYyMzctMzQuMzkyMjk4LTMzLjUwNTA5Mkw1NzEuOTE3MTM2IDU5MS41OTk5MTkgNTcxLjkxNzEzNiA1OTEuNTk5OTE5ek05MzIuMDAxMTkxIDMzNC4yMTI4MjhjLTIyLjk0MDQ3OC01NC4zMjAxNjMtNTUuOTU4NDc2LTEwMy4xODQwNTctOTcuNzI5ODM1LTE0NC45Mjg4MS00MS44MzE3MzQtNDEuODg5MDM5LTkwLjYxMDY5NC03NC44MTkwMzMtMTQ0LjczMDI4OS05Ny44NDQ0NDUtMTEyLjQ4Mzg2NC00Ny41NDM4MjktMjQyLjQyNzM3OC00Ny41NDM4MjktMzU0Ljk3MTYxNyAwLTU0LjE0NzIyNCAyMy4wMjU0MTMtMTAyLjgzODE4IDU1Ljk1NjQzLTE0NC43MjYxOTYgOTcuODQ0NDQ1LTQxLjgzMTczNCA0MS44MzA3MTEtNzQuODQ5NzMyIDkwLjc1MjkzNC05Ny43MDIyMDYgMTQ0LjkyODgxLTIzLjcxNjE0NCA1Ni40MTQ4NzEtMzUuODMyMDkgMTE2LjA3NDY0NS0zNS44MzIwOSAxNzcuNzQ0MTkzIDAgNjEuNjExMjIgMTIuMDU5NjY0IDEyMS4yNjk5NzEgMzUuNzQ4MTc5IDE3Ny42Mjk1ODMgMjIuODgwMTAzIDU0LjIzMzE4MiA1NS44OTgxMDEgMTAzLjAzOTc3MSA5Ny43MjU3NDIgMTQ1LjA0MzQyMSA0MS43NzU0NTIgNDEuODI5Njg3IDkwLjQ5NTA2IDc0LjgxNjk4NiAxNDQuNzMwMjg5IDk3Ljc4NjExNyA1Ni4zMjk5MzYgMjMuODU3MzYxIDExNS45ODk3MTEgMzUuODAxMzkxIDE3Ny40NTU2MjEgMzUuODAxMzkxIDYxLjUyNjI4NSAwIDEyMS4xNTc0MDctMTEuOTQ0MDMgMTc3LjUxNTk5Ni0zNS44NTk3MTkgNTQuMjM1MjI5LTIyLjk2ODEwOCAxMDIuOTUzODEzLTU1Ljg5ODEwMSAxNDQuNzMwMjg5LTk3LjcyNzc4OSA0MS44Mjc2NDEtNDEuOTQ1MzIxIDc0Ljc4OTM1Ny05MC44MTAyMzkgOTcuNjk4MTEzLTE0NS4wNzQxMiAyMy43NzE0MDMtNTYuMzI5OTM2IDM1Ljc3NTgwOC0xMTUuOTg5NzExIDM1Ljc3NTgwOC0xNzcuNTk5OTA3Qzk2Ny43NDUyNzcgNDUwLjI4NzQ3MyA5NTUuNzQ0OTY1IDM5MC42Mjc2OTggOTMyLjAwMTE5MSAzMzQuMjEyODI4TDkzMi4wMDExOTEgMzM0LjIxMjgyOHpNOTA5LjY5MjA5MyA1MTEuOTU4MDQ0YzAgMjE5LjU0NTIyOC0xNzguMzQ2OTIxIDM5OC4xMjIzOTMtMzk3LjY2MTkwNSAzOTguMTIyMzkzLTIxOS4yNTk3MjYgMC0zOTcuNjA2NjQ3LTE3OC41NzcxNjUtMzk3LjYwNjY0Ny0zOTguMTIyMzkzIDAtMjE5LjYzMzIzMyAxNzguMzQ2OTIxLTM5OC4xODA3MjEgMzk3LjYwNjY0Ny0zOTguMTgwNzIxQzczMS4zNDUxNzIgMTEzLjc3NjMgOTA5LjY5MjA5MyAyOTIuMzI0ODEyIDkwOS42OTIwOTMgNTExLjk1ODA0NEw5MDkuNjkyMDkzIDUxMS45NTgwNDR6TTU2MC4wNjExMTEgMjc2LjAxNzQwNGMtOC42MTQxOTIgOS40NzQ3OTItMTMuMDM0ODc0IDIwLjk1NzMxMS0xMy4wMzQ4NzQgMzQuMjIyNDI5IDAgMTAuOTA5NDY3IDMuNTI5MzgzIDIwLjQxMTg4OSAxMC41MDkzNTUgMjcuNzkwOTUgNy4wOTE1MTIgNy40OTI2NDggMTYuMjc2NzA4IDExLjM0MDI3OSAyNi43NTc0MSAxMS4zNDAyNzkgOC40Njk5MDUgMCAyMS4wNzM5NjgtMi41ODM4NDggMzIuNjEzNzkyLTE1LjQxNzEzMSA5LjAxNDMwNC05LjcwMjk4OSAxMy41NTA2MjEtMjEuMzg4MTIzIDEzLjU1MDYyMS0zNC4zOTQzNDUgMC0xMC42NTE1OTQtMy42NzM2NjktMTkuOTgyMS0xMC42ODEyNy0yNy4yMTc4OThDNjA0LjQ0NzAxNyAyNTYuNTUyMDc0IDU3Ni41NDI0OCAyNTguMjQ1NjQ1IDU2MC4wNjExMTEgMjc2LjAxNzQwNEw1NjAuMDYxMTExIDI3Ni4wMTc0MDR6IiBwLWlkPSI2MjU3IiBmaWxsPSIjMzMzMzMzIj48L3BhdGg+PC9zdmc+',
                width:'',
                height:'200px',
                dateVal:'',
                faultColor:['#ffdb75','#ff6b21'],
                faultTypeBusStat:{},
                milesFaultBox:'milesFaultBox',
                milesData:[],
                milesWidth:0,
                mileActive:0,
                busMile:0,
                busType:{
                    busTypeId:'',
                    busTypeName:'',
                },
                bustypeList:{},
                faultBusTypeData:{
                    name:'',
                    data:[
                        {"value": 0, "name": "故障A","id":0},
                        {"value": 0, "name": "故障B","id":1}
                    ],
                },
                faultBusTypeStatbox:'faultBusTypeStatbox',
                faultBusTypeStatData:{},
                tempFaultbox:'tempFaultbox',
                temperatureStatData:{},
                tempFault:{
                    name:'',
                    legendData:['A车辆', 'B车辆', '环境温度'],
                    xAxisData:['公司一', '公司二', '公司三', '公司四', '公司五', '公司六', '公司七'],
                    data:[
                        {data:[240, 59, 75, 232, 96, 76, 135]},
                        {data:[200, 89, 98, 132, 256, 56, 2]},
                        {data:[23, 19, 17, 32, 25, 27, 13]},
                    ]
                }
            };
        },
        components: {
            EchartsYBar,
            EchartsPie,
            EchartsXBar,
            EchartsLineXbar
        },
        created(){
            this.getMilesData();
            this.dateVal=formatDate(new Date().getTime());
            this.getBustypeListData();
            this.getFaultBusTypeStatData();
            this.getTemperatureStatData()
        },
        watch:{
        },
        mounted(){

        },
        methods: {
            getDate(val){
                this.dateVal=formatDate(new Date(val).getTime());
                this.getFaultTypeBusStatData();
                this.getBustypeListData();
                this.getFaultBusTypeStatData()
                this.getTemperatureStatData()
            },
            getMilesData(){
                getConfigBusMilesData().then(resp => {
                    if(resp.data.code==0){
                        this.milesData=resp.data.data.milesData;
                        this.milesWidth=(100/(this.milesData.length-1)).toFixed(2);

                        this.busMile=this.milesData[0]
                        this.getFaultTypeBusStatData()
                    }

                })

            },
            changeBusMile(val1,val2){
                this.busMile=val1;
                this.mileActive=val2;
                //console.log(this.busMile+'~'+this.mileActive)
                this.getFaultTypeBusStatData()
            },
            getFaultTypeBusStatData(){

                getBusStatByDateAndMileData(this.dateVal,this.busMile).then(resp => {
                    if(resp.data.code==0){
                        this.faultTypeBusStat=resp.data.data.busStatData;
                        this.faultTypeBusStat.xAxisName=""
                    }

                })

            },
            getBustypeListData(){
                getInfobustypeAllListData().then(resp => {
                    if(resp.data.code==0){
                        this.bustypeList=resp.data.data.list;
                        this.busType.busTypeId=this.bustypeList[0].busTypeId;
                        this.busType.busTypeName=this.bustypeList[0].busTypeName;

                        this.getFaultBusTypeData()
                    }
                })


            },
            changeBusType(val){
                this.busType.busTypeId=val.busTypeId;
                this.busType.busTypeName=val.busTypeName
                this.getFaultBusTypeData()
            },
            getFaultBusTypeData(){
                getFaultStatByDateAndTypeIdData(this.dateVal,this.busType.busTypeId).then(resp => {
                    if(resp.data.code==0){
                        this.faultBusTypeData.name='故障百分比';
                        this.faultBusTypeData.data=resp.data.data.list;
                    }

                })

            },
            getFaultBusTypeStatData(){
                getFaultBusTypeByDateData(this.dateVal).then(resp => {
                    if(resp.data.code==0){
                        this.faultBusTypeStatData=resp.data.data.list
                    }
                })

            },
            getTemperatureStatData(){
                getFaultTemperatureStatData(this.dateVal).then(resp => {
                    if(resp.data.code==0){
                        this.temperatureStatData=resp.data.data.list;
                    }
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
    height: 30px;
}
.title{
    margin:0 0 5px 0;
}
.mr5{
    margin-right: 5px;
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
.boxTitle /deep/ .el-select-dropdown__wrap{
    margin-bottom: 0;
}
.selectDate{
    position: absolute;
    right: 0;
    bottom: 0;
}
.el-input__icon {
    line-height: 35px;
}
.horizontal{
    width: 68%;
    margin-left: 15%;
    margin-top: -10px;
    margin-bottom: 10px;
    height: 1px;
    background-color:#C0C4CC;
    position: relative;
    font-size: 10px;
}
.circle{
    width: 10px;
    height: 10px;
    border-radius: 50%;
    position: absolute;
    top: -5px;
    background-color: #dddddd;
    border: 1px solid #C0C4CC;
}
.circleCur{
    background-color: #f40020;
    border: 1px solid #f40020;
}
.circle span{
    position: absolute;
    top: 10px;
    width: 40px;
    display: inline-block;
    left: -17px;
    text-align: center;
}
/deep/ .el-scrollbar .el-scrollbar__wrap{
    margin-bottom: 0!important;
}
</style>
