<!--
 @Author: songwenxin
 @Filename: index.vue
 @ProjectName: gfm-manage
 @Mail: songwenxin666@sina.com
 @Date: 2019-09-24 13:23
 @file-description:销售预览组件页面
-->
<template>
    <div>
        <div class="box">
            <div class="mb10">
                <el-select class="selectArea" v-for="(itemList,index) in areaList" :key="'itemList'+index" v-model="areaIdList[index]"
                    placeholder="请选择" @change="changeAreaList($event,index)" :popper-append-to-body="false">
                    <el-option v-for="item in itemList" :key="item.areaId" :label="item.areaName" :value="item.areaId">
                    </el-option>
                </el-select>
            </div>
            <div>
                <el-row :gutter="15">
                    <el-col :span="8" v-for="(item,index) in companyStatList" :key="'companyStat'+index">
                        <div :class="'box saleView saleView'+(index+1)">
                            <div class="saleViewimg" :style="'background-image:url('+ saleIcon[index].icon+');background-size:'+(index==1?'40px 40px':'30px 30px')"></div>
                            <div>{{item.name}}<br><span class="font20">{{item.value}}</span></div>
                        </div>
                    </el-col>
                </el-row>
            </div>
            <div>
                <el-table :data="companyList" border style="width: 100%" :header-row-style="{backgroundColor:'#ff5491',background:' -webkit-linear-gradient(0deg,#6f52fa,#de53bd,#ff5491)!important',color:'#fff'}"
                    :header-cell-style="{background:'none!important',color:'#fff!important'}">
                    <el-table-column label="客户名称" prop="companyName"></el-table-column>
                    <el-table-column label="所属城市" prop="areaName"></el-table-column>
                    <el-table-column label="联系人" prop="companyPerson"></el-table-column>
                    <el-table-column label="联系方式" prop="companyPhone"></el-table-column>
                    <el-table-column label="总订单量" prop="orderCount"></el-table-column>
                    <el-table-column label="总交付量" prop="deliveryCount"></el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button size="mini" class="saleBtn" @click="toDetail(scope.row.companyId)">查看</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
        <div>
            <el-row :gutter="15">
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            客户车辆交付统计
                        </div>
                        <div class="carStatistics customerCar" v-for="obj in deliveryStatList" :key="obj.companyId">
                            <div style="text-align: right;">{{obj.companyName}}</div>
                            <div class="mb10" v-for="(item,index) in obj.orderData" :key="item.id">
                                <text-progess :progressObj="item" :bg="(index % 2==0)?busDeliverbg[0]:busDeliverbg[1]"></text-progess>
                            </div>

                        </div>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="box">
                        <div class="boxTitle">
                            <img :src="infoicon">
                            客户车辆出保统计
                        </div>
                        <div class="carStatistics customerCar" v-for="obj in outDateStatList" :key="obj.companyId">
                            <div style="text-align: right;">{{obj.companyName}}</div>
                            <div class="mb10" v-for="(item,index) in obj.orderData" :key="item.id">
                                <text-progess :progressObj="item" :bg="(index % 2==0)?busOrderbg[0]:busOrderbg[1]"></text-progess>
                            </div>

                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="box">
            <el-row :gutter="15">
                <el-col :span="6" v-for="(item,index) in eventStatList" :key="'eventStat'+index">
                    <div :class="'saleView saleView'+(index+1)">
                        <div class="saleViewimg" v-if="item.type=='卡片冒用'" :style="'background-image:url('+ saleIcon[6].icon+');'"></div>
                        <div class="saleViewimg" v-if="item.type=='可燃气体'" :style="'background-image:url('+ saleIcon[3].icon+');'"></div>
                        <div class="saleViewimg" v-if="item.type=='重点人员'" :style="'background-image:url('+ saleIcon[4].icon+');'"></div>
                        <div class="saleViewimg" v-if="item.type=='交通违章'" :style="'background-image:url('+ saleIcon[5].icon+');background-size:40px 40px'"></div>
                        <div>{{item.name}}<br><span class="font20">{{item.value}}</span></div>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import {
        getPercent,
        getAreaList,
        getCompanyStat,
        getCompanyListData,
        getDeliveryStatData,
        getOutDateStatByAreaIdData,
        getEventStatData,
        getInitData
    } from "@/api/sale";
    import TextProgess from "@/components/bus/text-progress";
    import customerListData from './customerListData.json'
    import areaData from './areaData.json'

    export default {
        name: "GfmAdminSalePreview",
        data() {
            return {
                infoicon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk3ODA3NjY3MDYwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjYyNTYiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTcxLjkxNzEzNiA1OTEuNTk5OTE5Yy0xMy4yOTA3MDEgMTIuNjg4OTk3LTIyLjYyNDI3NyAyMC42MTI0NTctMjguODUyMTE4IDI1LjYzNjg5IDMuMDQyMjg5LTE2LjQ1MDY3IDEwLjk2ODgxOS00OS4wNjQ0NjIgMzAuMDU5NjItMTEzLjI4OTIwNiAxOS4wMDY4ODktNjMuNjIyMDE2IDIwLjY0MjEzMy03NC43MzIwNTIgMjAuNjQyMTMzLTgwLjE4NzI5NyAwLTkuNTMxMDc0LTMuOTYxMjE4LTE3Ljk3MjMyNy0xMC44ODA4MTUtMjMuODAxMDc5LTE1LjUzMzc4OC0xMi44MDQ2My00Mi45MjI1NzgtMTAuMjc4MDg3LTc1Ljk5Njg1OCA4LjQ0MTI1My0xOC40MzE3OTEgMTAuMzM2NDE2LTM4LjAxMDcwOSAyNy4yMTc4OTgtNTkuODg2OTQ5IDUxLjIxOTU0NWwtMTEuNDAwNjU0IDEyLjY4ODk5NyAzNy44NDE4NjMgMjkuMTY5MzQzIDkuNjczMzEzLTkuNTYwNzVjMTAuNTM2OTg0LTEwLjIxOTc1OSAxNy42ODM3NTQtMTYuODgxNDgyIDIyLjQ1MjM2MS0yMS4zMzE4NDEtMjguODg0ODY0IDk1LjA2MDAyOS00Mi45NTEyMzEgMTU0LjE0MzY4My00Mi45NTEyMzEgMTgwLjU4NTkxNSAwIDEyLjAwMjM1OCAzLjQ3NDEyNSAyMS45NjUyNjggMTAuNTA5MzU1IDI5LjUxNDE5NyA3LjA5MTUxMiA3Ljc1MjU2OCAxNy4wNTIzNzQgMTEuNzk5NzQ0IDI4LjQ0ODkzNiAxMS43OTk3NDQgMTEuMTY4MzY0IDAgMjMuOTQzMzE4LTQuMzA1MDQ5IDM5LjQ3NzEwNy0xMy4xMTg3ODUgMTQuMDEwMDg1LTcuOTUyMTEyIDM1LjAyNzc3MS0yNS4zMjI3MzUgNjQuNDI0Mjg4LTUzLjAwMDA5OGwxMS45NDQwMy0xMS40MjYyMzctMzQuMzkyMjk4LTMzLjUwNTA5Mkw1NzEuOTE3MTM2IDU5MS41OTk5MTkgNTcxLjkxNzEzNiA1OTEuNTk5OTE5ek05MzIuMDAxMTkxIDMzNC4yMTI4MjhjLTIyLjk0MDQ3OC01NC4zMjAxNjMtNTUuOTU4NDc2LTEwMy4xODQwNTctOTcuNzI5ODM1LTE0NC45Mjg4MS00MS44MzE3MzQtNDEuODg5MDM5LTkwLjYxMDY5NC03NC44MTkwMzMtMTQ0LjczMDI4OS05Ny44NDQ0NDUtMTEyLjQ4Mzg2NC00Ny41NDM4MjktMjQyLjQyNzM3OC00Ny41NDM4MjktMzU0Ljk3MTYxNyAwLTU0LjE0NzIyNCAyMy4wMjU0MTMtMTAyLjgzODE4IDU1Ljk1NjQzLTE0NC43MjYxOTYgOTcuODQ0NDQ1LTQxLjgzMTczNCA0MS44MzA3MTEtNzQuODQ5NzMyIDkwLjc1MjkzNC05Ny43MDIyMDYgMTQ0LjkyODgxLTIzLjcxNjE0NCA1Ni40MTQ4NzEtMzUuODMyMDkgMTE2LjA3NDY0NS0zNS44MzIwOSAxNzcuNzQ0MTkzIDAgNjEuNjExMjIgMTIuMDU5NjY0IDEyMS4yNjk5NzEgMzUuNzQ4MTc5IDE3Ny42Mjk1ODMgMjIuODgwMTAzIDU0LjIzMzE4MiA1NS44OTgxMDEgMTAzLjAzOTc3MSA5Ny43MjU3NDIgMTQ1LjA0MzQyMSA0MS43NzU0NTIgNDEuODI5Njg3IDkwLjQ5NTA2IDc0LjgxNjk4NiAxNDQuNzMwMjg5IDk3Ljc4NjExNyA1Ni4zMjk5MzYgMjMuODU3MzYxIDExNS45ODk3MTEgMzUuODAxMzkxIDE3Ny40NTU2MjEgMzUuODAxMzkxIDYxLjUyNjI4NSAwIDEyMS4xNTc0MDctMTEuOTQ0MDMgMTc3LjUxNTk5Ni0zNS44NTk3MTkgNTQuMjM1MjI5LTIyLjk2ODEwOCAxMDIuOTUzODEzLTU1Ljg5ODEwMSAxNDQuNzMwMjg5LTk3LjcyNzc4OSA0MS44Mjc2NDEtNDEuOTQ1MzIxIDc0Ljc4OTM1Ny05MC44MTAyMzkgOTcuNjk4MTEzLTE0NS4wNzQxMiAyMy43NzE0MDMtNTYuMzI5OTM2IDM1Ljc3NTgwOC0xMTUuOTg5NzExIDM1Ljc3NTgwOC0xNzcuNTk5OTA3Qzk2Ny43NDUyNzcgNDUwLjI4NzQ3MyA5NTUuNzQ0OTY1IDM5MC42Mjc2OTggOTMyLjAwMTE5MSAzMzQuMjEyODI4TDkzMi4wMDExOTEgMzM0LjIxMjgyOHpNOTA5LjY5MjA5MyA1MTEuOTU4MDQ0YzAgMjE5LjU0NTIyOC0xNzguMzQ2OTIxIDM5OC4xMjIzOTMtMzk3LjY2MTkwNSAzOTguMTIyMzkzLTIxOS4yNTk3MjYgMC0zOTcuNjA2NjQ3LTE3OC41NzcxNjUtMzk3LjYwNjY0Ny0zOTguMTIyMzkzIDAtMjE5LjYzMzIzMyAxNzguMzQ2OTIxLTM5OC4xODA3MjEgMzk3LjYwNjY0Ny0zOTguMTgwNzIxQzczMS4zNDUxNzIgMTEzLjc3NjMgOTA5LjY5MjA5MyAyOTIuMzI0ODEyIDkwOS42OTIwOTMgNTExLjk1ODA0NEw5MDkuNjkyMDkzIDUxMS45NTgwNDR6TTU2MC4wNjExMTEgMjc2LjAxNzQwNGMtOC42MTQxOTIgOS40NzQ3OTItMTMuMDM0ODc0IDIwLjk1NzMxMS0xMy4wMzQ4NzQgMzQuMjIyNDI5IDAgMTAuOTA5NDY3IDMuNTI5MzgzIDIwLjQxMTg4OSAxMC41MDkzNTUgMjcuNzkwOTUgNy4wOTE1MTIgNy40OTI2NDggMTYuMjc2NzA4IDExLjM0MDI3OSAyNi43NTc0MSAxMS4zNDAyNzkgOC40Njk5MDUgMCAyMS4wNzM5NjgtMi41ODM4NDggMzIuNjEzNzkyLTE1LjQxNzEzMSA5LjAxNDMwNC05LjcwMjk4OSAxMy41NTA2MjEtMjEuMzg4MTIzIDEzLjU1MDYyMS0zNC4zOTQzNDUgMC0xMC42NTE1OTQtMy42NzM2NjktMTkuOTgyMS0xMC42ODEyNy0yNy4yMTc4OThDNjA0LjQ0NzAxNyAyNTYuNTUyMDc0IDU3Ni41NDI0OCAyNTguMjQ1NjQ1IDU2MC4wNjExMTEgMjc2LjAxNzQwNEw1NjAuMDYxMTExIDI3Ni4wMTc0MDR6IiBwLWlkPSI2MjU3IiBmaWxsPSIjMzMzMzMzIj48L3BhdGg+PC9zdmc+',
                saleIcon: [{
                        icon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM2MTkzMjUwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjI2NzIiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMzg0IDU3NmEyMjQgMjI0IDAgMSAxIDE1OC4zOS02NS42MUEyMjIuNTMgMjIyLjUzIDAgMCAxIDM4NCA1NzZ6IG0wLTM4NGMtODguMjIgMC0xNjAgNzEuNzgtMTYwIDE2MHM3MS43OCAxNjAgMTYwIDE2MCAxNjAtNzEuNzggMTYwLTE2MC03MS43OC0xNjAtMTYwLTE2MHoiIHAtaWQ9IjI2NzMiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48cGF0aCBkPSJNNjcyIDg5NmEzMiAzMiAwIDAgMS0zMi0zMlY3NjRjMC0xMDMuNjYtODQuMzQtMTg4LTE4OC0xODhIMzE2Yy0xMDMuNjYgMC0xODggODQuMzQtMTg4IDE4OHYxMDBhMzIgMzIgMCAwIDEtNjQgMFY3NjRhMjUyIDI1MiAwIDAgMSAyNTItMjUyaDEzNmEyNTIgMjUyIDAgMCAxIDI1MiAyNTJ2MTAwYTMyIDMyIDAgMCAxLTMyIDMyek05MjkgODk2YTMyIDMyIDAgMCAxLTMyLTMyVjc2NGMwLTg0LjI5LTUzLjcxLTE1Ni43LTEzMy42Ni0xODAuMThhNjEuNTggNjEuNTggMCAwIDEtMjEuNTctMTA2LjkzIDE2MC41NyAxNjAuNTcgMCAwIDAtNDguMjYtMjc2LjA5IDMyIDMyIDAgMSAxIDIxLTYwLjQ1IDIyNC41NSAyMjQuNTUgMCAwIDEgNzEuMDggMzgzLjM0IDI1MS40IDI1MS40IDAgMCAxIDEyNC44MyA4OC4xOEM5NDMuNTEgNjU1LjgyIDk2MSA3MDguNDMgOTYxIDc2NHYxMDBhMzIgMzIgMCAwIDEtMzIgMzJ6IiBwLWlkPSIyNjc0IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PC9zdmc+'
                    },
                    {
                        icon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjwvc3ZnPg=='
                    },
                    {
                        icon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM1NzE5Njk3IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjUwMTgiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNNTEyLjEwMzEzMiAzMjAuMTIwOTc5YTEwNi4yMjE3NDMgMTA2LjIyMTc0MyAwIDEgMS03NS41MDcwMjIgMzEuMzU0NjExQTEwNi4yMjE3NDMgMTA2LjIyMTc0MyAwIDAgMSA1MTIuMTAzMTMyIDMyMC4xMjA5NzltMC02My45ODkwMDJhMTcwLjg1MDYzNSAxNzAuODUwNjM1IDAgMSAwIDEyMC45MzkyMTQgNDkuOTExNDIyQTE2OS41NzA4NTUgMTY5LjU3MDg1NSAwIDAgMCA1MTIuMTAzMTMyIDI1Ni4xMzE5Nzd6IiBmaWxsPSIjZmZmZmZmIiBwLWlkPSI1MDE5Ij48L3BhdGg+PHBhdGggZD0iTTgwNS4xNzI3NjEgMTI0LjMxNDYzM2E0MDcuNjA5OTQyIDQwNy42MDk5NDIgMCAwIDAtNTg2LjEzOTI1NyAwIDQzMS4yODU4NzMgNDMxLjI4NTg3MyAwIDAgMCAwIDU5OS41NzY5NDhMNTEyLjEwMzEzMiAxMDI0bDI5My4wNjk2MjktMzAwLjEwODQxOWE0MzEuMjg1ODczIDQzMS4yODU4NzMgMCAwIDAgMC01OTkuNTc2OTQ4eiBtLTQ0LjE1MjQxMSA1NTcuMzQ0MjA3bC0yNDguOTE3MjE4IDI1NS45NTYwMDctMjQ4LjkxNzIxNy0yNTUuOTU2MDA3YTM2NS4zNzcyMDEgMzY1LjM3NzIwMSAwIDAgMSAwLTUwOC43MTI1NjUgMzQ2LjgyMDM5IDM0Ni44MjAzOSAwIDAgMSA0OTcuODM0NDM1IDAgMzY1LjM3NzIwMSAzNjUuMzc3MjAxIDAgMCAxIDAgNTA4LjcxMjU2NXoiIGZpbGw9IiNmZmZmZmYiIHAtaWQ9IjUwMjAiPjwvcGF0aD48L3N2Zz4='
                    },
                    {
                        icon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM2MDYwNTk0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjE3MzkiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNMzM2IDk3Mi44Yy02MC44LTEyOC0yOC44LTIwMS42IDE5LjItMjY4LjggNTEuMi03Ni44IDY0LTE1MC40IDY0LTE1MC40czQxLjYgNTEuMiAyNS42IDEzNC40YzcwLjQtODAgODMuMi0yMDggNzMuNi0yNTYgMTYwIDExMiAyMzAuNCAzNTguNCAxMzcuNiA1MzcuNiA0OTIuOC0yODEuNiAxMjEuNi03MDAuOCA1Ny42LTc0NS42IDIyLjQgNDggMjUuNiAxMjgtMTkuMiAxNjYuNC03My42LTI4MS42LTI1Ni0zMzYtMjU2LTMzNiAyMi40IDE0NC03Ni44IDMwMC44LTE3Mi44IDQxOS4yLTMuMi01Ny42LTYuNC05Ni0zOC40LTE1My42LTYuNCAxMDUuNi04Ni40IDE4OC44LTEwOC44IDI5NC40Qzg5LjYgNzU4LjQgMTQwLjggODYwLjggMzM2IDk3Mi44TDMzNiA5NzIuOHoiIGZpbGw9IiNmZmZmZmYiIHAtaWQ9IjE3NDAiPjwvcGF0aD48L3N2Zz4='
                    },
                    {
                        icon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM2MTM0NTUxIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjI1MTEiIGRhdGEtc3BtLWFuY2hvci1pZD0iYTMxM3guNzc4MTA2OS4wLmk0IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTkyMS45MjYgNzgxLjE4NWMyLjk1NiA4Ljg3OCA1LjIyMiAyMC4zNzEgNi42MTQgMzQuMjk5IDEuMzkyIDEzLjkyOSAyLjA4OSAyLjc4NiAyLjA4OSAxNi43MTUgMCAxMy45MzItMS4wNDIgMjcuMTYzLTIuOTYxIDM5LjY5OS0yLjA4OSAxMi41MzUtNS4yMiAyMS45NC05LjIyNCAyOC4zODMtMi45NjEgNC4xNzgtMTEuNDkzIDguNTMxLTI1LjQyMiAxMy40MDZDODc5LjA5IDkxOC41NjEgNzMzLjcwNyA5NjAgNTI5LjI5NiA5NjBjLTE4Ni44MzEgMC0zNDcuNzExLTMwLjI5Ni0zNzMuMzA3LTM2LjA0Mi0yNS41OTYtNS43NDctNDAuOTE3LTEwLjYyMS00Ni4zMTQtMTQuNjI1LTYuNjE4LTQuODc1LTExLjQ5My0xOS4zMjktMTQuNjI5LTQzLjUyOC0zLjEzMS0yNC4zNzktMS45MTQtMzAuNDcxIDQuMDA4LTY4LjQyNyAzLjY1Ny0yMS45NCAxMi41MzUtMzguNjUzIDI2LjYzOS00OS44MDEgMTQuMjc4LTExLjMxMyAzMS4xNjgtMjAuMTk1IDUwLjMyMS0yNi42MzggMTkuMzI0LTYuNjE0IDEwOC44Mi0zNy40MzYgMTI5LjM2Ni00Mi44MzEgMjAuNTQ2LTUuMzk3IDM4LjQ4My0xMy4wNTcgNTMuODAzLTIzLjE1NyAxMi41MzYtNy42NjEgMjIuMTEtMTUuMTUxIDI4LjkwMy0yMi4yOSA2Ljc4OS03LjEzNSAxMS42NjQtMTQuMjc1IDE0LjI3OS0yMS40MTMgMi42MTEtNy4xNCA0LjAwMy0xNC40NTQgNC4wMDMtMjIuMjkgMC03LjY2MS0wLjM1LTE2LjAxOC0wLjg3Mi0yNS4wNzEtMS4yMTctMTQuMjc5LTUuNzQ3LTI1LjI0Ni0xMy43NTMtMzIuOTA3LTguMDExLTcuNjYxLTE2Ljg5LTE1LjY3MS0yNy4xNjQtMjQuMDI5LTQuODc1LTQuMTc4LTguODc4LTkuOTI1LTEyLjUzNS0xNy40MTUtMy42NTctNy40ODUtNi43ODktMTQuOTctOS43NS0yMi42MzEtMi45NjEtOC44ODItNS45MjItMTguMTExLTguODgyLTI3LjUxLTQuMTc4LTEuMjIyLTguMzU3LTMuMTM2LTEyLjUzNS02LjI3My0zLjY1OC0yLjk1Ni03LjQ4Ni03LjEzNS0xMS42NjQtMTIuNTM1LTQuMTc4LTUuMzk1LTcuNjYxLTEzLjA1Ni0xMC43OTYtMjMuMTU2LTIuOTYxLTkuOTI2LTQuMDAzLTE5LjE1NC0yLjk2MS0yNy4xNiAwLjg3Mi04LjAxMiAyLjQzOS0xNC45NzYgNC44NzUtMjAuODk3IDIuMjY0LTYuNjE0IDUuOTIyLTEyLjcxIDEwLjc5Ni0xOC44MDMtMC41MjItMjIuNDYxIDAuNTIxLTQ1LjA5NiAzLjY1OC02Ny43MzIgMi4yNjQtMTguOTc5IDYuNjEzLTM5LjE3NSAxMi44ODEtNjAuNTkzIDYuMjcyLTIxLjQxOCAxNS4zMjYtNDAuNzQyIDI3LjE2NC01Ny44MDYgMTEuMzE4LTE2LjAxOCAyMy44NTMtMjkuMjU0IDM3Ljc4MS0zOS43IDEzLjkzMi0xMC40NDYgMjguMDM2LTE4LjYyOCA0Mi4zMS0yNC41NDkgMTQuMjc5LTUuOTIyIDI4LjU1OC05LjkyNiA0My4xODItMTIuNTM1QzQ5Mi41NTcgNjUuMzkyIDUwNi42NTkgNjQgNTIwLjQxMyA2NGMxNy4wNjQgMCAzMy43NzcgMi4wODkgNDkuNDQ5IDYuMjY3IDE1LjY3MiA0LjE3OSAzMC4yOTcgOS40MDQgNDQuMDUgMTYuMDE4IDEzLjU4MiA2LjYxOCAyNS45NDcgMTMuNzU3IDM2LjM5MiAyMS43NjggMTAuNzk2IDguMDA3IDE5LjMyNCAxNi4xOTMgMjUuOTQzIDI0LjU1IDE0LjggMTguOTc4IDI1Ljk0MyAzOS44NzEgMzMuNDMyIDYyLjY4MSA3LjQ4NiAyMi44MDcgMTIuNzEyIDQ0LjIyNSAxNS40OTYgNjQuNDIxIDMuNjU3IDIzLjY4MiA1LjA1MSA0Ny41MzYgNC41MjUgNzEuMjEzIDQuMTc4IDIuMjY0IDcuNDkgNS45MjIgOS43NSAxMC43OTYgMi4yNjUgNC4xNzggNC4zNTQgOS40MDQgNS43NDcgMTYuMDE4IDEuMzkzIDYuNjE3IDEuNTY5IDE0LjQ1NCAwLjM1IDI0LjAyOC0xLjc0MiAxMi41NC00LjcwMyAyMi4yOS04LjM1NiAyOS43NzUtNC4wMDggNy40ODYtOC4xODcgMTMuMjMyLTEyLjg4NiAxNy40MS01LjM5NiA0Ljg3NS0xMC45NzIgOC4wMTEtMTYuODkgOS43NTQtMi4yNjQgOS40LTUuMDUgMTguODAzLTguMDExIDI3LjUxLTIuOTYxIDcuNjYxLTYuNDQzIDE1LjMyMS0xMC4wOTYgMjIuNjMyLTQuMDA3IDcuNDg5LTguMTg2IDEzLjIzNS0xMi44ODUgMTcuNDE1LTUuNCA0Ljg3NS0xMC4yNzUgOC43MDMtMTUuMTQ5IDEyLjAxNC00Ljg3NSAzLjEzMS04Ljg3OSA2LjYxNC0xMi41MzYgMTAuMDk1LTMuNjU3IDMuNjU4LTYuNjE4IDcuODM3LTguODc4IDEyLjg4Ny0yLjI2NSA1LjA1LTQuMTc5IDExLjE0Mi01LjQwMSAxOC4yODItMS43MzkgOC44ODItMi4yNiAxNy45MzUtMS43MzkgMjcuMTY0IDAuNTIyIDkuMDUzIDIuOTYxIDE4LjQ1MyA3LjE0IDI3LjUxIDQuMTc4IDkuMDUzIDEwLjk2NyAxNy45MzEgMjAuNTQ2IDI2LjI4OCA5LjM5OSA4LjM1NyAyMi4yODUgMTUuNjcxIDM4LjMwMiAyMi4yOSAxNC44IDUuOTE3IDMxLjE2OCAxMS4xNDIgNDguNTc4IDE1LjQ5NiAxNy41ODUgNC41MjUgODguMTAzIDM0LjgyMSAxMDQuMjk2IDQxLjA4OCAxNi4xOTIgNi4yNzMgMzAuODIxIDE0LjEwNCA0My41MzIgMjMuNTA3IDEyLjM1OCAxMC40NDcgMjEuNDEyIDIyLjgxMSAyNi44MTIgMzguMzA4ek01MjYuMzM1IDkwMi4xOTRjMi45NjEgMCA3LjQ4NS0xLjkxNCAxMy43NTMtNS43NDcgNi4yNjctNC4wMDMgMTIuMzY0LTguMzU3IDE4LjI4Mi0xMy43NTMgNS45MjItNS4zOTcgMTEuMTQyLTEwLjc5OCAxNS40OTYtMTYuMDE4IDQuNTI4LTUuNDAxIDYuNjE3LTkuNDA0IDYuNjE3LTEyLjU0IDAtMy42NTMtMC44NzItOS43NS0yLjYxLTE4LjgwMy0xLjc0My04Ljg3OC00LjAwOC0xOC44MDQtNi4yNzMtMjkuMjQ5LTIuMjYtMTAuNzk4LTQuODc1LTIxLjI0My03LjEzNS0zMS42OTQtMi4yNjQtMTAuNDQ2LTQuNTI5LTE4LjgwMy02LjI2OC0yNS40MTcgNS45MTgtNC44NzkgMTAuOTY4LTkuOTI2IDE1LjE0Ni0xNS41MDEgNC4xNzgtNS43NDIgNi4yNjctMTEuNDg5IDYuMjY3LTE3LjQxIDAtNC44NzUtMS41NjctOS41NzUtNC44NzUtMTQuNjI1LTMuMTM2LTUuMDUtNi45NjQtOS4yMjktMTEuMTQyLTEyLjg4NS00Ljg3NS00LjE3OS05Ljc1LTguMzU3LTE1LjE1MS0xMi41MzZoLTQ0LjM5OWMtNS45MTggNC4xNzgtMTAuOTY4IDguMzU2LTE1LjE0NiAxMi41MzYtMy42NTcgMy42NTYtNi43OTMgNy42Ni05Ljc1IDEyLjUzNC0yLjk2MSA0Ljg3NS00LjUyOSA5LjQwNC00LjUyOSAxNC4yNzkgMCAzLjY1OCAyLjA4OSA4LjM1NyA2LjYxOCAxNC4yNzkgNC41MjUgNS45MjIgOS45MjUgMTEuNjY0IDE2LjM2NCAxNi44ODlhODY2LjM4MSA4NjYuMzgxIDAgMCAwLTcuMTM1IDIzLjE1N2MtMi45NjEgOS45MjUtNS43NDcgMjAuNTQ2LTguMzYyIDMxLjY4OS0yLjYxMSAxMC45NzEtNS4wNDUgMjEuNDE4LTcuMTM1IDMxLjE2OHMtMi45NjEgMTYuODg5LTIuOTYxIDIxLjc2M2MwIDIuOTYxIDIuMDg5IDcuMTQgNi42MTMgMTIuNTM1IDQuNTI5IDUuNDAxIDkuNzU0IDEwLjc5NiAxNi4wMjIgMTYuMDIyIDYuMjY3IDUuMzk1IDEyLjM2IDkuOTI1IDE4LjI4MiAxMy43NTMgNi4wOTYgMy40ODQgMTAuNjI1IDUuNTc0IDEzLjQxMSA1LjU3NHogbTAgMCIgcC1pZD0iMjUxMiIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjwvc3ZnPg=='
                    },
                    {
                        icon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM4MzQ4MTg0IiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjIyODg3IiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjIwMCIgaGVpZ2h0PSIyMDAiPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PC9zdHlsZT48L2RlZnM+PHBhdGggZD0iTTIxMi43IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0MyNjggNjE3LjYgMjQzLjMgNTkyLjggMjEyLjcgNTkyLjh6TTIxMi43IDY4NGMtMTkuOCAwLTM1LjktMTYtMzUuOS0zNS44IDAtMTkuOCAxNi4xLTM1LjkgMzUuOS0zNS45IDE5LjggMCAzNS45IDE2IDM1LjkgMzUuOUMyNDguNSA2NjggMjMyLjUgNjg0IDIxMi43IDY4NHoiIHAtaWQ9IjIyODg4IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PHBhdGggZD0iTTM4MSA1OTIuOGMtMzAuNiAwLTU1LjMgMjQuOC01NS4zIDU1LjQgMCAzMC42IDI0LjggNTUuMyA1NS4zIDU1LjMgMzAuNiAwIDU1LjQtMjQuOCA1NS40LTU1LjNDNDM2LjQgNjE3LjYgNDExLjYgNTkyLjggMzgxIDU5Mi44ek0zODEgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45QzQxNi45IDY2OCA0MDAuOSA2ODQgMzgxIDY4NHoiIHAtaWQ9IjIyODg5IiBmaWxsPSIjZmZmZmZmIj48L3BhdGg+PHBhdGggZD0iTTczNi41IDU5Mi44Yy0zMC42IDAtNTUuMyAyNC44LTU1LjMgNTUuNCAwIDMwLjYgMjQuOCA1NS4zIDU1LjMgNTUuMyAzMC42IDAgNTUuNC0yNC44IDU1LjQtNTUuM0M3OTEuOSA2MTcuNiA3NjcuMSA1OTIuOCA3MzYuNSA1OTIuOHpNNzM2LjUgNjg0Yy0xOS44IDAtMzUuOS0xNi0zNS45LTM1LjggMC0xOS44IDE2LjEtMzUuOSAzNS45LTM1LjkgMTkuOCAwIDM1LjkgMTYgMzUuOSAzNS45Qzc3Mi40IDY2OCA3NTYuMyA2ODQgNzM2LjUgNjg0eiIgcC1pZD0iMjI4OTAiIGZpbGw9IiNmZmZmZmYiPjwvcGF0aD48cGF0aCBkPSJNOTMyLjkgMzk1LjZjLTE3LjctMjEuOS0zNS45LTM3LjgtNzQuOC0zNy40LTguNSAwLjEtMjguMSA5LjQtMjguMSA5LjRzLTUwLjktNDQuNy03NC44LTQ2LjhMMTM3LjkgMzIwLjhjMCAwLTEzLjQtMi44LTE4LjcgOS40TDgxLjcgNDMzbDAgMTk2LjRjMCAwLTEuMyA5LjUgOS40IDkuNCAwLjQgMCAxNy45IDAgNDcuNCAwLjEgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNi42IDAgMTMuMiAwIDIwIDAgNC42LTM2LjkgMzYtNjUuNiA3NC4yLTY1LjYgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjYgNjkuOSAwIDE0MS42IDAgMjA3IDAgNC42LTM2LjkgMzYtNjUuNSA3NC4yLTY1LjUgMzguMiAwIDY5LjYgMjguNiA3NC4yIDY1LjUgNjMgMCA5NC4yIDAgOTQuMiAwczE5LjUtMjcgMTguNy05My41Yy0wLjYtNDkuNi00Mi42LTEyMy43LTc0LjgtMTU5IDAgMCAxNy42LTEzIDI4LjEtOS40IDEwLjUgMy43IDUwLjUgNjIuOSA2NS41IDU2LjFDOTQyLjMgNDMzIDk0My41IDQwOC43IDkzMi45IDM5NS42ek02MzMuNiA0NzAuNCAxMTkuMSA0NzAuNGwyOC4xLTExMi4yIDQ4Ni40IDBMNjMzLjYgNDcwLjR6TTg3Ni44IDQ5OC41bDAgNTYuMWMwIDAtNDkuNi0zNy4zLTkzLjUtNTYuMS02Mi0yNi42LTEzMS0yOC4xLTEzMS0yOC4xTDY1Mi4zIDM1OC4ybDExMi4yIDBjMCAwIDMyLjggMTguMSA2NS41IDU2LjFDODU2LjggNDQ1LjUgODc2LjMgNDk4LjggODc2LjggNDk4LjV6IiBwLWlkPSIyMjg5MSIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjwvc3ZnPg=='
                    },
                    {
                        icon: 'data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBzdGFuZGFsb25lPSJubyI/PjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+PHN2ZyB0PSIxNTk4MjM2MzE1MjgwIiBjbGFzcz0iaWNvbiIgdmlld0JveD0iMCAwIDEwMjQgMTAyNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHAtaWQ9IjQxMDkiIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iMjAwIiBoZWlnaHQ9IjIwMCI+PGRlZnM+PHN0eWxlIHR5cGU9InRleHQvY3NzIj48L3N0eWxlPjwvZGVmcz48cGF0aCBkPSJNODU4LjY1NiA4NjRIMTY1LjM0NEMxMDkuNDcyIDg2NCA2NCA4MTguNTYgNjQgNzYyLjY4OFYyNjEuMzEyQzY0IDIwNS40NCAxMDkuNDcyIDE2MCAxNjUuMzQ0IDE2MGg2OTMuMzEyQzkxNC41MjggMTYwIDk2MCAyMDUuNDQgOTYwIDI2MS4zMTJ2NTAxLjM3NkM5NjAgODE4LjU2IDkxNC41MjggODY0IDg1OC42NTYgODY0ek0xNjUuMzQ0IDIyNEMxNDQuNzM2IDIyNCAxMjggMjQwLjczNiAxMjggMjYxLjMxMnY1MDEuMzc2QzEyOCA3ODMuMjY0IDE0NC43MzYgODAwIDE2NS4zNDQgODAwaDY5My4zMTJDODc5LjI2NCA4MDAgODk2IDc4My4yNjQgODk2IDc2Mi42ODhWMjYxLjMxMkM4OTYgMjQwLjczNiA4NzkuMjY0IDIyNCA4NTguNjU2IDIyNEgxNjUuMzQ0ek04MDAgNDE2SDIyNGMtMTcuNjY0IDAtMzItMTQuMzM2LTMyLTMyczE0LjMzNi0zMiAzMi0zMmg1NzZjMTcuNjk2IDAgMzIgMTQuMzM2IDMyIDMycy0xNC4zMDQgMzItMzIgMzJ6TTMyMCA3MzZoLTk2Yy0xNy42NjQgMC0zMi0xNC4zMDQtMzItMzJzMTQuMzM2LTMyIDMyLTMyaDk2YzE3LjY2NCAwIDMyIDE0LjMwNCAzMiAzMnMtMTQuMzM2IDMyLTMyIDMyeiIgcC1pZD0iNDExMCIgZmlsbD0iI2ZmZmZmZiI+PC9wYXRoPjwvc3ZnPg=='
                    },
                ],
                parentAreaId: 0,
                initSearchFlag: true,
                initData: {},
                companyStatList: [],
                companyList: [],
                deliveryStatList: [],
                outDateStatList: [],
                eventStatList: [],
                busDeliverbg: [{
                        color: '#f76b1c',
                        bg1: '#fbb162',
                        bg2: '#eb5602'
                    },
                    {
                        color: '#429421',
                        bg1: '#77b708',
                        bg2: '#2e9205'
                    }
                ],
                busOrderbg: [{
                        color: '#cb1c3b',
                        bg1: '#ff406b',
                        bg2: '#cb1c3b'
                    },
                    {
                        color: '#242bb8',
                        bg1: '#de70dd',
                        bg2: '#242bb8'
                    }
                ],
                areaList: [],
                areaIdList: [],
            }
        },
        components: {
            TextProgess
        },
        created() {},
        mounted() {
            this.getInitData();
        },
        methods: {
            getInitData() {
                getInitData().then(resp => {
                    if (resp.data.code == 0) {
                        this.initData = resp.data.data.data;
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
                        this.getData(initAreaId)
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
                this.getData(data)

            },
            getData(areaId){
              this.getCompanyStat(areaId);
              this.getCompanyListData(areaId);
              this.getDeliveryStatData(areaId);
              this.getOutDateStatData(areaId);
              this.getEventStatData(areaId);

            },
            getCompanyStat(areaId) {
                getCompanyStat(areaId).then(resp => {
                    if (resp.data.code == 0) {
                        this.companyStatList = [];
                        this.companyStatList = resp.data.data.list
                    }

                })

            },
            getCompanyListData(areaId) {
                getCompanyListData(areaId).then(resp => {
                    if (resp.data.code == 0) {
                        this.companyList = [];
                        this.companyList = resp.data.data.list
                    }

                })

            },
            getDeliveryStatData(areaId) {
                getDeliveryStatData(areaId).then(resp => {
                    if (resp.data.code == 0) {
                        this.deliveryStatList = []
                        this.deliveryStatList = resp.data.data.list;
                        for (let i = 0; i < this.deliveryStatList.length; i++) {
                            for (let j = 0; j < this.deliveryStatList[i].orderData.length; j++) {
                                this.deliveryStatList[i].orderData[j].percent = getPercent(Number(this.deliveryStatList[
                                    i].orderData[j].secondCount), Number(this.deliveryStatList[i].orderData[
                                    j].firstCount))
                            }
                        }
                    }

                })


            },
            getOutDateStatData(areaId) {
                getOutDateStatByAreaIdData(areaId).then(resp => {
                    if (resp.data.code == 0) {
                        this.outDateStatList = [];
                        this.outDateStatList = resp.data.data.list;
                        for (let i = 0; i < this.outDateStatList.length; i++) {
                            for (let j = 0; j < this.outDateStatList[i].orderData.length; j++) {
                                this.outDateStatList[i].orderData[j].percent = getPercent(Number(this.outDateStatList[
                                    i].orderData[j].secondCount), Number(this.outDateStatList[i].orderData[
                                    j].firstCount))
                            }
                        }
                    }
                })

            },
            getEventStatData(areaId) {
                getEventStatData(areaId).then(resp => {
                    if (resp.data.code == 0) {
                        this.eventStatList = [];
                        this.eventStatList = resp.data.data.list;
                    }
                })
            },
            toDetail(id) {
                console.log(id)
                this.$router.push({
                    path: '/Customer',
                    query: {
                        id: id,
                    }
                })
            }



        },

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

    .mr5 {
        margin-right: 5px;
    }

    .mb10 {
        margin-bottom: 10px;
    }

    .font20 {
        font-size: 20px;
    }

    .box {
        padding: 15px 10px;
        border: 1px solid #e5e8ed;
        border-radius: 3px;
        background-color: #fff;
        font-size: 14px;
        margin-bottom: 10px;
        /* overflow: hidden; */
        box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
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

    .progressBg {
        width: calc(100% - 6px);
        height: 19px;
        background-color: #fff;
        padding: 3px;
        border: 1px solid #e5e8ed;
        margin: 5px 0;
    }

    .progressC {
        height: 19px;
        line-height: 19px;
        color: #fff;
    }

    .progressC span {
        padding: 0 5px;
        font-size: 12px;
    }

    .saleView {
        color: #fff;
        border: none;
        height: 50px;
        font-size: 12px;
        padding: 10px 15px;
        border-radius: 3px;
    }

    .saleView1 {
        background-color: #6f52fa;
        background: -webkit-linear-gradient(0deg, #6f52fa, #fd577f)
    }

    .saleView2 {
        background-color: #fd577f;
        background: -webkit-linear-gradient(0deg, #ff4568, #ffa55b)
    }

    .saleView3 {
        background-color: #094ff9;
        background: -webkit-linear-gradient(0deg, #094ff9, #00eff9)
    }

    .saleView4 {
        background-color: #ff9500;
        background: -webkit-linear-gradient(0deg, #ff9500, #ffc600)
    }

    .saleView .saleViewimg {
        width: 48px;
        height: 48px;
        border: 1px solid #fff;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.2);
        background-size: 30px 30px;
        background-position: center center;
        float: left;
        background-repeat: no-repeat;
        margin-right: 10px;
    }

    .saleBtn {
        border: none;
        color: #fff;
        background-color: #ff6b21;
        background: -webkit-linear-gradient(270deg, #ffaf4c, #ff7123)
    }

    .customerCar {
        padding: 10px;
        background-color: #f1f7fd;
        border: 1px solid #e5e8ed;
        border-radius: 3px;
        margin: 5px 0;
    }

    .el-progress--text-inside .el-progress-bar {
        background-color: #fff;
        padding: 3px;
        border: 1px solid #e5e8ed;
        border-radius: 100px;
    }

    .el-progress-bar__outer {
        background-color: #fff;
    }

    .selectArea {
        width: 150px;
        margin-right: 10px;
    }

    /deep/ .el-scrollbar .el-scrollbar__wrap {
        margin-bottom: 0 !important;
    }
</style>
<style>
    .el-scrollbar .el-scrollbar__wrap {
        margin-bottom: 0 !important;
    }
</style>
