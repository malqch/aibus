<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:客户详情组件页面
-->
<template>
    <div>
        <div class="customer">
            <el-row :gutter="20">
                <el-col :span="21">
                    <ul>
                        <li>客户名称：{{infodata.companyName}}</li>
                        <li>所属城市：{{infodata.areaName}}</li>
                        <li>联系人：{{infodata.companyPerson}}</li>
                        <li>联系方式：{{infodata.companyPhone}}</li>
                        <li>电子邮箱：{{infodata.companyEmail}}</li>
                        <li @click="showMap" style="text-align: right;cursor: pointer;"><i class="el-icon-map-location"></i>地址定位</li>
                    </ul>
                </el-col>
                <el-col :span="3" style="text-align: right;cursor: pointer;" @click.native="goBack">
                    <i class=" fa fa-mail-reply-all"></i>
                    返回
                </el-col>
            </el-row>

        </div>
        <div class="box">
            <div class="boxTitle">
                <img :src="infoicon">
                车辆订单及交付统计
            </div>
            <el-row :gutter="50">
                <el-col :span="12" v-for="(item,index) in deliveryStatList" :key="item.id">
                    <text-progess :progressObj="item" :bg="(index % 2==0)?busDeliverbg[0]:busDeliverbg[1]"></text-progess>
                </el-col>
            </el-row>
        </div>
        <div>
            <el-row :gutter="15">
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            故障统计分析-总数{{radar.total}}
                        </div>
                        <echarts-radar :radarObj="radar" :height="height"></echarts-radar>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            车辆统计分析
                        </div>
                        <div class="carStatistics">
                            <div class="carbox" v-for="(item,index) in busStatusStat" :key="item.id">
                                <div class="carboxTitle">
                                    <div class="carbg" :style="'background-image:url('+ busIcon[index%5].icon+');'"></div>
                                    {{item.name}}
                                    <span :style="'float: right; color:' + busIcon[index%5].color +';'">{{item.number}}/{{item.total}}</span>
                                </div>

                                <el-progress :text-inside="true" :stroke-width="13" :percentage="item.percent" :show-text="false" :color="busIcon[index%5].color"></el-progress>
                            </div>
                        </div>
                    </div>
                </el-col>
            </el-row>
            <el-row :gutter="15">
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            故障级别统计分析-总数{{faultLevelStat.total}}
                        </div>
                        <echarts-bar :barObj="faultLevelStat" :height="height" :color="faultColor" :boxid="faultBarId"></echarts-bar>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="box" style="height: 240px;overflow: auto;">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            客户车辆出保统计
                        </div>
                        <div class="carStatistics customerCar">
                            <div class="mb10" v-for="(item,index) in outDateStat" :key="item.id">
                                <text-progess :progressObj="item" :bg="(index % 2==0)?customerCarbg[0]:customerCarbg[1]"></text-progess>
                            </div>

                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <Map v-show="mapVisible" ref="Map"></Map>
    </div>
</template>

<script>
    import { getPercent,getInfobuscompanyData,getRadarData,getBusStatusCompanyStat,getFaultLevelCompanyStat,
    getDeliveryCompanyStatData,getOutDateCompanyStatData} from "@/api/sale";
    import EchartsRadar from "@/components/bus/echarts-radar";
    import EchartsBar from "@/components/bus/echarts-bar-ycategory";
    import TextProgess from "@/components/bus/text-progress";
    import customerListData from './customerListData.json'
    import Map from '@/components/bus/map.vue'
    export default {
        name: "GfmAdminCustomer",
        data() {
            return {
                busIcon:[
                    {
                       icon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjNjdDMjNBIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjNjdDMjNBIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiM2N0MyM0EiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iIzY3QzIzQSI+PC9wYXRoPjwvc3ZnPg==',
                       color:'#67C23A'
                    },
                    {
                       icon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjRjU2QzZDIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjRjU2QzZDIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiNGNTZDNkMiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iI0Y1NkM2QyI+PC9wYXRoPjwvc3ZnPg==',
                       color:'#F56C6C'
                    },
                    {
                        icon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjZTZhMjNjIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjZTZhMjNjIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiNlNmEyM2MiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iI2U2YTIzYyI+PC9wYXRoPjwvc3ZnPg==',
                        color:'#e6a23c'
                    },
                    {
                        icon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjNDI0MjQyIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjNDI0MjQyIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiM0MjQyNDIiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iIzQyNDI0MiI+PC9wYXRoPjwvc3ZnPg==',
                        color:'#424242'
                    },
                    {
                        icon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjYTIwNzFlIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjYTIwNzFlIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiNhMjA3MWUiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iI2EyMDcxZSI+PC9wYXRoPjwvc3ZnPg==',
                        color:'#a2071e'
                    },
                ],
                infoicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk3ODA3NjY3MDYwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjYyNTYiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTcxLjkxNzEzNiA1OTEuNTk5OTE5Yy0xMy4yOTA3MDEgMTIuNjg4OTk3LTIyLjYyNDI3NyAyMC42MTI0NTctMjguODUyMTE4IDI1LjYzNjg5IDMuMDQyMjg5LTE2LjQ1MDY3IDEwLjk2ODgxOS00OS4wNjQ0NjIgMzAuMDU5NjItMTEzLjI4OTIwNiAxOS4wMDY4ODktNjMuNjIyMDE2IDIwLjY0MjEzMy03NC43MzIwNTIgMjAuNjQyMTMzLTgwLjE4NzI5NyAwLTkuNTMxMDc0LTMuOTYxMjE4LTE3Ljk3MjMyNy0xMC44ODA4MTUtMjMuODAxMDc5LTE1LjUzMzc4OC0xMi44MDQ2My00Mi45MjI1NzgtMTAuMjc4MDg3LTc1Ljk5Njg1OCA4LjQ0MTI1My0xOC40MzE3OTEgMTAuMzM2NDE2LTM4LjAxMDcwOSAyNy4yMTc4OTgtNTkuODg2OTQ5IDUxLjIxOTU0NWwtMTEuNDAwNjU0IDEyLjY4ODk5NyAzNy44NDE4NjMgMjkuMTY5MzQzIDkuNjczMzEzLTkuNTYwNzVjMTAuNTM2OTg0LTEwLjIxOTc1OSAxNy42ODM3NTQtMTYuODgxNDgyIDIyLjQ1MjM2MS0yMS4zMzE4NDEtMjguODg0ODY0IDk1LjA2MDAyOS00Mi45NTEyMzEgMTU0LjE0MzY4My00Mi45NTEyMzEgMTgwLjU4NTkxNSAwIDEyLjAwMjM1OCAzLjQ3NDEyNSAyMS45NjUyNjggMTAuNTA5MzU1IDI5LjUxNDE5NyA3LjA5MTUxMiA3Ljc1MjU2OCAxNy4wNTIzNzQgMTEuNzk5NzQ0IDI4LjQ0ODkzNiAxMS43OTk3NDQgMTEuMTY4MzY0IDAgMjMuOTQzMzE4LTQuMzA1MDQ5IDM5LjQ3NzEwNy0xMy4xMTg3ODUgMTQuMDEwMDg1LTcuOTUyMTEyIDM1LjAyNzc3MS0yNS4zMjI3MzUgNjQuNDI0Mjg4LTUzLjAwMDA5OGwxMS45NDQwMy0xMS40MjYyMzctMzQuMzkyMjk4LTMzLjUwNTA5Mkw1NzEuOTE3MTM2IDU5MS41OTk5MTkgNTcxLjkxNzEzNiA1OTEuNTk5OTE5ek05MzIuMDAxMTkxIDMzNC4yMTI4MjhjLTIyLjk0MDQ3OC01NC4zMjAxNjMtNTUuOTU4NDc2LTEwMy4xODQwNTctOTcuNzI5ODM1LTE0NC45Mjg4MS00MS44MzE3MzQtNDEuODg5MDM5LTkwLjYxMDY5NC03NC44MTkwMzMtMTQ0LjczMDI4OS05Ny44NDQ0NDUtMTEyLjQ4Mzg2NC00Ny41NDM4MjktMjQyLjQyNzM3OC00Ny41NDM4MjktMzU0Ljk3MTYxNyAwLTU0LjE0NzIyNCAyMy4wMjU0MTMtMTAyLjgzODE4IDU1Ljk1NjQzLTE0NC43MjYxOTYgOTcuODQ0NDQ1LTQxLjgzMTczNCA0MS44MzA3MTEtNzQuODQ5NzMyIDkwLjc1MjkzNC05Ny43MDIyMDYgMTQ0LjkyODgxLTIzLjcxNjE0NCA1Ni40MTQ4NzEtMzUuODMyMDkgMTE2LjA3NDY0NS0zNS44MzIwOSAxNzcuNzQ0MTkzIDAgNjEuNjExMjIgMTIuMDU5NjY0IDEyMS4yNjk5NzEgMzUuNzQ4MTc5IDE3Ny42Mjk1ODMgMjIuODgwMTAzIDU0LjIzMzE4MiA1NS44OTgxMDEgMTAzLjAzOTc3MSA5Ny43MjU3NDIgMTQ1LjA0MzQyMSA0MS43NzU0NTIgNDEuODI5Njg3IDkwLjQ5NTA2IDc0LjgxNjk4NiAxNDQuNzMwMjg5IDk3Ljc4NjExNyA1Ni4zMjk5MzYgMjMuODU3MzYxIDExNS45ODk3MTEgMzUuODAxMzkxIDE3Ny40NTU2MjEgMzUuODAxMzkxIDYxLjUyNjI4NSAwIDEyMS4xNTc0MDctMTEuOTQ0MDMgMTc3LjUxNTk5Ni0zNS44NTk3MTkgNTQuMjM1MjI5LTIyLjk2ODEwOCAxMDIuOTUzODEzLTU1Ljg5ODEwMSAxNDQuNzMwMjg5LTk3LjcyNzc4OSA0MS44Mjc2NDEtNDEuOTQ1MzIxIDc0Ljc4OTM1Ny05MC44MTAyMzkgOTcuNjk4MTEzLTE0NS4wNzQxMiAyMy43NzE0MDMtNTYuMzI5OTM2IDM1Ljc3NTgwOC0xMTUuOTg5NzExIDM1Ljc3NTgwOC0xNzcuNTk5OTA3Qzk2Ny43NDUyNzcgNDUwLjI4NzQ3MyA5NTUuNzQ0OTY1IDM5MC42Mjc2OTggOTMyLjAwMTE5MSAzMzQuMjEyODI4TDkzMi4wMDExOTEgMzM0LjIxMjgyOHpNOTA5LjY5MjA5MyA1MTEuOTU4MDQ0YzAgMjE5LjU0NTIyOC0xNzguMzQ2OTIxIDM5OC4xMjIzOTMtMzk3LjY2MTkwNSAzOTguMTIyMzkzLTIxOS4yNTk3MjYgMC0zOTcuNjA2NjQ3LTE3OC41NzcxNjUtMzk3LjYwNjY0Ny0zOTguMTIyMzkzIDAtMjE5LjYzMzIzMyAxNzguMzQ2OTIxLTM5OC4xODA3MjEgMzk3LjYwNjY0Ny0zOTguMTgwNzIxQzczMS4zNDUxNzIgMTEzLjc3NjMgOTA5LjY5MjA5MyAyOTIuMzI0ODEyIDkwOS42OTIwOTMgNTExLjk1ODA0NEw5MDkuNjkyMDkzIDUxMS45NTgwNDR6TTU2MC4wNjExMTEgMjc2LjAxNzQwNGMtOC42MTQxOTIgOS40NzQ3OTItMTMuMDM0ODc0IDIwLjk1NzMxMS0xMy4wMzQ4NzQgMzQuMjIyNDI5IDAgMTAuOTA5NDY3IDMuNTI5MzgzIDIwLjQxMTg4OSAxMC41MDkzNTUgMjcuNzkwOTUgNy4wOTE1MTIgNy40OTI2NDggMTYuMjc2NzA4IDExLjM0MDI3OSAyNi43NTc0MSAxMS4zNDAyNzkgOC40Njk5MDUgMCAyMS4wNzM5NjgtMi41ODM4NDggMzIuNjEzNzkyLTE1LjQxNzEzMSA5LjAxNDMwNC05LjcwMjk4OSAxMy41NTA2MjEtMjEuMzg4MTIzIDEzLjU1MDYyMS0zNC4zOTQzNDUgMC0xMC42NTE1OTQtMy42NzM2NjktMTkuOTgyMS0xMC42ODEyNy0yNy4yMTc4OThDNjA0LjQ0NzAxNyAyNTYuNTUyMDc0IDU3Ni41NDI0OCAyNTguMjQ1NjQ1IDU2MC4wNjExMTEgMjc2LjAxNzQwNEw1NjAuMDYxMTExIDI3Ni4wMTc0MDR6IiBwLWlkPSI2MjU3IiBmaWxsPSIjMzMzMzMzIj48L3BhdGg+PC9zdmc+',
                mapVisible:false,
                height:'210px',
                companyId:'',
                infodata:{
                    "companyName": "",
                    "areaName": "",
                    "companyPerson": "",
                    "companyPhone": "",
                    "companyEmail": "",
                    "companyLongitude": "",
                    "companyLatitude": ""
                },
                deliveryStatList:[],
                radar:{
                    name:"故障统计分析",
                    data:[0,0],
                    indicatorList:[
                        {"text": "测试","max": 5},
                        {"text": "测试","max": 5},
                    ]
                },
                busStatusStat:[],
                faultBarId:'faultLevelStatBar',
                faultColor:['#f45060','#a0051c'],
                faultLevelStat:{},
                outDateStat:{},
                busDeliverbg:[
                    {
                        color:'#f76b1c',
                        bg1:'#fbb162',
                        bg2:'#eb5602'
                    },
                    {
                        color:'#429421',
                        bg1:'#77b708',
                        bg2:'#2e9205'
                    }
                ],
                customerCarbg:[
                    {
                        color:'#cb1c3b',
                        bg1:'#ff406b',
                        bg2:'#cb1c3b'
                    },
                    {
                        color:'#242bb8',
                        bg1:'#de70dd',
                        bg2:'#242bb8'
                    }
                ]
            };
        },
        components: {
            EchartsRadar,
            EchartsBar,
            TextProgess,
            Map
        },
        created(){
            this.companyId=this.$route.query.id;
            //this.companyId=12345678;
            this.getInfoData();
            this.getDeliveryStatData();
            this.getRadarData();
            this.getBusStatusStatData();
            this.getFaultLevelStatData();
            this.getOutDateStatData();

        },
        mounted(){

        },
        methods: {
            getInfoData(){
                getInfobuscompanyData(this.companyId).then(resp => {
                    if(resp.data.code==0){
                        this.infodata=resp.data.data.data
                    }

                })

            },
            showMap() {
                this.mapVisible = true
                this.$nextTick(() => {
                    this.$refs.Map.init(this.infodata.companyLongitude, this.infodata.companytLatitude)
                });
            },
            getDeliveryStatData(){
                getDeliveryCompanyStatData(this.companyId).then(resp => {
                    if(resp.data.code==0){
                        this.deliveryStatList=resp.data.data.list;
                        for(let i=0;i<this.deliveryStatList.length;i++){
                            this.deliveryStatList[i].percent=getPercent(Number(this.deliveryStatList[i].secondCount),Number(this.deliveryStatList[i].firstCount))
                        }
                    }
                })

            },
            getRadarData(){
                getRadarData(this.companyId).then(resp => {
                    if(resp.data.code==0){
                        this.radar=resp.data.data.radar;
                    }

                })

            },
            getBusStatusStatData(){
                getBusStatusCompanyStat(this.companyId).then(resp => {

                    if(resp.data.code==0){
                        this.busStatusStat=resp.data.data.list;
                        for(let i=0;i<this.busStatusStat.length;i++){
                            this.busStatusStat[i].percent=getPercent(Number(this.busStatusStat[i].number),Number(this.busStatusStat[i].total))
                        }
                    }

                })

            },
            getFaultLevelStatData(){
                getFaultLevelCompanyStat(this.companyId).then(resp => {
                    if(resp.data.code==0){
                        this.faultLevelStat=resp.data.data.data;
                        this.faultLevelStat.legendData=['故障名称'],
                        this.faultLevelStat.xAxisName='故障数'
                    }

                })

            },
            getOutDateStatData(){
                getOutDateCompanyStatData(this.companyId).then(resp => {
                    if(resp.data.code==0){
                        this.outDateStat=resp.data.data.list;
                        for(let i=0;i<this.outDateStat.length;i++){
                            this.outDateStat[i].percent=getPercent(Number(this.outDateStat[i].secondCount),Number(this.outDateStat[i].firstCount))
                        }
                    }
                })

            },
            goBack(){
                this.$router.push({
                    path: '/SalePreview'
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
.title{
    margin:0 0 5px 0;
}
.mr5{
    margin-right: 5px;
}
.mb10{
    margin-bottom: 10px;
}
.customer{
    font-size: 12px;
    line-height: 40px;
    overflow: hidden;
    color: #fff;
    border-radius: 3px;
    margin: 10px 0;
    padding: 0 10px;
    background-color: #fd577f;
    background: -webkit-linear-gradient(0deg,#6f52fa,#b554bf,#fd577f);
    box-shadow: 2px 2px 5px rgba(0,0,0,0.3);
}
.customer ul{
    padding: 0;
    margin: 0;
}
.customer ul li{
    float: left;
    margin-right: 40px;
}
.customer ul li:last-child{
    margin-right: 0;
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

.progressBg{
    width: calc(100% - 6px);
    height: 19px;
    background-color: #fff;
    padding: 3px;
    border: 1px solid #e5e8ed;
    margin: 5px 0;
}
.progressC{
    height: 19px;
    line-height: 19px;
    color: #fff;
}
.progressC span{
    padding: 0 5px;
    font-size: 12px;
}
.carStatistics{
    font-size: 12px;
}
.carbox{
    margin-top: 12px;
}
.carboxTitle{
    margin-bottom: 6px;
    font-size: 12px;
}
.carbg{
    width: 45px;
    height: 20px;
    background-size:90px 40px;
    background-position:center center;
    float: left;
    background-repeat: no-repeat;
}
.customerCar{
    padding: 10px;
    background-color: #f1f7fd;
    border: 1px solid #e5e8ed;
    border-radius: 3px;
}
/deep/ .el-progress--text-inside .el-progress-bar{
    background-color: #fff;
    padding: 3px;
    border: 1px solid #e5e8ed;
    border-radius: 100px;
}
/deep/ .el-progress-bar__outer{
    background-color: #fff;
}
/deep/ .el-progress-bar__outer {
    height: 6px;
    border-radius: 100px;
    background-color: #ebeef5;
    overflow: hidden;
    position: relative;
    vertical-align: middle;
}
</style>
