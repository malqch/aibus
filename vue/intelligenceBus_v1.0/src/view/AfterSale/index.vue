<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:售后维修组件页面
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
                                售后车辆分析-故障
                            </div>
                            <div class="faultTip">
                                <div class="faultTipimg" :style="'background-image:url('+ hexagonicon +');'">
                                    <img :src="tipicon">
                                </div>
                                {{faultDetail}}
                            </div>
                            <echarts-single-xbar :barObj="faultTypestatData" :height="height" :color="faultColor" :boxid="faultTypestatbox"></echarts-single-xbar>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="box">
                            <div class="boxTitle" style="position: relative;">
                                <img :src="infoicon">
                                维修车辆清单查看
<!--                                <ul class="statusDiv">-->
<!--                                    <li :class="isTrue?'statusActived':''" @click='selectStatus(0)'>维修中</li>-->
<!--                                    <li :class="isTrue?'':'statusActived'" @click='selectStatus(1)'>维修完</li>-->
<!--                                </ul>-->
                            </div>
                            <el-date-picker class="busesDate" size="mini" @change="getDate"
                              v-model="busesDate"
                              type="daterange"
                              range-separator="至"
                              start-placeholder="开始日期"
                              end-placeholder="结束日期">
                            </el-date-picker>
                            <el-table :data="maintainBuses" border max-height="210" style="width: 100%;overflow-y:hidden;overflow-x: auto;">
                                <el-table-column width="100px" label="编号" prop="busId"></el-table-column>
                                <el-table-column  width="140px" label="车辆VIN码" prop="vinCode"></el-table-column>
                                <el-table-column width="140px" label="申请维修时间" prop="createdDate"></el-table-column>
                                <el-table-column label="维修状态" prop="busStatusName"></el-table-column>
                                <el-table-column label="操作" width="90px">
                                  <template slot-scope="scope">
                                    <el-button icon="el-icon-copy-document" size="mini" class="lookupBtn"  @click="showMaintainDetail(scope.row)">查看</el-button>
                                  </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </el-col>
                </el-row>
            </div>
            <div class="box" style="margin-bottom: 0;">
                <div class="boxTitle" style="position: relative;">
                    <img :src="infoicon">
                    售后车辆数据展示-{{ displayBusStatusName }}
                    <div class="statusDiv">
                        <el-select class="faultType" v-model="busStatusName" placeholder="请选择" @change="changeBusStatusId" :popper-append-to-body="false">
                            <el-option
                              v-for="item in busStatusTypeList"
                              :key="item.busStatusId"
                              :label="item.busStatusName"
                              :value="item.busStatusId">
                            </el-option>
                        </el-select>
                        <el-select class="faultType" v-model="vin" filterable
                                   style="width:220px" placeholder="请选择VIN码" @change="changeVin" :popper-append-to-body="false">
                            <el-option
                              v-for="item in vinList"
                              :key="item"
                              :label="item"
                              :value="item">
                            </el-option>
                        </el-select>
                        <el-button type="primary" size="small" icon="el-icon-search" @click="handleList(1)">搜索</el-button>
                    </div>
                </div>
                <el-table :data="faultBusesData" border style="width: 100%;margin-top: 28px;">
                    <el-table-column width="60px"  type="index" :index="table_index" label="序号" prop="busId"></el-table-column>
                    <el-table-column label="车辆VIN码" prop="vinCode"></el-table-column>
                    <el-table-column label="所属公交公司" prop="companyName"></el-table-column>
                    <el-table-column label="联系人" prop="companyPerson"></el-table-column>
                    <el-table-column label="联系方式" prop="companyPhone"></el-table-column>
                    <el-table-column label="车辆状态" prop="busStatusName">
                        <!-- <template slot-scope="scope">
                          <el-tag type="primary" size="small" @click="changeBusStatus(scope.row)">{{scope.row.busStatusName}}</el-tag>
                        </template> -->
                       <!-- <template slot-scope="scope">
                            <div class="abc">
                                <el-popover
                                        placement="top-start"
                                        title=""
                                        width="200"
                                        trigger="hover"
                                        content="">
                                    <el-radio-group v-model="scope.row.busStatusId" @change="changeBusStatusByBusId(scope.row)">
                                        <el-radio v-for="item in busStatusTypeList" :key="item.busStatusId" :label="item.busStatusId">{{item.busStatusName}}</el-radio>
                                    </el-radio-group>
                                    <span slot="reference" style="color:#0D96FF;cursor: pointer;">{{scope.row.busStatusName}}</span>
                                </el-popover>
                            </div>

                        </template> -->
                    </el-table-column>
                    <el-table-column label="备注" prop="" :show-overflow-tooltip="false">
                        <template slot-scope="scope">
                            <el-tooltip class="item" effect="dark" placement="top">
                                <div v-html="scope.row.maintenanceDesc" slot="content"></div>
                                <div class="lineCls">{{changeBr(scope.row.maintenanceDesc)}}</div>
                            </el-tooltip>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="180px">
                      <template slot-scope="scope">
                        <el-button icon="el-icon-copy-document" size="mini" class="lookupBtn"  @click="showCarDetail(scope.row)">查看</el-button>
                        <el-button size="mini" class="editBtn"  @click="maintainDesc(scope.row)"><i class="fa fa-wrench" aria-hidden="true"></i>操作</el-button>
                      </template>
                    </el-table-column>
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
        <maintain-detail v-show="maintainDetailVisible" ref='maintainDetail'></maintain-detail>
        <car-detail v-show="carDetailVisible" ref='carDetail' @refreshDataList="handleList()"></car-detail>
        <maintain-desc v-show="maintainDescVisible" ref="maintainDesc" @refreshDataList="refreshData()"></maintain-desc>
    </div>
</template>

<script>
    import { getPercent,getAreaList,getCustomerCompanyListData,getKnowledgeFaultStatData,getFaultTypeStatByCompanyIdData,
     getMaintainByCompanyIdData,getBusStatusListData,getBusVinListData,getFaultBusesPageData,changeBusStatusByBusId
     } from "@/api/sale";
    import { formatDate,getInitData } from "@/api/sale";
    import customerListData from './customerListData.json'
    import areaData from './areaData.json'
    import EchartsSingleXbar from "@/components/bus/echarts-singlebar-xcategory";
    import carDetail from './car-detail'
    import maintainDetail from './maintain-detail'
    import maintainDesc from './maintainDesc'
    export default {
        name: "GfmAdminSalePreview",
        data() {
            return {
                infoicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk3ODA3NjY3MDYwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjYyNTYiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTcxLjkxNzEzNiA1OTEuNTk5OTE5Yy0xMy4yOTA3MDEgMTIuNjg4OTk3LTIyLjYyNDI3NyAyMC42MTI0NTctMjguODUyMTE4IDI1LjYzNjg5IDMuMDQyMjg5LTE2LjQ1MDY3IDEwLjk2ODgxOS00OS4wNjQ0NjIgMzAuMDU5NjItMTEzLjI4OTIwNiAxOS4wMDY4ODktNjMuNjIyMDE2IDIwLjY0MjEzMy03NC43MzIwNTIgMjAuNjQyMTMzLTgwLjE4NzI5NyAwLTkuNTMxMDc0LTMuOTYxMjE4LTE3Ljk3MjMyNy0xMC44ODA4MTUtMjMuODAxMDc5LTE1LjUzMzc4OC0xMi44MDQ2My00Mi45MjI1NzgtMTAuMjc4MDg3LTc1Ljk5Njg1OCA4LjQ0MTI1My0xOC40MzE3OTEgMTAuMzM2NDE2LTM4LjAxMDcwOSAyNy4yMTc4OTgtNTkuODg2OTQ5IDUxLjIxOTU0NWwtMTEuNDAwNjU0IDEyLjY4ODk5NyAzNy44NDE4NjMgMjkuMTY5MzQzIDkuNjczMzEzLTkuNTYwNzVjMTAuNTM2OTg0LTEwLjIxOTc1OSAxNy42ODM3NTQtMTYuODgxNDgyIDIyLjQ1MjM2MS0yMS4zMzE4NDEtMjguODg0ODY0IDk1LjA2MDAyOS00Mi45NTEyMzEgMTU0LjE0MzY4My00Mi45NTEyMzEgMTgwLjU4NTkxNSAwIDEyLjAwMjM1OCAzLjQ3NDEyNSAyMS45NjUyNjggMTAuNTA5MzU1IDI5LjUxNDE5NyA3LjA5MTUxMiA3Ljc1MjU2OCAxNy4wNTIzNzQgMTEuNzk5NzQ0IDI4LjQ0ODkzNiAxMS43OTk3NDQgMTEuMTY4MzY0IDAgMjMuOTQzMzE4LTQuMzA1MDQ5IDM5LjQ3NzEwNy0xMy4xMTg3ODUgMTQuMDEwMDg1LTcuOTUyMTEyIDM1LjAyNzc3MS0yNS4zMjI3MzUgNjQuNDI0Mjg4LTUzLjAwMDA5OGwxMS45NDQwMy0xMS40MjYyMzctMzQuMzkyMjk4LTMzLjUwNTA5Mkw1NzEuOTE3MTM2IDU5MS41OTk5MTkgNTcxLjkxNzEzNiA1OTEuNTk5OTE5ek05MzIuMDAxMTkxIDMzNC4yMTI4MjhjLTIyLjk0MDQ3OC01NC4zMjAxNjMtNTUuOTU4NDc2LTEwMy4xODQwNTctOTcuNzI5ODM1LTE0NC45Mjg4MS00MS44MzE3MzQtNDEuODg5MDM5LTkwLjYxMDY5NC03NC44MTkwMzMtMTQ0LjczMDI4OS05Ny44NDQ0NDUtMTEyLjQ4Mzg2NC00Ny41NDM4MjktMjQyLjQyNzM3OC00Ny41NDM4MjktMzU0Ljk3MTYxNyAwLTU0LjE0NzIyNCAyMy4wMjU0MTMtMTAyLjgzODE4IDU1Ljk1NjQzLTE0NC43MjYxOTYgOTcuODQ0NDQ1LTQxLjgzMTczNCA0MS44MzA3MTEtNzQuODQ5NzMyIDkwLjc1MjkzNC05Ny43MDIyMDYgMTQ0LjkyODgxLTIzLjcxNjE0NCA1Ni40MTQ4NzEtMzUuODMyMDkgMTE2LjA3NDY0NS0zNS44MzIwOSAxNzcuNzQ0MTkzIDAgNjEuNjExMjIgMTIuMDU5NjY0IDEyMS4yNjk5NzEgMzUuNzQ4MTc5IDE3Ny42Mjk1ODMgMjIuODgwMTAzIDU0LjIzMzE4MiA1NS44OTgxMDEgMTAzLjAzOTc3MSA5Ny43MjU3NDIgMTQ1LjA0MzQyMSA0MS43NzU0NTIgNDEuODI5Njg3IDkwLjQ5NTA2IDc0LjgxNjk4NiAxNDQuNzMwMjg5IDk3Ljc4NjExNyA1Ni4zMjk5MzYgMjMuODU3MzYxIDExNS45ODk3MTEgMzUuODAxMzkxIDE3Ny40NTU2MjEgMzUuODAxMzkxIDYxLjUyNjI4NSAwIDEyMS4xNTc0MDctMTEuOTQ0MDMgMTc3LjUxNTk5Ni0zNS44NTk3MTkgNTQuMjM1MjI5LTIyLjk2ODEwOCAxMDIuOTUzODEzLTU1Ljg5ODEwMSAxNDQuNzMwMjg5LTk3LjcyNzc4OSA0MS44Mjc2NDEtNDEuOTQ1MzIxIDc0Ljc4OTM1Ny05MC44MTAyMzkgOTcuNjk4MTEzLTE0NS4wNzQxMiAyMy43NzE0MDMtNTYuMzI5OTM2IDM1Ljc3NTgwOC0xMTUuOTg5NzExIDM1Ljc3NTgwOC0xNzcuNTk5OTA3Qzk2Ny43NDUyNzcgNDUwLjI4NzQ3MyA5NTUuNzQ0OTY1IDM5MC42Mjc2OTggOTMyLjAwMTE5MSAzMzQuMjEyODI4TDkzMi4wMDExOTEgMzM0LjIxMjgyOHpNOTA5LjY5MjA5MyA1MTEuOTU4MDQ0YzAgMjE5LjU0NTIyOC0xNzguMzQ2OTIxIDM5OC4xMjIzOTMtMzk3LjY2MTkwNSAzOTguMTIyMzkzLTIxOS4yNTk3MjYgMC0zOTcuNjA2NjQ3LTE3OC41NzcxNjUtMzk3LjYwNjY0Ny0zOTguMTIyMzkzIDAtMjE5LjYzMzIzMyAxNzguMzQ2OTIxLTM5OC4xODA3MjEgMzk3LjYwNjY0Ny0zOTguMTgwNzIxQzczMS4zNDUxNzIgMTEzLjc3NjMgOTA5LjY5MjA5MyAyOTIuMzI0ODEyIDkwOS42OTIwOTMgNTExLjk1ODA0NEw5MDkuNjkyMDkzIDUxMS45NTgwNDR6TTU2MC4wNjExMTEgMjc2LjAxNzQwNGMtOC42MTQxOTIgOS40NzQ3OTItMTMuMDM0ODc0IDIwLjk1NzMxMS0xMy4wMzQ4NzQgMzQuMjIyNDI5IDAgMTAuOTA5NDY3IDMuNTI5MzgzIDIwLjQxMTg4OSAxMC41MDkzNTUgMjcuNzkwOTUgNy4wOTE1MTIgNy40OTI2NDggMTYuMjc2NzA4IDExLjM0MDI3OSAyNi43NTc0MSAxMS4zNDAyNzkgOC40Njk5MDUgMCAyMS4wNzM5NjgtMi41ODM4NDggMzIuNjEzNzkyLTE1LjQxNzEzMSA5LjAxNDMwNC05LjcwMjk4OSAxMy41NTA2MjEtMjEuMzg4MTIzIDEzLjU1MDYyMS0zNC4zOTQzNDUgMC0xMC42NTE1OTQtMy42NzM2NjktMTkuOTgyMS0xMC42ODEyNy0yNy4yMTc4OThDNjA0LjQ0NzAxNyAyNTYuNTUyMDc0IDU3Ni41NDI0OCAyNTguMjQ1NjQ1IDU2MC4wNjExMTEgMjc2LjAxNzQwNEw1NjAuMDYxMTExIDI3Ni4wMTc0MDR6IiBwLWlkPSI2MjU3IiBmaWxsPSIjMzMzMzMzIj48L3BhdGg+PC9zdmc+',
                hexagonicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MzI2ODk1NDUwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjE4MTgiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTEyLjAyNDQ3NSAwbDQ0NS40MTMyNzUgMjU1Ljk4NDY0MVY3NjguMDE1MzU5bC00NDUuNDEzMjc1IDI1NS45ODQ2NDFMNjYuNTA4ODA2IDc2OC4wMTUzNTlWMjU2LjA0NjA3N3oiIHAtaWQ9IjE4MTkiIGZpbGw9IiNmZGExMTUiPjwvcGF0aD48L3N2Zz4=',
                tipicon:'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MzI2NzM0NjcwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjgwNjciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTEyLjAyMzY1NyAwLjE2NjUzMUMzMzYuOTk4NDc0IDAuMTY2NTMxIDE5Mi40ODc1NDkgMTUxLjM5NDQzMSAxOTIuNDg3NTQ5IDMzNy4zNTg2ODhjMCAxMTMuNDIwOTI1IDQyLjczMjc1NyAyMDQuNzA3ODE3IDEyNy44Nzg0MTUgMjY1LjY3MjM2NHYxMDkuMDA2OTEzYzAgMTIuNDEwNDExIDcuMDM2ODMxIDI0LjMwOTA1MyAxNS42NzI5NDIgMzIuNjg5Mjc5YTQzLjY5MjMyNSA0My42OTIzMjUgMCAwIDAgMjkuNzQ2NjA1IDExLjc3MDdjMS4xNTE0ODEgMCAyLjc1MDc2MSAwIDMuOTAyMjQyLTAuMTI3OTQzbDI5Mi45MjQwOTItMjQuNjI4OTA5YTQ1LjQ4MzUxOCA0NS40ODM1MTggMCAwIDAgNDEuMDA1NTM1LTQ0LjMzMjAzN1Y2MDMuNzk4NzA2Yzc0LjUyNjQ0LTU2LjQ4NjU2MyAxMjcuNjg2NTAxLTE1NC4wNDI2MzIgMTI3LjY4NjUwMS0yNjYuNDQwMDE4IDAtMTg1Ljk2NDI1Ny0xNDQuMjU1MDQtMzM3LjE5MjE1Ny0zMTkuMjgwMjI0LTMzNy4xOTIxNTd6IG0xMjcuMzAyNjc0IDUzOS4wODUyNDJjLTEzLjgxNzc3OCA3Ljc0MDUxNC0yMC44NTQ2MDkgMjIuODM3NzE2LTIwLjg1NDYwOSAzOS4yNzgzMTJ2NjcuODA5NDY1bC0yMTIuOTYwMTAxIDE3LjQ2NDEzNVY1NzguNTMwMDg1YzAtMTUuODAwODg1LTYuMTQxMjM1LTMwLjQ1MDI4OC0xOS4xOTEzNTgtMzguMzgyNzE1LTczLjMxMDk4Ny00NC45NzE3NDktMTA4LjA0NzM0NS0xMTEuMzA5ODc1LTEwOC4wNDczNDQtMjAyLjc4ODY4MiAwLTEzNi44OTgzNTMgMTA0LjkxMjc1Ni0yNDguMjcyMTk5IDIzMy4zMDI5NC0yNDguMjcyMTk5IDEyOC4zOTAxODQgMCAyMzMuODE0NzEgMTExLjM3Mzg0NyAyMzMuODE0NzEgMjQ4LjI3MjE5OSAwIDg4Ljg1NTk4Ny0zOS4wMjI0MjggMTY0LjM0MTk5NC0xMDYuMDY0MjM4IDIwMS44OTMwODV6IG02MC40NTI3NzcgMjg3LjEwMjcxM2MxLjcyNzIyMiAyNC41NjQ5MzgtMTUuODAwODg1IDQ1Ljg2NzM0NS0zOS4wODYzOTkgNDcuNjU4NTM5bC0yOTAuODEzMDQyIDIyLjEzNDAzM2MtMS4wMjM1MzkgMC4xMjc5NDItMi4xMTEwNDkgMC4xMjc5NDItMy4xMzQ1ODkgMC4xMjc5NDItMjEuOTQyMTE5IDAtNDAuNDkzNzY1LTE3Ljg0Nzk2My00Mi4xNTcwMTYtNDEuMjYxNDE5LTEuNjYzMjUxLTI0LjUwMDk2NyAxNS44NjQ4NTYtNDUuODAzMzc0IDM5LjE1MDM3LTQ3LjU5NDU2OGwyOTAuODEzMDQzLTIyLjE5ODAwNGMyMy4yMjE1NDMtMS43OTExOTMgNDMuNTY0MzgyIDE2LjYzMjUxIDQ1LjIyNzYzMyA0MS4xMzM0Nzd6TTYwNy4xNDg4MjEgOTY2Ljg5OTE5N2MxLjc5MTE5MyAyNC41MDA5NjctMTUuNjA4OTcxIDQ1Ljg2NzM0NS0zOC45NTg0NTYgNDcuNzIyNTFsLTEwNS40ODg0OTcgOC41NzIxMzktMy4zMjY1MDIgMC4xMjc5NDNjLTIxLjg3ODE0OCAwLTQwLjQyOTc5NC0xNy43MjAwMi00Mi4xNTcwMTYtNDEuMDY5NTA2LTEuNzkxMTkzLTI0LjUwMDk2NyAxNS42NzI5NDItNDUuODY3MzQ1IDM4Ljk1ODQ1Ni00Ny43ODY0ODFsMTA1LjU1MjQ2OC04LjUwODE2OWMyMy4zNDk0ODUtMS43OTExOTMgNDMuNjI4MzUzIDE2LjQ0MDU5NyA0NS40MTk1NDcgNDAuOTQxNTY0eiBtNzQuNTI2NDQtNjMwLjMwODE2M2EzNy43NDMwMDQgMzcuNzQzMDA0IDAgMCAxLTQwLjQyOTc5NCAzNC45MjgyNzFjLTIwLjM0MjgzOS0yLjExMTA0OS0zNS4yNDgxMjctMjEuMTEwNDk0LTMzLjI2NTAyMS00Mi41NDA4NDMgNS44ODUzNS02My42NTEzMzctNDcuMjc0NzExLTg2LjIzMzE2OC01My4zNTE5NzQtODguNjAwMTAyYTM5LjY2MjE0IDM5LjY2MjE0IDAgMCAxLTIyLjE5ODAwNC00OS43Njk1ODggMzYuNDYzNTggMzYuNDYzNTggMCAwIDEgNDYuODkwODg0LTIzLjYwNTM3YzM5LjUzNDE5NyAxNC40NTc0OSAxMTEuNzU3Njc0IDY4LjU3NzExOSAxMDIuMzUzOTA5IDE2OS41ODc2MzJ6IiBmaWxsPSIjZmZmZmZmIiBwLWlkPSI4MDY4Ij48L3BhdGg+PC9zdmc+',
                parentAreaId:0,
                initSearchFlag:true,
                initData:{},
                companyList:[],
                companyName:'',
                companyId:'',
                faultDetail:'',
                height:'200px',
                faultColor:['#f75a66','#b11921'],
                faultTypestatbox:'faultTypestatbox',
                faultTypestatData:{},
                status:0,
                isTrue:true,
                busesDate:[],
                startTime:'',
                endTime:'',
                maintainBuses:[],
                busStatusTypeList:[],
                displayBusStatusName:'',
                busStatusId:'',
                busStatusCode:'',
                busStatusName:'',
                vin:'',
                vinList:[],
                form: {
                    busStatusId: 0,
                    companyId:0,
                    vin:'',
                    page: 1,
                    limit: 10
                },
                table: {
                    currPage:1,
                    pageSize:5,
                    totalCount:50
                },
                showFaultDesc:false,
                faultBusesData:[],
                maintainDetailVisible:false,
                carDetailVisible:false,
                maintainDescVisible:false,
                areaList: [],
                areaIdList: [],
            }
        },
        components: {
            EchartsSingleXbar,
            carDetail,
            maintainDetail,
            maintainDesc
        },
        created(){
        },
        mounted(){
            this.getInitData();
            // this.getProvinceData();
            this.startTime=formatDate(new Date(new Date().toLocaleDateString()).getTime()- 24 * 60 * 60 * 1000 * 10);
            this.endTime=formatDate(new Date(new Date().toLocaleDateString()).getTime() +  24 * 60 * 60 * 1000 - 1);
            // this.startTime=this.startTime.substring(0,10);
            // this.endTime=this.endTime.substring(0,10);
            this.busesDate=[this.startTime,this.endTime];
            this.getBusStatusType();
        },
        methods: {
            changeBr : function(val){
                if(val){
                    return val.replace(/<br\/>/g, '\n')
                }
            },
            getInitData(){
                getInitData().then(resp => {
                    if(resp.data.code==0){
                        this.initData=resp.data.data.data;
                        this.initAreaList();
                    }
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
                this.faultBusesData=[];
                this.table ={}
                this.maintainBuses=[]
                getCustomerCompanyListData(id).then(resp => {
                    if(resp.data.code==0){
                        this.companyList=resp.data.data.list;
                        // 第一次
                        if(this.initSearchFlag){
                            this.companyName=this.initData.companyName;
                            this.companyId=this.initData.companyId;
                        }else{
                            this.companyName=this.companyList[0].companyName;
                            this.companyId=this.companyList[0].companyId;
                        }
                        this.initSearchFlag = false;

                        this.getFaultDetail();
                        this.getFaultTypestatData();
                        this.getMaintainBuses();
                        this.getVinList(this.busStatusId,this.companyId);
                        this.handleList(1)

                    }

                })

            },
            changeCompany(data){
                this.companyId=data;
                this.faultTypestatData={}
                this.getFaultDetail();
                this.getFaultTypestatData();
                this.getMaintainBuses();
                this.getVinList()
                this.handleList(1);
                this.maintainBuses=[]
            },
            getFaultDetail(){
                this.faultDetail='';
                getKnowledgeFaultStatData().then(resp => {
                    if(resp.data.code==0){
                        this.faultDetail=resp.data.data.text
                    }
                })

            },
            getFaultTypestatData(){
                this.faultTypestatData={};
                getFaultTypeStatByCompanyIdData(this.companyId).then(resp => {
                    if(resp.data.code==0){
                        this.faultTypestatData=resp.data.data.data;
                        this.faultTypestatData.xAxisName="故障类型";
                        this.faultTypestatData.yAxisName="车数"
                    }
                })

            },
            selectStatus(val){
                this.status=val;
                if(val==0){
                    this.isTrue=true;
                }else{
                    this.isTrue=false;
                }
                this.maintainBuses=[]
                this.getMaintainBuses()
            },
            getDate(val){
                this.startTime=formatDate(new Date(val[0]).getTime());
                this.endTime=formatDate(new Date(val[1]).getTime() +  24 * 60 * 60 * 1000 -1);
                this.busesDate=[this.startTime,this.endTime];
                this.getMaintainBuses()
                // this.startTime=this.startTime.substring(0,10);
                // this.endTime=this.endTime.substring(0,10);
            },
            getMaintainBuses(){
                this.maintainBuses=[];
                getMaintainByCompanyIdData(this.companyId,this.startTime,this.endTime,this.status).then(resp => {
                    if(resp.data.code==0){
                        this.maintainBuses=resp.data.data.list;
                    }
                })

            },
            getBusStatusType(){
                this.busStatusTypeList=[
                    {
                    	"busStatusId":'',
                    	"busStatusName":"全部",
                        "busStatusCode":""
                    }
                ]


                getBusStatusListData().then(resp => {
                    if(resp.data.code==0){
                        resp.data.data.list.forEach(ele => {
                            this.busStatusTypeList.push(ele)
                        })
                    }
                })
                this.busStatusName=this.busStatusTypeList[0].busStatusName
                this.displayBusStatusName = this.busStatusName
            },
            getBusStatus(busStatusId){
                for (let i = 0; i < this.busStatusTypeList.length; i++) {
                    if(this.busStatusTypeList[i].busStatusId === busStatusId){
                        return this.busStatusTypeList[i]
                    }
                }
            },
            changeBusStatusId(data){
                this.busStatusId=data;
                this.getVinList();
                //console.log(this.busStatusCode)
            },
            getVinList(){
                this.vinList=[];
                this.vin='全部';
                this.vinList.push("全部")
                getBusVinListData(this.busStatusId,this.companyId).then(resp => {
                    if(resp.data.code==0){
                        resp.data.data.list.forEach(ele => {
                            this.vinList.push(ele)
                        })
                    }
                })

            },
            changeVin(val){

                this.vin=val

            },
            table_index(index) {
                return (this.form.page - 1) * this.form.limit + index + 1
            },
            handleSizeChange(val) {
                this.form.limit = val;
                this.handleList(1);
                //console.log(this.form.page);
            },
            handleCurrentChange(val) {
                this.form.page = val;
                this.handleList();
                //console.log(this.form.page);
            },
            handleList(page) {
                if(this.busStatusCode=='maintenance'){
                    this.showFaultDesc=true;
                }else{
                    this.showFaultDesc=false
                }
                if(page){this.form.page=1;}
                this.form.busStatusId=this.busStatusId;
                this.form.companyId=this.companyId;
                if(this.vin=='全部'){
                    this.form.vin=''
                }else{
                    this.form.vin=this.vin;
                }
                this.faultBusesData=[];
                this.table ={}
                getFaultBusesPageData(this.form).then(resp => {
                    if(resp.data.code==0){
                        let busStatus = this.getBusStatus(this.busStatusId);
                        console.log(this.busStatusId)
                        console.log(busStatus)
                        this.busStatusCode=busStatus.busStatusCode;
                        this.displayBusStatusName = busStatus.busStatusName;
                        this.faultBusesData=resp.data.data.page.list;
                        this.table = resp.data.data.page;
                    }
                })


            },
            changeBusStatusByBusId(val){
                console.log(val)
                changeBusStatusByBusId(val.busId,val.busStatusId).then(resp => {
                    if(resp.data.code==0){
                        this.handleList();
                    }
                })

            },
            showMaintainDetail(res){
                this.maintainDetailVisible=true;
                this.$nextTick(()=>{
                    //this.init();
                    this.$refs.maintainDetail.init(res);

                })
            },
            showCarDetail(res){
                this.carDetailVisible=true
                 this.$nextTick(()=>{
                     //this.init();
                     this.$refs.carDetail.init(res);

                 })



            },
            maintainDesc(res){
                this.maintainDescVisible=true;
                this.$nextTick(()=>{
                    //this.init();
                    this.$refs.maintainDesc.maintainDescinit(res);

                })
            },
            refreshData(){
                this.handleList();
                this.getMaintainBuses()
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
    min-height: 40px;
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
.el-table th{
    background-color: #fff !important;
    color: #91979d !important;
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
/* .statusDiv li{
    float: left;
    padding: 3px 5px;
    border: 1px solid #479cf6;
    color: #479cf6;
    cursor: pointer;
} */
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
.lineCls {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
/deep/ .el-scrollbar .el-scrollbar__wrap{
    margin-bottom: 0!important;
}
</style>
